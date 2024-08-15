/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package com.ghintech.puntocom.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MBankAccount;
import org.compiere.model.MInvoice;
import org.compiere.model.MLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.model.X_I_Order;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.ghintech.puntocom.model.M_C_Invoice_Fiscal;

/**
 *	Import Order from I_Order
 *  @author Oscar Gomez
 * 			<li>BF [ 2936629 ] Error when creating bpartner in the importation order
 * 			<li>https://sourceforge.net/tracker/?func=detail&aid=2936629&group_id=176962&atid=879332
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ImportOrder.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ImportOrder extends SvrProcess
{
	/**	Client to be imported to		*/
	private int				m_AD_Client_ID = 0;
	/**	Organization to be imported to		*/
	private int				m_AD_Org_ID = 0;
	/**	Delete old Imported				*/
	private boolean			m_deleteOldImported = false;
	/**	Document Action					*/
	private String			m_docAction = MOrder.DOCACTION_Prepare;


	/** Effective						*/
	private Timestamp		m_DateValue = null;
	

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Client_ID"))
				m_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("AD_Org_ID"))
				m_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DeleteOldImported"))
				m_deleteOldImported = "Y".equals(para[i].getParameter());
			else if (name.equals("DocAction"))
				m_docAction = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		if (m_DateValue == null)
			m_DateValue = new Timestamp (System.currentTimeMillis());
		
	}	//	prepare


	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		StringBuilder sql = null;
		int no = 0;
		StringBuilder clientCheck = new StringBuilder(" AND AD_Client_ID=").append(m_AD_Client_ID);
		
		//StringBuilder linefilter = new StringBuilder(" AND opos_line IS NOT NULL AND opos_numberoflines IS NOT NULL");
		//solucion temporal hasta corregir integracion por ws
		//StringBuilder linefilter = new StringBuilder(" AND AD_Org_ID != 1000017");
		StringBuilder linefilter = new StringBuilder("");
		//	****	Prepare	****

		//	Delete Old Imported
		if (m_deleteOldImported)
		{
			sql = new StringBuilder ("DELETE I_Order ")
				  .append("WHERE I_IsImported='Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			if (log.isLoggable(Level.FINE)) log.fine("Delete Old Impored =" + no);
		}

		//	Set Client, Org, IsActive, Created/Updated
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET AD_Client_ID = COALESCE (AD_Client_ID,").append (m_AD_Client_ID).append ("),")
			  .append(" AD_Org_ID = COALESCE (AD_Org_ID,").append (m_AD_Org_ID).append ("),")
			  .append(" IsActive = COALESCE (IsActive, 'Y'),")
			  .append(" Created = COALESCE (Created, SysDate),")
			  .append(" CreatedBy = COALESCE (CreatedBy, 0),")
			  .append(" Updated = COALESCE (Updated, SysDate),")
			  .append(" UpdatedBy = COALESCE (UpdatedBy, 0),")
			  .append(" I_ErrorMsg = ' ',")
			  .append(" I_IsImported = 'N' ")
			  .append("WHERE (I_IsImported<>'Y' OR I_IsImported IS NULL)");
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.INFO)) log.info ("Reset=" + no);

		//modificacion del sql para que no traiga nulo el valor de la org
		sql = new StringBuilder ("UPDATE I_Order o ")	//	 
				.append("SET M_Warehouse_ID=(SELECT M_Warehouse_ID FROM M_Warehouse w WHERE w.Name=o.LocatorName")
				.append(" AND o.AD_Client_ID=w.AD_Client_ID), AD_Org_ID=(SELECT AD_Org_ID FROM M_Warehouse w WHERE w.Name=o.LocatorName AND o.AD_Client_ID=w.AD_Client_ID)")
				.append("WHERE M_Warehouse_ID IS NULL AND I_IsImported<>'Y' AND LocatorName IS NOT NULL").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());	//	Warehouse by POS Name
			if (no != 0)
				log.fine("Got POS to set Warehouse=" + no);
		//	Warehouse

		
		sql = new StringBuilder ("UPDATE I_Order o ")
			.append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Org, '")
			.append("WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0")
			.append(" OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))")
			.append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Org=" + no);

		//	Document Type - PO - SO
		sql = new StringBuilder ("UPDATE I_Order o ")	//	PO Document Type Name
			  .append("SET C_DocType_ID=(SELECT C_DocType_ID FROM C_DocType d WHERE d.Name=o.DocTypeName")
			  .append(" AND d.DocBaseType='POO' AND o.AD_Client_ID=d.AD_Client_ID) ")
			  .append("WHERE C_DocType_ID IS NULL AND IsSOTrx='N' AND DocTypeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set PO DocType=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")	//	SO Document Type Name
			  .append("SET C_DocType_ID=(SELECT C_DocType_ID FROM C_DocType d WHERE d.Name=o.DocTypeName")
			  .append(" AND d.DocBaseType='SOO' AND o.AD_Client_ID=d.AD_Client_ID) ")
			  .append("WHERE C_DocType_ID IS NULL AND IsSOTrx='Y' AND DocTypeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set SO DocType=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_DocType_ID=(SELECT C_DocType_ID FROM C_DocType d WHERE d.Name=o.DocTypeName")
			  .append(" AND d.DocBaseType IN ('SOO','POO') AND o.AD_Client_ID=d.AD_Client_ID) ")
			//+ "WHERE C_DocType_ID IS NULL AND IsSOTrx IS NULL AND DocTypeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
			  .append("WHERE C_DocType_ID IS NULL AND DocTypeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set DocType=" + no);
		sql = new StringBuilder ("UPDATE I_Order ")	//	Error Invalid Doc Type Name
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid DocTypeName, ' ")
			  .append("WHERE C_DocType_ID IS NULL AND DocTypeName IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid DocTypeName=" + no);
		//	DocType Default
		sql = new StringBuilder ("UPDATE I_Order o ")	//	Default PO
			  .append("SET C_DocType_ID=(SELECT MAX(C_DocType_ID) FROM C_DocType d WHERE d.IsDefault='Y'")
			  .append(" AND d.DocBaseType='POO' AND o.AD_Client_ID=d.AD_Client_ID) ")
			  .append("WHERE C_DocType_ID IS NULL AND IsSOTrx='N' AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set PO Default DocType=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")	//	Default SO
			  .append("SET C_DocType_ID=(SELECT MAX(C_DocType_ID) FROM C_DocType d WHERE d.IsDefault='Y'")
			  .append(" AND d.DocBaseType='SOO' AND o.AD_Client_ID=d.AD_Client_ID) ")
			  .append("WHERE C_DocType_ID IS NULL AND IsSOTrx='Y' AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set SO Default DocType=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_DocType_ID=(SELECT MAX(C_DocType_ID) FROM C_DocType d WHERE d.IsDefault='Y'")
			  .append(" AND d.DocBaseType IN('SOO','POO') AND o.AD_Client_ID=d.AD_Client_ID) ")
			  .append("WHERE C_DocType_ID IS NULL AND IsSOTrx IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Default DocType=" + no);
		sql = new StringBuilder ("UPDATE I_Order ")	// No DocType
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No DocType, ' ")
			  .append("WHERE C_DocType_ID IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("No DocType=" + no);
					
		//	Set IsSOTrx
		sql = new StringBuilder ("UPDATE I_Order o SET IsSOTrx='Y' ")
			  .append("WHERE EXISTS (SELECT * FROM C_DocType d WHERE o.C_DocType_ID=d.C_DocType_ID AND d.DocBaseType='SOO' AND o.AD_Client_ID=d.AD_Client_ID)")
			  .append(" AND C_DocType_ID IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set IsSOTrx=Y=" + no);
		sql = new StringBuilder ("UPDATE I_Order o SET IsSOTrx='N' ")
			  .append("WHERE EXISTS (SELECT * FROM C_DocType d WHERE o.C_DocType_ID=d.C_DocType_ID AND d.DocBaseType='POO' AND o.AD_Client_ID=d.AD_Client_ID)")
			  .append(" AND C_DocType_ID IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set IsSOTrx=N=" + no);

		//	Price List
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET M_PriceList_ID=(SELECT MAX(M_PriceList_ID) FROM M_PriceList p WHERE p.IsDefault='Y'")
			  .append(" AND p.C_Currency_ID=o.C_Currency_ID AND p.IsSOPriceList=o.IsSOTrx AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE M_PriceList_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Default Currency PriceList=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET M_PriceList_ID=(SELECT MAX(M_PriceList_ID) FROM M_PriceList p WHERE p.IsDefault='Y'")
			  .append(" AND p.IsSOPriceList=o.IsSOTrx AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE M_PriceList_ID IS NULL AND C_Currency_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Default PriceList=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET M_PriceList_ID=(SELECT MAX(M_PriceList_ID) FROM M_PriceList p ")
			  .append(" WHERE p.C_Currency_ID=o.C_Currency_ID AND p.IsSOPriceList=o.IsSOTrx AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE M_PriceList_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Currency PriceList=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET M_PriceList_ID=(SELECT MAX(M_PriceList_ID) FROM M_PriceList p ")
			  .append(" WHERE p.IsSOPriceList=o.IsSOTrx AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE M_PriceList_ID IS NULL AND C_Currency_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set PriceList=" + no);
		//
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No PriceList, ' ")
			  .append("WHERE M_PriceList_ID IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No PriceList=" + no);

		// @Trifon - Import Order Source
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_OrderSource_ID=(SELECT C_OrderSource_ID FROM C_OrderSource p")
			  .append(" WHERE o.C_OrderSourceValue=p.Value AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE C_OrderSource_ID IS NULL AND C_OrderSourceValue IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Order Source=" + no);
		// Set proper error message
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Not Found Order Source, ' ")
			  .append("WHERE C_OrderSource_ID IS NULL AND C_OrderSourceValue IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No OrderSource=" + no);
		
		//	Payment Term
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_PaymentTerm_ID=(SELECT C_PaymentTerm_ID FROM C_PaymentTerm p")
			  .append(" WHERE o.PaymentTermValue=p.Value AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE C_PaymentTerm_ID IS NULL AND PaymentTermValue IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set PaymentTerm=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_PaymentTerm_ID=(SELECT MAX(C_PaymentTerm_ID) FROM C_PaymentTerm p")
			  .append(" WHERE p.IsDefault='Y' AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE C_PaymentTerm_ID IS NULL AND o.PaymentTermValue IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Default PaymentTerm=" + no);
		//
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No PaymentTerm, ' ")
			  .append("WHERE C_PaymentTerm_ID IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("No PaymentTerm=" + no);

		//	Warehouse
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET M_Warehouse_ID=(SELECT MAX(M_Warehouse_ID) FROM M_Warehouse w")
			  .append(" WHERE o.AD_Client_ID=w.AD_Client_ID AND o.AD_Org_ID=w.AD_Org_ID) ")
			  .append("WHERE M_Warehouse_ID IS NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());	//	Warehouse for Org
		if (no != 0)
			if (log.isLoggable(Level.FINE)) log.fine("Set Warehouse=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET M_Warehouse_ID=(SELECT M_Warehouse_ID FROM M_Warehouse w")
			  .append(" WHERE o.AD_Client_ID=w.AD_Client_ID) ")
			  .append("WHERE M_Warehouse_ID IS NULL")
			  .append(" AND EXISTS (SELECT AD_Client_ID FROM M_Warehouse w WHERE w.AD_Client_ID=o.AD_Client_ID GROUP BY AD_Client_ID HAVING COUNT(*)=1)")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			if (log.isLoggable(Level.FINE)) log.fine("Set Only Client Warehouse=" + no);
		//
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Warehouse, ' ")
			  .append("WHERE M_Warehouse_ID IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("No Warehouse=" + no);

		//	BP from EMail
		/*sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET (C_BPartner_ID,AD_User_ID)=(SELECT C_BPartner_ID,AD_User_ID FROM AD_User u")
			  .append(" WHERE o.EMail=u.EMail AND o.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL) ")
			  .append("WHERE C_BPartner_ID IS NULL AND EMail IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set BP from EMail=" + no);
		//	BP from ContactName
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET (C_BPartner_ID,AD_User_ID)=(SELECT C_BPartner_ID,AD_User_ID FROM AD_User u")
			  .append(" WHERE o.ContactName=u.Name AND o.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL) ")
			  .append("WHERE C_BPartner_ID IS NULL AND ContactName IS NOT NULL")
			  .append(" AND EXISTS (SELECT Name FROM AD_User u WHERE o.ContactName=u.Name AND o.AD_Client_ID=u.AD_Client_ID AND u.C_BPartner_ID IS NOT NULL GROUP BY Name HAVING COUNT(*)=1)")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set BP from ContactName=" + no);*/
		//	BP from TaxID
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_BPartner_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp")
			  .append(" WHERE o.TaxID=bp.TaxID AND o.AD_Client_ID=bp.AD_Client_ID) ")
			  .append("WHERE C_BPartner_ID IS NULL AND o.TaxID IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set BP from Value=" + no);
		
		//	BP from Value
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_BPartner_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp")
			  .append(" WHERE o.BPartnerValue=bp.Value AND o.AD_Client_ID=bp.AD_Client_ID) ")
			  .append("WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set BP from Value=" + no);
		//	Default BP
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_BPartner_ID=(SELECT C_BPartnerCashTrx_ID FROM AD_ClientInfo c")
			  .append(" WHERE o.AD_Client_ID=c.AD_Client_ID) ")
			  .append("WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NULL AND Name IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Default BP=" + no);

		//	Existing Location ? Exact Match
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET (BillTo_ID,C_BPartner_Location_ID)=(SELECT C_BPartner_Location_ID,C_BPartner_Location_ID")
			  .append(" FROM C_BPartner_Location bpl INNER JOIN C_Location l ON (bpl.C_Location_ID=l.C_Location_ID)")
			  .append(" WHERE o.C_BPartner_ID=bpl.C_BPartner_ID AND bpl.AD_Client_ID=o.AD_Client_ID")
			  .append(" AND DUMP(o.Address1)=DUMP(l.Address1) AND DUMP(o.Address2)=DUMP(l.Address2)")
			  .append(" AND DUMP(o.City)=DUMP(l.City) AND DUMP(o.Postal)=DUMP(l.Postal)")
			  .append(" AND o.C_Region_ID=l.C_Region_ID AND o.C_Country_ID=l.C_Country_ID) ")
			  .append("WHERE C_BPartner_ID IS NOT NULL AND C_BPartner_Location_ID IS NULL")
			  .append(" AND I_IsImported='N'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Found Location=" + no);
		//	Set Bill Location from BPartner
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET BillTo_ID=(SELECT MAX(C_BPartner_Location_ID) FROM C_BPartner_Location l")
			  .append(" WHERE l.C_BPartner_ID=o.C_BPartner_ID AND o.AD_Client_ID=l.AD_Client_ID")
			  .append(" AND ((l.IsBillTo='Y' AND o.IsSOTrx='Y') OR (l.IsPayFrom='Y' AND o.IsSOTrx='N'))")
			  .append(") ")
			  .append("WHERE C_BPartner_ID IS NOT NULL AND BillTo_ID IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set BP BillTo from BP=" + no);
		//	Set Location from BPartner
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_BPartner_Location_ID=(SELECT MAX(C_BPartner_Location_ID) FROM C_BPartner_Location l")
			  .append(" WHERE l.C_BPartner_ID=o.C_BPartner_ID AND o.AD_Client_ID=l.AD_Client_ID")
			  .append(" AND ((l.IsShipTo='Y' AND o.IsSOTrx='Y') OR o.IsSOTrx='N')")
			  .append(") ")
			  .append("WHERE C_BPartner_ID IS NOT NULL AND C_BPartner_Location_ID IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set BP Location from BP=" + no);
		//
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No BP Location, ' ")
			  .append("WHERE C_BPartner_ID IS NOT NULL AND (BillTo_ID IS NULL OR C_BPartner_Location_ID IS NULL)")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("No BP Location=" + no);

		//	Set Country
		/**
		sql = new StringBuffer ("UPDATE I_Order o "
			  + "SET CountryCode=(SELECT MAX(CountryCode) FROM C_Country c WHERE c.IsDefault='Y'"
			  + " AND c.AD_Client_ID IN (0, o.AD_Client_ID)) "
			  + "WHERE C_BPartner_ID IS NULL AND CountryCode IS NULL AND C_Country_ID IS NULL"
			  + " AND I_IsImported<>'Y'").append (clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.fine("Set Country Default=" + no);
		**/
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c")
			  .append(" WHERE o.CountryCode=c.CountryCode AND c.AD_Client_ID IN (0, o.AD_Client_ID)) ")
			  .append("WHERE C_BPartner_ID IS NULL AND C_Country_ID IS NULL AND CountryCode IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Country=" + no);
		//
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Country, ' ")
			  .append("WHERE C_BPartner_ID IS NULL AND C_Country_ID IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Country=" + no);

		//	Set Region
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("Set RegionName=(SELECT MAX(Name) FROM C_Region r")
			  .append(" WHERE r.IsDefault='Y' AND r.C_Country_ID=o.C_Country_ID")
			  .append(" AND r.AD_Client_ID IN (0, o.AD_Client_ID)) ")
			  .append("WHERE C_BPartner_ID IS NULL AND C_Region_ID IS NULL AND RegionName IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Region Default=" + no);
		//
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("Set C_Region_ID=(SELECT C_Region_ID FROM C_Region r")
			  .append(" WHERE r.Name=o.RegionName AND r.C_Country_ID=o.C_Country_ID")
			  .append(" AND r.AD_Client_ID IN (0, o.AD_Client_ID)) ")
			  .append("WHERE C_BPartner_ID IS NULL AND C_Region_ID IS NULL AND RegionName IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Region=" + no);
		//
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Region, ' ")
			  .append("WHERE C_BPartner_ID IS NULL AND C_Region_ID IS NULL ")
			  .append(" AND EXISTS (SELECT * FROM C_Country c")
			  .append(" WHERE c.C_Country_ID=o.C_Country_ID AND c.HasRegion='Y')")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Region=" + no);

		//	Product
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p")
			  .append(" WHERE o.ProductValue=p.Value AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE M_Product_ID IS NULL AND ProductValue IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Product from Value=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p")
			  .append(" WHERE o.UPC=p.UPC AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE M_Product_ID IS NULL AND UPC IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Product from UPC=" + no);
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET M_Product_ID=(SELECT MAX(M_Product_ID) FROM M_Product p")
			  .append(" WHERE o.SKU=p.SKU AND o.AD_Client_ID=p.AD_Client_ID) ")
			  .append("WHERE M_Product_ID IS NULL AND SKU IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Product fom SKU=" + no);
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Product, ' ")
			  .append("WHERE M_Product_ID IS NULL AND (ProductValue IS NOT NULL OR UPC IS NOT NULL OR SKU IS NOT NULL)")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Product=" + no);

		//	Charge
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_Charge_ID=(SELECT C_Charge_ID FROM C_Charge c")
			  .append(" WHERE o.ChargeName=c.Name AND o.AD_Client_ID=c.AD_Client_ID) ")
			  .append("WHERE C_Charge_ID IS NULL AND ChargeName IS NOT NULL AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Charge=" + no);
		sql = new StringBuilder ("UPDATE I_Order ")
				  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Charge, ' ")
				  .append("WHERE C_Charge_ID IS NULL AND (ChargeName IS NOT NULL)")
				  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Charge=" + no);
		//
		
		sql = new StringBuilder ("UPDATE I_Order ")
				  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Product and Charge, ' ")
				  .append("WHERE M_Product_ID IS NOT NULL AND C_Charge_ID IS NOT NULL ")
				  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Product and Charge exclusive=" + no);

		//	Tax
		sql = new StringBuilder ("UPDATE I_Order o ")
			  .append("SET C_Tax_ID=(SELECT MAX(C_Tax_ID) FROM C_Tax t")
			  .append(" WHERE o.TaxIndicator=t.TaxIndicator AND o.AD_Client_ID=t.AD_Client_ID) ")
			  .append("WHERE C_Tax_ID IS NULL AND TaxIndicator IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("Set Tax=" + no);
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Tax, ' ")
			  .append("WHERE C_Tax_ID IS NULL AND TaxIndicator IS NOT NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Invalid Tax=" + no);
		
		// Duplicated Order
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Duplicated Order' ")
			  .append("WHERE DocumentNo||C_DocType_ID IN (SELECT DocumentNo||C_DocType_ID FROM C_Order WHERE DocStatus = 'CO')")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("Duplicated Order=" + no);
		commitEx();
		
		//	-- New BPartner ---------------------------------------------------
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<X_I_Order> implist = new Query(getCtx(), X_I_Order.Table_Name, "I_IsImported='N' AND C_BPartner_ID IS NULL"+linefilter, get_TrxName())
					.setOrderBy("documentno,bpartnervalue").list();
		if(implist != null)
		for(X_I_Order imp:implist){
			if (imp.getBPartnerValue () == null)
			{
				if (imp.getEMail () != null)
					imp.setBPartnerValue (imp.getEMail ());
				else if (imp.getName () != null)
					imp.setBPartnerValue (imp.getName ());
				else
					continue;
			}
			if (imp.getName () == null)
			{
				if (imp.getContactName () != null)
					imp.setName (imp.getContactName ());
				else
					imp.setName (imp.getBPartnerValue ());
			}
			//	BPartner
			//MBPartner bp = MBPartner.get (getCtx(), imp.getBPartnerValue());
			MBPartner bp = MBPartner.get (getCtx(), imp.getBPartnerValue(), get_TrxName());
			if (bp == null)
			{
				bp = new MBPartner (getCtx (), -1, get_TrxName());
				bp.setClientOrg (imp.getAD_Client_ID (), imp.getAD_Org_ID ());
				bp.setValue (imp.getBPartnerValue ());
				bp.setTaxID(imp.getBPartnerValue ());
				
				if(MSysConfig.getValue("POSIntegrationIsLCOActive").compareTo("Y")==0){
					//Asingnar tipo de identificación RIF cuando empieza en (J,V,E,G)
					if (String.valueOf(imp.getBPartnerValue ().charAt(0)).contains("J") || 
							String.valueOf(imp.getBPartnerValue ().charAt(0)).contains("V") ||
							String.valueOf(imp.getBPartnerValue ().charAt(0)).contains("E") ||
							String.valueOf(imp.getBPartnerValue ().charAt(0)).contains("G") )
						bp.set_CustomColumn("LCO_TaxIdType_ID", 1000001);
					else
						bp.set_CustomColumn("LCO_TaxIdType_ID", 1000000);
				
				}
				bp.setName (imp.getName ());
				bp.setIsCustomer(true);
				boolean setBPsalesRep = MSysConfig.getBooleanValue("POSIntegration_setBpSalesRep",false, getProcessInfo().getAD_Client_ID());
				if(setBPsalesRep==true && bp.getSalesRep_ID()==0)
					bp.setSalesRep_ID(imp.getSalesRep_ID());
				try{
				if(imp.get_ValueAsString("BPartnerGroupValue")!=null || !imp.get_ValueAsString("BPartnerGroupValue").isEmpty()){
					MBPGroup BPGroup = new Query(getCtx(), MBPGroup.Table_Name, " Value = '"+imp.get_ValueAsString("BPartnerGroupValue")+"'", get_TrxName()).first();
					bp.setBPGroup(BPGroup);
				}
				}catch(Exception e){
					//Keep Going
				}
				
				if (!bp.save ())
					continue;
			}
			else{
				
			}
			imp.setC_BPartner_ID (bp.getC_BPartner_ID ());
//			BP 
			MBPartnerLocation bpl = null; 
			MBPartnerLocation[] bpls = bp.getLocations(true);
			for (int i = 0; bpl == null && i < bpls.length; i++)
			{
				if (imp.getCity().compareTo(bpls[i].getName())==0)
					bpl = bpls[i];
				else if (imp.getC_BPartner_Location_ID() == bpls[i].getC_BPartner_Location_ID())
					bpl = bpls[i];
				//	Same Location ID
				else if (imp.getC_Location_ID() == bpls[i].getC_Location_ID())
					bpl = bpls[i];
				//	Same Location Info
				else if (imp.getC_Location_ID() == 0)
				{
					MLocation loc = bpls[i].getLocation(false);
					if (loc.equals(imp.getC_Country_ID(), imp.getC_Region_ID(), 
							imp.getPostal(), "", imp.getCity(), 
							imp.getAddress1(), imp.getAddress2()))
						bpl = bpls[i];
				}
			}
			if (bpl == null)
			{
				//	New Location
				MLocation loc = new MLocation (getCtx (), 0, get_TrxName());
				loc.setAddress1 (imp.getAddress1 ());
				loc.setAddress2 (imp.getAddress2 ());
				loc.setCity (imp.getCity ());
				loc.setPostal (imp.getPostal ());
				if (imp.getC_Region_ID () != 0)
					loc.setC_Region_ID (imp.getC_Region_ID ());
				loc.setC_Country_ID (imp.getC_Country_ID ());
				if (!loc.save ())
					continue;
				//
				bpl = new MBPartnerLocation (bp);
				bpl.setC_Location_ID (loc.getC_Location_ID ());
				if (!bpl.save ())
					continue;
			}
			imp.setC_Location_ID (bpl.getC_Location_ID ());
			imp.setBillTo_ID (bpl.getC_BPartner_Location_ID ());
			imp.setC_BPartner_Location_ID (bpl.getC_BPartner_Location_ID ());
			
			//	User/Contact
			if (imp.getContactName () != null 
				|| imp.getEMail () != null 
				|| imp.getPhone () != null)
			{
				MUser[] users = bp.getContacts(true);
				MUser user = null;
				for (int i = 0; user == null && i < users.length;  i++)
				{
					String name = users[i].getName();
					if (name.equals(imp.getContactName()) 
						|| name.equals(imp.getName()))
					{
						user = users[i];
						imp.setAD_User_ID (user.getAD_User_ID ());
					}
				}
				if (user == null)
				{
					user = new MUser (bp);
					if (imp.getContactName () == null)
						user.setName (imp.getName ());
					else
						user.setName (imp.getContactName ());
					user.setEMail (imp.getEMail ());
					user.setPhone (imp.getPhone ());
					try{
					if(imp.get_ValueAsString("Phone2")!=null)
						user.setPhone2 (imp.get_ValueAsString("Phone2"));
					if(imp.get_Value("Birthday")!=null)
						user.setBirthday((Timestamp)imp.get_Value("Birthday"));
					}catch(Exception e){
						//Keep Going
					}
					if (user.save ())
						imp.setAD_User_ID (user.getAD_User_ID ());
				}
			}
			imp.save ();
			commitEx();
		}
		
/*
		//	Go through Order Records w/o C_BPartner_ID
		sql = new StringBuilder ("SELECT max(I_Order_ID) I_Order_ID FROM I_Order ")
			  .append("WHERE I_IsImported='N' AND C_BPartner_ID IS NULL").append (clientCheck).append(" GROUP BY bpartnervalue");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				X_I_Order imp = new X_I_Order (getCtx (), rs.getInt("I_Order_ID"), get_TrxName());
				if (imp.getBPartnerValue () == null)
				{
					if (imp.getEMail () != null)
						imp.setBPartnerValue (imp.getEMail ());
					else if (imp.getName () != null)
						imp.setBPartnerValue (imp.getName ());
					else
						continue;
				}
				if (imp.getName () == null)
				{
					if (imp.getContactName () != null)
						imp.setName (imp.getContactName ());
					else
						imp.setName (imp.getBPartnerValue ());
				}
				//	BPartner
				MBPartner bp = MBPartner.get (getCtx(), imp.getBPartnerValue());
				if (bp == null)
				{
					bp = new MBPartner (getCtx (), -1, get_TrxName());
					bp.setClientOrg (imp.getAD_Client_ID (), imp.getAD_Org_ID ());
					bp.setValue (imp.getBPartnerValue ());
					bp.setName (imp.getName ());
					bp.setIsCustomer(true);
					if (!bp.save ())
						continue;
				}
				imp.setC_BPartner_ID (bp.getC_BPartner_ID ());
				
				//	BP 
				MBPartnerLocation bpl = null; 
				MBPartnerLocation[] bpls = bp.getLocations(true);
				for (int i = 0; bpl == null && i < bpls.length; i++)
				{
					if (imp.getC_BPartner_Location_ID() == bpls[i].getC_BPartner_Location_ID())
						bpl = bpls[i];
					//	Same Location ID
					else if (imp.getC_Location_ID() == bpls[i].getC_Location_ID())
						bpl = bpls[i];
					//	Same Location Info
					else if (imp.getC_Location_ID() == 0)
					{
						MLocation loc = bpls[i].getLocation(false);
						if (loc.equals(imp.getC_Country_ID(), imp.getC_Region_ID(), 
								imp.getPostal(), "", imp.getCity(), 
								imp.getAddress1(), imp.getAddress2()))
							bpl = bpls[i];
					}
				}
				if (bpl == null)
				{
					//	New Location
					MLocation loc = new MLocation (getCtx (), 0, get_TrxName());
					loc.setAddress1 (imp.getAddress1 ());
					loc.setAddress2 (imp.getAddress2 ());
					loc.setCity (imp.getCity ());
					loc.setPostal (imp.getPostal ());
					if (imp.getC_Region_ID () != 0)
						loc.setC_Region_ID (imp.getC_Region_ID ());
					loc.setC_Country_ID (imp.getC_Country_ID ());
					if (!loc.save ())
						continue;
					//
					bpl = new MBPartnerLocation (bp);
					bpl.setC_Location_ID (loc.getC_Location_ID ());
					if (!bpl.save ())
						continue;
				}
				imp.setC_Location_ID (bpl.getC_Location_ID ());
				imp.setBillTo_ID (bpl.getC_BPartner_Location_ID ());
				imp.setC_BPartner_Location_ID (bpl.getC_BPartner_Location_ID ());
				
				//	User/Contact
				if (imp.getContactName () != null 
					|| imp.getEMail () != null 
					|| imp.getPhone () != null)
				{
					MUser[] users = bp.getContacts(true);
					MUser user = null;
					for (int i = 0; user == null && i < users.length;  i++)
					{
						String name = users[i].getName();
						if (name.equals(imp.getContactName()) 
							|| name.equals(imp.getName()))
						{
							user = users[i];
							imp.setAD_User_ID (user.getAD_User_ID ());
						}
					}
					if (user == null)
					{
						user = new MUser (bp);
						if (imp.getContactName () == null)
							user.setName (imp.getName ());
						else
							user.setName (imp.getContactName ());
						user.setEMail (imp.getEMail ());
						user.setPhone (imp.getPhone ());
						if (user.save ())
							imp.setAD_User_ID (user.getAD_User_ID ());
					}
				}
				imp.save ();
				sql = new StringBuilder ("UPDATE I_Order ")
				  .append("SET C_BPartner_ID = ")
				  .append(bp.getC_BPartner_ID())
				  .append("SET")
				  .append(" WHERE BPartnerValue = '")
				  .append(imp.getBPartnerValue())
				  .append("' AND I_IsImported<>'Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			}	//	for all new BPartners
			//
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "BP - " + sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		*/
		sql = new StringBuilder ("UPDATE I_Order ")
			  .append("SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No BPartner, ' ")
			  .append("WHERE C_BPartner_ID IS NULL")
			  .append(" AND I_IsImported<>'Y'").append (clientCheck);
		sql.append(linefilter);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning ("No BPartner=" + no);

		commitEx();
		
		//	-- New Orders -----------------------------------------------------

		int noInsert = 0;
		int noInsertLine = 0;

		//	Go through Order Records w/o
		sql = new StringBuilder ("SELECT IO.* FROM I_Order IO INNER JOIN (SELECT DocumentNo,C_DocType_ID,COUNT(opos_line) as numberoflines ")
				.append(" FROM I_Order GROUP BY DocumentNo,C_DocType_ID) Temp_IO ON Temp_IO.DocumentNo=IO.DocumentNo AND Temp_IO.C_DocType_ID=IO.C_DocType_ID")
			  .append(" WHERE IO.I_IsImported='N' AND IO.DocumentNo||IO.C_DocType_ID NOT IN (SELECT DocumentNo||C_DocType_ID FROM C_Order WHERE DocStatus = 'CO') " )
			 .append(" AND IO.opos_numberoflines=Temp_IO.numberoflines").append (clientCheck);
		sql.append(linefilter);
		sql.append(" ORDER BY IO.C_BPartner_ID, IO.BillTo_ID, IO.C_BPartner_Location_ID, IO.I_Order_ID");
		
		/*SELECT IO.* FROM I_Order IO INNER JOIN (SELECT DocumentNo,COUNT(opos_line) as numberoflines 
				FROM I_Order GROUP BY DocumentNo) Temp_IO ON Temp_IO.DocumentNo=IO.DocumentNo WHERE 
				IO.opos_numberoflines!=Temp_IO.numberoflines ORDER BY IO.C_BPartner_ID, IO.BillTo_ID, 
				IO.C_BPartner_Location_ID, IO.I_Order_ID Limit 100;*/

		long time_start, time_end;
		time_start = System.currentTimeMillis();
		
		List<X_I_Order> imps = new Query(getCtx(), X_I_Order.Table_Name, 
				"I_Order.I_IsImported='N' AND I_Order.DocumentNo||I_Order.C_DocType_ID NOT IN "
				+ "(SELECT DocumentNo||C_DocType_ID FROM C_Order WHERE DocStatus = 'CO') AND I_Order.opos_numberoflines=Temp_IO.numberoflines"
				+ clientCheck+ linefilter,get_TrxName())
				.addJoinClause("INNER JOIN (SELECT DocumentNo,C_DocType_ID,COUNT(opos_line) as numberoflines FROM I_Order "
				+ "GROUP BY DocumentNo,C_DocType_ID) Temp_IO ON Temp_IO.DocumentNo=I_Order.DocumentNo AND Temp_IO.C_DocType_ID=I_Order.C_DocType_ID")
				.setOrderBy(" ORDER BY I_Order.C_BPartner_ID, I_Order.BillTo_ID, I_Order.C_BPartner_Location_ID, I_Order.I_Order_ID")
				.list();
		int oldC_BPartner_ID = 0;
		int oldBillTo_ID = 0;
		int oldC_BPartner_Location_ID = 0;
		String oldDocumentNo = "";
		//
		MOrder order = null;
		M_C_Invoice_Fiscal fiscal = null;
		int lineNo = 0;
		String msgPayment=null;
		for(X_I_Order imp:imps){
			String cmpDocumentNo = imp.getDocumentNo();
			if (cmpDocumentNo == null)
				cmpDocumentNo = "";
			//	New Order
			if (oldC_BPartner_ID != imp.getC_BPartner_ID() 
				|| oldC_BPartner_Location_ID != imp.getC_BPartner_Location_ID()
				|| oldBillTo_ID != imp.getBillTo_ID() 
				|| !oldDocumentNo.equals(cmpDocumentNo))
			{
				if (order != null)
				{
					if (m_docAction != null && m_docAction.length() > 0)
					{
						order.setDocAction(m_docAction);
						if(!order.processIt (m_docAction)) {
							log.warning("Order Process Failed: " + order + " - " + order.getProcessMsg());
							throw new IllegalStateException("Order Process Failed: " + order + " - " + order.getProcessMsg());							
						}						
					}
					order.saveEx();
					commitEx();
					msgPayment=createAllocation(order);
				}
				oldC_BPartner_ID = imp.getC_BPartner_ID();
				oldC_BPartner_Location_ID = imp.getC_BPartner_Location_ID();
				oldBillTo_ID = imp.getBillTo_ID();
				oldDocumentNo = imp.getDocumentNo();
				if (oldDocumentNo == null)
					oldDocumentNo = "";
				//
				order = new MOrder (getCtx(), 0, get_TrxName());
				order.setClientOrg (imp.getAD_Client_ID(), imp.getAD_Org_ID());
				order.setC_DocTypeTarget_ID(imp.getC_DocType_ID());
				order.setIsSOTrx(imp.isSOTrx());
				if (imp.getDeliveryRule() != null ) {
					order.setDeliveryRule(imp.getDeliveryRule());
				}
				if (imp.get_Value("PaymentRule") != null ) {
					order.set_CustomColumn("PaymentRule", imp.get_Value("PaymentRule"));
				}
				else order.setPaymentRule("B");
				
				if (imp.getDocumentNo() != null)
					order.setDocumentNo(imp.getDocumentNo());
				//	Ship Partner
				order.setC_BPartner_ID(imp.getC_BPartner_ID());
				order.setC_BPartner_Location_ID(imp.getC_BPartner_Location_ID());
				if (imp.getAD_User_ID() != 0)
					order.setAD_User_ID(imp.getAD_User_ID());
				//	Bill Partner
				order.setBill_BPartner_ID(imp.getC_BPartner_ID());
				order.setBill_Location_ID(imp.getBillTo_ID());
				//
				if (imp.getDescription() != null)
					order.setDescription(imp.getDescription());
				order.setC_PaymentTerm_ID(imp.getC_PaymentTerm_ID());
				order.setM_PriceList_ID(imp.getM_PriceList_ID());
				order.setM_Warehouse_ID(imp.getM_Warehouse_ID());
				if (imp.getM_Shipper_ID() != 0)
					order.setM_Shipper_ID(imp.getM_Shipper_ID());
				//	SalesRep from Import or the person running the import
				if (imp.getSalesRep_ID() != 0)
					order.setSalesRep_ID(imp.getSalesRep_ID());
				if (order.getSalesRep_ID() == 0)
					order.setSalesRep_ID(getAD_User_ID());
				//
				if (imp.getAD_OrgTrx_ID() != 0)
					order.setAD_OrgTrx_ID(imp.getAD_OrgTrx_ID());
				if (imp.getC_Activity_ID() != 0)
					order.setC_Activity_ID(imp.getC_Activity_ID());
				if (imp.getC_Campaign_ID() != 0)
					order.setC_Campaign_ID(imp.getC_Campaign_ID());
				if (imp.getC_Project_ID() != 0)
					order.setC_Project_ID(imp.getC_Project_ID());
				//
				if (imp.getDateOrdered() != null)
					order.setDateOrdered(imp.getDateOrdered());
				if (imp.getDateAcct() != null)
					order.setDateAcct(imp.getDateAcct());
				
				// Set Order Source
				if (imp.getC_OrderSource() != null)
					order.setC_OrderSource_ID(imp.getC_OrderSource_ID());
				//
				order.saveEx();				
				fiscal = new M_C_Invoice_Fiscal(getCtx(), 0, get_TrxName());				
				if(!imp.get_ValueAsString("fiscal_invoicenumber").isEmpty()){
					fiscal.setfiscal_invoicenumber(imp.get_ValueAsString("fiscal_invoicenumber"));
					fiscal.setfiscalprint_serial(imp.get_ValueAsString("fiscalprint_serial"));
					fiscal.setfiscal_zreport(imp.get_ValueAsString("fiscal_zreport"));
					fiscal.setC_Order_ID(order.getC_Order_ID());
					fiscal.setAD_Org_ID(order.getAD_Org_ID());
					try{
						fiscal.save();
					}
					catch(Exception e){
						log.log(Level.WARNING, "No se Pudo crear registro Fiscal de Orden Nro: "+order.getDocumentNo());
					}
				}
				else 
					log.log(Level.WARNING, "No se importo factura fiscal para Orden Nro: "+order.getDocumentNo());				
				noInsert++;
				lineNo = 10;
			}
			imp.setC_Order_ID(order.getC_Order_ID());
			//	New OrderLine
			MOrderLine line = new MOrderLine (order);
			line.setLine(lineNo);
			
			lineNo += 10;
			if (imp.getM_Product_ID() != 0)
				line.setM_Product_ID(imp.getM_Product_ID(), true);
			
			if (imp.getC_Charge_ID() != 0)
				line.setC_Charge_ID(imp.getC_Charge_ID());
			
			line.setQty(imp.getQtyOrdered());
			line.setPrice();
			
			if (imp.getPriceActual().compareTo(Env.ZERO) != 0)
				line.setDiscount(BigDecimal.valueOf(100));
			//BigDecimal test=imp.getPriceActual();
			
			line.setPrice(imp.getPriceActual());
						
			if (imp.getC_Tax_ID() != 0)
				line.setC_Tax_ID(imp.getC_Tax_ID());
			else
			{
				line.setTax();
				imp.setC_Tax_ID(line.getC_Tax_ID());
			}
			if (imp.getFreightAmt() != null)
				line.setFreightAmt(imp.getFreightAmt());
			
			if (imp.getLineDescription() != null)
				line.setDescription(imp.getLineDescription());
			
			line.setLineNetAmt(line.getQtyEntered().multiply(line.getPriceEntered()));
			
			line.saveEx();
			imp.setC_OrderLine_ID(line.getC_OrderLine_ID());
			imp.setI_IsImported(true);
			imp.setProcessed(true);
			//
			if (imp.save())
				noInsertLine++;
		}
		
		if (order != null)
		{
			
			if (m_docAction != null && m_docAction.length() > 0)
			{
				order.setDocAction(m_docAction);
				if(!order.processIt (m_docAction)) {
					log.warning("Order Process Failed: " + order + " - " + order.getProcessMsg());
					throw new IllegalStateException("Order Process Failed: " + order + " - " + order.getProcessMsg());
					
				}
			}
			if(fiscal.getfiscal_invoicenumber() != null){
				fiscal.setC_Invoice_ID(order.getC_Invoice_ID());
				try{
					fiscal.save();
				}
				catch(Exception e){ }
			}
			
			order.saveEx();
			msgPayment=createAllocation(order);
			commitEx();
			//para que ejecute el proceso en la ultima orden
			

		}
		
		/*
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			rs = pstmt.executeQuery ();
			//
			int oldC_BPartner_ID = 0;
			int oldBillTo_ID = 0;
			int oldC_BPartner_Location_ID = 0;
			String oldDocumentNo = "";
			//
			MOrder order = null;
			M_C_Invoice_Fiscal fiscal = null;
			int lineNo = 0;
			String msgPayment=null;
			while (rs.next ())
			{
				X_I_Order imp = new X_I_Order (getCtx (), rs, get_TrxName());

				String cmpDocumentNo = imp.getDocumentNo();
				if (cmpDocumentNo == null)
					cmpDocumentNo = "";
				//	New Order
				if (oldC_BPartner_ID != imp.getC_BPartner_ID() 
					|| oldC_BPartner_Location_ID != imp.getC_BPartner_Location_ID()
					|| oldBillTo_ID != imp.getBillTo_ID() 
					|| !oldDocumentNo.equals(cmpDocumentNo))
				{
					if (order != null)
					{
						if (m_docAction != null && m_docAction.length() > 0)
						{
							order.setDocAction(m_docAction);
							if(!order.processIt (m_docAction)) {
								log.warning("Order Process Failed: " + order + " - " + order.getProcessMsg());
								throw new IllegalStateException("Order Process Failed: " + order + " - " + order.getProcessMsg());
								
							}
							
							
							
						}
						order.saveEx();
						commitEx();
						msgPayment=createAllocation(order);
					}
					oldC_BPartner_ID = imp.getC_BPartner_ID();
					oldC_BPartner_Location_ID = imp.getC_BPartner_Location_ID();
					oldBillTo_ID = imp.getBillTo_ID();
					oldDocumentNo = imp.getDocumentNo();
					if (oldDocumentNo == null)
						oldDocumentNo = "";
					//

					order = new MOrder (getCtx(), 0, get_TrxName());
					order.setClientOrg (imp.getAD_Client_ID(), imp.getAD_Org_ID());
					order.setC_DocTypeTarget_ID(imp.getC_DocType_ID());
					order.setIsSOTrx(imp.isSOTrx());
					if (imp.getDeliveryRule() != null ) {
						order.setDeliveryRule(imp.getDeliveryRule());
					}
					if (imp.get_Value("PaymentRule") != null ) {
						order.set_CustomColumn("PaymentRule", imp.get_Value("PaymentRule"));
					}
					if (imp.getDocumentNo() != null)
						order.setDocumentNo(imp.getDocumentNo());
					//	Ship Partner
					order.setC_BPartner_ID(imp.getC_BPartner_ID());
					order.setC_BPartner_Location_ID(imp.getC_BPartner_Location_ID());
					if (imp.getAD_User_ID() != 0)
						order.setAD_User_ID(imp.getAD_User_ID());
					//	Bill Partner
					order.setBill_BPartner_ID(imp.getC_BPartner_ID());
					order.setBill_Location_ID(imp.getBillTo_ID());
					//
					if (imp.getDescription() != null)
						order.setDescription(imp.getDescription());
					order.setC_PaymentTerm_ID(imp.getC_PaymentTerm_ID());
					order.setM_PriceList_ID(imp.getM_PriceList_ID());
					order.setM_Warehouse_ID(imp.getM_Warehouse_ID());
					if (imp.getM_Shipper_ID() != 0)
						order.setM_Shipper_ID(imp.getM_Shipper_ID());
					//	SalesRep from Import or the person running the import
					if (imp.getSalesRep_ID() != 0)
						order.setSalesRep_ID(imp.getSalesRep_ID());
					if (order.getSalesRep_ID() == 0)
						order.setSalesRep_ID(getAD_User_ID());
					//
					if (imp.getAD_OrgTrx_ID() != 0)
						order.setAD_OrgTrx_ID(imp.getAD_OrgTrx_ID());
					if (imp.getC_Activity_ID() != 0)
						order.setC_Activity_ID(imp.getC_Activity_ID());
					if (imp.getC_Campaign_ID() != 0)
						order.setC_Campaign_ID(imp.getC_Campaign_ID());
					if (imp.getC_Project_ID() != 0)
						order.setC_Project_ID(imp.getC_Project_ID());
					//
					if (imp.getDateOrdered() != null)
						order.setDateOrdered(imp.getDateOrdered());
					if (imp.getDateAcct() != null)
						order.setDateAcct(imp.getDateAcct());
					
					// Set Order Source
					if (imp.getC_OrderSource() != null)
						order.setC_OrderSource_ID(imp.getC_OrderSource_ID());
					//
					order.saveEx();
					
					fiscal = new M_C_Invoice_Fiscal(getCtx(), 0, get_TrxName());
					
					if(!imp.get_ValueAsString("fiscal_invoicenumber").isEmpty()){
						fiscal.setfiscal_invoicenumber(imp.get_ValueAsString("fiscal_invoicenumber"));
						fiscal.setfiscalprint_serial(imp.get_ValueAsString("fiscalprint_serial"));
						fiscal.setfiscal_zreport(imp.get_ValueAsString("fiscal_zreport"));
						fiscal.setC_Order_ID(order.getC_Order_ID());
						fiscal.setAD_Org_ID(order.getAD_Org_ID());
						try{
							fiscal.save();
						}
						catch(Exception e){
							System.out.println("No se Pudo crear registro Fiscal de Orden Nro: "+order.getDocumentNo());
						}
					}
					else 
						System.out.println("No se importo factura fiscal para Orden Nro: "+order.getDocumentNo());
					
					noInsert++;
					lineNo = 10;
				}
				imp.setC_Order_ID(order.getC_Order_ID());
				//	New OrderLine
				MOrderLine line = new MOrderLine (order);
				line.setLine(lineNo);
				
				lineNo += 10;
				if (imp.getM_Product_ID() != 0)
					line.setM_Product_ID(imp.getM_Product_ID(), true);
				
				if (imp.getC_Charge_ID() != 0)
					line.setC_Charge_ID(imp.getC_Charge_ID());
				
				line.setQty(imp.getQtyOrdered());
				line.setPrice();
				
				if (imp.getPriceActual().compareTo(Env.ZERO) != 0)
					line.setDiscount(BigDecimal.valueOf(100));
				//BigDecimal test=imp.getPriceActual();
				
				line.setPrice(imp.getPriceActual());
				
				
				if (imp.getC_Tax_ID() != 0)
					line.setC_Tax_ID(imp.getC_Tax_ID());
				else
				{
					line.setTax();
					imp.setC_Tax_ID(line.getC_Tax_ID());
				}
				if (imp.getFreightAmt() != null)
					line.setFreightAmt(imp.getFreightAmt());
				
				if (imp.getLineDescription() != null)
					line.setDescription(imp.getLineDescription());
				
				line.setLineNetAmt(line.getQtyEntered().multiply(line.getPriceEntered()));
				
				line.saveEx();
				imp.setC_OrderLine_ID(line.getC_OrderLine_ID());
				imp.setI_IsImported(true);
				imp.setProcessed(true);
				//
				if (imp.save())
					noInsertLine++;
			}
			if (order != null)
			{
				
				if (m_docAction != null && m_docAction.length() > 0)
				{
					order.setDocAction(m_docAction);
					if(!order.processIt (m_docAction)) {
						log.warning("Order Process Failed: " + order + " - " + order.getProcessMsg());
						throw new IllegalStateException("Order Process Failed: " + order + " - " + order.getProcessMsg());
						
					}
				}
				if(fiscal.getfiscal_invoicenumber() != null){
					fiscal.setC_Invoice_ID(order.getC_Invoice_ID());
					try{
						fiscal.save();
					}
					catch(Exception e){ }
				}
				
				order.saveEx();
				msgPayment=createAllocation(order);
				commitEx();
				//para que ejecute el proceso en la ultima orden
				

			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Order - " + sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		*/
		time_end = System.currentTimeMillis();
		System.out.println("EL PROCESO DURÓ :"+ ( time_end - time_start ) +" milisegundos");
		
		
		//	Set Error to indicator to not imported
		sql = new StringBuilder ("UPDATE I_Order ")
			.append("SET I_IsImported='N', Updated=SysDate ")
			.append("WHERE I_IsImported<>'Y'").append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		//
		addLog (0, null, new BigDecimal (noInsert), "@C_Order_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noInsertLine), "@C_OrderLine_ID@: @Inserted@");
		StringBuilder msgreturn = new StringBuilder("#").append(noInsert).append("/").append(noInsertLine);
		return msgreturn.toString();
	}	//	doIt


	private String createAllocation(MOrder order) {
		int count=0;
		if(order.getPaymentRule().compareTo("P")==0){
			log.warning("Vale Detectado en Orden "+order.getDocumentNo());
			while(order.getC_Invoice_ID()==0 && count <5){
				try {
					Thread.sleep(2000);
					count++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			boolean complete=false;
			log.warning("Verifique que ya existe el Numero de factura "+order.getC_Invoice_ID());
			count=0;
			while(!complete && count <5){
				MInvoice tempinv=new MInvoice(getCtx(), order.getC_Invoice_ID(), get_TrxName());
				if(tempinv!=null){
					if(tempinv.getDocStatus().compareTo("CO")==0){
						complete=true;
					}
				}
				System.out.println(tempinv.getDocStatus());
				try {
					Thread.sleep(2000);
					count++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			MInvoice tempinv=new MInvoice(getCtx(), order.getC_Invoice_ID(), get_TrxName());
			if(tempinv!=null){
				if(tempinv.getDocStatus().compareTo("CO")!=0){
					log.severe("Luego de 10 intentos la factura no estaba completada. Error");
					return "Luego de 10 intentos la factura no estaba completada. Error";
				}
			}
			log.warning("La factura esta completada");
			MAllocationHdr Allocation = new MAllocationHdr(getCtx(), Integer.parseInt(order.getDescription()), get_TrxName());
			List<MAllocationLine> querya = null;
			BigDecimal totalAllocated = BigDecimal.ZERO;
			 	querya = new Query(Env.getCtx(),MAllocationLine.Table_Name,MAllocationLine.COLUMNNAME_C_AllocationHdr_ID+
				 " = ? ",get_TrxName())
			 		.setParameters(Allocation.getC_AllocationHdr_ID())
			 		.list();
			 	for (MAllocationLine AllocationLine:querya) {
			 	totalAllocated = totalAllocated.add(AllocationLine.getAmount());
			 	//System.out.println("totalAllocated = "+totalAllocated);
			 	}
			MAllocationLine AllocationLine = new MAllocationLine(Allocation);
			AllocationLine.setAD_Org_ID(order.getAD_Org_ID());
			AllocationLine.setC_Order_ID(order.getC_Order_ID());
			AllocationLine.setC_Invoice_ID(order.getC_Invoice_ID());
			AllocationLine.setAmount(totalAllocated.negate());
			AllocationLine.setC_BPartner_ID(order.getC_BPartner_ID());
			AllocationLine.saveEx();
			Allocation.setDocAction(MAllocationHdr.DOCACTION_Complete);	
			if(!Allocation.processIt(MAllocationHdr.DOCACTION_Complete)) {
				log.severe("Allocation Process Failed: " + order + " - " + Allocation.getProcessMsg());
				throw new IllegalStateException("Allocation Process Failed: " + Allocation + " - " + Allocation.getProcessMsg());
			}
			Allocation.saveEx();
			//MOrder Order = new MOrder(getCtx(), p_C_Order_ID, get_TrxName());
			//Order.getC_Invoice_ID();
			String linefilter = "AD_Org_ID=? AND C_Currency_ID=?";
			MBankAccount ba = new Query(getCtx(), MBankAccount.Table_Name, linefilter, get_TrxName())
			.setParameters(order.getAD_Org_ID(), order.getC_Currency_ID())
			.setOrderBy("IsDefault DESC")
			.first();
			if(totalAllocated.setScale(2, RoundingMode.CEILING).abs().compareTo(order.getGrandTotal().setScale(2, RoundingMode.CEILING).abs())!=0){
				MPayment Payment = new MPayment(getCtx(), 0, get_TrxName());
				Payment.setAD_Org_ID(order.getAD_Org_ID());
				Payment.setC_BPartner_ID(order.getC_BPartner_ID());
				Payment.setC_BankAccount_ID(ba.getC_BankAccount_ID());
				Payment.setC_Currency_ID(order.getC_Currency_ID());
				Payment.setDescription("PAGO POR LA DIFERENCIA DE NOTAS DE CREDITO ASIGNADAS = "+totalAllocated+" VS TOTAL ORDEN = "+order.getGrandTotal());
				Payment.setC_DocType_ID(true);
				Payment.setTenderType(MPayment.TENDERTYPE_Cash);
				//PAGO POR LA DIFERENCIA DE NOTAS DE CREDITO ASIGNADAS VS TOTAL ORDEN
				Payment.setPayAmt(order.getGrandTotal().add(totalAllocated));
				Payment.setC_Invoice_ID(order.getC_Invoice_ID());
				//		Save payment
				Payment.saveEx();

				Payment.setDocAction(MPayment.DOCACTION_Complete);
				if (!Payment.processIt (MPayment.DOCACTION_Complete)){
					return "Cannot Complete the Payment :" + Payment;
				}
				Payment.saveEx();
			}
			else {
				if(Allocation.getDocStatus() == "CO"){
					MInvoice invoice = new MInvoice(getCtx(), order.getC_Invoice_ID(), get_TrxName());
					invoice.setIsPaid(true);
					invoice.saveEx();	
				}						
			}

		}
		return "Payment Ok";
	}

}	//	ImportOrder
