package com.dsinv.abac.util;

import java.util.Calendar;

public class Constants {

	public static final String GLOBAL_MSG_KEY = "REQUEST_MESSAGE";
	public static final String GLOBAL_LOGIN_ROLE = "GLOBAL_LOGIN_ROLE";
    public static final String MESSAGE_FILE_PATH = "../configurations/messages/messages_en.properties";
    public static final String APPLICATION_CONFIGURATION_FILE_PATH = "../configurations/properties/database.properties";
    public static final String BREADCRUMB_REACTIVE_PROJECT_MANAGEMENT = "Reactive Project Management";
    public static final String CHECK_BOX_ON="on";
    public static final String CHECK_BOX_OFF="off";
    public static final String MONTH_DAY_YEAR="MM/dd/yyyy";
    public static final String EMPTY_STRING="";
    public static final String DISCRIMINATOR_VALUE_EMPLOYEE="EMPLOYEE";
    public static final String DISCRIMINATOR_VALUE_CUSTOMER="CUSTOMER";
    public static final String DISCRIMINATOR_VALUE_VENDOR="VENDOR";
    public static final String LEDGER_TYPE_AL="AL";		// Advanced Ledger
    public static final String LEDGER_TYPE_AP="AP";		// Accounts Payable
    public static final String LEDGER_TYPE_AR="AR";		// Accounts Receivable
    public static final String LEDGER_TYPE_CL="CL";		// Completion Ledger
    public static final String LEDGER_TYPE_GL="GL";		// General Ledger
    public static final String LEDGER_TYPE_SL="SL";		// Shipping Ledger
    public static final String LEDGER_TYPE_VMF="VM";		// Vendor master file
    public static final String LEDGER_TYPE_CMF="CM";		// Vendor master file
    public static final String LEDGER_TYPE_EMF="EM";		// Vendor master file
    public static final String DOWNLOAD_PATH =  Utils.getApplicationPropertyValue("folder.import.download");
    public static final String TEMP_PATH =  Utils.getApplicationPropertyValue("folder.import.temp");
    public static final String FAILED_PATH =  Utils.getApplicationPropertyValue("folder.import.failed");
    public static final String PASSED_PATH =  Utils.getApplicationPropertyValue("folder.import.passed");
    public static final String WEEKEND =  "Fri";
    public static final String TRANSACTION =  "Transaction";
    public static final String STATUS_NEW =  "New";
    public static final String REALTIME_PROJECT=  "RealTimeProject";
    public static final String REALTIME_PROJECT_TABLE=  "real_time_project";
    public static final String MONTH_DAY_YEAR_UNDER_SCORE="MMddyyyy";
    public static final String CONTROL="Control";
    public static final String CONTROL_TABLE="control";
    public static final String ACTIVE="true";
    public static final String PROACTIVE_PROJECT="ProactiveProject";
    public static final String PROACTIVE_PROJECT_TABLE="proactive_project";
    public static final String REACTIVE_PROJECT="ReactiveProject";
    public static final String USER="User";
    public static final String USER_TABLE="user";
    public static final String POLICY="Policy";
    public static final String TRAINING="Training";
    public static final String POLICY_TABLE="policy";
    public static final String TRAINING_TABLE="training";
    public static final String TRANSACTION_APPROVAL="TransactionApproval";
    public static final String TRANSACTION_APPROVAL_TABLE="transaction_approval";
    public static final String PROACTIVE_BLOCK_WEIGHT="ProactiveBlockWeight";
    public static final String REALTIME_PROJECT_NAME="projectName";
    public static final String REALTIME_PROJECT_NAME_COLUMN="project_name";
    public static final String REALTIME_STATUS_COLUMN="status";
    public static final String REALTIME_ASSIGNTO="assignTo";
    public static final String REALTIME_ASSIGNTO_COLUMN="assign_to";
    public static final String REALTIME_ASSIGNMENT_SIZE="noOfTransaction";
    public static final String REALTIME_ASSIGNMENT_SIZE_COLUMN="no_of_transaction";
    public static final String REALTIME_ASSIGNMENT_REVIEW_COLUMN="review";
    public static final String LOOKUP="Lookup";
    public static final String APDetail="APDetail1";
    public static final String FDDetail="FDDetail1";
    public static final String ARDetail="ARDetail";
    public static final String VALUES="VALUES";
    public static final String PERSON_INFORMATION_INSERT_QUERY="INSERT INTO person_information(name, tax_ein_no, bank_account_no, bank_account_location, type, address, address1, city, state, zip_code, country_name, discriminator_value)";
    public static final String TRANSACTIONLI_INSERT_QUERY="INSERT INTO transaction_li (transaction_id, headName, value)";
    public static final String TRANSACTION_INSERT_QUERY="INSERT INTO transaction (transaction_type, transaction_module, created, created_by, transaction_date, amount, approver, approve_date, real_time_touched, customer_master_ledger_FK, vendor_master_ledger_FK, employee_master_ledger_FK, job_Id)";
    public static final String ACCOUNTS_PAYABLE_LEDGER_QUERY="INSERT INTO accounts_payable_ledger_information (accounts_payable_balance, approver_name, bank_account, contact_identification, document_date, document_number, initiator_or_submitor_name, inter_company_transfer_amount, manual_override, payee_name, payment_currency, payment_method, purchase_order_amount, purchase_order_date, recipient_bank_account_domicile, tin, transaction_narrative_description,  transaction_number, transaction_date, unit_of_measure_quantity, unit_of_measure_cost,  transaction_fk,  transaction_amount, is_debit)";
    public static final String ACCOUNTS_RECEIVABLE_LEDGER_QUERY="INSERT INTO accounts_receivable_ledger_information (approver_name, bank_account, document_date, document_number, initiator_or_submitor_name, inter_company_transfer_amount, manual_override, payment_currency, payment_method, recipient_bank_account_domicile, render_date, transaction_document_number, transaction_narrative_description, transaction_number, transaction_fk, is_debit )";
    public static final String VENDOR_MASTER_LEDGER_QUERY = "INSERT INTO vendor_master_ledger(entity_address, entity_address1, vendor_id, entity_name, entity_status, entity_type, recipient_bank_account_domicile, tin, city, state, zip_code, bank_account_no, bank_account_location, paymentMethod, currency, is_debit)";
    public static final String CUSTOMER_MASTER_LEDGER_QUERY = "INSERT INTO customer_master_ledger (entity_address, entity_address1, entity_name, entity_status, entity_type, recipient_bank_account_domicile , is_debit)";
    public static final String EMPLOYEE_MASTER_LEDGER_QUERY = "INSERT INTO employee_master_ledger (entity_name, employee_address, city, zip, country_name, tin,  bank_domicile, bank_account_no, status, approval_limit, employee_id, is_debit )";

    public static final String REAL_TIME_TRANSACTION ="RealTimeTransaction";
    public static final String HOLIDAY ="Holiday";
    public static final String HOLIDAY_TABLE_NAME ="holiday";
    public static final String THIRD_PATRY_TRANSACTION ="Third Party Transaction";
    public static final String CUSTOMER_TRANSACTION ="Customer Transaction";
    public static final String CUSTOMER_MASTER_LEDGER_FK ="customer_master_ledger_FK";
    public static final String VENDOR_MASTER_LEDGER_FK ="vendor_master_ledger_FK";
    public static final String EMPLOYEE_MASTER_LEDGER_FK ="employee_master_ledger_FK";
    public static final String VENDOR_MASTER_LEDGER="vendor_master_ledger";
    public static final String EMPLOYEE_MASTER_LEDGER="employee_master_ledger";
    public static final String CUSTOMER_MASTER_LEDGER="customer_master_ledger";
    public static final String POLICY_PATH="policies";
    public static final String REAL_TIME_TRANSACTION_POLICY_TABLE="RealTimeTransactionPolicy";
    public static final String EMPLOYEE_MASTER_LEDGER_CLASS="employeeMasterLedger";
    public static final String VENDOR_MASTER_LEDGER_CLASS="vendorMasterLedger";
    public static final String SIGNED="Signed";
    public static final String CONFIRMED="Confirmed";
    public static final String OUTSTANDING="Outstanding";
    public static final String EMPLOYEE_MASTER_LEDGER_CL="EmployeeMasterLedger";
    public static final String VENDOR_MASTER_LEDGER_CL="VendorMasterLedger";
    public static final String TRAINING_CERTIFICATE="certificate";
    public static final String TRAINING_RETAKE="retake";
    public static final String TRAINING_QUESTION="TrainingQuestion";
    public static final String TRAINING_QUESTION_ANSWER="TrainingQuestionAnswer";
    public static final String TRAINING_START="start";
    public static final String IA_ANALYST_REVIEWED=" ia_analyst_reviewed ";// one space for sql
    public static final String IA_MANAGER_REVIEWED=" ia_manager_reviewed ";
    public static final String LEGAL_REVIEWED=" legal_reviewed ";
    public static final String ADMIN_REVIEWED=" admin_reviewed ";
    public static final String IA_MANAGER_COMPARE = " AND rtpCND.decision = 'Further Action Required' ";
    public static final String LEGAL_COMPARE = " AND rtpCND.decision = 'Further Action Required' AND rtpt.ia_manager_reviewed = 1 ";




    public static final String TRANSACTION_POLICY_PATH="transaction_policies";

//    public static final String COMPLETE="Complete";
//    public static final int WEEKEND = Calendar.WEDNESDAY;

}
