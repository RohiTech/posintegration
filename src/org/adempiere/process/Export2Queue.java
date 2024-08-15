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

import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MPInstance;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MProductPrice;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.WebEnv;

/**
 * Called from Process in POS Synchronisation menu.
 * @author Based on Compiere 2006. Redhuan D. Oon http://www.red1.org/adempiere
 * @param set from Process params in prepare()
 * @throws Exception for ActiveMQ Stomp connection
 * Product Info is complex involving Storage synched to Org as POS client
 */ 

public class Export2Queue extends SvrProcess
{
	private int pProductCategoryID;
	private int pAD_Org_ID;
	private int pC_Order_ID;
	private int pBPartnerGroupID; 
	private int pPriceListVersionID;
	private String p_IsSelfService = "Y";
	private String p_LinkProducts = ""; //defaults can be overiden by params
	private String p_LinkCustomers = "";
	private String p_LinkUsers = ""; //Users queue
	private String p_LinkCreditMemo = ""; 
	private String p_LinkURL = "localhost";
	private String p_ACK = "N";
	private String p_SendAll = "N"; //send all data or just last updated
	private String errmsg = "";
	private int count = 0;
	private String mainMsg = "";
	private String p_LinkResendOrders = "";
	private int pTaxCategoryID;
	private String Products_ProcessLastRun = "2000-01-01";
	private String WhereClientID="";
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			WhereClientID= "AD_Client_ID ="+getAD_Client_ID()+" ";
			if (name.equals("LinkProducts"))
				p_LinkProducts = (String)para[i].getParameter();
			else if (name.equals("LinkCustomers"))
				p_LinkCustomers = (String)para[i].getParameter();
			else if (name.equals("LinkUsers"))
				p_LinkUsers = (String)para[i].getParameter();
			else if (name.equals("LinkCreditMemo"))
				p_LinkCreditMemo = (String)para[i].getParameter();
			else if (name.equals("LinkResendOrders"))
				p_LinkResendOrders = (String)para[i].getParameter();
			else if (para[i].getParameter() == null)
			;
			else if (name.equals("LinkURL"))
				p_LinkURL = (String)para[i].getParameter();

			else if (name.equals("IsSelfService"))
				p_IsSelfService = (String)para[i].getParameter();
			
			else if (name.equals("pAD_Org_ID"))
				pAD_Org_ID = para[i].getParameterAsInt();

			else if (name.equals("C_Order_ID"))
				pC_Order_ID = para[i].getParameterAsInt();
			
			else if (name.equals(MProductCategory.COLUMNNAME_M_Product_Category_ID))
			{
				pProductCategoryID = para[i].getParameterAsInt();
			}
			else if (name.equals(MBPartner.COLUMNNAME_C_BP_Group_ID))
			{
				pBPartnerGroupID = para[i].getParameterAsInt();
			}
			else if (name.equals(MPriceListVersion.COLUMNNAME_M_PriceList_Version_ID))
			{
				pPriceListVersionID = para[i].getParameterAsInt();
			}
			else if (name.equals("ACK"))
			{
				p_ACK = (String)para[i].getParameter();
			}
			else if (name.equals("SendAll"))
			{
				p_SendAll = (String)para[i].getParameter();
			}
			else if (name.equals(MTaxCategory.COLUMNNAME_C_TaxCategory_ID))
			{
				pTaxCategoryID = para[i].getParameterAsInt();
			}
			else
			{
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
		}

	} 
	
	@Override
	protected String doIt() throws Exception 
	{
		int product_updated = 0;
		int user_updated = 0;
		int bpartner_updated = 0;
		String processlastrun = "2000-01-01";
 			 if (p_LinkProducts !=null){

 						
 						/*MPInstance PInstance = new Query(Env.getCtx(),"AD_PInstance","AD_Process_ID=53271 "+ 
 										"AND AD_PInstance.isprocessing = 'N' "
										,null)
 										.addJoinClause("INNER JOIN AD_PInstance_Para b ON (AD_PInstance.AD_PInstance_ID = b.AD_PInstance_ID AND b.parametername = 'LinkProducts' AND b.p_string IS NOT NULL)")
 										.addJoinClause("INNER JOIN AD_PInstance_Para c ON (AD_PInstance.AD_PInstance_ID = c.AD_PInstance_ID AND (c.parametername = 'pAD_Org_ID')) AND c.p_number = "+pAD_Org_ID)
 										.setOrderBy("AD_PInstance.updated DESC")
 										.first();
 				*/
  				//Fixed AD_Process_ID=53271 		
 				 
 				//last run default value
 				Products_ProcessLastRun = "2000-01-01";	
 				//last run current value
 				MPInstance PInstance = new Query(Env.getCtx(),"AD_PInstance","AD_Process_ID=53271 AND AD_PInstance.isprocessing = 'N' ",get_TrxName())
									.addJoinClause("INNER JOIN (SELECT AD_PInstance_ID FROM AD_PInstance_Para WHERE parametername = 'LinkProducts' AND p_string = '"+p_LinkProducts+
											"') b ON AD_PInstance.AD_PInstance_ID = b.AD_PInstance_ID")
									.setOrderBy("AD_PInstance.updated DESC")
									.first();
 						
 				//updated process last run value if we not want to send all data
 				if (PInstance!=null && p_SendAll.compareTo("N")==0)
 					Products_ProcessLastRun = PInstance.get_ValueAsString("updated");
 				
 				//Check if info, price or storage of the product was modified since the last run 
 				List<MProduct> Products = new Query(Env.getCtx(), MProduct.Table_Name, "(("+MProduct.COLUMNNAME_Updated+
 						">='"+Products_ProcessLastRun+"' AND "+WhereClientID+") OR "+
 						MProduct.COLUMNNAME_M_Product_ID+" IN (SELECT M_Product_ID FROM M_ProductPrice WHERE updated >='"+Products_ProcessLastRun+"' AND "+WhereClientID+") OR "+
 						MProduct.COLUMNNAME_M_Product_ID+" IN (SELECT M_Product_ID FROM M_StorageOnHand WHERE updated >='"+Products_ProcessLastRun+"' AND "+WhereClientID+"))"
 						, get_TrxName()).list();
 				
 				product_updated = Products.size();
 				
 				if (product_updated!=0 || p_SendAll.compareTo("Y")==0){
 					
 					List<MProduct> queryp = null;
 					if (pProductCategoryID > 0) {
 						//get products within this category
 						 queryp = new Query(Env.getCtx(), MProduct.Table_Name, MProductCategory.COLUMNNAME_M_Product_Category_ID+"=? AND "
 								+MProduct.COLUMNNAME_IsSelfService+"=? AND (("+MProduct.COLUMNNAME_Updated+
 		 						">='"+Products_ProcessLastRun+"' AND "+WhereClientID+") OR "+
 		 						MProduct.COLUMNNAME_M_Product_ID+" IN (SELECT M_Product_ID FROM M_ProductPrice WHERE updated >='"+Products_ProcessLastRun+"' AND "+WhereClientID+") OR "+
 		 						MProduct.COLUMNNAME_M_Product_ID+" IN (SELECT M_Product_ID FROM M_Storage WHERE updated >='"+Products_ProcessLastRun+"' AND "+WhereClientID+"))"
 		 						, get_TrxName())
 						.setParameters(pProductCategoryID, p_IsSelfService )
 						.list();
 	 					String message = ProductXML(queryp);
 	 					MQClient.setParams(p_LinkURL, 61613, p_LinkProducts);
 	 					errmsg = MQClient.sendMessage(message) ;
 	 					 if (errmsg.equals(""))
 	 					 {
 	 						 mainMsg="Sent to Queue: "+count+" Productos .. "; 
 	 						 count = 0;
 	 					 }else{
 	 						 return errmsg;
 	 					 }
 					}else{
 						if(p_SendAll.compareTo("Y")==0)
 							Products_ProcessLastRun = "2000-01-01";
 						List<MProductCategory> querypcategory=new Query(getCtx(), MProductCategory.Table_Name, null, get_TrxName()).list();
 						for(MProductCategory pcategory:querypcategory){
 							queryp = new Query(Env.getCtx(), MProduct.Table_Name, MProductCategory.COLUMNNAME_M_Product_Category_ID+"=? AND "
								 +MProduct.COLUMNNAME_IsSelfService+"=? AND (("+MProduct.COLUMNNAME_Updated+
		 						">='"+Products_ProcessLastRun+"' AND "+WhereClientID+") OR "+
		 						MProduct.COLUMNNAME_M_Product_ID+" IN (SELECT M_Product_ID FROM M_ProductPrice WHERE updated >='"+Products_ProcessLastRun+"' AND "+WhereClientID+") OR "+
		 						MProduct.COLUMNNAME_M_Product_ID+" IN (SELECT M_Product_ID FROM M_Storage WHERE updated >='"+Products_ProcessLastRun+"' AND "+WhereClientID+"))"
		 						, get_TrxName())
 								.setParameters(pcategory.getM_Product_Category_ID(), p_IsSelfService )
 								.list();
 							String message = ProductXML(queryp);
 							if (message.compareTo("No Products")!=0){
 								MQClient.setParams(p_LinkURL, 61613, p_LinkProducts);
 								errmsg = MQClient.sendMessage(message);
 								if (!errmsg.equals(""))
 								{
 									return errmsg;
 								}
 							}
 						}
 						mainMsg="Sent to Queue: "+count+" Products .. ";
 						count = 0;
 					}
 				}

 			 }
		//Customers Queue
 		if (p_LinkCustomers !=null){
	  		processlastrun = "2000-01-01";	
	  		MPInstance PInstance = new Query(Env.getCtx(),"AD_PInstance","AD_Process_ID=53271 "+ 
	  				"AND AD_PInstance.AD_PInstance_ID !="+Env.getProcessInfo(getCtx()).getAD_PInstance_ID(),get_TrxName())
	  				.addJoinClause("INNER JOIN AD_PInstance_Para b ON (AD_PInstance.AD_PInstance_ID = b.AD_PInstance_ID AND b.parametername = 'LinkCustomers' AND b.p_string IS NOT NULL)")
	  				.addJoinClause("INNER JOIN AD_PInstance_Para c ON (AD_PInstance.AD_PInstance_ID = c.AD_PInstance_ID AND (c.parametername = 'pAD_Org_ID')) AND c.p_number = "+pAD_Org_ID)
	  				.setOrderBy("AD_PInstance.updated DESC")
	  				.first();
			if (PInstance!=null && p_SendAll.compareTo("N")==0)
				processlastrun = PInstance.get_ValueAsString("updated");
			bpartner_updated = new Query(Env.getCtx(), MBPartner.Table_Name,MBPartner.COLUMNNAME_Updated+">='"+processlastrun+"' AND "+MBPartner.COLUMNNAME_IsActive+" = 'Y'"+
								" AND "+WhereClientID, get_TrxName()).count();
			if (bpartner_updated!=0 || p_SendAll.compareTo("Y")==0){
				if (pBPartnerGroupID > 0) {
				 List<MBPartner> querybp = new Query(Env.getCtx(),MBPartner.Table_Name,MBPartner.COLUMNNAME_C_BP_Group_ID+"=? AND "
						 +MBPartner.COLUMNNAME_IsCustomer+"=? AND "
						 +MBPartner.COLUMNNAME_Updated+">='"+processlastrun+"' AND "+WhereClientID,get_TrxName())
				.setParameters(pBPartnerGroupID, "Y" )
				.list();
				 MBPGroup group = new Query(Env.getCtx(),MBPGroup.Table_Name,
						 MBPGroup.COLUMNNAME_C_BP_Group_ID+"=?",get_TrxName())
				 .setParameters(pBPartnerGroupID)
				 .first();
				String message = CustomerXML(querybp, group);
				MQClient.setParams(p_LinkURL, 61613, p_LinkCustomers);
				errmsg = MQClient.sendMessage(message) ;
				if (errmsg.equals(""))
				{
					mainMsg = mainMsg + count+" Clientes .. ";
					count = 0;
				}
				else return errmsg;
			}
			else{
				List<MBPartner> querybp = new Query(Env.getCtx(),MBPartner.Table_Name,MBPartner.COLUMNNAME_IsCustomer+"='Y' AND "
							 +MBPartner.COLUMNNAME_Updated+">='"+processlastrun+"' AND "+WhereClientID,get_TrxName()).list();
				String message = CustomerXML(querybp, null);
				MQClient.setParams(p_LinkURL, 61613, p_LinkCustomers);
				errmsg = MQClient.sendMessage(message) ;
				if (errmsg.equals(""))
				{
					mainMsg = mainMsg + count+" Clientes .. ";
					count = 0;
				}
				else return errmsg;
			}
		}

	}
	if (p_LinkUsers !=null){
		//Get users updated after last sync
		processlastrun = "2000-01-01";	
		MPInstance PInstance = new Query(Env.getCtx(),"AD_PInstance","AD_Process_ID=53271 "+ 
			"AND AD_PInstance.AD_PInstance_ID !="+Env.getProcessInfo(getCtx()).getAD_PInstance_ID(),get_TrxName())
			.addJoinClause("INNER JOIN AD_PInstance_Para b ON (AD_PInstance.AD_PInstance_ID = b.AD_PInstance_ID AND b.parametername = 'LinkUsers' AND b.p_string LIKE '"+p_LinkUsers+"')")
			.addJoinClause("INNER JOIN AD_PInstance_Para c ON (AD_PInstance.AD_PInstance_ID = c.AD_PInstance_ID AND (c.parametername = 'pAD_Org_ID')) AND c.p_number = "+pAD_Org_ID)
			.setOrderBy("AD_PInstance.updated DESC")
		  	.first();
		if (PInstance!=null && p_SendAll.compareTo("N")==0)
			processlastrun = PInstance.get_ValueAsString("updated");
			user_updated = new Query(Env.getCtx(), MUser.Table_Name,"("+MUser.COLUMNNAME_AD_Org_ID+"= "+pAD_Org_ID+"  OR AD_User.SentToAll = 'Y') "
					+" AND "+MUser.COLUMNNAME_Updated+">='"+processlastrun+"' AND "+WhereClientID, get_TrxName()).count();
			bpartner_updated = new Query(Env.getCtx(),MBPartner.Table_Name,MBPartner.COLUMNNAME_IsSalesRep+"= 'Y' AND "
					+MBPartner.COLUMNNAME_Updated+">='"+processlastrun+"' AND "+WhereClientID,get_TrxName()).count();
			if (user_updated!=0 || p_SendAll.compareTo("Y")==0 || bpartner_updated!=0){
				//CREAR LISTADO DE USUARIOS
				List<MUser> queryu = new Query(Env.getCtx(), MUser.Table_Name, "(AD_User.Updated >= '"+processlastrun+"' OR bp.Updated >= '"+processlastrun+"') AND ( "
						+MUser.Table_Name+"."+MUser.COLUMNNAME_AD_Org_ID+"= "+pAD_Org_ID+"  OR AD_User.SentToAll = 'Y') "+" AND AD_User."+WhereClientID,get_TrxName())
					 .addJoinClause(" JOIN C_BPartner bp ON AD_User.C_BPartner_ID = bp.C_BPartner_ID AND bp.IsSalesRep = 'Y'")
					 .list();
				String message = UserXML(queryu);
				log.log(Level.WARNING,message.concat(String.valueOf(pAD_Org_ID)));
				MQClient.setParams(p_LinkURL, 61613, p_LinkUsers);
				errmsg = MQClient.sendMessage(message) ;
				if (errmsg.equals("")){
					mainMsg = mainMsg + count+" Usuarios ";
					count = 0;
				}
				else return errmsg;
			}
		}
		//Customer ToalOpenBalance		
		if(p_LinkCreditMemo!=null){
			String message = CreditMemoXML();
			MQClient.setParams(p_LinkURL, 61613, p_LinkCreditMemo);
			errmsg = MQClient.sendMessage(message) ;
			if (errmsg.equals(""))
			{
				mainMsg = mainMsg + count+" Notas de Credito ";
				count = 0;
			}
			else return errmsg;
		}
		//Resend Order 		
		if(p_LinkResendOrders!=null){
			String message = ResendOrdersXML();
			MQClient.setParams(p_LinkURL, 61613, p_LinkResendOrders);
			errmsg = MQClient.sendMessage(message) ;
			if (errmsg.equals(""))
			{
				mainMsg = mainMsg + count+" Ordenes por reenviar ";
				count = 0;
			}
			else return errmsg;
		}
		if (pBPartnerGroupID==0 && pProductCategoryID==0 && p_ACK.equals("Y")){
			String returned = "";
			if (p_LinkProducts !=null && (product_updated != 0 || p_SendAll.compareTo("Y")==0)){ 
			MQClient.setParams(p_LinkURL, 61613, p_LinkProducts);
			returned = MQClient.receiveMessage(); //this will clear Products queue from last single message
				
			if (returned.equals("NO SERVICE")) return returned;
			}
			if (p_LinkCustomers !=null && (bpartner_updated != 0 || p_SendAll.compareTo("Y")==0)){
			MQClient.setParams(p_LinkURL, 61613, p_LinkCustomers);
			returned = MQClient.receiveMessage();
			}
			if (p_LinkUsers !=null && (user_updated != 0 || p_SendAll.compareTo("Y")==0)){
			MQClient.setParams(p_LinkURL, 61613, p_LinkUsers);  //Crear cola de Usuarios
			returned = MQClient.receiveMessage();  //Crear cola de Usuarios
			}
			if(p_LinkCreditMemo!=null){
				MQClient.setParams(p_LinkURL, 61613, p_LinkCreditMemo);  //Crear cola de Notas de Crédito
				returned = MQClient.receiveMessage();  //Crear cola de Notas de Crédito				
			}
			if (returned.equals("No queued message")) return returned;
			return "You deleted any last queued message.";
		}
	
		
		if (mainMsg.equals(""))
			return "Parametros Incorrectos. Ningun registro ha sido modificado desde la ultima sincronización";
		else return mainMsg;
	}

	private String ProductXML(List<MProduct> query) {
		//get ProdCategory details i.e. Name
		if(query.size()<=0){
			return "No Products";
		}
		 MProductCategory prodcat = null;
  	 try { 
	      StringWriter res = new StringWriter(); 
	      XMLStreamWriter writer = XMLOutputFactory.newInstance() 
	            .createXMLStreamWriter(res); 
	      writer.writeStartDocument(); 
	      	writer.writeStartElement("entityDetail"); 
	      	writer.writeStartElement("type"); 
	      	writer.writeCharacters("openbravoPOS");
	      	writer.writeEndElement(); 
 		      	
		for (MProduct product:query) {
			//get product category
			prodcat = new Query(Env.getCtx(),MProductCategory.Table_Name,
					 MProductCategory.COLUMNNAME_M_Product_Category_ID+"=?",get_TrxName())
			 .setParameters(product.getM_Product_Category_ID())
			 .first();

			//get pricing details from Product's ProductPrice
			int anyPrice = new Query(Env.getCtx(), MProductPrice.Table_Name,MProductPrice.COLUMNNAME_M_PriceList_Version_ID+
					"=? AND "+MProductPrice.COLUMNNAME_M_Product_ID+"=?" ,get_TrxName())
			.setParameters(pPriceListVersionID, product.getM_Product_ID()).count();
			
			//above checks if any exist before fetching them. If not, default should be zero.
 			if (anyPrice==0) continue;
			MProductPrice price = new Query(Env.getCtx(),MProductPrice.Table_Name,MProductPrice.COLUMNNAME_M_PriceList_Version_ID+
					"=? AND "+MProductPrice.COLUMNNAME_M_Product_ID+"=?", get_TrxName())
					.setParameters(pPriceListVersionID, product.getM_Product_ID())
					.first();
	
		/*	List<MLocator> locators = new Query(Env.getCtx(),MLocator.Table_Name,MLocator.COLUMNNAME_M_Warehouse_ID+
				" IN (SELECT M_Warehouse_ID from M_Warehouse WHERE IsInTransit = 'N' AND IsActive = 'Y' AND AD_Client_ID = "+
				Env.getAD_Client_ID(getCtx())+" AND AD_Org_ID = "+pAD_Org_ID+")",null).list();
		*/
					
			List<MLocator> locators = null;
			if(p_SendAll.compareTo("Y")==0)
				locators = new Query(Env.getCtx(),MLocator.Table_Name,MLocator.COLUMNNAME_M_Warehouse_ID+
							" IN (SELECT M_Warehouse_ID from M_Warehouse WHERE POS_notsend != 'Y' AND IsActive = 'Y' AND AD_Client_ID = "+
							Env.getAD_Client_ID(getCtx())+" )",get_TrxName()).list();
			else
				locators = new Query(Env.getCtx(),MLocator.Table_Name,MLocator.COLUMNNAME_M_Warehouse_ID+
						" IN (SELECT M_Warehouse_ID from M_Warehouse WHERE POS_notsend != 'Y' AND IsActive = 'Y' AND AD_Client_ID = "+
						Env.getAD_Client_ID(getCtx())+" )" +
						" AND "+MLocator.COLUMNNAME_M_Locator_ID+" IN (SELECT M_Locator_ID FROM M_StorageOnHand WHERE updated >='"+Products_ProcessLastRun+
						"' AND "+WhereClientID+")",
						get_TrxName()).list();
					 
			if(locators.size()==0)
				locators = new Query(Env.getCtx(),MLocator.Table_Name,"IsDefault = 'Y' AND "+MLocator.COLUMNNAME_M_Warehouse_ID+" IN (SELECT M_Warehouse_ID FROM " +
						"AD_OrgInfo WHERE AD_Org_ID = "+pAD_Org_ID+")",get_TrxName()).list();

			  for (MLocator locator:locators){	
				  BigDecimal store =null;
				  if(locator.getM_Warehouse().isInTransit()==true)
					  store = new Query(Env.getCtx(),MStorageOnHand.Table_Name,MStorageOnHand.COLUMNNAME_M_Product_ID+
							"=? AND "+MStorageOnHand.COLUMNNAME_M_Locator_ID+" IN (SELECT M_Locator_ID FROM M_Locator WHERE M_Warehouse_ID = ?) " +
							"",get_TrxName())
							.setParameters(product.getM_Product_ID(),locator.getM_Warehouse_ID())
							.setOrderBy("Created DESC")
							.sum("QtyOnHand");
				  else
				  store = new Query(Env.getCtx(),MStorageOnHand.Table_Name,MStorageOnHand.COLUMNNAME_M_Product_ID+
						"=? AND "+MStorageOnHand.COLUMNNAME_M_Locator_ID+"=?",get_TrxName())
				.setParameters(product.getM_Product_ID(),locator.getM_Locator_ID())
				.setOrderBy("Created DESC")
				.sum("QtyOnHand");
				  

				//System.out.println(store);
				//get Warehouse Name from Store Locator to match to POS Locator Name
				MLocator loc = new Query(Env.getCtx(),MLocator.Table_Name,MLocator.COLUMNNAME_M_Locator_ID+"=?",get_TrxName())
				.setParameters(locator.getM_Locator_ID())
				.first();
				count++;
				 	writer.writeStartElement("detail");
				 	
			      	writer.writeStartElement("DocType"); 
				    writer.writeCharacters(MProduct.Table_Name);
				    writer.writeEndElement(); //</DocType>
				    
		            //Warehouse Name as POS name
		            writer.writeStartElement("POSLocatorName");
		            writer.writeCharacters(loc.getWarehouseName());
		            writer.writeEndElement();
		            
		            //Send Warehouse ID to queue
		            writer.writeStartElement("M_Warehouse_ID");
		            writer.writeCharacters(Integer.toString(loc.getM_Warehouse_ID()));
		            writer.writeEndElement();
		            
		            //Send InTransit Check to queue
		            writer.writeStartElement("isInTransit");
		            if(loc.getM_Warehouse().isInTransit()==true)
		            	writer.writeCharacters("Y");
		            else
		            	writer.writeCharacters("N");		            
		            writer.writeEndElement();		            
		            
			      	writer.writeStartElement("ProductName"); 
			      	String auxname=product.getName().replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			      	auxname=auxname.replaceAll("\\+", "%2B");
		            writer.writeCharacters(URLEncoder.encode(auxname,WebEnv.ENCODING));
		            writer.writeEndElement();  
		            
		            //Storage Data - QtyOnHand
		            writer.writeStartElement("QtyOnHand");
		            if(store == null || !product.isActive())
		            	writer.writeCharacters("0");
		            else
		            	writer.writeCharacters(store.toString());
		            writer.writeEndElement();
		            
		            //ProductCategory model
		          	writer.writeStartElement(MProductCategory.COLUMNNAME_M_Product_Category_ID); 
		            writer.writeCharacters(Integer.toString(product.getM_Product_Category_ID()));
		            writer.writeEndElement();    
		            writer.writeStartElement("CategoryName"); 
			      	auxname=prodcat.getName().replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			      	auxname=auxname.replaceAll("\\+", "%2B");
		            writer.writeCharacters(URLEncoder.encode(auxname,WebEnv.ENCODING));
		            writer.writeEndElement();  
		   
			      	writer.writeStartElement(MProduct.COLUMNNAME_M_Product_ID); 
		            writer.writeCharacters(Integer.toString(product.getM_Product_ID()));
		            writer.writeEndElement();  
		            
					//Tax model i.e. Name, Percentage? (Rate is complex in AD)
					
		            //Send an specific Tax Category
		            MTaxCategory taxCategory=null;
		            MTax tax=null;
					if(pTaxCategoryID>0){
						taxCategory = new Query(Env.getCtx(),MTaxCategory.Table_Name,MTaxCategory.COLUMNNAME_C_TaxCategory_ID+"=?",get_TrxName())
						 .setParameters(pTaxCategoryID)
						 .first();		
						tax = new Query(Env.getCtx(),MTax.Table_Name,MTax.COLUMNNAME_C_TaxCategory_ID+"=? and "
								+MTax.COLUMNNAME_IsDefault+"='Y'",get_TrxName())
						 		.setParameters(pTaxCategoryID)
						 		.first();
					}
					else{
						taxCategory = new Query(Env.getCtx(),MTaxCategory.Table_Name,MTaxCategory.COLUMNNAME_C_TaxCategory_ID+"=?",get_TrxName())
						 .setParameters(product.getC_TaxCategory().getC_TaxCategory_ID())
						 .first();	
						tax = new Query(Env.getCtx(),MTax.Table_Name,MTax.COLUMNNAME_C_TaxCategory_ID+"=? and "
								+MTax.COLUMNNAME_IsDefault+"='Y'",get_TrxName())
						 		.setParameters(product.getC_TaxCategory().getC_TaxCategory_ID())
						 		.first();
					}
					

					writer.writeStartElement(MTaxCategory.COLUMNNAME_C_TaxCategory_ID); 
		            writer.writeCharacters(Integer.toString(taxCategory.getC_TaxCategory_ID()));
		            writer.writeEndElement();
		            			            
			        writer.writeStartElement("TaxCategoryName"); 
				    auxname=taxCategory.getName().replaceAll("%(?![0-9a-fA-F]{2})", "%25");
				    auxname=auxname.replaceAll("\\+", "%2B");
			        writer.writeCharacters(URLEncoder.encode(auxname,WebEnv.ENCODING));
			        writer.writeEndElement();  
			        writer.writeStartElement(MTax.COLUMNNAME_C_Tax_ID); 
		            writer.writeCharacters(Integer.toString(tax.getC_Tax_ID()));
		            writer.writeEndElement();
		            //agregamos la tasa
		            writer.writeStartElement("TaxRate"); 
		            writer.writeCharacters(tax.getRate().toString());
		            writer.writeEndElement();
		            
			        writer.writeStartElement("TaxName"); 
			      	auxname=tax.getName().replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			      	auxname=auxname.replaceAll("\\+", "%2B");
		            writer.writeCharacters(URLEncoder.encode(auxname,WebEnv.ENCODING));
		            writer.writeEndElement();
  
		            
			      	writer.writeStartElement(MProduct.COLUMNNAME_UPC); 
			      	auxname=product.getUPC().replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			      	auxname=auxname.replaceAll("\\+", "%2B");
		            writer.writeCharacters(URLEncoder.encode(auxname,WebEnv.ENCODING));
		            writer.writeEndElement();  
		            
		            //Pricing info
			      	writer.writeStartElement(price.COLUMNNAME_PriceList); 
		            writer.writeCharacters(price.getPriceList().toString());
		            writer.writeEndElement();  
		            
			      	writer.writeStartElement(price.COLUMNNAME_PriceLimit); 
		            writer.writeCharacters(price.getPriceLimit().toString());
		            writer.writeEndElement(); 
		            
		            writer.writeEndElement(); //</detail>
		         // if there exist Storage and exist Price
				} 
 		 // Product
		}
		
	 // end of XML creation 
        writer.writeEndElement();  //</entityDetail>
        writer.writeEndDocument();  
        if (count==0) return "No Products"; 
        return res.toString();          
   	 }catch (Exception ex) { 
		    ex.printStackTrace(); 
		    return "XML creation ERROR"; 
		  }
	}
   	 
 	private String CustomerXML(List<MBPartner> query, MBPGroup group) {
 	  	 try { 
 		      StringWriter res = new StringWriter(); 
 		      XMLStreamWriter writer = XMLOutputFactory.newInstance() 
 		            .createXMLStreamWriter(res); 
 		      writer.writeStartDocument(); 
 		      	writer.writeStartElement("entityDetail"); 
 		      	writer.writeStartElement("type"); 
 		      	writer.writeCharacters("openbravoPOS");
 		      	writer.writeEndElement(); 
 	 		      	
 			for (MBPartner partner:query) {		
 				MUser contact = new Query(getCtx(), MUser.Table_Name, MUser.COLUMNNAME_C_BPartner_ID+"="+partner.get_ID(), get_TrxName())
 						.setOrderBy(MUser.COLUMNNAME_Created+" DESC").first();
 				count++;
 				
 		      	writer.writeStartElement("detail");
		      	
 		      	writer.writeStartElement("DocType"); 
 		      	writer.writeCharacters(MBPartner.Table_Name);
 		      	writer.writeEndElement(); 
 		      	
 		      	writer.writeStartElement("CustomerName"); 
 	            writer.writeCharacters(URLEncoder.encode(partner.getName(),WebEnv.ENCODING));
 	            writer.writeEndElement();  
  
 	          	writer.writeStartElement(MBPartner.COLUMNNAME_Value); 
 	            writer.writeCharacters(partner.getValue());
 	            writer.writeEndElement();    
 	            
 	          	writer.writeStartElement(MBPartner.COLUMNNAME_IsActive);
 	          	if (partner.isActive())
 	          		writer.writeCharacters("Y");
 	          	else
 	          		writer.writeCharacters("N");
 	            writer.writeEndElement();   	            
 	            
 	            writer.writeStartElement(MBPartner.COLUMNNAME_Description);
 	            if(partner.getDescription() != null)
 	            writer.writeCharacters(URLEncoder.encode(partner.getDescription(),WebEnv.ENCODING));
 	            writer.writeEndElement();  
 	   
 		      	writer.writeStartElement(MBPartner.COLUMNNAME_C_BPartner_ID); 
 	            writer.writeCharacters(Integer.toString(partner.getC_BPartner_ID()));
 	            writer.writeEndElement();  
 	            
 		      	writer.writeStartElement("Address1"); 
 		      	MBPartnerLocation bploc = new Query(getCtx(), MBPartnerLocation.Table_Name, MBPartnerLocation.COLUMNNAME_C_BPartner_ID+" = "+
 		      								partner.getC_BPartner_ID(), get_TrxName()).first();
 		      	if(bploc!=null){
	 		      	MLocation loc = new MLocation(getCtx(), bploc.getC_Location_ID(), get_TrxName());
	 		      	if(loc.getAddress1() != null)
	 		      		writer.writeCharacters(URLEncoder.encode(loc.getAddress1(),WebEnv.ENCODING));
 		      	}
 	            writer.writeEndElement(); 
 	            
 	            writer.writeStartElement(MBPartner.COLUMNNAME_TotalOpenBalance); 
	            writer.writeCharacters(partner.getTotalOpenBalance().toString());
	            writer.writeEndElement();

	            //New Columns
	            writer.writeStartElement("SalesRep_ID");
	            if(partner.getSalesRep_ID()>0)
		    	writer.writeCharacters(Integer.toString(partner.getSalesRep_ID()));
		        writer.writeEndElement();
		        
	            writer.writeStartElement("SalesRep_Name");
	            if(partner.getSalesRep_ID()>0)
		    	writer.writeCharacters(URLEncoder.encode(partner.getSalesRep().getName(),WebEnv.ENCODING));
		        writer.writeEndElement();
		        
	            writer.writeStartElement(MUser.COLUMNNAME_EMail);
	            if(contact!=null){
		            if(contact.getEMail()!=null) 
			            writer.writeCharacters(URLEncoder.encode(contact.getEMail(),WebEnv.ENCODING));
	            }
		        writer.writeEndElement();
		        
	            writer.writeStartElement(MUser.COLUMNNAME_Phone);
	            if(contact!=null){
		        if(contact.getPhone()!=null)
			            writer.writeCharacters(URLEncoder.encode(contact.getPhone(),WebEnv.ENCODING));
	            }
	            writer.writeEndElement();		            

	            writer.writeStartElement(MUser.COLUMNNAME_Phone2);
	            if(contact!=null){
		            if(contact.getPhone2()!=null)
			            writer.writeCharacters(URLEncoder.encode(contact.getPhone2(),WebEnv.ENCODING));
	            }
		        writer.writeEndElement();
		               
	            writer.writeStartElement("BPartnerBirthday");
		        if(contact!=null){
		            if(contact.getBirthday()!=null)
			            writer.writeCharacters(contact.getBirthday().toString());
		        }
		        writer.writeEndElement();
		        
	            writer.writeStartElement("BPartnerGroup");
		            if(partner.getC_BP_Group_ID()>0)
			            writer.writeCharacters(URLEncoder.encode(partner.getC_BP_Group().getValue(),WebEnv.ENCODING));
		        writer.writeEndElement();
	            	/*}
	            	catch(NullPointerException e){
	            		//
	            	}
	            	*/
 	            writer.writeEndElement();  //detail
	            }

 	        writer.writeEndElement();  	//entityDetail
 	        writer.writeEndDocument();  //message
 	        return res.toString();
 	   	 }catch (Exception ex) { 
 			    ex.printStackTrace(); 
 			    return "XML creation ERROR"; 
 			  }
	}
 	

	/**
	 *  Función para enviar los ejecutivos de venta a la cola
	 */
 	private String UserXML(List<MUser> query) {
 		
 		try { 
		      StringWriter res = new StringWriter();
		      XMLStreamWriter writer = XMLOutputFactory.newInstance() 
		            .createXMLStreamWriter(res); 
		      writer.writeStartDocument(); 
		      writer.writeStartElement("entityDetail"); 
		      writer.writeStartElement("type"); 
		      writer.writeCharacters("Users");
		      writer.writeEndElement(); 
		      
		      for (MUser user:query) {
				
		      	writer.writeStartElement("detail"); 
		      	writer.writeStartElement(MUser.COLUMNNAME_AD_User_ID); 
	            writer.writeCharacters(Integer.toString(user.getAD_User_ID()));
	            writer.writeEndElement();  
		      	
		      	writer.writeStartElement(MUser.COLUMNNAME_Name); 
	            writer.writeCharacters(URLEncoder.encode(user.getName(),WebEnv.ENCODING));
	            writer.writeEndElement();
	            
 	          	writer.writeStartElement(MUser.COLUMNNAME_IsActive);
 	          	if (user.isActive())
 	          		writer.writeCharacters("Y");
 	          	else
 	          		writer.writeCharacters("N");
 	            writer.writeEndElement();
	            writer.writeEndElement(); //</detail>
				count++;
		      }
		      // end of XML creation 
		      writer.writeEndElement();  //</entityDetail>
		      writer.writeEndDocument();   
		      return res.toString();
		   	 }catch (Exception ex) { 
				    ex.printStackTrace(); 
				    return "XML creation ERROR"; 
				  }		        
	}

	/**
	 *  Función para enviar las notas de credito con saldo pendiente a la cola
	 */
 	//private String NCXML() {
 	private String CreditMemoXML() {

		String sql = "SELECT op.AD_Client_ID,bp.value,sum(op.openamt) as openamt " 
			+ " FROM RV_BPartnerOpen op" 
			+ " JOIN C_DocType dt ON dt.C_DocType_ID = op.C_DocType_ID"
			+ " JOIN C_BPartner bp on bp.C_BPartner_ID = op.C_BPartner_ID"
			+ " WHERE op.AD_Client_ID = ? AND op.openamt != 0";
			
		if (pC_Order_ID != 0){ sql = sql + " AND op.C_BPartner_ID = ? ";}
			sql=sql + " GROUP BY op.AD_Client_ID,bp.value"
			+ " ORDER BY op.AD_Client_ID,bp.value";
 		
		MOrder order = new MOrder(getCtx(), pC_Order_ID, get_TrxName());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
 		try { 
		      StringWriter res = new StringWriter(); 
		      XMLStreamWriter writer = XMLOutputFactory.newInstance() 
		            .createXMLStreamWriter(res); 
		      writer.writeStartDocument(); 
		      	writer.writeStartElement("entityDetail"); 
		      	writer.writeStartElement("type"); 
		      	writer.writeCharacters("CreditMemo");
		      	writer.writeEndElement(); 
		      	
				pstmt = DB.prepareStatement(sql, get_TrxName()); //
				pstmt.setInt(1, Env.getContextAsInt(getCtx(), "AD_Client_ID"));
				
				if (pC_Order_ID != 0){ pstmt.setInt(2, order.getC_BPartner_ID());}
				
				rs = pstmt.executeQuery();	 		      
				while (rs.next()) {
				//if (pC_Order_ID == 0){
						
				
				count++;
				
		      	writer.writeStartElement("detail");
		      	writer.writeStartElement("BPValue"); 
	            writer.writeCharacters(rs.getString("value"));
	            writer.writeEndElement();  
 
	            writer.writeStartElement("OpenAmount"); 
	            writer.writeCharacters(rs.getString("openamt"));
	            writer.writeEndElement(); 
	            
	            writer.writeEndElement();  
				}	
	        writer.writeEndElement();  
	        writer.writeEndDocument();  
	        return res.toString();
	   	 }catch (Exception ex) { 
			    ex.printStackTrace(); 
			    return "XML creation ERROR"; 
			  }
	} 
 	
 	private String ResendOrdersXML() {

 		StringBuilder sql = new StringBuilder( "SELECT IO.* FROM I_Order IO INNER JOIN (SELECT DocumentNo,COUNT(opos_line) as numberoflines ")
				.append(" FROM I_Order GROUP BY DocumentNo) Temp_IO ON Temp_IO.DocumentNo=IO.DocumentNo ")
			  .append(" WHERE IO.I_IsImported='N' AND IO.DocumentNo NOT IN (SELECT DocumentNo FROM C_Order WHERE DocStatus = 'CO') " )
			 .append(" AND IO.opos_numberoflines!=Temp_IO.numberoflines")
			.append(" ORDER BY IO.C_BPartner_ID, IO.BillTo_ID, IO.C_BPartner_Location_ID, IO.I_Order_ID");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
 		try { 
		      StringWriter res = new StringWriter(); 
		      XMLStreamWriter writer = XMLOutputFactory.newInstance() 
		            .createXMLStreamWriter(res); 
		      writer.writeStartDocument(); 
		      	writer.writeStartElement("entityDetail"); 
		      	writer.writeStartElement("type"); 
		      	writer.writeCharacters("ResendOrders");
		      	writer.writeEndElement(); 
		      	
				pstmt = DB.prepareStatement(sql.toString(), get_TrxName()); //
						
				
				rs = pstmt.executeQuery();	 		      
				while (rs.next()) {
				
						
				
				count++;
				
		      	writer.writeStartElement("detail");
		      	
		      	writer.writeStartElement("AD_Org_ID"); 
	            writer.writeCharacters(rs.getString("AD_Org_ID"));
	            writer.writeEndElement();
		      	
		      	writer.writeStartElement("ticketid");
		      	String[] temp= rs.getString("DocumentNo").split("-");
	            writer.writeCharacters(temp[1]);
	            writer.writeEndElement();  
 
	             
	            
	            writer.writeEndElement();  
	 			
				
	            
				}	
	        writer.writeEndElement();  
	        writer.writeEndDocument();  
	        return res.toString();
	   	 }catch (Exception ex) { 
			    ex.printStackTrace(); 
			    return "XML creation ERROR"; 
			  }
	} 
}
