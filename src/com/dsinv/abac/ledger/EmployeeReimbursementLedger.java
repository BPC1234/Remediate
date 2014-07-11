package com.dsinv.abac.ledger;

import com.dsinv.abac.entity.Transaction;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 6:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmployeeReimbursementLedger {

    private long id;
    private Transaction transaction;
    private String approverName;
    private Date documentDate;
    private String entityName;
    private String expenseTypeOrAccount;
    private String initiatorOrSubmitterName;
    private String manualOverride;
    private String recipientName;
    private double transactionAmount;
    private Date transactionDate;
    private  String transactionNarrativeOrDescription;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getExpenseTypeOrAccount() {
        return expenseTypeOrAccount;
    }

    public void setExpenseTypeOrAccount(String expenseTypeOrAccount) {
        this.expenseTypeOrAccount = expenseTypeOrAccount;
    }

    public String getInitiatorOrSubmitterName() {
        return initiatorOrSubmitterName;
    }

    public void setInitiatorOrSubmitterName(String initiatorOrSubmitterName) {
        this.initiatorOrSubmitterName = initiatorOrSubmitterName;
    }

    public String getManualOverride() {
        return manualOverride;
    }

    public void setManualOverride(String manualOverride) {
        this.manualOverride = manualOverride;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionNarrativeOrDescription() {
        return transactionNarrativeOrDescription;
    }

    public void setTransactionNarrativeOrDescription(String transactionNarrativeOrDescription) {
        this.transactionNarrativeOrDescription = transactionNarrativeOrDescription;
    }
}
