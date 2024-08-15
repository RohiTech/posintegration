package com.ghintech.puntocom.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.util.DB;


public class M_POSCloseCash extends X_POSCloseCash {
	
	private static final long serialVersionUID = 1L;

	public M_POSCloseCash(Properties ctx, PO source, int AD_Client_ID,
			int AD_Org_ID) {
		super(ctx, source, AD_Client_ID, AD_Org_ID);
		// TODO Auto-generated constructor stub
	}
	
	public M_POSCloseCash(Properties ctx, int POS_Close_Cash_ID, String trxName, ResultSet rs) {
		super(ctx, POS_Close_Cash_ID, trxName, rs);
		// TODO Auto-generated constructor stub
	}
	
	public M_POSCloseCash(Properties ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	
	public M_POSCloseCash(Properties ctx, int POS_Close_Cash_ID, String trxName) {
		super(ctx, POS_Close_Cash_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
    /** Load Constructor */
    public M_POSCloseCash (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

	/**
	 * 	Void Document.
	 * 	@return true if success 
	 * @throws SQLException 
	 */
	public String voidIt(int p_Record_ID) throws SQLException
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());		
		
		// SQL for the payments asociated
		String sql = "SELECT C_Payment_ID FROM C_Payment WHERE POS_Close_Cash_ID = ?";
		PreparedStatement pstmt = DB.prepareStatement(sql, null);
		pstmt.setInt(1, getPOS_Close_Cash_ID());
		ResultSet rs = pstmt.executeQuery();
		int m_created = 0;
		try{
			while(rs.next()){ 
				//Create the MPayment entity for revelsal
				MPayment Payment = new MPayment(getCtx(),rs.getInt("C_Payment_ID"),get_TrxName());
				Payment.voidIt();
				m_created++;
				//System.out.println(m_created);
			}
		}catch (SQLException e)
		{
			log.log(Level.SEVERE, "Line - " + sql.toString(), e);
		}
		if (m_created > 0){
			setDocStatus("VO");
			saveEx();
		}

		return "Anulado";
	}
    }
