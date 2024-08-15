package com.ghintech.puntocom.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class M_POSCloseCashLine extends X_POSCloseCashLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public M_POSCloseCashLine(Properties ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	public M_POSCloseCashLine(Properties ctx, int ID, String trxName) {
		super(ctx, ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public M_POSCloseCashLine(Properties ctx, int ID, String trxName,
			ResultSet rs) {
		super(ctx, ID, trxName, rs);
		// TODO Auto-generated constructor stub
	}

	public M_POSCloseCashLine(Properties ctx, PO source, int AD_Client_ID,
			int AD_Org_ID) {
		super(ctx, source, AD_Client_ID, AD_Org_ID);
		// TODO Auto-generated constructor stub
	}

	public M_POSCloseCashLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public void setClientOrg (int AD_Client_ID, int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	//	setClientOrg

	/**
	 *  Parent Constructor.
	 *  @param  Close Cash parent order
	 */
	public M_POSCloseCashLine (M_POSCloseCash POS_Close_Cash)
	{
		this (POS_Close_Cash.getCtx(), 0, POS_Close_Cash.get_TrxName());
		if (POS_Close_Cash.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setPOS_Close_Cash_ID (POS_Close_Cash.getPOS_Close_Cash_ID());	//	parent
		setClientOrg(POS_Close_Cash);
	}	//	M_POSCloseCashLine

	public BigDecimal totalline(){
		BigDecimal amt = Env.ZERO;
		amt = getCashAmt().add(getCheckAmt()).add(getCreditCardAmt()).add(getDebitCardAmt()).add(getWireTransferAmt());
		return amt;
	}
	/*protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		
		return UpdateTotalLines(getPOS_Close_Cash_ID(), get_TrxName());
	}	//	afterSave
	
	/**
	 *	Update TotalLines in Header
	 *	@return true if header updated with totallines
	 */
	
	/*public static boolean UpdateTotalLines(int POS_Close_Cash_ID, String trxName)
	{
		//	Update POS_Close_Cash
		String sql = 
			"UPDATE POS_Close_Cash "
			+ " SET TotalLines="
				+ " (SELECT (sum(cashamt)+sum(checkamt)+sum(creditcardamt)+sum(debitcardamt)+sum(wiretransferamt))"
			+ " FROM POS_Close_Cash_Line WHERE POS_Close_Cash_ID = ?) "
			+ " WHERE POS_Close_Cash_ID =?";
		int no = DB.executeUpdateEx(sql, new Object[] {POS_Close_Cash_ID}, trxName);

		return no == 1;
	}	//	updateHeaderWithholding
	*/

}
