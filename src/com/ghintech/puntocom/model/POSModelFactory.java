package com.ghintech.puntocom.model;

import java.sql.ResultSet;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;

public class POSModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		// TODO Auto-generated method stub
		if(tableName.equals(M_POSCloseCash.Table_Name))
			return M_POSCloseCash.class;	
		if(tableName.equals(M_POSCloseCashLine.Table_Name))
			return M_POSCloseCashLine.class;	
		return null;
	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		// TODO Auto-generated method stub
		if(tableName.equals(M_POSCloseCash.Table_Name))
			return new M_POSCloseCash(Env.getCtx(),Record_ID,trxName);
		if(tableName.equals(M_POSCloseCashLine.Table_Name))
			return new M_POSCloseCashLine(Env.getCtx(),Record_ID,trxName);
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		// TODO Auto-generated method stub
		if(tableName.equals(M_POSCloseCash.Table_Name))
			return new M_POSCloseCash(Env.getCtx(),rs,trxName);
		if(tableName.equals(M_POSCloseCashLine.Table_Name))
			return new M_POSCloseCashLine(Env.getCtx(),rs,trxName);
		return null;
	}

}
