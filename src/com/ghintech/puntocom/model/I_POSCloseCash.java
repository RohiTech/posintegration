package com.ghintech.puntocom.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

public interface I_POSCloseCash {
	  /** TableName=POS_Close_Cash */
    public static final String Table_Name = "POS_Close_Cash";

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
	
	 /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Get DateTrx.
	  * Date of this record transaction
	  */
	public Timestamp getDateTrx();
	
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";
    
    /** Column name DateProcessed */
    public static final String COLUMNNAME_DateProcessed = "DateProcessed";
    
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();
	
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();
	
    /** Column name Import_Lines */
    public static final String COLUMNNAME_Import_Lines = "Import_Lines";

	/** Set Process Now	  */
	public void setImport_Lines (boolean Import_Lines);

	/** Get Process Now	  */
	public boolean isImport_Lines();
	
    /** Column name POS_Close_Cash_UU */
    public static final String COLUMNNAME_POS_Close_Cash_UU = "POS_Close_Cash_UU";

	/** Set POS_Close_Cash_UU	  */
	public void setPOS_Close_Cash_UU (String POS_Close_Cash_UU);

	/** Get POS_Close_Cash_UU	  */
	public String getPOS_Close_Cash_UU();
	
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
	
	/** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set TotalLines.
	  * TotalLines
	  */
	public void setTotalLines (BigDecimal TotalLines);

	/** Get TotalLines.
	  * TotalLines
	  */
	public BigDecimal getTotalLines();
	
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * Processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed (); 
	
	/** Set Processed Date.
	Set The date when the document has been processed
  */
	public void setDateProcessed();
	
}
