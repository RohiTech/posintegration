package com.ghintech.puntocom.model;

import java.sql.ResultSet;
import java.util.Properties;

public class MBankAccount_Commission extends X_C_BankAccount_Commission{

	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4087206405098365657L;
	
	public MBankAccount_Commission(Properties ctx,int C_BankAccount_Commission_ID, String trxName) {
		super(ctx, C_BankAccount_Commission_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	public MBankAccount_Commission(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
