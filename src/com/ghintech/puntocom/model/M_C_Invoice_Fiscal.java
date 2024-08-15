package com.ghintech.puntocom.model;

import java.sql.ResultSet;
import java.util.Properties;

public class M_C_Invoice_Fiscal extends X_C_Invoice_Fiscal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8574835898671888562L;

	public M_C_Invoice_Fiscal(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public M_C_Invoice_Fiscal(Properties ctx, int C_Invoice_Fiscal_ID,
			String trxName) {
		super(ctx, C_Invoice_Fiscal_ID, trxName);
		// TODO Auto-generated constructor stub
	}

}
