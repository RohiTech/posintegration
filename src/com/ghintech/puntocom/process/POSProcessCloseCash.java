package com.ghintech.puntocom.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.logging.Level;




import org.compiere.model.MBankAccount;
import org.compiere.model.MOrg;
import org.compiere.model.MPayment;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.ghintech.puntocom.model.MBankAccount_Commission;
import com.ghintech.puntocom.model.M_POSCloseCash;
import com.ghintech.puntocom.model.M_POSCloseCashLine;

public class POSProcessCloseCash extends SvrProcess{
	private int p_Record_ID = 0;
	private String docAction = "";
	private String 		p_DocumentNo= "";				// Document No
	private String 		p_Description= "";				// Description
	private String      CurSymbol= "";
	private int 		p_C_BPartner_ID = 0; 			// Business Partner to be used as bridge
	private int		p_C_Currency_ID = 0;			// Payment Currency
	private int 		p_C_ConversionType_ID = 0;		// Payment Conversion Type
	private int		p_C_Charge_ID = 0;				// Charge to be used as bridge

	private BigDecimal 	p_Amount = Env.ZERO;  			// Amount to be transfered between the accounts
	private int 		p_From_C_BankAccount_ID = 0;	// Bank Account From
	private int 		p_To_C_BankAccount_ID= 0;		// Bank Account To
	private Timestamp	p_StatementDate = null;  		// Date Statement
	private Timestamp	p_DateAcct = null;  			// Date Account
	private int         m_created = 0;
	//private BigDecimal  total_lines = Env.ZERO;         // Total Amount to be transfered
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (ProcessInfoParameter p : para) {
			String name = p.getParameterName();
			if (name == null)
				;
			else if (name.equals("DocAction"))
				docAction = p.getParameter().toString();
			else
				log.severe("Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
		
	}

	@Override
	protected String doIt() throws Exception {

		
		M_POSCloseCash CloseCash = new M_POSCloseCash(getCtx(), p_Record_ID, get_TrxName());
		if (docAction.equals("CO")){
			String result = completeIt(p_Record_ID);
			StringBuilder sql = null;
			int no = 0;
			if (log.isLoggable(Level.FINE)) log.fine("Set Processed=" + no);
			sql = new StringBuilder ("UPDATE POS_Close_Cash_Line ")	//	Error Invalid Doc Type Name
				  .append("SET Processed='Y' ")
				  .append("WHERE POS_Close_Cash_ID = ").append (p_Record_ID);
			//no = DB.executeUpdate(sql.toString(), get_TrxName());
			if (no != 0)
				log.warning ("Processed Failed=" + no);
			
			return result;				
		}
			
		else if (docAction.equals("VO"))
			return CloseCash.voidIt(p_Record_ID);
		else 
			return null;
	}

	public String completeIt(int p_Record_ID)  {
		
		if (log.isLoggable(Level.INFO)) log.info("From Bank="+p_From_C_BankAccount_ID+" - To Bank="+p_To_C_BankAccount_ID
				+ " - C_BPartner_ID="+p_C_BPartner_ID+"- C_Charge_ID= "+p_C_Charge_ID+" - Amount="+p_Amount+" - DocumentNo="+p_DocumentNo
				+ " - Description="+p_Description+ " - Statement Date="+p_StatementDate+
				" - Date Account="+p_DateAcct);
    	M_POSCloseCash CloseCash = new M_POSCloseCash(getCtx(), p_Record_ID, get_TrxName());
		MOrg org = new MOrg(getCtx(), CloseCash.getAD_Org_ID(), null);
		p_C_BPartner_ID = new Query(getCtx(),"AD_OrgInfo","AD_Org_ID = "+CloseCash.getAD_Org_ID(),get_TrxName()).first().get_ValueAsInt("C_BPartner_ID");
		if(p_C_BPartner_ID == 0) throw new IllegalStateException("No Existe un representante para la Organización: "+org.getName());
		p_C_Charge_ID= new Query(getCtx(),"AD_OrgInfo","AD_Org_ID = "+CloseCash.getAD_Org_ID(),get_TrxName()).first().get_ValueAsInt("C_Charge_ID");
		if(p_C_Charge_ID == 0) throw new IllegalStateException("No Existe un Cargo POS para la Organización: "+org.getName());
		p_From_C_BankAccount_ID = new Query(getCtx(),"C_BankAccount","IsDefault = 'Y' AND BankAccountType = 'B' AND AD_Org_ID = "+CloseCash.getAD_Org_ID(),get_TrxName()).first().get_ValueAsInt("C_BankAccount_ID");
		if(p_From_C_BankAccount_ID == 0) throw new IllegalStateException("No Existe una Caja POS para la Organización: "+org.getName());		
		p_C_Currency_ID = new Query(getCtx(),"C_AcctSchema","C_AcctSchema.AD_Client_ID = "+CloseCash.getAD_Client_ID(),get_TrxName()).addJoinClause("INNER JOIN C_Currency ON C_Currency.C_Currency_ID = C_AcctSchema.C_Currency_ID").first().get_ValueAsInt("C_Currency_ID");
		CurSymbol = new Query(getCtx(),"C_Currency","C_AcctSchema.AD_Client_ID = "+CloseCash.getAD_Client_ID(),get_TrxName()).addJoinClause("INNER JOIN C_AcctSchema ON C_Currency.C_Currency_ID = C_AcctSchema.C_Currency_ID").first().get_ValueAsString("CurSymbol");
		

		String sql = "SELECT POS_Close_Cash_Line_ID FROM POS_Close_Cash_Line WHERE POS_Close_Cash_ID = "+ CloseCash.getPOS_Close_Cash_ID()+" ORDER BY POS_Close_Cash_Line_ID DESC"; //lineas de cierre de caja
		p_StatementDate = CloseCash.getDateTrx();
		p_DateAcct = CloseCash.getDateTrx();
		PreparedStatement pstmt1 = null;
		try{
			pstmt1 = DB.prepareStatement(sql, get_TrxName()); //
			ResultSet rs3 = pstmt1.executeQuery();

			while(rs3.next()){
				M_POSCloseCashLine line = new M_POSCloseCashLine(CloseCash.getCtx(),rs3.getInt("POS_Close_Cash_Line_ID"),CloseCash.get_TrxName());
				p_To_C_BankAccount_ID = line.getC_BankAccount_ID();
					p_Amount = line.getCashAmt();
					p_Description = "Transferencia por Monto en Efectivo: "+p_Amount+" "+CurSymbol+" para Cierre de Caja";
					if(p_Amount.compareTo(BigDecimal.ZERO) > 0)
						generateBankTransfer();
					p_Amount = line.getCheckAmt();
					p_Description = "Transferencia por Monto en Cheque: "+p_Amount+" "+CurSymbol+" para Cierre de Caja";
					if(p_Amount.compareTo(BigDecimal.ZERO) > 0)
						generateBankTransfer();
	
					//TARJETA DE CREDITO
					p_Amount = line.getCreditCardAmt();
					p_Description = "Transferencia por Monto en TDC: "+p_Amount+" "+CurSymbol+" para Cierre de Caja";
					if(p_Amount.compareTo(BigDecimal.ZERO) > 0){
						generateBankCommissions(line,"C");
						generateBankTransfer();						
					}
					
					//TARJETA DE DEBITO
					p_Amount = line.getDebitCardAmt();
					p_Description = "Transferencia por Monto en TDD: "+p_Amount+" "+CurSymbol+" para Cierre de Caja";
					if(p_Amount.compareTo(BigDecimal.ZERO) > 0){
						generateBankCommissions(line,"D");
						generateBankTransfer();						
					}				
					
					p_Amount = line.getWireTransferAmt();
					p_Description = "Transferencia por Monto en Transferencia: "+p_Amount+" "+CurSymbol+" para Cierre de Caja";
					if(p_Amount.compareTo(BigDecimal.ZERO) > 0)
						generateBankTransfer();
				//}
			}
		}catch (SQLException e)
		{
			log.log(Level.SEVERE, "Line - " + sql.toString(), e);
			throw new IllegalStateException("Error Procesando Pagos");
		}
		StringBuilder msgreturn = new StringBuilder("@Created@ = ").append(m_created);
		if (m_created != 0){
			CloseCash.setDocStatus("CO");
			CloseCash.setProcessed(true); 
			CloseCash.set_ValueOfColumn("DateProcessed", new Timestamp (System.currentTimeMillis()));
			CloseCash.saveEx();
			try{
				pstmt1 = DB.prepareStatement(sql, get_TrxName()); //
				ResultSet rs3 = pstmt1.executeQuery();
				while(rs3.next()){
					M_POSCloseCashLine line = new M_POSCloseCashLine(CloseCash.getCtx(),rs3.getInt("POS_Close_Cash_Line_ID"),CloseCash.get_TrxName());
					line.set_ValueOfColumn("Processed", true);
					line.saveEx();
				}
			}catch (SQLException e)
			{
				log.log(Level.SEVERE, "Line - " + sql.toString(), e);
				throw new IllegalStateException("Error Procesando Lineas");
			}		
		} 
		else
			throw new IllegalStateException("No hay lineas que Procesar ");
			
		return msgreturn.toString(); 
		//return "@Created@ = " + m_created;
    	
    }

    private void generateBankTransfer ()
	{

    	int p_C_DocType_ID_From = MSysConfig.getIntValue("DoctypePaymentCloseCash",0, getProcessInfo().getAD_Client_ID());
    	int p_C_DocType_ID_To = MSysConfig.getIntValue("DoctypeReceiptCloseCash",0, getProcessInfo().getAD_Client_ID());
		MBankAccount mBankFrom = new MBankAccount(getCtx(),p_From_C_BankAccount_ID, get_TrxName());
		MBankAccount mBankTo = new MBankAccount(getCtx(),p_To_C_BankAccount_ID, get_TrxName());		
		MPayment paymentBankFrom = new MPayment(getCtx(), 0 ,  get_TrxName());
		paymentBankFrom.setC_BankAccount_ID(mBankFrom.getC_BankAccount_ID());
		if (!Util.isEmpty(p_DocumentNo, true))
			paymentBankFrom.setDocumentNo(p_DocumentNo);
		paymentBankFrom.setDateAcct(p_DateAcct);
		paymentBankFrom.setDateTrx(p_StatementDate);
		paymentBankFrom.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
		paymentBankFrom.setDescription(p_Description);
		paymentBankFrom.setC_BPartner_ID (p_C_BPartner_ID);
		paymentBankFrom.setC_Currency_ID(p_C_Currency_ID);
		if (p_C_ConversionType_ID > 0)
			paymentBankFrom.setC_ConversionType_ID(p_C_ConversionType_ID);	
		paymentBankFrom.setPayAmt(p_Amount);
		paymentBankFrom.setOverUnderAmt(Env.ZERO);
		paymentBankFrom.setC_DocType_ID(p_C_DocType_ID_From);
		paymentBankFrom.setC_Charge_ID(p_C_Charge_ID);
		paymentBankFrom.setAD_Org_ID(mBankFrom.getAD_Org_ID());    			//modificacion para que tome la organizacion del banco y no del contexto
		paymentBankFrom.set_CustomColumn("POS_Close_Cash_ID", p_Record_ID); //modificacion para guardar el id del cierre de caja en el pago
		paymentBankFrom.saveEx();
		if(!paymentBankFrom.processIt(MPayment.DOCACTION_Complete)) {
			log.warning("Payment Process Failed: " + paymentBankFrom + " - " + paymentBankFrom.getProcessMsg());
			throw new IllegalStateException("Payment Process Failed: " + paymentBankFrom + " - " + paymentBankFrom.getProcessMsg());
		}
		paymentBankFrom.saveEx();
		addBufferLog(paymentBankFrom.getC_Payment_ID(), paymentBankFrom.getDateTrx(),
				null, paymentBankFrom.getC_DocType().getPrintName()+ " " + paymentBankFrom.getDocumentNo(),
				MPayment.Table_ID, paymentBankFrom.getC_Payment_ID());
		m_created++;

		MPayment paymentBankTo = new MPayment(getCtx(), 0 ,  get_TrxName());
		paymentBankTo.setC_BankAccount_ID(mBankTo.getC_BankAccount_ID());
		if (!Util.isEmpty(p_DocumentNo, true))
			paymentBankTo.setDocumentNo(p_DocumentNo);
		paymentBankTo.setDateAcct(p_DateAcct);
		paymentBankTo.setDateTrx(p_StatementDate);
		paymentBankTo.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
		paymentBankTo.setDescription(p_Description);
		paymentBankTo.setC_BPartner_ID (p_C_BPartner_ID);
		paymentBankTo.setC_Currency_ID(p_C_Currency_ID);
		if (p_C_ConversionType_ID > 0)
			paymentBankFrom.setC_ConversionType_ID(p_C_ConversionType_ID);	
		paymentBankTo.setPayAmt(p_Amount);
		paymentBankTo.setOverUnderAmt(Env.ZERO);
		paymentBankTo.setC_DocType_ID(p_C_DocType_ID_To);
		System.out.println("EL TIPO DE DOCUMENTO A GENERAR ES: "+p_C_DocType_ID_To);
		paymentBankTo.setAD_Org_ID(mBankTo.getAD_Org_ID()); 				//modificacion para que tome la organizacion del banco y no del contexto
		paymentBankTo.set_CustomColumn("POS_Close_Cash_ID", p_Record_ID); 	//modificacion para guardar el id del cierre de caja en el pago
		paymentBankTo.saveEx();
		if (!paymentBankTo.processIt(MPayment.DOCACTION_Complete)) {
			log.warning("Payment Process Failed: " + paymentBankTo + " - " + paymentBankTo.getProcessMsg());
			throw new IllegalStateException("Payment Process Failed: " + paymentBankTo + " - " + paymentBankTo.getProcessMsg());
		}
		paymentBankTo.saveEx();
		addBufferLog(paymentBankTo.getC_Payment_ID(), paymentBankTo.getDateTrx(),
				null, paymentBankTo.getC_DocType().getPrintName() + " " + paymentBankTo.getDocumentNo(),
				MPayment.Table_ID, paymentBankTo.getC_Payment_ID());
		m_created++;
		return;

	}  //  createCashLines

    private void generateBankCommissions (M_POSCloseCashLine CloseCashLine, String TrxType){
    	
    	MBankAccount BankAccount = new MBankAccount(getCtx(),p_To_C_BankAccount_ID, get_TrxName());//BANCO SOBRE EL CUAL SE VA A CALCULAR LA COMISION
    	M_POSCloseCash CloseCash = new M_POSCloseCash(getCtx(), CloseCashLine.getPOS_Close_Cash_ID(),get_TrxName());
    	BigDecimal amt = p_Amount;     	//monto de la transferencia (debito o credito)
    	BigDecimal totalComm = Env.ZERO;
    	BigDecimal totalISLR = Env.ZERO;	
    	//TOMAR DEL BANCO VALORES PARA LAS TASAS
    	BigDecimal DebitRate = (BigDecimal)BankAccount.get_Value("DebitRate");
    	BigDecimal CreditRate = (BigDecimal)BankAccount.get_Value("CreditRate");
    	BigDecimal ISLRRate = (BigDecimal)BankAccount.get_Value("ISLRRate");
    	//BUSCAMOS SI EXISTE UN VALOR EN LAS LINEAS DE COMISIONES BANCARIAS
    	MBankAccount_Commission baCommision = new Query(getCtx(), MBankAccount_Commission.Table_Name, MBankAccount.COLUMNNAME_C_BankAccount_ID+"=? and AD_Org_ID=?", get_TrxName())
    		.setParameters(BankAccount.getC_BankAccount_ID(),CloseCash.getAD_Org_ID()).first();
    	if(baCommision!=null){
    		DebitRate = baCommision.getdebitrate();
        	CreditRate = baCommision.getcreditrate();
        	ISLRRate = baCommision.getislrrate();
    	}
    	// Variables para ID de cargo para comisiones
    	int Commissions_C_Charge_ID = 0;
    	int ISLR_C_Charge_ID = 0;
    	if (DebitRate==null || CreditRate==null || ISLRRate==null)
    		throw new IllegalStateException("Falta Asignar tasas para cuenta: "+BankAccount.getName());
    	//Cálculo de la comision TrxType=C (Credito) TrxType=D (Debito)
    	if (TrxType == "D" && DebitRate.compareTo(Env.ZERO)>0){
    		totalComm = amt.multiply(DebitRate).divide(new BigDecimal(100));    		
    	}else if (TrxType == "C" && CreditRate.compareTo(Env.ZERO)>0){
    		totalComm = amt.multiply(CreditRate).divide(new BigDecimal(100));
    		totalISLR = amt.multiply(ISLRRate).divide(new BigDecimal(100));
    	}else
    		return;
    	//Buscar valores para los cargos por comisiones bancarias
		PreparedStatement pst = null;
		ResultSet rs = null;
    	String sql = "SELECT Name,Value FROM AD_SysConfig WHERE "+
    			"Name IN ('ISLR_C_Charge_ID','Commissions_C_Charge_ID')"; 
    	try{
    		pst = DB.prepareStatement(sql, null); //
    		rs = pst.executeQuery();
			while(rs.next()){
				if(rs.getString("Name").compareTo("ISLR_C_Charge_ID")==0) {
					ISLR_C_Charge_ID = Integer.parseInt(rs.getString("Value"));
				}

				if(rs.getString("Name").compareTo("Commissions_C_Charge_ID")==0){
					Commissions_C_Charge_ID = Integer.parseInt(rs.getString("Value"));
				}
					
			}
    	}catch (SQLException e)	{
    		log.log(Level.SEVERE, "Line - " + sql.toString(), e);
		}
		 
//    	Crear Comprobante de Egreso para comisiones
    	MPayment paymentBank = new MPayment(getCtx(), 0 ,  get_TrxName());
		//paymentBank.setC_BankAccount_ID(BankAccount.getC_BankAccount_ID());
		MBankAccount mBankFrom = new MBankAccount(getCtx(),p_From_C_BankAccount_ID, get_TrxName());
		paymentBank.setC_BankAccount_ID(mBankFrom.getC_BankAccount_ID());
		if (!Util.isEmpty(p_DocumentNo, true))
			paymentBank.setDocumentNo(p_DocumentNo);
		paymentBank.setDateAcct(p_DateAcct);
		paymentBank.setDateTrx(p_StatementDate);
		paymentBank.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
		paymentBank.setDescription("Comision Banco "+BankAccount.getName()+" para Cierre de Caja Nro: "+CloseCash.getPOS_Close_Cash_ID());
		paymentBank.setC_BPartner_ID (p_C_BPartner_ID);
		paymentBank.setC_Currency_ID(p_C_Currency_ID);
		if (p_C_ConversionType_ID > 0)
			paymentBank.setC_ConversionType_ID(p_C_ConversionType_ID);	
		paymentBank.setPayAmt(totalComm); //TOTAL COMISIONES PARA LA LINEA DE TARJETA DE CREDITO O DEBITO
		paymentBank.setOverUnderAmt(Env.ZERO);
		paymentBank.setC_DocType_ID(false);
		paymentBank.setC_Charge_ID(Commissions_C_Charge_ID); //Cargo para comisiones 
		paymentBank.setAD_Org_ID(mBankFrom.getAD_Org_ID());    //modificación para que tome la organización del banco y no del contexto
		paymentBank.set_CustomColumn("POS_Close_Cash_ID", p_Record_ID); //modificacion para guardar el id del cierre de caja en el pago
		paymentBank.saveEx();
		if(!paymentBank.processIt(MPayment.DOCACTION_Complete)) {
			log.warning("Payment Process Failed: " + paymentBank + " - " + paymentBank.getProcessMsg());
			throw new IllegalStateException("Payment Process Failed: " + paymentBank + " - " + paymentBank.getProcessMsg());
		}
		paymentBank.saveEx();
		addBufferLog(paymentBank.getC_Payment_ID(), paymentBank.getDateTrx(),
				null, paymentBank.getC_DocType().getPrintName() + " " + paymentBank.getDocumentNo()+" Comisión Banco",
				MPayment.Table_ID, paymentBank.getC_Payment_ID());
		m_created++;
		
//    	Crear Comprobante de Egreso para ISLR
		if(totalISLR.compareTo(Env.ZERO)>0){
	    	MPayment paymentBankISLR = new MPayment(getCtx(), 0 ,  get_TrxName());
			//paymentBankISLR.setC_BankAccount_ID(BankAccount.getC_BankAccount_ID());
			paymentBankISLR.setC_BankAccount_ID(mBankFrom.getC_BankAccount_ID());
			if (!Util.isEmpty(p_DocumentNo, true))
				paymentBankISLR.setDocumentNo(p_DocumentNo);
			paymentBankISLR.setDateAcct(p_DateAcct);
			paymentBankISLR.setDateTrx(p_StatementDate);
			paymentBankISLR.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
			paymentBankISLR.setDescription("Comision ISLR "+BankAccount.getName()+" para Cierre de Caja Nro: "+CloseCash.getPOS_Close_Cash_ID());
			paymentBankISLR.setC_BPartner_ID (p_C_BPartner_ID);
			paymentBankISLR.setC_Currency_ID(p_C_Currency_ID);
			if (p_C_ConversionType_ID > 0)
				paymentBankISLR.setC_ConversionType_ID(p_C_ConversionType_ID);	
			paymentBankISLR.setPayAmt(totalISLR); //total ISLR PARA LA LINEA DE CIERRE DE CAJA
			paymentBankISLR.setOverUnderAmt(Env.ZERO);
			paymentBankISLR.setC_DocType_ID(false);
			paymentBankISLR.setC_Charge_ID(ISLR_C_Charge_ID); //COLOCAR EL CARGO POR DEFECTO PARA ISLR
			//paymentBankISLR.setAD_Org_ID(BankAccount.getAD_Org_ID());    //modificacion para que tome la organizacion del banco y no del contexto
			paymentBankISLR.setAD_Org_ID(mBankFrom.getAD_Org_ID());
			paymentBankISLR.set_CustomColumn("POS_Close_Cash_ID", p_Record_ID); //modificacion para guardar el id del cierre de caja en el pago
			paymentBankISLR.saveEx();
			if(!paymentBankISLR.processIt(MPayment.DOCACTION_Complete)) {
				log.warning("Payment Process Failed: " + paymentBankISLR + " - " + paymentBankISLR.getProcessMsg());
				throw new IllegalStateException("Payment Process Failed: " + paymentBankISLR + " - " + paymentBankISLR.getProcessMsg());
			}
			paymentBankISLR.saveEx();
			addBufferLog(paymentBankISLR.getC_Payment_ID(), paymentBankISLR.getDateTrx(),
					null, paymentBankISLR.getC_DocType().getPrintName() + " " + paymentBankISLR.getDocumentNo()+" Comisión ISLR",
					MPayment.Table_ID, paymentBankISLR.getC_Payment_ID());
			m_created++;
		}
		
		p_Amount = p_Amount.subtract(totalISLR.add(totalComm));
		return;    	
    }
}
