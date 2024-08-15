package com.ghintech.puntocom.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import org.compiere.model.MOrg;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.ghintech.puntocom.model.M_POSCloseCash;
import com.ghintech.puntocom.model.M_POSCloseCashLine;

public class ImportLinesFromBankAccounts extends SvrProcess{
	/**	Record to be used		*/
	private int	m_POS_Close_Cash_ID = 0;
	private String mainMsg = "";
	private int count = 0;
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("POS_Close_Cash_ID"))
				m_POS_Close_Cash_ID= Integer.parseInt(para[i].getParameter().toString());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
	} //prepare

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		if (m_POS_Close_Cash_ID != 0){
			//System.out.print(m_POS_Close_Cash_ID);
			M_POSCloseCash CloseCash = new M_POSCloseCash(getCtx(),m_POS_Close_Cash_ID,get_TrxName());
			int orgID = CloseCash.getAD_Org_ID();
			MOrg org = new MOrg(getCtx(), orgID, null);
			if (orgID == 0)
				throw new IllegalStateException("Falló al Crear Lineas: Debe Crear Registro en una Organización diferente a "+ org.getName());
			String sql = "SELECT a.C_Bank_ID,a.C_BankAccount_ID FROM C_BankAccount a"+
				" WHERE a.isActive='Y' AND (a.AD_Org_ID = ? OR AD_Org_ID = 1000009) AND BankAccountType!='B' "+
					"AND C_BankAccount_ID NOT IN (SELECT C_BankAccount_ID FROM POS_Close_Cash_Line WHERE POS_Close_Cash_ID = ?)"; 
			try{
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				pstmt = DB.prepareStatement(sql, null); //
				pstmt.setInt(1, orgID);
				pstmt.setInt(2, m_POS_Close_Cash_ID);
				rs = pstmt.executeQuery();
				while(rs.next()){
					count++;
					M_POSCloseCashLine line = new M_POSCloseCashLine(getCtx());
					line.setPOS_Close_Cash_ID(m_POS_Close_Cash_ID);
					line.setAD_Org_ID(orgID);
					line.setC_Bank_ID(rs.getInt("C_Bank_ID"));
					line.setC_BankAccount_ID(rs.getInt("C_BankAccount_ID"));
					//System.out.println(line.getPOS_Close_Cash_ID());
					line.save();
				}
				if (count ==0)
					throw new IllegalStateException("Falló al Crear Lineas: No existen mas Cuentas Bancarias para la Organización "+ org.getName());
				else mainMsg = "Se crearon "+count+" lineas";  
					
			}catch (SQLException e)
			{
				log.log(Level.SEVERE, "Line - " + sql.toString(), e);
			}
		}
		return mainMsg;
	}

}
