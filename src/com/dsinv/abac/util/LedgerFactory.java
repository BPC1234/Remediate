package com.dsinv.abac.util;

import com.dsinv.abac.ledger.CustomerMasterLedger;
import com.dsinv.abac.ledger.Ledger;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.*;
import com.dsinv.abac.vo.admin.CSVTransaction;

public class LedgerFactory {

    private StringBuffer apLedgerSB = new StringBuffer();
    private StringBuffer arLedgerSB = new StringBuffer();
    private StringBuffer vmfLedgerSB = new StringBuffer();
    private StringBuffer cmfLedgerSB = new StringBuffer();
    private StringBuffer emfLedgerSB = new StringBuffer();

	public static Ledger getLedger(String isDebit, String ledgerType, CSVTransaction csvTransaction){

		if(!Utils.isNullOrEmpty(isDebit) && csvTransaction != null){

			if(Constants.LEDGER_TYPE_AL.equals(ledgerType)){
				AdvancedLedger alLedger= new AdvancedLedger();
				return alLedger.process(alLedger, csvTransaction, isDebit);
			}else if(Constants.LEDGER_TYPE_AP.equals(ledgerType)){
				AccountsPayableLedger apLedger= new AccountsPayableLedger();
				return apLedger.process(apLedger, csvTransaction, isDebit);
			}else if(Constants.LEDGER_TYPE_AR.equals(ledgerType)){
				AccountsReceivableLedger arLedger = new AccountsReceivableLedger();
				return arLedger.process(arLedger, csvTransaction, isDebit);
			}else if(Constants.LEDGER_TYPE_CL.equals(ledgerType)){
				CompletionLedger clLedger = new CompletionLedger();
				return clLedger.process(clLedger, csvTransaction, isDebit);
			}else if(Constants.LEDGER_TYPE_GL.equals(ledgerType)){
				GeneralLedger glLedger = new GeneralLedger();
				return glLedger.process(glLedger, csvTransaction, isDebit);
			}else if(Constants.LEDGER_TYPE_SL.equals(ledgerType)){
				ShippingLedger slLedger= new ShippingLedger();
				return slLedger.process(slLedger, csvTransaction, isDebit);
			}else if(Constants.LEDGER_TYPE_VMF.equals(ledgerType)){
                VendorMasterLedger vlLedger= new VendorMasterLedger();
				return vlLedger.process(vlLedger, csvTransaction, isDebit);
			}else if(Constants.LEDGER_TYPE_CMF.equals(ledgerType)){
                CustomerMasterLedger customerMasterLedger = new CustomerMasterLedger();
				return customerMasterLedger.process(customerMasterLedger, csvTransaction, isDebit);
			}
//            else if(Constants.LEDGER_TYPE_EMF.equals(ledgerType)){
//                EmployeeMasterLedger employeeMasterLedger = new EmployeeMasterLedger();
//				return employeeMasterLedger.process(employeeMasterLedger, csvTransaction, isDebit);
//			}
		}
		return null;
	}

    public static String insertSql(String isDebit, String ledgerType, CSVTransaction csvTransaction){

        if(!Utils.isNullOrEmpty(isDebit) && csvTransaction != null){

            if(Constants.LEDGER_TYPE_AP.equals(ledgerType)){
                AccountsPayableLedger apLedger= new AccountsPayableLedger();
                return apLedger.insertsql(apLedger, csvTransaction, isDebit);
            }else if(Constants.LEDGER_TYPE_AR.equals(ledgerType)){
                AccountsReceivableLedger arLedger = new AccountsReceivableLedger();
                return arLedger.insertsql(arLedger, csvTransaction, isDebit);
            }   else if(Constants.LEDGER_TYPE_VMF.equals(ledgerType)){
                VendorMasterLedger vlLedger= new VendorMasterLedger();
                return vlLedger.insertsql(vlLedger, csvTransaction, isDebit);
            }else if(Constants.LEDGER_TYPE_CMF.equals(ledgerType)){
                CustomerMasterLedger customerMasterLedger = new CustomerMasterLedger();
                return customerMasterLedger.insertsql(customerMasterLedger, csvTransaction, isDebit);
            }/*else if(Constants.LEDGER_TYPE_EMF.equals(ledgerType)){
                EmployeeMasterLedger employeeMasterLedger = new EmployeeMasterLedger();
                return employeeMasterLedger.insertsql(employeeMasterLedger, csvTransaction, isDebit);
            }*/
        }
        return null;
    }
}