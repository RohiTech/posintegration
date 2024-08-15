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
package com.ghintech.puntocom.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_Invoice_Fiscal
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_C_Invoice_Fiscal 
{

    /** TableName=C_Invoice_Fiscal */
    public static final String Table_Name = "C_Invoice_Fiscal";

    /** AD_Table_ID=1000020 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_Invoice_Fiscal_ID */
    public static final String COLUMNNAME_C_Invoice_Fiscal_ID = "C_Invoice_Fiscal_ID";

	/** Set C_Invoice_Fiscal	  */
	public void setC_Invoice_Fiscal_ID (int C_Invoice_Fiscal_ID);

	/** Get C_Invoice_Fiscal	  */
	public int getC_Invoice_Fiscal_ID();

    /** Column name c_invoice_fiscal_UU */
    public static final String COLUMNNAME_c_invoice_fiscal_UU = "c_invoice_fiscal_UU";

	/** Set c_invoice_fiscal_UU	  */
	public void setc_invoice_fiscal_UU (String c_invoice_fiscal_UU);

	/** Get c_invoice_fiscal_UU	  */
	public String getc_invoice_fiscal_UU();

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name fiscal_invoicenumber */
    public static final String COLUMNNAME_fiscal_invoicenumber = "fiscal_invoicenumber";

	/** Set fiscal_invoicenumber	  */
	public void setfiscal_invoicenumber (String fiscal_invoicenumber);

	/** Get fiscal_invoicenumber	  */
	public String getfiscal_invoicenumber();

    /** Column name fiscalprint_serial */
    public static final String COLUMNNAME_fiscalprint_serial = "fiscalprint_serial";

	/** Set fiscalprint_serial	  */
	public void setfiscalprint_serial (String fiscalprint_serial);

	/** Get fiscalprint_serial	  */
	public String getfiscalprint_serial();

    /** Column name fiscal_zreport */
    public static final String COLUMNNAME_fiscal_zreport = "fiscal_zreport";

	/** Set fiscal_zreport	  */
	public void setfiscal_zreport (String fiscal_zreport);

	/** Get fiscal_zreport	  */
	public String getfiscal_zreport();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
    
	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
	
    /** Column name fiscal_zreport_zero */
    public static final String COLUMNNAME_fiscal_zreport_zero = "fiscal_zreport_zero";
    
	/** Set fiscal_zreport_zero.
	  * The Record Contain a Z Report with no transactions
	  */
	public void setfiscal_zreport_zero (boolean fiscal_zreport_zero);

	/** Get fiscal_zreport_zero.
	  * The Record Contain a Z Report with no transactions
	  */
	public boolean getfiscal_zreport_zero();
	
    /** Column name fiscal_zreport_zero */
    public static final String COLUMNNAME_date_zreport_zero = "date_zreport_zero";
    
	/** Set date_zreport_zero.
	  * The date of the Z Report with no transactions
	  */
	public void setdate_zreport_zero (Timestamp date_zreport_zero);

	/** Get fiscal_zreport_zero.
	  * The Record Contain a Z Report with no transactions
	  */
	public Timestamp getdate_zreport_zero();
}
