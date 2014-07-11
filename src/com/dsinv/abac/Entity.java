package com.dsinv.abac;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/27/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Entity {
    REACTIVE_PROJECT("ReactiveProject"),
    REGION("Region"),
    PROACTIVE_TRANSACTION("ProactiveTransaction"),
    PROACTIVE_BLOCK_WEIGHT("ProactiveBlockWeight"),
    PROACTIVE_PROJECT("ProactiveProject"),
    PROACTIVE_TRANSACTION_AUDIT_TRIAL("ProactiveTransactionAuditTrial"),
    PROACTIVE_TRANSACTION_COMMENT("ProactiveTransactionComment"),
    PROACTIVE_TRANSACTION_CND("ProactiveTransactionCND"),
    REACTIVE_TRANSACTION("ReactiveTransaction"),
    REACTIVE_TRANSACTION_COMMENT("ReactiveTransactionComment"),
    REACTIVE_TRANSACTION_CND("ReactiveTransactionCND"),
    REACTIVE_TRANSACTION_AUDIT_TRIAL("ReactiveTransactionAuditTrial"),
    TRANSACTION("Transaction"),
    USER("User"),
    PROACTIVE_BLOCK_WEIGHT_RATIO("ProactiveBlockWeightRatio"),
    REAL_TIME_PROJECT("RealTimeProject"),
    REAL_TIME_TRANSACTION("RealTimeTransaction"),
    REAL_TIME_TRANSACTION_CND("RealTimeTransactionCND"),
    REAL_TIME_TRANSACTION_AUDIT_TRIAL("RealTimeTransactionAuditTrial"),
    REAL_TIME_TRANSACTION_SUPPORTING_DOCUMENT("RealTimeTransactionSupportingDocument"),
    CONTROL("Control"),
    POLICY("Policy"),
    POLICY_AND_PROCEDURE("PolicyAndProcedure"),
    TRAINING("Training"),
    PROACTIVE_TRANSACTION_SUPPORT_DOCUMENT("ProactiveTransactionSupportingDocument"),
    REACTIVE_TRANSACTION_SUPPORT_DOCUMENT("ReactiveTransactionSupportingDocument"),
    REALTIME_TRANSACTION_SUPPORT_DOCUMENT("RealTimeTransactionSupportingDocument"),
    WEEKEND("Weekend"),
    CUSTOMER_MASTER_LEDGER("CustomerMasterLedger"),
    VENDOR_MASTER_LEDGER("VendorMasterLedger"),
    EMPLOYEE_MASTER_LEDGER("EmployeeMasterLedger"),
    TRANSACTION_POLICY("TransactionPolicy");

    private String value;

    private Entity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
