package com.dsinv.abac.ledger.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dsinv.abac.entity.PersonEntity;
import com.dsinv.abac.ledger.Ledger;
import com.dsinv.abac.util.Utils;
import com.dsinv.abac.vo.admin.CSVTransaction;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="accounts_payable_ledger_information")
public class AccountsPayableLedger extends Ledger {

	@Column(name="accounts_payable_balance")
	private double accountsPayableBalance;
	
	@Column(name="approver_name")
    private String approverName;
	
	@Column(name="bank_account")
    private String bankAccount;
	
	@Column(name="contact_identification")
    private String contactIdentification;
	
	@Column(name="document_date")
    private Date documentDate;

    @Column(name="document_number")
    private String documentNumber;

    @Column(name="initiator_or_submitor_name")
    private String initiatorOrSubmitorName;
	
	@Column(name="inter_company_transfer_amount")
    private double interCompanyTransferAmount;
	
	@Column(name="manual_override")
    private String manualOverride;
	
	@Column(name="payee_name")
    private String payeeName;
	
	@Column(name="payment_currency")
    private String paymentCurrency;
	
	@Column(name="payment_method")
    private String paymentMethod;
	
	@Column(name="purchase_order_amount")
    private double purchaseOrderAmount;
	
	@Column(name="purchase_order_date")
    private Date purchaseOrderDate;
	
	@Column(name="recipient_bank_account_domicile")
    private String recipientBankAccountDomicile;
	
	@Column(name="tin")
    private String tin;
	
/*	@Column(name="transaction_narrative")
    private String transactionNarrative;
	*/
	@Column(name="transaction_narrative_description")
    private  String transactionDescription;
	
	@Column(name="transaction_number")
    private String transactionNumber;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name="unit_of_measure_quantity")
    private Integer unitOfMeasureQuantity;
	
	@Column(name="unit_of_measure_cost")
    private String unitOfMeasureCost;


    public double getAccountsPayableBalance() {
        return accountsPayableBalance;
    }

    public void setAccountsPayableBalance(double accountsPayableBalance) {
        this.accountsPayableBalance = accountsPayableBalance;
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

    public String getContactIdentification() {
        return contactIdentification;
    }

    public void setContactIdentification(String contactIdentification) {
        this.contactIdentification = contactIdentification;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public String getInitiatorOrSubmitorName() {
        return initiatorOrSubmitorName;
    }

    public void setInitiatorOrSubmitorName(String initiatorOrSubmitorName) {
        this.initiatorOrSubmitorName = initiatorOrSubmitorName;
    }

    public double getInterCompanyTransferAmount() {
        return interCompanyTransferAmount;
    }

    public void setInterCompanyTransferAmount(double interCompanyTransferAmount) {
        this.interCompanyTransferAmount = interCompanyTransferAmount;
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

    public double getPurchaseOrderAmount() {
        return purchaseOrderAmount;
    }

    public void setPurchaseOrderAmount(double purchaseOrderAmount) {
        this.purchaseOrderAmount = purchaseOrderAmount;
    }

    public Date getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(Date purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public String getRecipientBankAccountDomicile() {
        return recipientBankAccountDomicile;
    }

    public void setRecipientBankAccountDomicile(String recipientBankAccountDomicile) {
        this.recipientBankAccountDomicile = recipientBankAccountDomicile;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

/*    public String getTransactionNarrative() {
        return transactionNarrative;
    }

    public void setTransactionNarrative(String transactionNarrative) {
        this.transactionNarrative = transactionNarrative;
    }*/

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Integer getUnitOfMeasureQuantity() {
        return unitOfMeasureQuantity;
    }

    public void setUnitOfMeasureQuantity(Integer unitOfMeasureQuantity) {
        this.unitOfMeasureQuantity = unitOfMeasureQuantity;
    }

    public String getUnitOfMeasureCost() {
        return unitOfMeasureCost;
    }

    public void setUnitOfMeasureCost(String unitOfMeasureCost) {
        this.unitOfMeasureCost = unitOfMeasureCost;
    }



    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    @Override
    public String toString() {
        return "AccountsPayableLedger{" +
                "accountsPayableBalance=" + accountsPayableBalance +
                ", approverName='" + approverName + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", contactIdentification='" + contactIdentification + '\'' +
                ", documentDate=" + documentDate +
                ", initiatorOrSubmitorName='" + initiatorOrSubmitorName + '\'' +
                ", interCompanyTransferAmount=" + interCompanyTransferAmount +
                ", manualOverride='" + manualOverride + '\'' +
                ", payeeName='" + payeeName + '\'' +
                ", paymentCurrency='" + paymentCurrency + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", purchaseOrderAmount=" + purchaseOrderAmount +
                ", purchaseOrderDate=" + purchaseOrderDate +
                ", recipientBankAccountDomicile='" + recipientBankAccountDomicile + '\'' +
                ", tin='" + tin + '\'' +
//                ", transactionNarrative='" + transactionNarrative + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", transactionNumber='" + transactionNumber + '\'' +
                ", transactionDate=" + transactionDate +
                ", unitOfMeasureQuantity=" + unitOfMeasureQuantity +
                ", unitOfMeasureCost=" + unitOfMeasureCost +
               /* ", customer=" + customer +
                ", vendor=" + vendor +
                ", employee=" + employee +
               */ '}';
    }

    public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
		if(ledger instanceof AccountsPayableLedger){
			AccountsPayableLedger apLedger = (AccountsPayableLedger)ledger;
			apLedger.setAccountsPayableBalance(csvTransaction.getAccountsPayableBalance());
			apLedger.setApproverName(csvTransaction.getApproverName());
			apLedger.setBankAccount(csvTransaction.getBankAccount());
			apLedger.setContactIdentification(csvTransaction.getContractIdentification());
			apLedger.setDocumentDate(csvTransaction.getDocumentDate());
			apLedger.setInitiatorOrSubmitorName(csvTransaction.getInitiatorSubmitterName());
			apLedger.setInterCompanyTransferAmount(csvTransaction.getInterCompanyTransferAmount());
			apLedger.setManualOverride(csvTransaction.getManualOverride());
			apLedger.setPayeeName(csvTransaction.getPayeeName());
			apLedger.setPaymentCurrency(csvTransaction.getPaymentCurrency());
			apLedger.setPaymentMethod(csvTransaction.getPaymentMethod());
			apLedger.setPurchaseOrderAmount(csvTransaction.getPurchaseOrderAmount());
			apLedger.setPurchaseOrderDate(csvTransaction.getPurchaseOrderDate());
			apLedger.setRecipientBankAccountDomicile(csvTransaction.getRecipientBankAccountDomicile());
			apLedger.setTin(csvTransaction.getTin());
//			apLedger.setTransactionNarrative(csvTransaction.getTransactionNarrative());
			apLedger.setTransactionDescription(csvTransaction.getTransactionDescription());
			apLedger.setTransactionNumber(csvTransaction.getTransactionNumber());
			apLedger.setUnitOfMeasureCost("");
			apLedger.setUnitOfMeasureQuantity(csvTransaction.getUnitOfMeasureQuantity());
			/*apLedger.setCustomer(csvTransaction.getTransaction().getCustomer());
			apLedger.setEmployee(csvTransaction.getTransaction().getEmployee());
			apLedger.setVendor(csvTransaction.getTransaction().getVendor());
			
	*/		apLedger.setTransaction(csvTransaction.getTransaction());
			apLedger.setTransactionAmount(csvTransaction.getTransactionAmount());
			if("+".equals(isDebit))
				apLedger.setIsDebit(1);
			else
				apLedger.setIsDebit(-1);
			return apLedger;		
		}
		return null;
	}
    public String insertsql(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
        StringBuffer ledgerSB = new StringBuffer();
        ledgerSB.append("(");
        ledgerSB.append(csvTransaction.getAccountsPayableBalance());
        ledgerSB.append(",'");
        ledgerSB.append(csvTransaction.getApproverName());
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getBankAccount());
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getContractIdentification());
        ledgerSB.append("','");
        ledgerSB.append(Utils.formattingDateInDb(csvTransaction.getDocumentDate()));
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getDocumentNumber());
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getInitiatorSubmitterName());
        ledgerSB.append("',");
        ledgerSB.append(csvTransaction.getInterCompanyTransferAmount());
        ledgerSB.append(",'");
        ledgerSB.append(csvTransaction.getManualOverride());
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getPayeeName());
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getPaymentCurrency());
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getPaymentMethod());
        ledgerSB.append("',");
        ledgerSB.append(csvTransaction.getPurchaseOrderAmount());
        ledgerSB.append(",'");
        ledgerSB.append(Utils.formattingDateInDb(csvTransaction.getPurchaseOrderDate()));
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getRecipientBankAccountDomicile());
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getTin());
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getTransactionDescription());
        ledgerSB.append("','");
        ledgerSB.append(csvTransaction.getTransactionNumber());
        ledgerSB.append("','");
        ledgerSB.append(Utils.formattingDateInDb(csvTransaction.getTransactionDate()));
        ledgerSB.append("',");
        ledgerSB.append(csvTransaction.getUnitOfMeasureQuantity());
        ledgerSB.append(",");
        ledgerSB.append(csvTransaction.getUnitOfMeasureCost());
        ledgerSB.append(",");
        ledgerSB.append(csvTransaction.getTransaction().getId());
        ledgerSB.append(",");
        ledgerSB.append(csvTransaction.getTransactionAmount());
        ledgerSB.append(",");
        if("+".equals(isDebit))
            ledgerSB.append(1);
        else
            ledgerSB.append(-1);
        ledgerSB.append(")");

        return ledgerSB.toString();
    }

}
