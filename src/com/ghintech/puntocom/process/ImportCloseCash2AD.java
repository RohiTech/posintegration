package com.ghintech.puntocom.process;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.adempiere.exceptions.AdempiereException;
import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.compiere.model.MBankAccount;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPriceList;
import org.compiere.model.MTax;
import org.compiere.model.MUser;
import org.compiere.model.MWarehouse;
import org.compiere.model.Query;
import org.compiere.model.X_I_Invoice;
import org.compiere.model.X_I_Payment;
import org.compiere.process.ProcessInfoLog;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ImportCloseCash2AD extends SvrProcess {
	//Creacion de variables para almacenar parametros de idempiere
	private static int cnt = 0;							//Contador de registros importados
	private static String p_Test = "N";					//Es una Sincronizacion de prueba no envia respuesta al activemq de mensaje leido
	private static String p_Link = "/queue/ClosedCash";	//Nombre de la cola de Cierre de Caja
	private static String p_LinkURL = "localhost";		//URL del servidor ActiveMQ
	private static int p_C_Charge_ID = 0;
	private static int p_C_DocTypeInvoice_ID = 0;
	private static int p_C_DocTypePayment_ID = 0;
	private static int Difference = 0; 
	private static int AD_Org_ID = 0;
	private static int invoice_created = 0;
	private static int payment_created = 0;
	// Bucle para leer todos los mensajes de la cola
/**
		while(true){
			StompFrame message = null;
			try{
				message = connection.receive();		
			} catch(Exception e){
				break;
			}		
			//System.out.println(message.getBody());
			if	(parseXMLString(message.getBody()))
				System.out.println("SUCCESS: Records equal to I_Invoice");
			else
				System.out.println("ERROR: Records not equal to I_Invoice");
		
			if (p_Test.equals("N")) //not to acknowledge but to allow for repeat read
				connection.ack(message, "MQOrders");
		}	
		connection.commit("MQOrders");
		connection.disconnect();

		}
	*/
	private boolean createInvoice(NodeList details){
		X_I_Invoice invoice = new X_I_Invoice (Env.getCtx (), 0, null);
		invoice.set_ValueOfColumn("AD_PInstance_ID", getProcessInfo().getAD_PInstance_ID());
		System.out.println("factura: "+getProcessInfo().getAD_PInstance_ID());
    	invoice.setDateInvoiced(Env.getContextAsDate(getCtx(), "#Date"));		
    	invoice.setDateAcct(Env.getContextAsDate(getCtx(), "#Date"));			
		MTax tax = new Query(Env.getCtx(),MTax.Table_Name,MTax.COLUMNNAME_AD_Client_ID+"="+
				Env.getAD_Client_ID(Env.getCtx())+" AND "+MTax.COLUMNNAME_IsTaxExempt+"='Y' AND "+MTax.COLUMNNAME_Rate+"=0",null).first();
		if(tax == null){
			getProcessInfo().setError(true);
			getProcessInfo().addLog(new ProcessInfoLog(getProcessInfo().getAD_Process_ID(), new Timestamp(System.currentTimeMillis()), null, "Falta Crear Tasa de Impuesto Exenta en el sistema"));
			log.log(Level.SEVERE, "Falta Crear Tasa de Impuesto Exenta en el sistema");
			return false;
		}
		invoice.setC_Tax_ID(tax.getC_Tax_ID());
		//QUITAR TIPO DOCUMENTO ESTATICO   
		//invoice.setC_DocType_ID(1000051);   //otras cuentas por cobrar
		invoice.setC_DocType_ID(p_C_DocTypeInvoice_ID);
		MPriceList pricelist = new Query(Env.getCtx(),MPriceList.Table_Name,MPriceList.COLUMNNAME_AD_Client_ID+"="+
				Env.getAD_Client_ID(Env.getCtx())+" AND "+MPriceList.COLUMNNAME_IsDefault+"='Y'",null).first();
		if(pricelist == null){
			getProcessInfo().setError(true);
			getProcessInfo().addLog(new ProcessInfoLog(getProcessInfo().getAD_Process_ID(), new Timestamp(System.currentTimeMillis()), null, "No existe lista de precios por defecto"));
			log.log(Level.SEVERE, "No Existe lista de precios por defecto");
			return false;
		}
		invoice.setM_PriceList_ID(pricelist.get_ID());
		invoice.setQtyOrdered(new BigDecimal(1)); //valor 1 por defecto para la cantidad de la diferencia
		//invoice.setC_Charge_ID(p_C_Charge_ID);
    	for(int j = 0 ; j < details.getLength();j++) {
    		Node n = details.item(j);
    		String column = n.getNodeName();

    		if (column.equals("DateStart")) {
    			//System.out.print("La fecha es: "+Timestamp.valueOf(n.getTextContent())+" o eso creo.");
    	    	//invoice.setDateInvoiced(Timestamp.valueOf(n.getTextContent()));
    	    	//invoice.setDateAcct(Timestamp.valueOf(n.getTextContent()));
    	    }
    	    else if (column.equals(X_I_Invoice.COLUMNNAME_AD_Client_ID))
    	    	invoice.set_ValueOfColumn(X_I_Invoice.COLUMNNAME_AD_Client_ID, Integer.parseInt(n.getTextContent()));
    		else if (column.equals("POSLocatorName")){
				invoice.setAD_Org_ID(AD_Org_ID);
    		}
    		else if (column.equals("UserName")){
    			MUser User = new Query(Env.getCtx(),MUser.Table_Name,MUser.COLUMNNAME_AD_User_ID+"=?",null)
				.setParameters(Integer.parseInt(n.getTextContent()))
				.first();
    			invoice.setAD_User_ID(User.getAD_User_ID());
    			invoice.setC_BPartner_ID(User.getC_BPartner_ID());
    			invoice.setC_BPartner_Location_ID(User.getC_BPartner_Location_ID());
    			invoice.set_ValueOfColumn("Description","Cuenta por Cobrar por descuadre de caja a Vendedor: "+User.getName());	
    		}
    		else if (column.equals("Difference"))
    			invoice.setPriceActual(BigDecimal.valueOf(Double.parseDouble(n.getTextContent())).abs());
    		//String text = n.getTextContent();
    		//System.out.println( "Node =  " + column + " Text = " + text);
    	}
    	invoice.save(); //saves each I_Order line for each Detail XML
    	invoice_created++;
    	//System.out.println(invoice.getI_Invoice_ID()+" "+invoice.getDateInvoiced()+" "+invoice.getC_DocType().getPrintName() + " " + invoice.getDocumentNo()+
    		//	" "+X_I_Invoice.Table_ID+" "+invoice.getI_Invoice_ID());
		addBufferLog(invoice.getI_Invoice_ID(), invoice.getDateInvoiced(),
				null, invoice.getC_DocType().getPrintName() + " " + invoice.getDocumentNo(),
				X_I_Invoice.Table_ID, invoice.getI_Invoice_ID());
		return true;
	}
	
	private boolean createPayment(NodeList details,int OrgBPartner){
		X_I_Payment Payment = new X_I_Payment (Env.getCtx (), 0, null);
		Payment.set_ValueOfColumn("AD_PInstance_ID", getProcessInfo().getAD_PInstance_ID());
		System.out.println("pago: "+getProcessInfo().getAD_PInstance_ID());
		MBankAccount BankAccount =  new Query(Env.getCtx(), MBankAccount.Table_Name, MBankAccount.COLUMNNAME_BankAccountType+
				"= 'B' AND "+MBankAccount.COLUMNNAME_IsDefault+"='Y' AND "+MBankAccount.COLUMNNAME_AD_Org_ID+
				"="+AD_Org_ID,null).first();
		if(BankAccount == null){
			log.log(Level.SEVERE, "No Existe Caja Predeterminada para la Organización: "+AD_Org_ID);
			return false;
		}
		Payment.setC_BankAccount_ID(BankAccount.get_ID());
		Payment.setC_Charge_ID(p_C_Charge_ID);
		Payment.setC_DocType_ID(p_C_DocTypePayment_ID);
		Payment.setIsReceipt(true);
		Payment.setTenderType("A");
		Payment.setTrxType("S");
		//Payment.set_ValueOfColumn("Description", "Ingreso Creado por diferencia en Vendedor: ");
    	for(int j = 0 ; j < details.getLength();j++) {
    		Node n = details.item(j);
    		String column = n.getNodeName();
    		if (column.equals("datestart")) {
    	    	Payment.setDateTrx(Timestamp.valueOf(n.getTextContent()));
    	    	Payment.setDateAcct(Timestamp.valueOf(n.getTextContent()));
    	    }
    	    else if (column.equals(X_I_Payment.COLUMNNAME_AD_Client_ID))
    	    	Payment.set_ValueOfColumn(X_I_Payment.COLUMNNAME_AD_Client_ID, Integer.parseInt(n.getTextContent()));
    		else if (column.equals("POSLocatorName")){
				Payment.setAD_Org_ID(AD_Org_ID);
    		}
    		else if (column.equals("UserName")){
    			MUser User = new Query(Env.getCtx(),MUser.Table_Name,MUser.COLUMNNAME_AD_User_ID+"=?",null)
				.setParameters(Integer.parseInt(n.getTextContent()))
				.first();
    			Payment.set_ValueOfColumn("Description","Ingreso Creado por diferencia en Vendedor: "+User.getName());
    			//NO User name needed the payment is generated to the business partner of the org
    			Payment.setC_BPartner_ID(OrgBPartner);
    		}
    		else if (column.equals("Difference"))
    			Payment.setPayAmt(BigDecimal.valueOf(Double.parseDouble(n.getTextContent())).abs());
    		//String text = n.getTextContent();
    		//System.out.println( "Node =  " + column + " Text = " + text);
    	}
    	Payment.saveEx(); //saves each I_Order line for each Detail XML
    	payment_created++;
    	//System.out.println(Payment.getI_Payment_ID()+" "+Payment.getDateTrx()+" "+Payment.getC_DocType().getPrintName() + " " + Payment.getDocumentNo()+
    		//	" "+X_I_Invoice.Table_ID+" "+Payment.getI_Payment_ID());
		addBufferLog(Payment.getI_Payment_ID(), Payment.getDateTrx(),
				null, Payment.getC_DocType().getPrintName() + " " + Payment.getDocumentNo(),
				X_I_Payment.Table_ID, Payment.getI_Payment_ID());
		return true;
	}
	
	private boolean parseXMLString(String message) throws  SAXException, ParserConfigurationException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new ByteArrayInputStream(message.getBytes()));
		Element docEle = doc.getDocumentElement(); 
		NodeList records = docEle.getElementsByTagName("detail");
		int difference = 0;
		cnt = 0;
		try{
    	for(int i = 0 ; i < records.getLength();i++) {
			//check for detail = POS Order only. Other data will be handle in later brackets
    	    if (!records.item(i).getFirstChild().getTextContent().equals("Closed Cash")){
    	    	cnt++;
    	    	continue;
    	    }
    	    String TypeCloseCash = null;
	    	NodeList details = records.item(i).getChildNodes();
	    	//int OrgID = 0;
	    	int OrgBPartner = 0;
	    	for(int ii = 0 ; ii < details.getLength();ii++) {
	    		Node node = details.item(ii);
	    		String nodename = node.getNodeName();
	    		if (nodename.equals("Difference")){
	    			difference = BigDecimal.valueOf(Double.parseDouble(node.getTextContent())).compareTo(Env.ZERO);
	    			//Difference = 0;
	    			if(difference < 0 )
	    				TypeCloseCash = "Invoice";
	    			else if (difference > 0)
	    				TypeCloseCash = "Payment";
	    			else {
	    				TypeCloseCash = "No Document";
	    				break;
	    			}
	    		}
	    		else if (nodename.equals("POSLocatorName")){
					MWarehouse Warehouse = new Query(Env.getCtx(),MWarehouse.Table_Name,MWarehouse.COLUMNNAME_Name+"=?",null)
					.setParameters(node.getTextContent())
					.first();	
					if(Warehouse == null){
						addLog("Almacen Desconocido: " +node.getTextContent());
						log.log(Level.SEVERE, "Almacen Desconocido: " +node.getTextContent());
						throw new AdempiereException("Almacen Desconocido: " +node.getTextContent());
					}
					MOrgInfo orginfo = new Query(getCtx(), MOrgInfo.Table_Name, MOrgInfo.COLUMNNAME_AD_Org_ID+"= ?", get_TrxName()).
							setParameters(Warehouse.getAD_Org_ID()).first();
					AD_Org_ID = orginfo.getAD_Org_ID();
					OrgBPartner = orginfo.get_ValueAsInt("C_BPartner_ID");
					/*
	    			String sql = "SELECT *"
	    					+ " FROM AD_OrgInfo" 
	    					+ " WHERE ad_org_ID = ?"; 
	    			try {
		    			PreparedStatement pstmt = null;
		    			ResultSet rs = null;
						pstmt = DB.prepareStatement(sql, null);
						pstmt.setInt(1, Warehouse.getAD_Org_ID());
						rs = pstmt.executeQuery();
						rs.next();
						//OrgID = rs.getInt("AD_Org_ID");
						AD_Org_ID = rs.getInt("AD_Org_ID");
						OrgBPartner = rs.getInt("C_BPartner_ID");
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    			*/
	    		}
	    		
	    	}
	    	System.out.println("Diferencia: "+difference+" Tipo de Documento a Crear "+TypeCloseCash);
	    	cnt++;
	    	if (TypeCloseCash.equals("Invoice")){
	    		if(!createInvoice(details))
	    			return false;
	    	}else if (TypeCloseCash.equals("Payment")){
	    		if(!createPayment(details,OrgBPartner))
	    			return false;
	    	}
    	}
		}
		catch(Exception e){System.out.println("ALGO MALO PASÓ");}
    	//msgreturn = new StringBuilder("Facturas @Created@ = ").append(invoice_created);

    	//msgreturn.append("\nPagos @Created@ = "+payment_created);
    	Difference = difference;
    	System.out.println(records.getLength());
    	System.out.println(cnt);
    	return (records.getLength()==cnt); //ensure the import orders are correct count to XML details
		
	}
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		cnt = 0;
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			
			if (name.equals("Test"))
				p_Test = (String)para[i].getParameter();
			else if (name.equals("Link"))
				p_Link = (String)para[i].getParameter();
			else if (name.equals("LinkURL"))
				p_LinkURL = (String)para[i].getParameter();
			else if (name.equals("C_Charge_ID"))
				p_C_Charge_ID = para[i].getParameterAsInt();
			else if (name.equals("C_DocTypeInvoice_ID"))
				p_C_DocTypeInvoice_ID = para[i].getParameterAsInt();
			else if (name.equals("C_DocType_ID"))
				p_C_DocTypePayment_ID = para[i].getParameterAsInt();
			else
			{
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
		}
		
	}

	@Override
	protected String doIt() throws Exception {
		StompConnection connection = new StompConnection();
		connection.open(p_LinkURL,61613); //to reference openbravo.properties in ExternalSalesHelper class
		connection.connect("","");
		connection.subscribe(p_Link, Subscribe.AckModeValues.CLIENT);
		connection.begin("MQOrders");
		int AD_PInstance_ID = getProcessInfo().getAD_PInstance_ID();
		int msjscnt = 0;
		System.out.println(AD_PInstance_ID);
		int no = 0;
		// Bucle para leer todos los mensajes de la cola
		while(true){
			StompFrame message = null;
			try{
				message = connection.receive();		
			} catch(Exception e){
				System.out.print("Fin de Lectura de mensaje");
				break;
			}		
			System.out.println(message.getBody());
			msjscnt++;
			String data = message.getBody();
			if	(parseXMLString(data))
				System.out.println("SUCCESS: Records equal to I_Invoice");
			else{
				connection.commit("MQOrders");
				connection.disconnect();
				String sql = "DELETE FROM I_Payment WHERE AD_PInstance_ID = "+AD_PInstance_ID;
				no = DB.executeUpdate(sql, get_TrxName());
				if (log.isLoggable(Level.INFO)) log.info ("Reset=" + no);
				sql = "DELETE FROM I_Invoice WHERE AD_PInstance_ID = "+AD_PInstance_ID;
				no = DB.executeUpdate(sql, get_TrxName());
				if (log.isLoggable(Level.INFO)) log.info ("Reset=" + no);
				commitEx();
				System.out.println("ERROR: Records not equal to I_Invoice");
				log.log(Level.SEVERE, "Closa Cash Import Fail ");
			}
				
		
			if (p_Test.equals("N")) //not to acknowledge but to allow for repeat read
				connection.ack(message, "MQOrders");
		}	
		connection.commit("MQOrders");
		connection.disconnect();
		//return "Records imported? Lost count, maybe "+Integer.toString(cnt);
		

			return "<html>Cantidad de Registros Importados: "+Integer.toString(invoice_created+payment_created)+"<br />"+
					"Cantidad de Diferencias Negativas = "+Integer.toString(invoice_created)+"<br />"+
					"Cantidad de Diferencias Positivas = "+Integer.toString(payment_created)+"<br />"+
					"Mensajes Leidos = "+msjscnt+"</html>";
			//return(msgreturn.toString());

		
	}

}
