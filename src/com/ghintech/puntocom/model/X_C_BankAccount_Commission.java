/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package com.ghintech.puntocom.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for C_BankAccount_Commission
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_C_BankAccount_Commission extends PO implements I_C_BankAccount_Commission, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150609L;

    /** Standard Constructor */
    public X_C_BankAccount_Commission (Properties ctx, int C_BankAccount_Commission_ID, String trxName)
    {
      super (ctx, C_BankAccount_Commission_ID, trxName);
      /** if (C_BankAccount_Commission_ID == 0)
        {
			setC_BankAccount_Commission_ID (0);
			setC_BankAccount_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_BankAccount_Commission (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_BankAccount_Commission[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_BankAccount_Commission.
		@param C_BankAccount_Commission_ID C_BankAccount_Commission	  */
	public void setC_BankAccount_Commission_ID (int C_BankAccount_Commission_ID)
	{
		if (C_BankAccount_Commission_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_Commission_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_Commission_ID, Integer.valueOf(C_BankAccount_Commission_ID));
	}

	/** Get C_BankAccount_Commission.
		@return C_BankAccount_Commission	  */
	public int getC_BankAccount_Commission_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_Commission_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BankAccount_Commission_UU.
		@param C_BankAccount_Commission_UU C_BankAccount_Commission_UU	  */
	public void setC_BankAccount_Commission_UU (String C_BankAccount_Commission_UU)
	{
		set_ValueNoCheck (COLUMNNAME_C_BankAccount_Commission_UU, C_BankAccount_Commission_UU);
	}

	/** Get C_BankAccount_Commission_UU.
		@return C_BankAccount_Commission_UU	  */
	public String getC_BankAccount_Commission_UU () 
	{
		return (String)get_Value(COLUMNNAME_C_BankAccount_Commission_UU);
	}

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set creditrate.
		@param creditrate creditrate	  */
	public void setcreditrate (BigDecimal creditrate)
	{
		set_Value (COLUMNNAME_creditrate, creditrate);
	}

	/** Get creditrate.
		@return creditrate	  */
	public BigDecimal getcreditrate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_creditrate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set debitrate.
		@param debitrate debitrate	  */
	public void setdebitrate (BigDecimal debitrate)
	{
		set_Value (COLUMNNAME_debitrate, debitrate);
	}

	/** Get debitrate.
		@return debitrate	  */
	public BigDecimal getdebitrate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_debitrate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set islrrate.
		@param islrrate islrrate	  */
	public void setislrrate (BigDecimal islrrate)
	{
		set_Value (COLUMNNAME_islrrate, islrrate);
	}

	/** Get islrrate.
		@return islrrate	  */
	public BigDecimal getislrrate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_islrrate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}