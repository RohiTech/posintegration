package com.ghintech.puntocom.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.DBException;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.ghintech.puntocom.model.M_C_Invoice_Fiscal;

public class FillZreportSequence extends SvrProcess{
	private int p_AD_Org_ID = 0;
	private Timestamp	p_from = null;
	private Timestamp	p_to = null;
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = para[i].getParameterAsInt();
			else if (name.equals("from"))
				p_from = (Timestamp)para[i].getParameter();
			else if (name.equals("to"))
				p_to = (Timestamp)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {

		//lista de Impresoras fiscales utilizadas en el periodo
		StringBuilder sql = new StringBuilder();
		String from = DB.TO_DATE(p_from);
		String to = DB.TO_DATE(p_to);
		sql.append("SELECT fiscalprint_serial FROM lve_salesbook_fiscal WHERE tipodocumento='F'")
			.append(" AND fiscal_zreport is not null AND fiscalprint_serial is not null")
			.append(" AND ").append(" datefilter between ").append(from).append(" AND ").append(to)
			.append(" AND ").append(" AD_Org_ID = ").append(p_AD_Org_ID)
			.append(" GROUP by fiscalprint_serial")
			.append(" ORDER by fiscalprint_serial");

		List<String> fiscalprint = new ArrayList<String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
		rs = pstmt.executeQuery();
		while (rs.next())
		{
			fiscalprint.add(rs.getString(1));
		}
		//System.out.println(fiscalprint);
		//System.out.println(fiscalprint.size());
		
		//recorriendo cada maquina fiscal 
		for(int i=0;i<fiscalprint.size();i++){
			//recuperar el ultimo z hasta la fecha
			String querya = "SELECT max(cast(fiscal_zreport AS int)) AS minZ FROM lve_salesbook_fiscal WHERE tipodocumento='F' "+
					" AND fiscal_zreport is not null "+
					" AND fiscalprint_serial is not null "+
					" AND datefilter < "+from+
					" AND AD_Org_ID = "+p_AD_Org_ID+
					" GROUP BY fiscalprint_serial ORDER BY  fiscalprint_serial";
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			rs.first();
			int minZ = rs.getInt("minZ");
			
		}
/**
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT cast(fiscal_zreport AS int),fiscalprint_serial,datefilter")
			.append(" FROM lve_salesbook ")
			.append(" WHERE tipodocumento='F' AND fiscal_zreport is not null ")
			.append(" AND fiscalprint_serial is not null AND datefilter between ").append(from)
			.append(" AND ").append(to).append(" AND ")
			.append(" AD_Org_ID = ").append(p_AD_Org_ID)
			//.append(" GROUP by fiscalprint_serial,fiscal_zreport")
			//.append(" ORDER by fiscalprint_serial,fiscal_zreport")
			.append(" UNION SELECT cast(fiscal_zreport AS int),fiscalprint_serial,date_zreport_zero FROM C_Invoice_Fiscal ")
			.append("WHERE fiscal_zreport_zero='Y' AND ad_org_id = ").append(p_AD_Org_ID)
			.append(" AND date_zreport_zero between ").append(from).append(" AND ").append(to)
			.append(" ORDER by fiscalprint_serial,fiscal_zreport");	
		//System.out.println(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			int zreport = 0;
			String fiscal_print = null;
			int zreport_new = 0;
			String fiscal_print_new = null;
			
			while (rs.next())
			{
				if(fiscal_print==null){			//verificamos el primer valor
					zreport = rs.getInt(1);
					fiscal_print = rs.getString(2); 
				}else{							//si no es el primer valor entonces comparamos con el anterior para buscar saltos
					zreport_new = rs.getInt(1);
					fiscal_print_new = rs.getString(2);
					if(fiscal_print_new.compareTo(fiscal_print)==0){
						Calendar c = Calendar.getInstance();
						c.setTime(rs.getDate(3));
						int salto = zreport_new-zreport;
						while (salto>1){				// verifica si existe un salto
							M_C_Invoice_Fiscal fiscal = new M_C_Invoice_Fiscal(getCtx(), 0, get_TrxName());
							c.add(Calendar.DATE, -1);
							java.util.Date date = c.getTime();
							Timestamp fechaz = new java.sql.Timestamp(date.getTime());
							fiscal.setdate_zreport_zero((fechaz));		//colocar fecha del z 1 dia antes del z nuevo
							String newz = String.format("%04d",zreport+1);
							fiscal.setAD_Org_ID(p_AD_Org_ID);
							fiscal.setfiscalprint_serial(fiscal_print_new);
							fiscal.setfiscal_zreport_zero(true);
							fiscal.setfiscal_zreport(newz);
							fiscal.saveEx();
							salto=salto-1;
							zreport=zreport+1;
							c.add(Calendar.DATE, 1);
							
						}
					}
					zreport = rs.getInt(1);
					fiscal_print = rs.getString(2);
				}				
			}
		
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql.toString());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
**/		
		return "";

	}

}
