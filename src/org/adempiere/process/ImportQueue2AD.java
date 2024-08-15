/******************************************************************************
 * Product: iDempiere - sub-project of ADempiere 				              *
 * Copyright (C) ALL GPL FOSS PROJECTS where taken				              *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.adempiere.process;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MCity;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.model.X_I_Order;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//remove // for testing
//import org.compiere.Adempiere;
//import org.compiere.model.MBPartner;
public class ImportQueue2AD extends SvrProcess{
	private static int cnt = 0;
	private static String p_Test = "N";
	private static String p_Link = "/queue/test";
	private static String p_LinkURL = "localhost";
	private static int p_AD_Org_ID = 0;
	private static int p_C_DocType_ID = 0;
	private static int p_Return_C_DocType_ID = 0;
	private static String trx;
	/**
	 * Called from Process in POS Synchronisation menu.
	 * @author Based on ActiveMQ and XML foss resource. Redhuan D. Oon http://www.red1.org/adempiere
	 * @param set from Process params in prepare()
	 * @throws Exception for ActiveMQ Stomp connection
	 * Importing of Orders from POS into I_Orders. Then separately from same menu
	 *  process the I_Orders into Sales Orders
	 */ 

	public void main(int AD_PInstance_ID) throws Exception {
//		parseXMLString(""); //uncomment for testing purpose. break and abort at next line
		cnt = 0;
		StompConnection connection = new StompConnection();
		connection.open(p_LinkURL,61613); //to reference openbravo.properties in ExternalSalesHelper class
		connection.connect("","");
		connection.subscribe(p_Link, Subscribe.AckModeValues.CLIENT);
		connection.begin("MQOrders");
		/*
		StompFrame message = connection.receive();
		System.out.println(message.getBody());
		
		if	(parseXMLString(message.getBody()))
			System.out.println("SUCCESS: Records equal to I_Orders");
		else
			System.out.println("ERROR: Records NOT equal to I_Orders");
		
		if (p_Test.equals("N")) //not to acknowledge but to allow for repeat read
			connection.ack(message, "MQOrders");
			*/

		//Bucle para leer todos los mensajes de la cola
		while(true){
			StompFrame message = null;
			try{
				message = connection.receive();		
			} catch(Exception e){
				break;
			}		
			//System.out.println(message.getBody());
			/*
			X_AD_PInstance_Para para = new Query(Env.getCtx(), X_AD_PInstance_Para.Table_Name, X_AD_PInstance_Para.COLUMNNAME_AD_PInstance_ID+"= "
						+AD_PInstance_ID+" AND ParameterName = 'AD_Org_ID'", null).first();
			p_AD_Org_ID = para.getP_Number().intValue();
			*/
			if	(parseXMLString(message.getBody())){
				System.out.println("SUCCESS: Records equal to I_Orders");
				if (p_Test.equals("N")) //not to acknowledge but to allow for repeat read
					connection.ack(message, "MQOrders");
			}else
				System.out.println("ERROR: Records NOT equal to I_Orders");
		}
		connection.commit("MQOrders");
		connection.disconnect();
		}
	
	private boolean parseXMLString(String message) throws  SAXException, ParserConfigurationException, IOException {
//uncomment for testing, together with above
//		message = "<?xml version=\"1.0\"?><entityDetail><type>I_Order</type><BPartnerValue>Joe Block</BPartnerValue><detail><DocTypeName>POS Order</DocTypeName><AD_Client_ID>11</AD_Client_ID><AD_Org_ID>11</AD_Org_ID><DocumentNo>40</DocumentNo><DateOrdered>2011-09-08 14:52:52.152</DateOrdered><ProductValue>Rake-Metal</ProductValue><QtyOrdered>1.0</QtyOrdered><PriceActual>12.0</PriceActual><TaxAmt>0.0</TaxAmt></detail></entityDetail>";
//		Adempiere.startupEnvironment(true);	
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();		
		/**Count for imported Orders*/
		int count = 0;
		DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(new ByteArrayInputStream(message.getBytes()));
	    Element docEle = doc.getDocumentElement(); 
	    NodeList records = docEle.getElementsByTagName("detail");
	    NodeList recordsBP = docEle.getElementsByTagName("BPartner");
	    MUser salesrepuser = null;
	    int opos_line=0;
	    int temporg=0;
	    int tempm_product_id=0;
	    String  temp_productvalue=null;
	    String tempdocumentno=null;
	    /*query para traer Nombre del pais de la organizacion para asignarlo por defecto al cliente
		String sql = "SELECT a.C_Country_ID"
				+ " FROM C_Country a" 
				+ " JOIN C_Location b ON a.C_Country_ID=b.C_Country_ID"
				+ " JOIN AD_Orginfo c ON b.C_Location_ID=c.C_Location_ID"
				+ " WHERE C.ad_org_ID = ?"; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		*/
		String countalloc = "";
	    	for(int i = 0 ; i < records.getLength();i++) {
				//check for detail = POS Order only. Other data will be handle in later brackets
	    	    if (!records.item(i).getFirstChild().getTextContent().equals("POS Order"))
	    	    	continue;	    	    
	    	    
	    	    X_I_Order order = new X_I_Order (Env.getCtx (), 0, trx);
    			BigDecimal creditnoteAmount = BigDecimal.ZERO;
	    		String Org_value = ""; 
	    	    //order.setAD_Org_ID(p_AD_Org_ID);    
		    	NodeList details = records.item(i).getChildNodes();
		    	for(int j = 0 ; j < details.getLength();j++) {
		    		Node n = details.item(j);
		    		String column = n.getNodeName();
		    		MOrg Org = null;

		    		//Agregar valor del parametro C_Doctype_ID en el XML
	    			//if (p_C_DocType_ID > 0) {
	    			//	order.setC_DocType_ID(p_C_DocType_ID);
	    			//}
	    			
		    		//get Customer Name
		    		//if (column.equals(X_I_Order.COLUMNNAME_BPartnerValue))
		    	    	//order.setBPartnerValue(n.getTextContent());
		    		
		    		if (column.equals("AD_Org_ID")) {
		    			order.setAD_Org_ID(Integer.parseInt(n.getTextContent()));
		    			Org = new MOrg(Env.getCtx(), Integer.parseInt(n.getTextContent()), trx);
		    			MWarehouse warehouse = new Query(Env.getCtx(), MWarehouse.Table_Name, 
		    					"M_Warehouse_ID IN (SELECT M_Warehouse_ID FROM AD_Orginfo WHERE AD_Org_ID = "+Org.getAD_Org_ID()+")", get_TrxName()).first();
		    			order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		    			Org_value = Org.getValue();
		    			temporg=Integer.parseInt(n.getTextContent());
		    		}
		    		
		    		else  if (column.equals(X_I_Order.COLUMNNAME_DateOrdered)) 
		    			order.setDateOrdered(Timestamp.valueOf(n.getTextContent()));
		    		
		    		else if (column.equals(X_I_Order.COLUMNNAME_AD_Client_ID))
		    			order.set_ValueOfColumn(X_I_Order.COLUMNNAME_AD_Client_ID, Integer.parseInt(n.getTextContent()));
		    		
		    		else if (column.equals("POSLocatorName")){
		    			MWarehouse warehouse = new Query(Env.getCtx(), MWarehouse.Table_Name, 
		    					"name = trim(?)", get_TrxName()).setParameters(n.getTextContent()).first();
		    			order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		    			//order.set_CustomColumn("LocatorName", n.getTextContent());

		    		}
		    			
		    	    
		    	//	else if (column.equals(X_I_Order.COLUMNNAME_DocTypeName))
		    		//	order.setDocTypeName((n.getTextContent()));		
		    					    		
		    		else if (column.equals(X_I_Order.COLUMNNAME_DocumentNo)){
		    			
		    			order.setDocumentNo((Org_value+"-"+n.getTextContent()));
		    			tempdocumentno=Org_value+"-"+n.getTextContent();
		    		
		    		}else if (column.equals(X_I_Order.COLUMNNAME_PriceActual))
		    			order.setPriceActual(BigDecimal.valueOf(Double.parseDouble(n.getTextContent())));
		    		
		    		
		    		else if (column.equals(X_I_Order.COLUMNNAME_M_Product_ID)){
		    			try{
		    				MProduct temp = new MProduct(getCtx(), Integer.parseInt(n.getTextContent()), get_TrxName());
			    			if(temp.getValue()!=null){
			    				order.setM_Product_ID(Integer.parseInt(n.getTextContent()));
			    				tempm_product_id=Integer.parseInt(n.getTextContent());
			    			}
		    			}catch(Exception ex){
		    				
		    			}
		    
		    			//order.setProductValue((n.getTextContent()));
		    		}
		    		
		    		else if (column.equals(X_I_Order.COLUMNNAME_ProductValue)){
		    			/*try{
		    				MProduct temp = new Query(getCtx(),MProduct.Table_Name, "Value = '"+n.getTextContent()+"'", get_TrxName()).first();
		    			if(temp.getValue()!=null){
		    				order.setM_Product_ID(temp.getM_Product_ID());
		    				tempm_product_id=temp.getM_Product_ID();
		    			}else
		    				order.setProductValue((n.getTextContent()));
		    			//tempm_product_id=Integer.parseInt(n.getTextContent());
		    			//order.setProductValue((n.getTextContent()));
		    			}
		    			catch(Exception e){
		    				log.log(Level.SEVERE, "Unknown Product: " + n.getTextContent());
		    			}*/
		    			temp_productvalue = n.getTextContent().replace("\t", "");
		    			order.setProductValue(temp_productvalue);
		    			
		    		}
		    		
		    		else if (column.equals(X_I_Order.COLUMNNAME_QtyOrdered)){
		    			order.setQtyOrdered(BigDecimal.valueOf(Double.parseDouble(n.getTextContent())));
		    			if (order.getQtyOrdered().compareTo(Env.ZERO) < 0){
		    				order.setC_DocType_ID(p_Return_C_DocType_ID);
		    				order.setDocTypeName(("Return POS Order"));
		    			}		    				
		    			else {
		    				order.setDocTypeName(("POS Order"));
		    				order.setC_DocType_ID(p_C_DocType_ID);
		    			}
		    				
		    			order.setQtyOrdered(order.getQtyOrdered().abs());
		    		}
		    	    
		    		//TODO red1 ImportOrder does not do anything with TaxAmt, maybe up to preset Tax_ID
		    		else if (column.equals(X_I_Order.COLUMNNAME_TaxAmt))
		    			order.setTaxAmt(BigDecimal.valueOf(Double.parseDouble(n.getTextContent())).abs());
		    		
		    		else if (column.equals(X_I_Order.COLUMNNAME_C_Tax_ID)){
		    			if (Integer.parseInt(n.getTextContent()) == 1000013)
		    				order.setC_Tax_ID(1000013);
		    			else if (Integer.parseInt(n.getTextContent()) == 1000014)
		    				order.setC_Tax_ID(1000014);
		    			else if (Integer.parseInt(n.getTextContent()) == 1000015)
		    				order.setC_Tax_ID(1000015);
		    			else
		    				order.setC_Tax_ID(1000001);
		    			/*else
		    				order.setC_Tax_ID(Integer.parseInt(n.getTextContent()));*/
		    		}
		    			
		    		
		    		/*
		    		else if (column.equals("paymentType")){
		    			System.out.println("<<"+n.getTextContent()+">>");
		    			if (n.getTextContent().equals("paperout")){
		    			order.set_ValueOfColumn("PaymentRule", "P");
		    			order.set_ValueOfColumn("C_PaymentTerm_ID", "1000001");
		    			}
		    			else {
		    			order.set_ValueOfColumn("PaymentRule", "B");
		    			order.set_ValueOfColumn("C_PaymentTerm_ID", "1000000");
		    			}
		    		}	*/	    	
		    		//TODO red1 lookup SalesRep for ID not implemented in ImportOrder, thus..
		    		//else if (column.equals(X_I_Order.COLUMNNAME_SalesRep_ID))
		    			//order.setSalesRep_ID(Integer.parseInt(n.getTextContent()));
		    		//red1 - ID should match exactly in ERP Server instance. 
		    		
		    		//Implementacion del campo Representante comercial haciendo el match con el nombre de usuario en el punto de venta
		    		else if (column.equals("UserName"))
		    		{
			    		try
			    		{
			    			
			    			salesrepuser = new Query(Env.getCtx(),MUser.Table_Name,MUser.COLUMNNAME_Name+"=?",trx)
			    					.setParameters(n.getTextContent())
			    					.first();	
			    			
			    			if (salesrepuser != null)
			    				order.setSalesRep_ID(salesrepuser.getAD_User_ID());
			    	   	 }catch (Exception ex) { 
			    			    ex.printStackTrace(); 
			    			    System.out.println("ERROR: El Usuario "+n.getTextContent()+" no Existe");
			    			  }

		    		}

		    		//Buscar Representante comercial por AD_User_ID desde el POS
		    		else if (column.equals("AD_User_ID"))
		    		{
			    		try
			    		{	
			    			if(n.getTextContent()!=null)
			    			salesrepuser = new Query(Env.getCtx(),MUser.Table_Name,MUser.COLUMNNAME_AD_User_ID+"=?",trx)
			    					.setParameters(Integer.parseInt(n.getTextContent()))
			    					.first();
			    			if (salesrepuser != null)
			    				order.setSalesRep_ID(salesrepuser.getAD_User_ID());
			    	   	 }catch (Exception ex) { 
			    			    ex.printStackTrace(); 
			    			    System.out.println("ERROR: El Usuario "+n.getTextContent()+" no Existe");
			    			  }
		    		}
		    		//Fin Campo Representante comercial
		    		else if (column.equals("C_Country_ID")){
		    			order.setC_Country_ID(Integer.parseInt(n.getTextContent()));		    			
		    		}
		    		else if (column.equals("C_Region_ID")){
		    			order.setC_Region_ID(Integer.parseInt(n.getTextContent()));
		    		}
		    		else if (column.equals("C_City_ID")){
		    			MCity City = new Query(getCtx(), MCity.Table_Name, "C_City_ID = "+(Integer.parseInt(n.getTextContent())), get_TrxName()).first();
		    			order.setCity(City.getName());
		    		}
		    		//Campo Nombre de Cliente
		    		else if (column.equals("BPartner"))
		    		{
		    			NodeList detailsBP = recordsBP.item(i).getChildNodes();
		    			for(int jj = 0 ; jj < detailsBP.getLength();jj++) {
				    		Node nn = detailsBP.item(jj);
				    		String columnBP = nn.getNodeName();
		    				if (columnBP.equals("BPartnerName"))
				    			{
		    					//order.set_CustomColumn("Name", nn.getTextContent());
		    					order.setName(nn.getTextContent());
		    					order.setContactName(nn.getTextContent());
				    			}
		    					
		    				else if (columnBP.equals("BPartnerValue"))
		    				{
		    					order.set_CustomColumn("TaxID", nn.getTextContent());
		    					order.setBPartnerValue(nn.getTextContent());
		    					
		    				}				    			
		    				else if (columnBP.equals("BPartnerAddress"))
				    			order.setAddress1(nn.getTextContent());
		    				/*else if (columnBP.equals("BPartnerCity"))
		    				{
		    					
		    					//Traer Pais
		    					try {
		    						pstmt = DB.prepareStatement(sql, trx);
									pstmt.setInt(1, Org_id);
									rs = pstmt.executeQuery();
									rs.next();
									order.setC_Country_ID(rs.getInt("C_Country_ID"));
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}finally {
									DB.close(rs);
							    }
		    				}*/
		    					
		    				//agregamos la regla de pago como caja de punto de venta por defecto
		    				order.set_ValueOfColumn("PaymentRule", "B");
				    		String textBP = nn.getTextContent();
				    		System.out.println( "Node =  " + columnBP + " Text = " + textBP);
		    			}
		    		}		    
    				else if (column.equals("Email")&&(n.getTextContent()!=null || !n.getTextContent().isEmpty()))
    						order.setEMail(n.getTextContent());
    				else if (column.equals("Phone")&&(n.getTextContent()!=null || !n.getTextContent().isEmpty()))
    						order.setPhone(n.getTextContent());
    				else if (column.equals("Phone2")&&(n.getTextContent()!=null || !n.getTextContent().isEmpty()))
    						order.set_CustomColumn("Phone2", n.getTextContent());
    				else if (column.equals("BPartnerGroup")&&(n.getTextContent()!=null || !n.getTextContent().isEmpty()))
    						order.set_CustomColumn("BPartnerGroupValue", n.getTextContent());
    				else if (column.equals("BPartnerBirthday")&&(n.getTextContent()!=null || !n.getTextContent().isEmpty()))
    						order.set_CustomColumn("Birthday", Timestamp.valueOf(n.getTextContent()));

    				else if (column.equals("BPartnerBirthday")){
    					if ((n.getTextContent()!=null || !n.getTextContent().isEmpty()))
    						order.set_CustomColumn("Birthday", Timestamp.valueOf(n.getTextContent()));
    					else if(MSysConfig.getValue("POSDefaultBirtday")==null)
    						order.set_CustomColumn("Birthday", Timestamp.valueOf(MSysConfig.getValue("POSDefaultBirtday")));
    				}   				
		    		else if (column.equals("creditnoteAmount"))
		    		{
		    			creditnoteAmount = BigDecimal.valueOf(Double.parseDouble(n.getTextContent())).negate();
		    		}
		    		else if (column.equals("fiscalprint_serial")){
	    				order.set_ValueOfColumn("fiscalprint_serial", n.getTextContent());
		    		}
		    		else if (column.equals("fiscal_invoicenumber")){
	    				order.set_ValueOfColumn("fiscal_invoicenumber", n.getTextContent());
		    		}
		    		else if (column.equals("fiscal_zreport")){
	    				order.set_ValueOfColumn("fiscal_zreport", n.getTextContent());
		    		}
		    		else if (column.equals("line")){
		    			opos_line = Integer.parseInt(n.getTextContent());
	    				order.set_ValueOfColumn("opos_line", Integer.parseInt(n.getTextContent()));
		    		}
		    		else if (column.equals("numberoflines")){
	    				order.set_ValueOfColumn("opos_numberoflines", Integer.parseInt(n.getTextContent()));
		    		}
		    		String text = n.getTextContent();
		    		System.out.println( "Node =  " + column + " Text = " + text);
		    		
		    	}
		    	//chequeamos que no exista esta informacion
		    	String sql2 = "Select documentno from i_order where ad_org_id=? and documentno=? and opos_line=? and (m_product_id=? OR " +
		    			"m_product_id = (SELECT m_product_id FROM m_product WHERE Value = ?) OR ProductValue = ?)" +
		    			" UNION (select o.documentno doc from c_orderline ol join c_order o on ol.c_order_id=o.c_order_id " +
		    			" WHERE o.documentno= ? and o.c_doctype_id = ?)"; 
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				boolean exist=false;
				try {
					pstmt2 = DB.prepareStatement(sql2, trx);
					pstmt2.setInt(1, temporg);
					pstmt2.setString(2, tempdocumentno);
					pstmt2.setInt(3, opos_line);
					pstmt2.setInt(4, tempm_product_id);
					pstmt2.setString(5, temp_productvalue);
					pstmt2.setString(6, temp_productvalue);
					pstmt2.setString(7, tempdocumentno);
					pstmt2.setInt(8, p_C_DocType_ID);
					rs2 = pstmt2.executeQuery();
					if(rs2.next()){
						System.out.println( "Linea ya importada. No se importará nuevamente ");
						log.log(Level.SEVERE, "Linea ya importada. No se importará nuevamente: " +tempdocumentno +" ORG "+temporg +" Product "+tempm_product_id);
						exist=true;
					}
						
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.log(Level.SEVERE, "Error en la consulta sql para el documento: " +tempdocumentno +" ORG "+temporg +" Product "+tempm_product_id);
				}finally {
					DB.close(rs2, pstmt2);
					rs2 = null;
					pstmt2 = null;
			    }
		    	
		    	//System.out.println( "Node =  " + X_I_Order.COLUMNNAME_C_DocType_ID + " Text = " + p_C_DocType_ID);  //Mostrar doctype_id en la respuesta
		    	if(!exist){
		    		if (!order.save()) return false; //saves each I_Order line for each Detail XML
	    			if( creditnoteAmount.compareTo(BigDecimal.ZERO)!=0){
	    				MAllocationHdr Allocation = new MAllocationHdr(Env.getCtx(), 0, trx);
	    				order.set_ValueOfColumn("PaymentRule", "P");
	    				Allocation.setAD_Org_ID(order.getAD_Org_ID());
	    				Allocation.setDescription(order.getDocumentNo());
	    				Allocation.setC_Currency_ID(MClient.get(getCtx(), getAD_Client_ID()).getC_Currency_ID());
				    	List<MInvoice> queryi = null;
				    	if(countalloc.compareTo(order.getDocumentNo())!=0 && creditnoteAmount.compareTo(BigDecimal.ZERO)!=0){
					    	countalloc = order.getDocumentNo();
					    	Allocation.saveEx();
							order.setDescription(String.valueOf(Allocation.getC_AllocationHdr_ID()));
							order.save();
							MBPartner bpartner = new Query(Env.getCtx(), MBPartner.Table_Name, "value = ?",trx).
									setParameters(order.getBPartnerValue()).first();
							if (bpartner != null){
								queryi = new Query(Env.getCtx(),MInvoice.Table_Name,MInvoice.COLUMNNAME_C_DocTypeTarget_ID+
									 " IN (SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType = 'ARC') AND "+
									 MInvoice.COLUMNNAME_C_BPartner_ID+"=? AND "+MInvoice.COLUMNNAME_IsPaid+"='N'",trx)
								.setParameters(bpartner.getC_BPartner_ID()).setOrderBy(MInvoice.COLUMNNAME_DateInvoiced+" DESC")
								.list();
							 
							 if (queryi != null)
								 for (MInvoice invoice:queryi) {
			    					 if(creditnoteAmount == BigDecimal.ZERO)
			    						 break;
			    					 //if(invoice.getGrandTotal().compareTo(creditnoteAmount) == -1){
			    					 if(invoice.getOpenAmt().compareTo(creditnoteAmount) == 1){
			    						 MAllocationLine AllocationLine = new MAllocationLine(Env.getCtx(), 0, trx);
			    						 AllocationLine.setAD_Org_ID(order.getAD_Org_ID());
			    						 AllocationLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
			    						 //AllocationLine.setAmount(invoice.getGrandTotal().negate());
			    						 AllocationLine.setAmount(invoice.getOpenAmt());
			    						 AllocationLine.setC_BPartner_ID(bpartner.getC_BPartner_ID());
			    						 AllocationLine.setC_AllocationHdr_ID(Allocation.getC_AllocationHdr_ID());
			    						 AllocationLine.saveEx();
			    						 //creditnoteAmount = creditnoteAmount.subtract(invoice.getGrandTotal());
			    						 creditnoteAmount = creditnoteAmount.subtract(invoice.getOpenAmt());
			    					 }
			    					 else{
			    						 MAllocationLine AllocationLine = new MAllocationLine(Env.getCtx(), 0, trx);
			    						 AllocationLine.setAD_Org_ID(order.getAD_Org_ID());
			    						 AllocationLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
			    						 AllocationLine.setAmount(creditnoteAmount);
			    						 AllocationLine.setOverUnderAmt(invoice.getOpenAmt().subtract(creditnoteAmount));
			    						 AllocationLine.setC_AllocationHdr_ID(Allocation.getC_AllocationHdr_ID());
			    						 try{
			    						 AllocationLine.saveEx();
			    						 }
			    						 catch (Exception e){
			    							 System.out.println("Error....");		 
			    						 }finally{
			    							 
			    						 }
			    						 creditnoteAmount = BigDecimal.ZERO; 
			    					 }
								 }
							}
				    	}	    			
				    }
			    	cnt++;
		    	}	
		    	
		    	count++;
		    } 
	    	//{
	    		//to handle import of other data such as payments or returns
	    	//}
			    
	    return (records.getLength()==count); //ensure the import orders are correct count to XML details
		
	}

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();			
			if (name.equals("Test"))
				p_Test = (String)para[i].getParameter();
			else if (name.equals("Link"))
				p_Link = (String)para[i].getParameter();
			else if (name.equals("LinkURL"))
				p_LinkURL = (String)para[i].getParameter();
			else if (name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();	
			else if (name.equals("Return_C_DocType_ID"))
				p_Return_C_DocType_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = para[i].getParameterAsInt();
			else
			{
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		trx = get_TrxName();
		main(getAD_PInstance_ID());
		//return "Records imported? Lost count, maybe "+Integer.toString(cnt);
		return "Cantidad de Registros Importados: "+Integer.toString(cnt);		
	}
}