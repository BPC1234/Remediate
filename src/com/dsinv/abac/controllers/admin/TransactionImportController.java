package com.dsinv.abac.controllers.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.dsinv.abac.Entity;
import com.dsinv.abac.dao.AdminDao;
import com.dsinv.abac.entity.*;
import com.dsinv.abac.ledger.CustomerMasterLedger;
import com.dsinv.abac.ledger.Vendor;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.AccountsPayableLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import com.dsinv.abac.service.AdminJdbcService;
import com.dsinv.abac.util.LedgerFactory;
import com.dsinv.abac.util.TransactionLIHeader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;

import com.dsinv.abac.service.AdminService;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.vo.admin.CSVTransaction;

@Controller
public class TransactionImportController {
	
	private static Logger logger = Logger.getLogger(TransactionImportController.class);
    private static long TRANSACTION_ID;

    @Autowired(required = true)
    private AdminService adminService;
    @Autowired(required = true)
    private AdminJdbcService adminJdbcService;
    @Autowired(required = true)
    private AdminDao adminDao;

    private StringBuffer apbf;
    private StringBuffer arbf;
    private StringBuffer vmfbf;
    private StringBuffer cmfbf;
    private StringBuffer emfbf;
    private StringBuffer transactionLiSB;
    private StringBuffer transactionSB;
    private StringBuffer personSB;

    long lastPersonId;
    long lastVendor;
    long lastCustomer;
    long lastEmployee;
    long customerId = 0;
    long vendorId = 0;
    long employeeId = 0;

    @RequestMapping(value = "/*/importTransactionsFromCSV.html", method = RequestMethod.GET)
    public String importTransactions(HttpServletRequest request, Model model) {
        logger.debug("Transaction import process is starting...");
        int totalFile = 0;
        int passedFile = 0;
        int failedFile = 0;
        long startTime;
        long endTime;
        int jobNo;
        long lastTxId;


        apbf = new StringBuffer();
        arbf = new StringBuffer();
        vmfbf = new StringBuffer();
        emfbf = new StringBuffer();
        cmfbf = new StringBuffer();
        transactionSB = new StringBuffer();
        transactionLiSB = new StringBuffer();
        personSB= new StringBuffer();
         try{
    	    String[] children = Utils.getFileNames(Constants.DOWNLOAD_PATH);
    	    totalFile = children.length;
            jobNo = adminJdbcService.getLastJobId() + 1;
             logger.debug("AMLOG: Import start : ");
             for(int i=0; i < children.length; i++){
                logger.debug("AMLOG: File move to temp. ");
    	    	Utils.moveFiles(children[i], Constants.DOWNLOAD_PATH, Constants.TEMP_PATH);
    	    	try{
                    logger.debug("AMLOG: Get CSV bean. ");
    				List<Object> csvTransactionList=adminService.CSV2Bean(Constants.TEMP_PATH + children[i], getHeaders(), getProcessors(), new CSVTransaction());
                    startTime =  new Date().getTime();
                    logger.debug("AMLOG: Save transaction start. ");
                    lastTxId = adminJdbcService.getLastTransactionId();
                    lastPersonId = adminJdbcService.getLastPersonId();
                    lastVendor = adminJdbcService.getLastId(Constants.VENDOR_MASTER_LEDGER);
                    lastEmployee = adminJdbcService.getLastId(Constants.EMPLOYEE_MASTER_LEDGER);
                    lastCustomer = adminJdbcService.getLastId(Constants.CUSTOMER_MASTER_LEDGER);
                    for (int j = 0; j < csvTransactionList.size(); j++) {
//    					adminService.saveCSVTransaction(csvTransactionList.get(j), jobNo);
                        if(j > 0 && j%25 == 0) {
                            savePartialTransactions();
                            apbf = new StringBuffer();
                            arbf = new StringBuffer();
                            vmfbf = new StringBuffer();
                            emfbf = new StringBuffer();
                            cmfbf = new StringBuffer();
                            transactionSB = new StringBuffer();
                            transactionLiSB = new StringBuffer();
                            personSB= new StringBuffer();

                        }
                          customerId = 0;
                          vendorId = 0;
                          employeeId = 0;

                        if(csvTransactionList.get(j) instanceof CSVTransaction) {
                            CSVTransaction csvTx = (CSVTransaction)csvTransactionList.get(j);
                            ++lastTxId;
                            Transaction tx = new Transaction();
                            tx.setId(lastTxId);
                            csvTx.setTransaction(tx);
                            saveLedgerInfo(csvTx);
                            logger.debug("AMLOG:: customerId: " + customerId +", vendorId: " + vendorId + ", employeeId " + employeeId + ", lastTxId " + lastTxId);
                            setTransactionSB(adminService.getTansactionSQL(csvTx, customerId, vendorId, employeeId, jobNo));
                            setTransactionLiSB(adminService.getATransactionLiSQL(csvTx, lastTxId));
                        }
    				}
                    savePartialTransactions();
                    endTime =  new Date().getTime();
                    logger.debug("AMLOG: File move to passed : " + (endTime - startTime));
    				Utils.moveFiles(children[i], Constants.TEMP_PATH, Constants.PASSED_PATH);
    				passedFile++;
    	    	}catch(Exception ex){
    				logger.debug("CERROR:: Transaction importing error for file: "+ children[i]);
    				logger.debug("CERROR:: Error Description: "+ ex.getMessage());
    				Utils.moveFiles(children[i], Constants. TEMP_PATH, Constants.FAILED_PATH);
    				failedFile++;
    	    	}
    	    }
            logger.debug("Imported Job id :" + jobNo);
            Job job = new Job();
            job.setCreated(Utils.getTodaysDate());
            job.setCreatedBy(Utils.getLoggedUserName());
            job.setJobId(jobNo);
            adminService.save(job);
            logger.debug("AMLOG: Rule start :");
//            applyRule(jobNo);
            logger.debug("AMLOG: Rule end.");

        }catch(Exception ex){
        	logger.debug("CERROR:: Exception occurs while importing csv files: "+ ex);
        }     
        adminService.addNode("Import Transaction", 50, request);
		model.addAttribute("totalFile",totalFile);
		model.addAttribute("passedFile",passedFile);
		model.addAttribute("failedFile",failedFile);
        return "admin/transactions";
    }

    public void saveLedgerInfo(CSVTransaction csvTransaction) {
        StringTokenizer ledgerTypes = new StringTokenizer(csvTransaction.getLedgerType(), ":");
        String tempStr = "";
        while(ledgerTypes.hasMoreElements()){
            tempStr = ledgerTypes.nextToken();
            String ledgerSql =  LedgerFactory.insertSql(tempStr.substring(0, 1), tempStr.substring(1, 3), csvTransaction);
            setLedgerSQL(ledgerSql, tempStr.substring(1, 3).trim(), csvTransaction);
        }
    }

    public void savePartialTransactions() {
        try {

            if(getVmfbf().length() > 0) {
                StringBuffer vmInsertSql = new StringBuffer();
                vmInsertSql.append(Constants.VENDOR_MASTER_LEDGER_QUERY);
                vmInsertSql.append(Constants.VALUES);
                vmInsertSql.append(appendSemicolon(getVmfbf()));
                adminJdbcService.executeSql(vmInsertSql);
                logger.debug("AMLOG:: Vendor saved.");
            }
            if(getCmfbf().length() > 0) {
                StringBuffer cmInsertSql = new StringBuffer();
                cmInsertSql.append(Constants.CUSTOMER_MASTER_LEDGER_QUERY);
                cmInsertSql.append(Constants.VALUES);
                cmInsertSql.append(appendSemicolon(getCmfbf()));
                adminJdbcService.executeSql(cmInsertSql);
                logger.debug("AMLOG:: Customer saved.");
            }
            if(getEmfbf().length() > 0) {
                StringBuffer emInsertSql = new StringBuffer();
                emInsertSql.append(Constants.EMPLOYEE_MASTER_LEDGER_QUERY);
                emInsertSql.append(Constants.VALUES);
                emInsertSql.append(appendSemicolon(getEmfbf()));
                adminJdbcService.executeSql(emInsertSql);
                logger.debug("AMLOG:: Employee saved.");
            }
            if(getTransactionSB().length() > 0) {
                StringBuffer txbf = new StringBuffer();
                txbf.append(Constants.TRANSACTION_INSERT_QUERY);
                txbf.append(Constants.VALUES);
                txbf.append(appendSemicolon(getTransactionSB()));
                adminJdbcService.executeSql(txbf);
            }
            if(getApbf().length() > 0) {
                StringBuffer apInsertSql = new StringBuffer();
                apInsertSql.append(Constants.ACCOUNTS_PAYABLE_LEDGER_QUERY);
                apInsertSql.append(Constants.VALUES);
                apInsertSql.append(appendSemicolon(getApbf()));
                adminJdbcService.executeSql(apInsertSql);
                logger.debug("AMLOG:: APDetail saved.");
            }
            if(getArbf().length() > 0) {
                StringBuffer arInsertSql = new StringBuffer();
                arInsertSql.append(Constants.ACCOUNTS_RECEIVABLE_LEDGER_QUERY);
                arInsertSql.append(Constants.VALUES);
                arInsertSql.append(appendSemicolon(getArbf()));
                adminJdbcService.executeSql(arInsertSql);
                logger.debug("AMLOG:: ARDetail saved.");

            }
            if(getTransactionLiSB().length() > 0) {
                StringBuffer txLiBf = new StringBuffer();
                txLiBf.append(Constants.TRANSACTIONLI_INSERT_QUERY);
                txLiBf.append(Constants.VALUES);
                txLiBf.append(appendSemicolon(getTransactionLiSB()));
                adminJdbcService.executeSql(txLiBf);
            }
        }
        catch (Exception ex) {
            logger.debug("CERROR:: partial transaction save exception: " + ex);
            logger.debug("CERROR:: Error description: " + ex);
        }

    }

    public void setLedgerSQL(String query, String ledgerType, CSVTransaction csvTx) {

        if (!Utils.isNullOrEmpty(query)) {

            if (Constants.LEDGER_TYPE_AP.equals(ledgerType)) {
                setApbf(query);
            } else if (Constants.LEDGER_TYPE_AR.equals(ledgerType)) {
                setArbf(query);
            } else if (Constants.LEDGER_TYPE_VMF.equals(ledgerType)) {
                setVmfbf(query);
                long aPerson = adminJdbcService.getLedgerId(csvTx.getVendorName(),Constants.VENDOR_MASTER_LEDGER);
                if(aPerson > 0 ) {
                    vendorId = aPerson;
                } else {
                    vendorId = lastVendor++;
                }
//                lastVendor++;
            } else if (Constants.LEDGER_TYPE_CMF.equals(ledgerType)) {
                setCmfbf(query);
                long aPerson = adminJdbcService.getLedgerId(csvTx.getCustomerName(), Constants.CUSTOMER_MASTER_LEDGER);
                if(aPerson > 0 ) {
                    customerId = aPerson;
                } else {
                    customerId = lastCustomer++;
                }
//                lastCustomer++;
            } else if (Constants.LEDGER_TYPE_EMF.equals(ledgerType)) {
                setEmfbf(query);
                long aPerson = adminJdbcService.getLedgerId(csvTx.getEmployeeName(),Constants.EMPLOYEE_MASTER_LEDGER);
                if(aPerson > 0 ) {
                    employeeId = aPerson;
                } else {
                    employeeId = lastEmployee++;
                }
//                lastEmployee++;
            }
        }

    }

    public String appendSemicolon(StringBuffer buffer) {
        if(buffer.lastIndexOf(",") > -1) {
            buffer.deleteCharAt(buffer.lastIndexOf(","));
            buffer.append(";");
        }
        return buffer.toString();

    }
    
	private String [] getHeaders(){
	    String [] headers=  {"transactionType"
				    		,"transactionModule"
				    		,"transactionCreatedBy"
				    		,"accountsPayableBalance"
				    		,"accountsReceivableBalance"
				    		,"advanceBalance"
				    		,"advanceType"
				    		,"amount"
				    		,"approvalLimit"
				    		,"approverName"
				    		,"approveDate"
				    		,"bankAccount"
				    		,"businessUnit"
				    		,"contractIdentification"
				    		,"creditLimit"
				    		,"discountAmount"
				    		,"documentDate"
				    		,"documentNumber"
				    		,"expenseAccount"
				    		,"expenseType"
				    		,"initiatorSubmitterName"
				    		,"interCompanyTransferAmount"
				    		,"inventoryAmount"
				    		,"manualOverride"
				    		,"orderQuantity"
				    		,"orderStatus"
				    		,"payeeName"
				    		,"paymentCurrency"
				    		,"paymentMethod"
				    		,"paymentTerms"
				    		,"previousOrder"
				    		,"previousOrderDate"
				    		,"purchaseOrderAmount"
				    		,"purchaseOrderDate"
				    		,"recipientBankAccountDomicile"
				    		,"recipientName"
				    		,"renderDate"
				    		,"salesOrderNumber"
				    		,"productCompletionStatus"
				    		,"productStockCode"
				    		,"productType"
				    		,"shipToAddress"
				    		,"shippedQuantity"
				    		,"shippingEntity"
				    		,"shippingNumber"
				    		,"shippingQuantity"
				    		,"tin"
				    		,"transactionAmount"
				    		,"transactionCost"
				    		,"transactionDate"
				    		,"transactionDocumentNumber"
//				    		,"transactionNarrative"
				    		,"transactionDescription"
				    		,"transactionNumber"
				    		,"unitOfMeasureCost"
				    		,"unitOfMeasurePrice"
				    		,"unitOfMeasureQuantity"
				    		,"ledgerType"
				    		,"customerName"
				    		,"customerAddress"
				    		,"customerAddress1"
				    		,"customerCity"
				    		,"customerState"
				    		,"customerZipCode"
				    		,"customerTaxEinNo"
				    		,"customerBankAccountNo"
				    		,"customerBankAccountLocation"
				    		,"customerType"
				    		,"customerCountry"
				    		,"vendorName"
				    		,"vendorAddress"
				    		,"vendorAddress1"
				    		,"vendorCity"
				    		,"vendorState"
				    		,"vendorZipCode"
				    		,"vendorTaxEinNo"
				    		,"vendorBankAccountNo"
				    		,"vendorBankAccountLocation"
				    		,"vendorType"
				    		,"vendorCountry"
				    		,"employeeName"
				    		,"employeeAddress"
				    		,"employeeCity"
				    		,"employeeState"
				    		,"employeeZipCode"
				    		,"employeeTaxEinNo"
				    		,"employeeBankAccountNo"
				    		,"employeeBankAccountLocation"
				    		,"employeeType"
				    		,"employeeCountry"
				    		,"entityStatus"

	    					};
	    return headers;
	}
	
	private CellProcessor [] getProcessors(){
		CellProcessor [] processors = {	 null
										,null
										,null
										,new ParseDouble()
										,new ParseDouble()
										,new ParseDouble()
										,null
										,new ParseDouble()
										,new ParseDouble()
										,null
                                        ,new ParseDate(Constants.MONTH_DAY_YEAR)
										,null
										,null
										,null
										,new ParseDouble()
										,new ParseDouble()
										,new ParseDate(Constants.MONTH_DAY_YEAR)
										,null
										,null
										,null
										,null
										,new ParseDouble()
										,new ParseDouble()
										,null
										,new ParseInt()
										,null
										,null
										,null
										,null
										,null
										,null
										,new ParseDate(Constants.MONTH_DAY_YEAR)
										,new ParseDouble()
										,new ParseDate(Constants.MONTH_DAY_YEAR)
										,null
										,null
										,new ParseDate(Constants.MONTH_DAY_YEAR)
										,new ParseInt()
										,null
										,null
										,null
										,null
										,new ParseInt()
										,null
										,null
										,new ParseInt()
										,null
										,new ParseDouble()
										,new ParseDouble()
										,new ParseDate(Constants.MONTH_DAY_YEAR)
										,null
//										,null
										,null
										,null
										,new ParseDouble()
										,new ParseDouble()
										,new ParseInt()
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null
										,null

									};
		return processors;
	}

    public void applyRule(int jobNo) {
       List<Rule> rules;
       try {
           adminJdbcService.deleteSuspiciousTransaction(jobNo);
           rules = adminService.getRules();
           if (rules != null) {
               logger.debug("Rules list size : " + rules.size());
               ExecutionActivity executionActivity = new ExecutionActivity();
               executionActivity.setExecutionTime(Utils.getTodaysDate());
               executionActivity.setComment("Applying rules for job no " + jobNo);
               executionActivity.setJobId(jobNo);
               adminService.save(executionActivity);
               for (Rule rule : rules) {
                   if (!Utils.isNullOrEmpty(rule.getSelectClause())){
                       adminJdbcService.saveSuspiciousTxList(rule, jobNo);
                   }
               }
           } else {
               logger.debug("No Rules found in database");
           }
       }  catch (Exception ex) {
            logger.debug("CERROR:Apply rule exception: " + ex);
            logger.debug("CERROR:Error description: " + ex.getMessage());
       }
    }

    @RequestMapping(value = "/*/loadTransaction.html", method = RequestMethod.GET)
    public String loadTransaction() {
        logger.debug("Transaction load start.");
        int jobNo;
        List apDetails = new ArrayList();
        List arDetails = new ArrayList();
        List fdDetails = new ArrayList();
        try {
            jobNo = adminJdbcService.getLastJobId() + 1;
            apDetails = adminJdbcService.getTempLedgerData(Constants.APDetail);
            if(apDetails != null) {
                logger.debug("APDetail size: " + apDetails);
                mappingAPDetails(apDetails, jobNo);
            }  else {
                logger.debug("No data found in APDetail.");
            }
            fdDetails = adminJdbcService.getTempLedgerData(Constants.FDDetail);
            if(fdDetails != null) {
                logger.debug("FDDetail size: " + fdDetails.size());
                mappingFDDetails(fdDetails, jobNo);
            }  else {
                logger.debug("No data found in APDetail.");
            }
            Job job = new Job();
            job.setCreated(Utils.getTodaysDate());
            job.setCreatedBy(Utils.getLoggedUserName());
            job.setJobId(jobNo);
            adminService.save(job);
//            applyRule(jobNo);

        } catch (Exception ex) {
            logger.debug("CERROR: Transaction load exception type: " + ex);
            logger.debug("CERROR: Error description: " + ex.getMessage());
        }

        return "admin/transactions";
    }
    public void mappingAPDetails(List apDetails, int jobId) {

        long personId = 0;
        long apId = 0;
//        long vmId = 0;
        String vmId = "";
//        long vmfId = 0;
        long vmfId = 0;
        double amount;
        String transAmount;
        String approver = "";
        Date transDate = null;
        try {
            for(Object ap : apDetails ) {
                Map map = (Map) ap;
                if( map.get("TransAmount1") != null)  {
                    transAmount =  (map.get("TransAmount1").toString());
//                    transAmount = transAmount.substring(transAmount.indexOf("$") + 1);
                    amount = Double.parseDouble(transAmount);
                }  else {
                    amount = 0;
                }
                if(map.get("TransDate") != null) {
                    transDate = Utils.stringToDate(map.get("TransDate").toString(), Constants.MONTH_DAY_YEAR);
                }
                if(map.get("ApproverName") != null) {
                    approver = map.get("ApproverName").toString();
                }
                vmfId = 0;
                if(map.get("VMFid") != null) {
//                    vmId = Long.parseLong(map.get("VMFid").toString());
                    vmId = map.get("VMFid").toString();
                    vmfId =  adminJdbcService.getVendorMasterLedgerId(vmId);
                    if(vmfId == 0) {
                        vmfId = saveVendorMasterLedger(0, vmId);
                    }/* else {
                        logger.debug("Vendor Master File Not Valid.");
                    }*/

                }
                logger.debug("AMLOG:: VMFid :" + vmfId);

                String transactionType = Constants.THIRD_PATRY_TRANSACTION;
                Transaction transaction = new Transaction();
                transaction.setAmount(amount);
                transaction.setJobId(jobId);
                transaction.setCreatedBy(Utils.getLoggedUserName());
                transaction.setCreated(Utils.today());
                transaction.setTransactionDate(transDate);
                transaction.setApprover(approver);
                transaction.setTransactionType(transactionType);
                if(vmfId > 0) {
                    VendorMasterLedger vendorMasterLedger = new VendorMasterLedger();
                    vendorMasterLedger.setId(vmfId);
                    transaction.setVendorMasterLedger(vendorMasterLedger);
                }
                adminService.save(transaction);

                saveAPDetailLi(transaction.getId(), map);

                if(map.get("id") != null) {
                    apId = Long.parseLong(map.get("id").toString());
                    if(apId > 0) {
//                        Map order = adminJdbcService.getOrdersEntityLedger(apId);
//                        saveOrdersEntityLi(transaction.getId(), order);
                        saveAccountsPayableLedger(transaction.getId(), apId, map.get("VMFid").toString());
                    } else {
                        logger.debug("Accounts Payable Ledger Id Not Valid.");
                    }
                } else {
                    logger.debug("Accounts Payable Ledger Id Not Found.");
                }

            }
        } catch (Exception ex) {
            logger.debug("CERROR: APDetails save exception.");
            logger.debug("CERROR: APDetails error description: " + ex.getMessage());

        }
    }

    public void mappingFDDetails(List fdDetails, int jobId) {

        long personId = 0;
        long fdId = 0;
//        long vmId = 0;
        String vmId = "";
       String payeeId = "";
        long vmfId = 0;
        long emfId = 0;
        double amount;
        String transAmount;
        String approver = "";
        Date transDate = null;
        try {
            for(Object fd : fdDetails ) {
                Map map = (Map) fd;


                if(map.get("PayeeID") != null) {
                    vmfId = 0;
                    emfId = 0;
                    payeeId = map.get("PayeeID").toString();
                    if(!Utils.isNullOrEmpty(payeeId)) {
                        if('V'== payeeId.charAt(0)) {
//                            emfId = saveEmployeeMasterLedger(0, payeeId);
                            continue;
                        } else if('E' == payeeId.charAt(0)) {
                            emfId = adminJdbcService.getEmployeeMasterLedgerId(payeeId);
                            if(emfId == 0) {
                                emfId = saveEmployeeMasterLedger(0, payeeId);
                            } else {
                                logger.debug("Employee Master File found Valid.");
                            }
                        }

                    }


                }


                if( map.get("TransAmount1") != null)  {
                    transAmount =  (map.get("TransAmount1").toString());
//                    transAmount = transAmount.substring(transAmount.indexOf("$") + 1);
                    amount = Double.parseDouble(transAmount);
                }  else {
                    amount = 0;
                }
                if(map.get("TransDate") != null) {
                    transDate = Utils.stringToDate(map.get("TransDate").toString(),Constants.MONTH_DAY_YEAR);
                }
                if(map.get("ApproverName") != null) {
                    approver = map.get("ApproverName").toString();
                }

                String transactionType = Constants.CUSTOMER_TRANSACTION;
                Transaction transaction = new Transaction();
                transaction.setAmount(amount);
                transaction.setJobId(jobId);
                transaction.setCreatedBy(Utils.getLoggedUserName());
                transaction.setCreated(Utils.today());
                transaction.setTransactionDate(transDate);
                transaction.setApprover(approver);
                transaction.setTransactionType(transactionType);
                if(vmfId > 0) {
                    VendorMasterLedger vendorMasterLedger = new VendorMasterLedger();
                    vendorMasterLedger.setId(vmfId);
                    transaction.setVendorMasterLedger(vendorMasterLedger);
                } else if(emfId > 0) {
                    EmployeeMasterLedger employeeMasterLedger = new EmployeeMasterLedger();
                    employeeMasterLedger.setId(emfId);
                    transaction.setEmployeeMasterLedger(employeeMasterLedger);
                }
                adminService.save(transaction);

                saveFDDetailLi(transaction.getId(), map);

                if(map.get("id") != null) {
                    fdId = Long.parseLong(map.get("id").toString());
                    if(fdId > 0) {
//                        Map order = adminJdbcService.getOrdersEntityLedger(apId);
//                        saveOrdersEntityLi(transaction.getId(), order);
//                        saveAccountsPayableLedger(transaction.getId(), apId);
                        saveFundsDisbursementLedger(transaction.getId(), fdId);
                    } else {
                        logger.debug("Found Disbursement Ledger Id Not Valid.");
                    }
                } else {
                    logger.debug("Found Disbursement Ledger Id Not Found.");
                }

            }
        } catch (Exception ex) {
            logger.debug("CERROR: FDDetails save exception.");
            logger.debug("CERROR: FDDetails error description: " + ex.getMessage());

        }
    }

    public long saveTrx(double amount, Date transDate, int jobId, String discriminator, PersonEntity personEntity, String approver, String transactionType) {
        Transaction transaction = new Transaction();
        try {
            transaction.setAmount(amount);
            transaction.setJobId(jobId);
            transaction.setCreatedBy(Utils.getLoggedUserName());
            transaction.setCreated(Utils.today());
            transaction.setTransactionDate(transDate);
            transaction.setApprover(approver);
            transaction.setTransactionType(transactionType);

            adminService.save(transaction);
        } catch (Exception ex) {
            logger.debug("CERROR: Transaction save exception.");
            logger.debug("CERROR: Transaction save error description: " + ex.getMessage());
        }
        return transaction.getId();
    }
    public void saveAccountsPayableLedger(long txId, long apid, String VMFid) {

        try {
             adminJdbcService.saveAccountsPaybleLedger(txId, apid,VMFid );
        } catch ( Exception ex) {
            logger.debug("CERROR: Accounts payable ledger exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
    }
    public void saveAccountsReceiveAbleLedger(long txId, long arid) {

        try {
             adminJdbcService.saveAccountsReceiveAbleLedger(txId, arid);
        } catch ( Exception ex) {
            logger.debug("CERROR: Accounts receive able ledger exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
    }

    public long saveVendorMasterLedger(long txId, String vmId) {

        try {
             return adminJdbcService.saveVendorMasterLedger(txId, vmId);
        } catch ( Exception ex) {
            logger.debug("CERROR: Vendor Master file exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
        return 0;
    }

    public long saveVendorPersonInfo(long vmId, String vendor, long vmfid) {
        long personId = 0;
        try {
             personId = adminJdbcService.saveVendorPersonInfo(vmId, vendor, vmfid);
        } catch ( Exception ex) {
            logger.debug("CERROR: Vendor Person exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
        return personId;
    }
    public long saveCustomerPersonInfo(long cuId, String customer, long cmfid, String employee, long emfid ) {
        long personId = 0;
        try {
             personId = adminJdbcService.saveCustomerPersonInfo(cuId, customer, cmfid, employee, emfid);
        } catch ( Exception ex) {
            logger.debug("CERROR: Vendor Person exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
        return personId;
    }
    public long saveEmployeePersonInfo(long cuId, String employee, long emfid) {
        long personId = 0;
        try {
             personId = adminJdbcService.saveEmployeePersonInfo(cuId, employee, emfid);
        } catch ( Exception ex) {
            logger.debug("CERROR: Vendor Person exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
        return personId;
    }
    public long saveCusomerMasterLedger(long txId, long cmId) {

        try {
             return adminJdbcService.saveCustomerMasterLedger(txId, cmId);
        } catch ( Exception ex) {
            logger.debug("CERROR: Accounts payable ledger exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
        return 0;
    }
    public long saveEmployeeMasterLedger(long txId, String payeeId) {

        try {
             return adminJdbcService.saveEmployeeMasterLedger(txId, payeeId);
        } catch ( Exception ex) {
            logger.debug("CERROR: Employee Master ledger exception.");
            logger.debug("CERROR: Employee Master Exception description: " + ex.getMessage());
        }
        return 0;
    }
   public void saveFundsDisbursementLedger(long txId, long fdId) {

        try {
             adminJdbcService.saveFundsDisbursementLedger(txId, fdId);
        } catch ( Exception ex) {
            logger.debug("CERROR: Found disbursement ledger exception.");
            logger.debug("CERROR: Exception description: " + ex.getMessage());
        }
    }

    public void mappingARDetails(List arDetails, int jobId) {
        long personId = 0;
        long arId = 0;
        long cmId = 0;
        long cmfId = 0;
        long emfId = 0;
        double amount;
        String transAmount;
        Date transDate = null;
        String approver = "";
        try {
            for (Object ar : arDetails) {
                Map map = (Map) ar;
                if (map.get("TransAmt") != null) {
                    transAmount = ((map.get("TransAmt").toString()).replaceAll(",", ""));
                    logger.debug("AMLOG: ARDetail transaction amount: " + transAmount);
                    transAmount = transAmount.substring(transAmount.indexOf("$") + 1);
                    amount = Double.parseDouble(transAmount);
                } else {
                    amount = 0;
                }
                if (map.get("TransDt") != null) {
                    transDate = Utils.validGeneralStrToDate(map.get("TransDt").toString());
                }
                if (map.get("Approver") != null) {
                    approver = map.get("Approver").toString();
                }
                if (map.get("CustomerID") != null) {
                    cmId = Long.parseLong(map.get("CustomerID").toString());
                    cmfId = adminJdbcService.getCustomerMasterLedgerId(cmId);
//                    emfId = adminJdbcService.getEmployeeMasterLedgerId(cmId);
                    if (cmfId == 0) {
                        cmfId = saveCusomerMasterLedger(0, cmId);
                    } else {
                        logger.debug("Customer Master File Not Valid.");
                    }
                    if(emfId == 0) {
//                        emfId = saveEmployeeMasterLedger(0, cmId);
                    } else {
                        logger.debug("Employee Master File found Valid.");
                    }
                } else {
                    logger.debug("Customer Master File Not Found.");
                }
                String transactionType = Constants.CUSTOMER_TRANSACTION;
                Transaction transaction = new Transaction();
                transaction.setAmount(amount);
                transaction.setJobId(jobId);
                transaction.setCreatedBy(Utils.getLoggedUserName());
                transaction.setCreated(Utils.today());
                transaction.setTransactionDate(transDate);
                transaction.setApprover(approver);
                transaction.setTransactionType(transactionType);
                if (cmfId > 0) {
                    CustomerMasterLedger customerMasterLedger = new CustomerMasterLedger();
                    customerMasterLedger.setId(cmfId);

                    transaction.setCustomerMasterLedger(customerMasterLedger);

                }
                if (emfId > 0) {
                    EmployeeMasterLedger employeeMasterLedger = new EmployeeMasterLedger();
                    employeeMasterLedger.setId(emfId);
                    transaction.setEmployeeMasterLedger(employeeMasterLedger);
                }
                adminService.save(transaction);
                if (map.get("id") != null) {
                    saveARDetailLi(transaction.getId(), map);
                   /* if(cmId > 0) {
                        Map customer =  adminJdbcService.getCustomerMasterLedger(cmId);
                        if(customer != null ){
                            saveCustomerMasterLi(txId, customer);
                        }
                    } else {
                        logger.debug("No customer found.");
                    }*/
                    arId = Long.parseLong(map.get("id").toString());
                    if (arId > 0) {
                        saveAccountsReceiveAbleLedger(transaction.getId(), arId);
                        Map fnd = adminJdbcService.getFundsDisbursementLedger(arId);
                        saveFundsDisbursementLedgerLi(transaction.getId(), fnd);
                    } else {
                        logger.debug("Accounts Receivable Ledger Not Valid.");
                    }
                } else {
                    logger.debug("Accounts Receivable Ledger Not Found.");
                }


            }
        } catch (Exception ex) {
            logger.debug("CERROR: ARDetails save exception.");
            logger.debug("CERROR: ARDetails error description: " + ex.getMessage());

        }
    }

    public void saveTransactionLi(long txId, String value, String header) {
        logger.debug("Transaction Li save start. ");
        try {
             adminJdbcService.saveTransactionLi(txId,  value, header);
        } catch (Exception ex ) {
            logger.debug("CERROR:: Transaction Li save exception." + ex);
            logger.debug("CERROR:: Exception description: " + ex.getMessage());
        }
    }
    @RequestMapping(value = "/*/applyRule.html", method = RequestMethod.GET)
    public void applyRuleTemp(@RequestParam("jobNo") int jobNo) {
         logger.debug("Apply rule start");
         applyRule(jobNo);
        logger.debug("Apply rule end.");
    }

    public void saveVendorMasterLi(long txId, Map map) {
        logger.debug("VendorMasterLi Li save start.");
        saveTransactionLi(txId, map.get("ContactFName") != null ? map.get("ContactFName").toString(): "", TransactionLIHeader.ContactFName.getHeader() );
        saveTransactionLi(txId, map.get("ContactLName") != null ? map.get("ContactLName").toString(): "", TransactionLIHeader.ContactLName.getHeader() );
        saveTransactionLi(txId, map.get("BankRouting") != null ? map.get("BankRouting").toString(): "", TransactionLIHeader.BankRouting.getHeader() );
        saveTransactionLi(txId, map.get("PaymentMethod") != null ? map.get("PaymentMethod").toString(): "", TransactionLIHeader.PaymentMethod.getHeader() );
        saveTransactionLi(txId, map.get("Currency") != null ? map.get("Currency").toString(): "", TransactionLIHeader.Currency.getHeader() );
        logger.debug("VendorMasterLi Li save end.");
    }

    public void saveARDetailLi(long txId, Map map) {
        logger.debug("ARDetail Li save start.");
        saveTransactionLi(txId, map.get("ApproverTitle") != null ? map.get("ApproverTitle").toString(): "", TransactionLIHeader.ApproverTitle.getHeader() );
        saveTransactionLi(txId, map.get("DocID") != null ? map.get("DocID").toString() : "", TransactionLIHeader.DocID.getHeader() );
        saveTransactionLi(txId, map.get("DocDt") != null ? map.get("DocDt").toString() : "", TransactionLIHeader.DocDt.getHeader() );
        saveTransactionLi(txId, map.get("CustomerType") != null ? map.get("CustomerType").toString() : "", TransactionLIHeader.CustomerType.getHeader() );
        saveTransactionLi(txId, map.get("TransNarrative") != null ? map.get("TransNarrative").toString() : "", TransactionLIHeader.TransNarrative.getHeader() );
        saveTransactionLi(txId, map.get("SKU") != null ? map.get("SKU").toString() : "", TransactionLIHeader.SKU.getHeader() );
        saveTransactionLi(txId, map.get("UnitQty") != null ? map.get("UnitQty").toString() : "", TransactionLIHeader.UnitQty.getHeader() );
        saveTransactionLi(txId, map.get("UnitPrice") != null ? map.get("UnitPrice").toString() : "", TransactionLIHeader.UnitPrice.getHeader() );
        saveTransactionLi(txId, map.get("GrossCost") != null ? map.get("GrossCost").toString() : "", TransactionLIHeader.GrossCost.getHeader() );
        saveTransactionLi(txId, map.get("Discount") != null ? map.get("Discount").toString() : "", TransactionLIHeader.Discount.getHeader() );
        saveTransactionLi(txId, map.get("DiscountAmt") != null ? map.get("DiscountAmt").toString() : "", TransactionLIHeader.DiscountAmt.getHeader() );
        saveTransactionLi(txId, map.get("Tax") != null ? map.get("Tax").toString() : "", TransactionLIHeader.Tax.getHeader() );
        saveTransactionLi(txId, map.get("Duty") != null ? map.get("Duty").toString() : "", TransactionLIHeader.Duty.getHeader() );
        saveTransactionLi(txId, map.get("ShippingTerms") != null ? map.get("ShippingTerms").toString() : "", TransactionLIHeader.ShippingTerms.getHeader() );
        saveTransactionLi(txId, map.get("ShippingID") != null ? map.get("ShippingID").toString() : "", TransactionLIHeader.ShippingID.getHeader() );
        saveTransactionLi(txId, map.get("Submitter") != null ? map.get("Submitter").toString() : "", TransactionLIHeader.Submitter.getHeader() );
        saveTransactionLi(txId, map.get("ManualOverride") != null ? map.get("ManualOverride").toString() : "", TransactionLIHeader.ManualOverride.getHeader() );
        saveTransactionLi(txId, map.get("RenderDt") != null ? map.get("RenderDt").toString() : "", TransactionLIHeader.RenderDt.getHeader() );
        logger.debug("ARDetail Li save end.");
    }
    public void saveAPDetailLi(long txId, Map map) {
        logger.debug("APDetail Li save start.");
        saveTransactionLi(txId, map.get("DocDate") != null ? map.get("DocDate").toString(): "", TransactionLIHeader.DocumentDate.getHeader() );
        saveTransactionLi(txId, map.get("DocNumber") != null ? map.get("DocNumber").toString() : "", TransactionLIHeader.DocumentNumber.getHeader() );
        saveTransactionLi(txId, map.get("InitiatorSubmitterName") != null ? map.get("InitiatorSubmitterName").toString() : "", TransactionLIHeader.InitiatorSubmitterName.getHeader() );
        saveTransactionLi(txId, map.get("ContractIdentification") != null ? map.get("ContractIdentification").toString() : "", TransactionLIHeader.ContractIdentification.getHeader() );
        saveTransactionLi(txId, map.get("ManualOverride") != null ? map.get("ManualOverride").toString() : "", TransactionLIHeader.ManualOverride.getHeader() );
        saveTransactionLi(txId, map.get("Revised PO Amount") != null ? map.get("Revised PO Amount").toString() : "", TransactionLIHeader.POamount.getHeader() );
        saveTransactionLi(txId, map.get("POdate") != null ? map.get("POdate").toString() : "", TransactionLIHeader.POdate.getHeader() );
        saveTransactionLi(txId, map.get("TransNarrative") != null ? map.get("TransNarrative").toString() : "", TransactionLIHeader.TransNarrative.getHeader() );
        saveTransactionLi(txId, map.get("UnitCost") != null ? map.get("UnitCost").toString() : "", TransactionLIHeader.UnitCost.getHeader());
        saveTransactionLi(txId, map.get("UnitQty") != null ? map.get("UnitQty").toString() : "", TransactionLIHeader.UnitQty.getHeader() );
        logger.debug("APDetail Li save end.");
    }
    public void saveFDDetailLi(long txId, Map map) {
        logger.debug("FDDetail Li save start.");
//        saveTransactionLi(txId, map.get("DocDate") != null ? map.get("DocDate").toString(): "", TransactionLIHeader.DocumentDate.getHeader() );
//        saveTransactionLi(txId, map.get("DocNumber") != null ? map.get("DocNumber").toString() : "", TransactionLIHeader.DocumentNumber.getHeader() );
         saveTransactionLi(txId, map.get("InitiatorSubmitterName") != null ? map.get("InitiatorSubmitterName").toString() : "", TransactionLIHeader.InitiatorSubmitterName.getHeader() );
          saveTransactionLi(txId, map.get("ContractIdentification") != null ? map.get("ContractIdentification").toString() : "", TransactionLIHeader.ContractIdentification.getHeader() );
          saveTransactionLi(txId, map.get("CheckDate") != null ? map.get("CheckDate").toString() : "", TransactionLIHeader.CheckDate.getHeader() );
          saveTransactionLi(txId, map.get("Check no") != null ? map.get("Check no").toString() : "", TransactionLIHeader.CheckNo.getHeader() );
          saveTransactionLi(txId, map.get("PayeeID") != null ? map.get("PayeeID").toString(): "", TransactionLIHeader.PayeeID.getHeader() );
         saveTransactionLi(txId, map.get("ManualOverride") != null ? map.get("ManualOverride").toString() : "", TransactionLIHeader.ManualOverride.getHeader() );
//        saveTransactionLi(txId, map.get("Revised PO Amount") != null ? map.get("Revised PO Amount").toString() : "", TransactionLIHeader.POamount.getHeader() );
//        saveTransactionLi(txId, map.get("POdate") != null ? map.get("POdate").toString() : "", TransactionLIHeader.POdate.getHeader() );
         saveTransactionLi(txId, map.get("TransNarrative") != null ? map.get("TransNarrative").toString() : "", TransactionLIHeader.TransNarrative.getHeader() );
//        saveTransactionLi(txId, map.get("UnitCost") != null ? map.get("UnitCost").toString() : "", TransactionLIHeader.UnitCost.getHeader());
//        saveTransactionLi(txId, map.get("UnitQty") != null ? map.get("UnitQty").toString() : "", TransactionLIHeader.UnitQty.getHeader() );
        logger.debug("FDDetail Li save end.");
    }

    public void saveCustomerMasterLi(long txId, Map map) {
        logger.debug("CustomerMasterLi Li save start.");
        saveTransactionLi(txId, map.get("LName") != null ? map.get("LName").toString(): "", TransactionLIHeader.LName.getHeader() );
        saveTransactionLi(txId, map.get("FName") != null ? map.get("FName").toString(): "", TransactionLIHeader.FName.getHeader() );
        saveTransactionLi(txId, map.get("RegionState") != null ? map.get("RegionState").toString(): "", TransactionLIHeader.RegionState.getHeader() );
        saveTransactionLi(txId, map.get("LatLong") != null ? map.get("LatLong").toString(): "", TransactionLIHeader.LatLong.getHeader() );
        saveTransactionLi(txId, map.get("TIN") != null ? map.get("TIN").toString(): "", TransactionLIHeader.TIN.getHeader() );
        saveTransactionLi(txId, map.get("Title") != null ? map.get("Title").toString(): "", TransactionLIHeader.Title.getHeader() );
        saveTransactionLi(txId, map.get("BankDomicile") != null ? map.get("BankDomicile").toString(): "", TransactionLIHeader.BankDomicile.getHeader() );
        saveTransactionLi(txId, map.get("BankRouting") != null ? map.get("BankRouting").toString(): "", TransactionLIHeader.BankRouting.getHeader() );
        saveTransactionLi(txId, map.get("StartDt") != null ? map.get("StartDt").toString(): "", TransactionLIHeader.StartDt.getHeader() );
        saveTransactionLi(txId, map.get("EndDt") != null ? map.get("EndDt").toString(): "", TransactionLIHeader.EndDt.getHeader() );
        saveTransactionLi(txId, map.get("Department") != null ? map.get("Department").toString(): "", TransactionLIHeader.Department.getHeader() );
        saveTransactionLi(txId, map.get("Supervisor") != null ? map.get("Supervisor").toString(): "", TransactionLIHeader.Supervisor.getHeader() );
        saveTransactionLi(txId, map.get("Salary") != null ? map.get("Salary").toString(): "", TransactionLIHeader.Salary.getHeader() );
        saveTransactionLi(txId, map.get("Currency") != null ? map.get("Currency").toString(): "", TransactionLIHeader.Currency.getHeader() );
        saveTransactionLi(txId, map.get("Gender") != null ? map.get("Gender").toString(): "", TransactionLIHeader.Gender.getHeader() );
        saveTransactionLi(txId, map.get("DOB") != null ? map.get("DOB").toString(): "", TransactionLIHeader.DOB.getHeader() );
        saveTransactionLi(txId, map.get("Ethnicity") != null ? map.get("Ethnicity").toString(): "", TransactionLIHeader.Ethnicity.getHeader() );
        saveTransactionLi(txId, map.get("SpendLimit") != null ? map.get("SpendLimit").toString(): "", TransactionLIHeader.SpendLimit.getHeader() );
        saveTransactionLi(txId, map.get("ApprovalLimit") != null ? map.get("ApprovalLimit").toString(): "", TransactionLIHeader.ApprovalLimit.getHeader() );
        saveTransactionLi(txId, map.get("BusinessUnit") != null ? map.get("BusinessUnit").toString(): "", TransactionLIHeader.BusinessUnit.getHeader() );

        logger.debug("CustomerMasterLi Li save end.");
    }

    public  void saveFundsDisbursementLedgerLi(long txId, Map map) {
        logger.debug("FundsDisbursementLedgerLi save start.");
        saveTransactionLi(txId, map.get("DocDt") != null ? map.get("DocDt").toString(): "", TransactionLIHeader.DocDt.getHeader() );
        saveTransactionLi(txId, map.get("DocNumber") != null ? map.get("DocNumber").toString(): "", TransactionLIHeader.DocNumber.getHeader() );
        saveTransactionLi(txId, map.get("InititatorName") != null ? map.get("InititatorName").toString(): "", TransactionLIHeader.InititatorName.getHeader() );
        saveTransactionLi(txId, map.get("ManualOverride") != null ? map.get("ManualOverride").toString(): "", TransactionLIHeader.ManualOverride.getHeader() );
        saveTransactionLi(txId, map.get("PayeeID") != null ? map.get("PayeeID").toString(): "", TransactionLIHeader.PayeeID.getHeader() );
        saveTransactionLi(txId, map.get("PayeeType") != null ? map.get("PayeeType").toString(): "", TransactionLIHeader.PayeeType.getHeader() );
        saveTransactionLi(txId, map.get("TransNarrative") != null ? map.get("TransNarrative").toString(): "", TransactionLIHeader.TransNarrative.getHeader() );
        saveTransactionLi(txId, map.get("Tax") != null ? map.get("Tax").toString(): "", TransactionLIHeader.Tax.getHeader() );
        saveTransactionLi(txId, map.get("ApproverTitle") != null ? map.get("ApproverTitle").toString(): "", TransactionLIHeader.ApproverTitle.getHeader() );
        saveTransactionLi(txId, map.get("CustomsDuty") != null ? map.get("CustomsDuty").toString(): "", TransactionLIHeader.CustomsDuty.getHeader() );
        logger.debug("FundsDisbursementLedgerLi save end.");
    }
    public void saveOrdersEntityLi(long txId, Map map) {
        logger.debug("OrdersEntityLi save start.");
        saveTransactionLi(txId, map.get("BusinessUnit") != null ? map.get("BusinessUnit").toString(): "", TransactionLIHeader.BusinessUnit.getHeader() );
        saveTransactionLi(txId, map.get("DiscountAmt") != null ? map.get("DiscountAmt").toString(): "", TransactionLIHeader.DiscountAmt.getHeader() );
        saveTransactionLi(txId, map.get("DocDt") != null ? map.get("DocDt").toString(): "", TransactionLIHeader.DocDt.getHeader() );
        saveTransactionLi(txId, map.get("DocNbr") != null ? map.get("DocNbr").toString(): "", TransactionLIHeader.DocNbr.getHeader() );
        saveTransactionLi(txId, map.get("PayeeID") != null ? map.get("PayeeID").toString(): "", TransactionLIHeader.PayeeID.getHeader() );
        saveTransactionLi(txId, map.get("PayeeType") != null ? map.get("PayeeType").toString(): "", TransactionLIHeader.PayeeType.getHeader() );
        saveTransactionLi(txId, map.get("Submitter") != null ? map.get("Submitter").toString(): "", TransactionLIHeader.Submitter.getHeader() );
        saveTransactionLi(txId, map.get("ManualOverride") != null ? map.get("ManualOverride").toString(): "", TransactionLIHeader.ManualOverride.getHeader() );
        saveTransactionLi(txId, map.get("Qty") != null ? map.get("Qty").toString(): "", TransactionLIHeader.Qty.getHeader() );
        saveTransactionLi(txId, map.get("Status") != null ? map.get("Status").toString(): "", TransactionLIHeader.Status.getHeader() );
        saveTransactionLi(txId, map.get("PaymentTerms") != null ? map.get("PaymentTerms").toString(): "", TransactionLIHeader.PaymentTerms.getHeader() );
        saveTransactionLi(txId, map.get("PreviousOrder") != null ? map.get("PreviousOrder").toString(): "", TransactionLIHeader.PreviousOrder.getHeader() );
        saveTransactionLi(txId, map.get("PreviousOrderDt") != null ? map.get("PreviousOrderDt").toString(): "", TransactionLIHeader.PreviousOrderDt.getHeader() );
        saveTransactionLi(txId, map.get("RenderDt") != null ? map.get("RenderDt").toString(): "", TransactionLIHeader.RenderDt.getHeader() );
        saveTransactionLi(txId, map.get("SalesOrderNumber") != null ? map.get("SalesOrderNumber").toString(): "", TransactionLIHeader.SalesOrderNumber.getHeader() );
        saveTransactionLi(txId, map.get("ServiceProductStockCode") != null ? map.get("ServiceProductStockCode").toString(): "", TransactionLIHeader.ServiceProductStockCode.getHeader() );
        saveTransactionLi(txId, map.get("ServiceProductType") != null ? map.get("ServiceProductType").toString(): "", TransactionLIHeader.ServiceProductType.getHeader() );
        saveTransactionLi(txId, map.get("ShipToId") != null ? map.get("ShipToId").toString(): "", TransactionLIHeader.ShipToId.getHeader() );
        saveTransactionLi(txId, map.get("ShippedQty") != null ? map.get("ShippedQty").toString(): "", TransactionLIHeader.ShippedQty.getHeader() );
        saveTransactionLi(txId, map.get("TransCost") != null ? map.get("TransCost").toString(): "", TransactionLIHeader.TransCost.getHeader() );
        saveTransactionLi(txId, map.get("UnitPrice") != null ? map.get("UnitPrice").toString(): "", TransactionLIHeader.UnitPrice.getHeader() );
        saveTransactionLi(txId, map.get("UnitQty") != null ? map.get("UnitQty").toString(): "", TransactionLIHeader.UnitQty.getHeader() );
        saveTransactionLi(txId, map.get("ServiceDt") != null ? map.get("ServiceDt").toString(): "", TransactionLIHeader.ServiceDt.getHeader() );
        saveTransactionLi(txId, map.get("Tax") != null ? map.get("Tax").toString(): "", TransactionLIHeader.Tax.getHeader() );
        saveTransactionLi(txId, map.get("Duty") != null ? map.get("Duty").toString(): "", TransactionLIHeader.Duty.getHeader() );
        saveTransactionLi(txId, map.get("ApproverDt") != null ? map.get("ApproverDt").toString(): "", TransactionLIHeader.ApproverDt.getHeader() );
        saveTransactionLi(txId, map.get("ApproverTitle") != null ? map.get("ApproverTitle").toString(): "", TransactionLIHeader.ApproverTitle.getHeader() );

        logger.debug("OrdersEntityLi save end.");
    }

    public StringBuffer getApbf() {
        return apbf;
    }

    public void setApbf(String  query) {
        apbf.append(query);
        apbf.append(',');
    }

    public StringBuffer getArbf() {
        return arbf;
    }

    public void setArbf(String  query) {
        arbf.append(query);
        arbf.append(',');
    }

    public StringBuffer getVmfbf() {
        return vmfbf;
    }

    public void setVmfbf(String  query) {
        vmfbf.append(query);
        vmfbf.append(',');
    }

    public StringBuffer getEmfbf() {
        return emfbf;
    }

    public void setEmfbf(String  query) {
        emfbf.append(query);
        emfbf.append(',');
    }

    public StringBuffer getCmfbf() {
        return cmfbf;
    }

    public void setCmfbf(String  query) {
        cmfbf.append(query);
        cmfbf.append(',');
    }

    public StringBuffer getTransactionLiSB() {
        return transactionLiSB;
    }

    public void setTransactionLiSB(String query) {
        transactionLiSB.append(query);
        transactionLiSB.append(',');

    }

    public StringBuffer getTransactionSB() {
        return transactionSB;
    }

    public void setTransactionSB(String  query) {
        transactionSB.append(query);
        transactionSB.append(',');
    }

    public StringBuffer getPersonSB() {
        return personSB;
    }

    public void setPersonSB(String query) {
        personSB.append(query);
        personSB.append(',');
    }




}
