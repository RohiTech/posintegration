package com.ghintech.puntocom.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

public class X_POSCloseCashLine extends PO implements I_POSCloseCashLine, I_Persistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public X_POSCloseCashLine(Properties ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	public X_POSCloseCashLine(Properties ctx, int ID, String trxName) {
		super(ctx, ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public X_POSCloseCashLine(Properties ctx, int ID, String trxName,
			ResultSet rs) {
		super(ctx, ID, trxName, rs);
		// TODO Auto-generated constructor stub
	}

	public X_POSCloseCashLine(Properties ctx, PO source, int AD_Client_ID,
			int AD_Org_ID) {
		super(ctx, source, AD_Client_ID, AD_Org_ID);
		// TODO Auto-generated constructor stub
	}

	public X_POSCloseCashLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPOS_Close_Cash_Line_UU(String POS_Close_Cash_Line_UU) {
		set_Value (COLUMNNAME_POS_Close_Cash_Line_UU, POS_Close_Cash_Line_UU);
	}

	@Override
	public String getPOS_Close_Cash_Line_UU() {
		// TODO Auto-generated method stub
		return (String)get_Value(COLUMNNAME_POS_Close_Cash_Line_UU);
	}

	@Override
	public void setPOS_Close_Cash_Line_ID(int POS_Close_Cash_Line_ID) {
		if (POS_Close_Cash_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_POS_Close_Cash_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_POS_Close_Cash_Line_ID, Integer.valueOf(POS_Close_Cash_Line_ID));
	}

	@Override
	public int getPOS_Close_Cash_Line_ID() {
		Integer ii = (Integer)get_Value(COLUMNNAME_POS_Close_Cash_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public void setCreditCardAmt(BigDecimal CreditCardAmt) {
		set_Value (COLUMNNAME_creditcardamt, CreditCardAmt);
		
	}

	@Override
	public BigDecimal getCreditCardAmt() {
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_creditcardamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public void setDebitCardAmt(BigDecimal DebitCardAmt) {
		set_Value (COLUMNNAME_debitcardamt, DebitCardAmt);		
	}

	@Override
	public BigDecimal getDebitCardAmt() {
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_debitcardamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public void setCashAmt(BigDecimal CashAmt) {
		set_Value (COLUMNNAME_cashamt, CashAmt);		
		
	}

	@Override
	public BigDecimal getCashAmt() {
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cashamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public void setCheckAmt(BigDecimal CheckAmt) {
		set_Value (COLUMNNAME_checkamt, CheckAmt);	
		
	}

	@Override
	public BigDecimal getCheckAmt() {
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_checkamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public void setWireTransferAmt(BigDecimal WireTransferAmt) {
		set_Value (COLUMNNAME_wiretransferamt, WireTransferAmt);	
		
	}

	@Override
	public BigDecimal getWireTransferAmt() {
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_wiretransferamt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public void setPOS_Close_Cash_ID(int POS_Close_Cash_ID) {
		if (POS_Close_Cash_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_POS_Close_Cash_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_POS_Close_Cash_ID, Integer.valueOf(POS_Close_Cash_ID));
		
	}

	@Override
	public int getPOS_Close_Cash_ID() {
		Integer ii = (Integer)get_Value(COLUMNNAME_POS_Close_Cash_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public void setC_Bank_ID(int C_Bank_ID) {
		if (C_Bank_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Bank_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
		
	}

	@Override
	public int getC_Bank_ID() {
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public void setC_BankAccount_ID(int C_BankAccount_ID) {
		if (C_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
		
	}

	@Override
	public int getC_BankAccount_ID() {
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public void setDescription(String Description) {
		set_Value (COLUMNNAME_Description, Description);
		
	}

	@Override
	public String getDescription() {
		return (String)get_Value(COLUMNNAME_Description);
	}

	@Override
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

	@Override
	protected int get_AccessLevel() {
		// TODO Auto-generated method stub
		return accessLevel.intValue();
	}

}
