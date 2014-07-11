package com.dsinv.abac.ledger;

import com.dsinv.abac.entity.Transaction;
import com.dsinv.abac.vo.admin.CSVTransaction;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "fund_disbursement_ledger")
public class FundsDisbursementLedger extends Ledger {

    private String approverName;
    private String bankAccount;
    private String entityAddress;
    private String entityName;
    private String initiatorOrSubmitterName;
    private String manualOverride;
    private String payeeName;
    private String paymentCurrency;
    private String paymentMethod;
    private String recipientBankAccountDomicile;
    private Date transactionDate;
    private  String transactionNarrativeOrDescription;
    private String transactionNumber;
    private String contractIdentification;
    private String checkDate;
    private String checkNo;
    private String payeeId;





    @Override
    public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
        return null;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getEntityAddress() {
        return entityAddress;
    }

    public void setEntityAddress(String entityAddress) {
        this.entityAddress = entityAddress;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
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

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRecipientBankAccountDomicile() {
        return recipientBankAccountDomicile;
    }

    public void setRecipientBankAccountDomicile(String recipientBankAccountDomicile) {
        this.recipientBankAccountDomicile = recipientBankAccountDomicile;
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

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getContractIdentification() {
        return contractIdentification;
    }

    public void setContractIdentification(String contractIdentification) {
        this.contractIdentification = contractIdentification;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }
}
