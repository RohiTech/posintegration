/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package com.ghintech.puntocom.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for C_Invoice_Fiscal
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_C_Invoice_Fiscal extends PO implements I_C_Invoice_Fiscal, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150306L;

    /** Standard Constructor */
    public X_C_Invoice_Fiscal (Properties ctx, int C_Invoice_Fiscal_ID, String trxName)
    {
      super (ctx, C_Invoice_Fiscal_ID, trxName);
      /** if (C_Invoice_Fiscal_ID == 0)
        {
			setC_Invoice_Fiscal_ID (0);
			setC_Invoice_ID (0);
			setC_Order_ID (0);
			setfiscal_invoicenumber (null);
			setfiscalprint_serial (null);
			setfiscal_zreport (null);
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Fiscal (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_Invoice_Fiscal[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_Invoice_Fiscal.
		@param C_Invoice_Fiscal_ID C_Invoice_Fiscal	  */
	public void setC_Invoice_Fiscal_ID (int C_Invoice_Fiscal_ID)
	{
		if (C_Invoice_Fiscal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Fiscal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Fiscal_ID, Integer.valueOf(C_Invoice_Fiscal_ID));
	}

	/** Get C_Invoice_Fiscal.
		@return C_Invoice_Fiscal	  */
	public int getC_Invoice_Fiscal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Fiscal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set c_invoice_fiscal_UU.
		@param c_invoice_fiscal_UU c_invoice_fiscal_UU	  */
	public void setc_invoice_fiscal_UU (String c_invoice_fiscal_UU)
	{
		set_ValueNoCheck (COLUMNNAME_c_invoice_fiscal_UU, c_invoice_fiscal_UU);
	}

	/** Get c_invoice_fiscal_UU.
		@return c_invoice_fiscal_UU	  */
	public String getc_invoice_fiscal_UU () 
	{
		return (String)get_Value(COLUMNNAME_c_invoice_fiscal_UU);
	}

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set fiscal_invoicenumber.
		@param fiscal_invoicenumber fiscal_invoicenumber	  */
	public void setfiscal_invoicenumber (String fiscal_invoicenumber)
	{
		set_Value (COLUMNNAME_fiscal_invoicenumber, fiscal_invoicenumber);
	}

	/** Get fiscal_invoicenumber.
		@return fiscal_invoicenumber	  */
	public String getfiscal_invoicenumber () 
	{
		return (String)get_Value(COLUMNNAME_fiscal_invoicenumber);
	}

	/** Set fiscalprint_serial.
		@param fiscalprint_serial fiscalprint_serial	  */
	public void setfiscalprint_serial (String fiscalprint_serial)
	{
		set_Value (COLUMNNAME_fiscalprint_serial, fiscalprint_serial);
	}

	/** Get fiscalprint_serial.
		@return fiscalprint_serial	  */
	public String getfiscalprint_serial () 
	{
		return (String)get_Value(COLUMNNAME_fiscalprint_serial);
	}

	/** Set fiscal_zreport.
		@param fiscal_zreport fiscal_zreport	  */
	public void setfiscal_zreport (String fiscal_zreport)
	{
		set_Value (COLUMNNAME_fiscal_zreport, fiscal_zreport);
	}

	/** Get fiscal_zreport.
		@return fiscal_zreport	  */
	public String getfiscal_zreport () 
	{
		return (String)get_Value(COLUMNNAME_fiscal_zreport);
	}

	@Override
	public void setfiscal_zreport_zero(boolean fiscal_zreport_zero) {
		set_Value (COLUMNNAME_fiscal_zreport_zero, fiscal_zreport_zero);
		
	}

	@Override
	public boolean getfiscal_zreport_zero() {
		return get_ValueAsBoolean(COLUMNNAME_fiscal_zreport_zero);
	}

	@Override
	public void setdate_zreport_zero(Timestamp date_zreport_zero) {
		set_Value (COLUMNNAME_date_zreport_zero, date_zreport_zero);
		
	}

	@Override
	public Timestamp getdate_zreport_zero() {
		return (Timestamp)get_Value(COLUMNNAME_date_zreport_zero);
	}
}