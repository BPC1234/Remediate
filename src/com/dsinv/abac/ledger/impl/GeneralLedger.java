package com.dsinv.abac.ledger.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dsinv.abac.entity.PersonEntity;
import com.dsinv.abac.ledger.Ledger;
import com.dsinv.abac.vo.admin.CSVTransaction;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="general_ledger_information")
public class GeneralLedger extends Ledger {

	@Column(name="approver_name")
    private String approverName;
	
	@Column(name="bank_account")
    private String bankAccount;
	
	@Column(name="document_number")
    private String documentNumber;
	
	@Column(name="expense_account")
    private String expenseAccount;
	
	@Column(name="expense_type")
    private String expenseType;
	
	@Column(name="initiator_or_submitor_name")
    private String initiatorOrSubmitterName;
	
	@Column(name="inter_company_transfer_amount")
    private double interCompanyTransferAmount;
	
	@Column(name="manual_override")
    private String manualOverride;
	
	@Column(name="payee_name")
    private String payeeName;
	
	@Column(name="payment_method")
    private String paymentMethod;
	
	@Column(name="recipient_name")
    private String recipientName;
	
	@Column(name="transaction_narrative")
    private String transactionNarrative;
	
	@Column(name="transaction_narrative_description")
    private  String transactionDescription;
	
	@Column(name="transaction_number")
    private String transactionNumber;

    @Column(name = "transaction_date")
    private Date transactionDate;


    @ManyToOne
    @JoinColumn(name="customer_fk")
    private PersonEntity customer;
	
	@ManyToOne
    @JoinColumn(name="vendor_fk")
    private PersonEntity vendor;

	@ManyToOne
    @JoinColumn(name="employee_fk")
    private PersonEntity employee;

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

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getExpenseAccount() {
        return expenseAccount;
    }

    public void setExpenseAccount(String expenseAccount) {
        this.expenseAccount = expenseAccount;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getInitiatorOrSubmitterName() {
        return initiatorOrSubmitterName;
    }

    public void setInitiatorOrSubmitterName(String initiatorOrSubmitterName) {
        this.initiatorOrSubmitterName = initiatorOrSubmitterName;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getTransactionNarrative() {
        return transactionNarrative;
    }

    public void setTransactionNarrative(String transactionNarrative) {
        this.transactionNarrative = transactionNarrative;
    }

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

	public PersonEntity getCustomer() {
		return customer;
	}

	public void setCustomer(PersonEntity customer) {
		this.customer = customer;
	}

	public PersonEntity getVendor() {
		return vendor;
	}

	public void setVendor(PersonEntity vendor) {
		this.vendor = vendor;
	}

	public PersonEntity getEmployee() {
		return employee;
	}

	public void setEmployee(PersonEntity employee) {
		this.employee = employee;
	}

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }


	public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
		if(ledger instanceof GeneralLedger){
			GeneralLedger glLedger = (GeneralLedger) ledger;
			glLedger.setApproverName(csvTransaction.getApproverName());
			glLedger.setBankAccount(csvTransaction.getBankAccount());
			glLedger.setDocumentNumber(csvTransaction.getDocumentNumber());
			glLedger.setExpenseAccount(csvTransaction.getExpenseAccount());
			glLedger.setExpenseType(csvTransaction.getExpenseType());
			glLedger.setInitiatorOrSubmitterName(csvTransaction.getInitiatorSubmitterName());
			glLedger.setInterCompanyTransferAmount(csvTransaction.getInterCompanyTransferAmount());
			glLedger.setManualOverride(csvTransaction.getManualOverride());
			glLedger.setPayeeName(csvTransaction.getPayeeName());
			glLedger.setPaymentMethod(csvTransaction.getPaymentMethod());
			glLedger.setRecipientName(csvTransaction.getRecipientName());
			glLedger.setTransactionNarrative(csvTransaction.getTransactionNarrative());
			glLedger.setTransactionDescription(csvTransaction.getTransactionDescription());
			glLedger.setTransactionNumber(csvTransaction.getTransactionNumber());
			
		/*	glLedger.setCustomer(csvTransaction.getTransaction().getCustomer());
			glLedger.setEmployee(csvTransaction.getTransaction().getEmployee());
			glLedger.setVendor(csvTransaction.getTransaction().getVendor());
*/
			glLedger.setTransaction(csvTransaction.getTransaction());
			glLedger.setTransactionAmount(csvTransaction.getTransactionAmount());
			if("+".equals(isDebit))
				glLedger.setIsDebit(1);
			else
				glLedger.setIsDebit(-1);
			return glLedger;
		}
		return null;
	}
}
