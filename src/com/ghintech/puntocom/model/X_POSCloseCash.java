package com.ghintech.puntocom.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

public class X_POSCloseCash extends PO implements I_POSCloseCash, I_Persistent {

	private static final long serialVersionUID = 1L;

	public X_POSCloseCash(Properties ctx, PO source, int AD_Client_ID,
			int AD_Org_ID) {
		super(ctx, source, AD_Client_ID, AD_Org_ID);
		// TODO Auto-generated constructor stub
	}
	
	public X_POSCloseCash(Properties ctx, int POS_Close_Cash_ID, String trxName, ResultSet rs) {
		super(ctx, POS_Close_Cash_ID, trxName, rs);
		// TODO Auto-generated constructor stub
	}
	
	public X_POSCloseCash(Properties ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	
    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }
	
	public X_POSCloseCash(Properties ctx, int POS_Close_Cash_ID, String trxName) {
		super(ctx, POS_Close_Cash_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
    /** Load Constructor */
    public X_POSCloseCash (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }
	/**
	 * 
	 */
	



    
	@Override
	public Timestamp getDateTrx() {
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	@Override
	public void setDescription(String Description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	
	@Override
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	
	@Override
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	@Override
	public void setImport_Lines(boolean Import_Lines) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isImport_Lines() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPOS_Close_Cash_UU(String POS_Close_Cash_UU) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPOS_Close_Cash_UU() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPOS_Close_Cash_ID(int POS_Close_Cash_ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPOS_Close_Cash_ID() {
		Integer ii = (Integer)get_Value(COLUMNNAME_POS_Close_Cash_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public void setTotalLines(BigDecimal TotalLines) {
		set_Value (COLUMNNAME_TotalLines, TotalLines);
		
	}

	@Override
	public BigDecimal getTotalLines() {
		// TODO Auto-generated method stub
		return null;
	}

    /** AccessLevel
     * @return 1 - Org 
     */
   protected int get_AccessLevel()
   {
     return accessLevel.intValue();
   }

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDateProcessed() {
		// TODO Auto-generated method stub
		
	}

}
