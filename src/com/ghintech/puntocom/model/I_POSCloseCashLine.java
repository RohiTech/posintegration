package com.ghintech.puntocom.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

public interface I_POSCloseCashLine {
	  /** TableName=POS_Close_Cash_Line */
    public static final String Table_Name = "POS_Close_Cash_Line";

    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);
    
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
	
    /** Column name POS_Close_Cash_Line_UU */
    public static final String COLUMNNAME_POS_Close_Cash_Line_UU = "POS_Close_Cash_Line_UU";

	/** Set POS_Close_Cash_Line_UU	  */
	public void setPOS_Close_Cash_Line_UU (String POS_Close_Cash_Line_UU);

	/** Get POS_Close_Cash_Line_UU	  */
	public String getPOS_Close_Cash_Line_UU();
	
    /** Column name POS_Close_Cash_Line_ID */
    public static final String COLUMNNAME_POS_Close_Cash_Line_ID = "POS_Close_Cash_Line_ID";

	/** Set Close Cash Line.
	  * Close Cash Line
	  */
	public void setPOS_Close_Cash_Line_ID (int POS_Close_Cash_Line_ID);

	/** Get Close Cash Line.
	  * Close Cash Line
	  */
	public int getPOS_Close_Cash_Line_ID();
	  
	/** Column name creditcardamt */
    public static final String COLUMNNAME_creditcardamt = "creditcardamt";

	/** Set creditcardamt.
	  * creditcardamt
	  */
	public void setCreditCardAmt (BigDecimal CreditCardAmt);

	/** Get creditcardamt.
	  * creditcardamt
	  */
	public BigDecimal getCreditCardAmt();
	
	/** Column name debitcardamt */
    public static final String COLUMNNAME_debitcardamt = "debitcardamt";

	/** Set debitcardamt.
	  * debitcardamt
	  */
	public void setDebitCardAmt (BigDecimal DebitCardAmt);

	/** Get debitcardamt.
	  * debitcardamt
	  */
	public BigDecimal getDebitCardAmt();

	/** Column name cashamt */
    public static final String COLUMNNAME_cashamt = "cashamt";

	/** Set cashamt.
	  * cashamt
	  */
	public void setCashAmt (BigDecimal CashAmt);

	/** Get cashamt.
	  * cashamt
	  */
	public BigDecimal getCashAmt();
	
	/** Column name checkamt */
    public static final String COLUMNNAME_checkamt = "checkamt";

	/** Set checkamt.
	  * checkamt
	  */
	public void setCheckAmt (BigDecimal CheckAmt);

	/** Get checkamt.
	  * checkamt
	  */
	public BigDecimal getCheckAmt();
	
	/** Column name wiretransferamt */
    public static final String COLUMNNAME_wiretransferamt = "wiretransferamt";

	/** Set wiretransferamt.
	  * wiretransferamt
	  */
	public void setWireTransferAmt (BigDecimal WireTransferAmt);

	/** Get wiretransferamt.
	  * wiretransferamt
	  */
	public BigDecimal getWireTransferAmt();
	
    /** Column name POS_Close_Cash_ID */
    public static final String COLUMNNAME_POS_Close_Cash_ID = "POS_Close_Cash_ID";

	/** Set Close Cash.
	  * Close Cash
	  */
	public void setPOS_Close_Cash_ID (int POS_Close_Cash_ID);

	/** Get Close Cash.
	  * Close Cash
	  */
	public int getPOS_Close_Cash_ID();
	
    /** Column name C_Bank_ID */
    public static final String COLUMNNAME_C_Bank_ID = "C_Bank_ID";

	/** Set Bank.
	  * Bank
	  */
	public void setC_Bank_ID (int C_Bank_ID);

	/** Get Bank.
	  * Bank
	  */
	public int getC_Bank_ID();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Bank Account.
	  * Bank Account
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

	/** Get Bank Account.
	  * Bank Account
	  */
	public int getC_BankAccount_ID();
	
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";
    
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();
}
