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
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="accounts_receivable_ledger_information")
public class AccountsReceivableLedger extends Ledger{

	@Column(name="approver_name")
	private String approverName;
	
	@Column(name="bank_account")
	private String bankAccount;
	
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
	
	@Column(name="payment_currency")
    private String paymentCurrency;
	
	@Column(name="payment_method")
    private String paymentMethod;
	
	@Column(name="recipient_bank_account_domicile")
    private String recipientBankAccountDomicile;
	
	@Column(name="render_date")
    private Date renderDate;
	
	@Column(name="transaction_document_number")
	private String transactionDocumentNumber;
	
	@Column(name="transaction_narrative_description")
    private  String transactionDescription;
	
	@Column(name="transaction_number")
    private String transactionNumber;

    @Column(name = "transaction_date")
    private Date transactionDate;
/*


    @ManyToOne
    @JoinColumn(name="customer_fk")
    private PersonEntity customer;
	
	@ManyToOne
    @JoinColumn(name="vendor_fk")
    private PersonEntity vendor;

	@ManyToOne
    @JoinColumn(name="employee_fk")
    private PersonEntity employee;
*/


    @Column(name="unit_of_measure_quantity")
    private Integer unitOfMeasureQuantity;

    @Column(name="unit_of_measure_cost")
    private Double unitOfMeasureCost;


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

    public Date getRenderDate() {
        return renderDate;
    }

    public void setRenderDate(Date renderDate) {
        this.renderDate = renderDate;
    }

    public String getTransactionDocumentNumber() {
        return transactionDocumentNumber;
    }

    public void setTransactionDocumentNumber(String transactionDocumentNumber) {
        this.transactionDocumentNumber = transactionDocumentNumber;
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

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/*public PersonEntity getCustomer() {
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
*/
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getUnitOfMeasureQuantity() {
        return unitOfMeasureQuantity;
    }

    public void setUnitOfMeasureQuantity(Integer unitOfMeasureQuantity) {
        this.unitOfMeasureQuantity = unitOfMeasureQuantity;
    }

    public Double getUnitOfMeasureCost() {
        return unitOfMeasureCost;
    }

    public void setUnitOfMeasureCost(Double unitOfMeasureCost) {
        this.unitOfMeasureCost = unitOfMeasureCost;
    }

    public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
		if(ledger instanceof AccountsReceivableLedger){
			AccountsReceivableLedger arLedger = (AccountsReceivableLedger) ledger;
			arLedger.setApproverName(csvTransaction.getApproverName());
			arLedger.setBankAccount(csvTransaction.getBankAccount());
			arLedger.setDocumentDate(csvTransaction.getDocumentDate());
			arLedger.setDocumentNumber(csvTransaction.getDocumentNumber());
			arLedger.setInitiatorOrSubmitorName(csvTransaction.getInitiatorSubmitterName());
			arLedger.setInterCompanyTransferAmount(csvTransaction.getInterCompanyTransferAmount());
			arLedger.setManualOverride(csvTransaction.getManualOverride());
			arLedger.setPaymentCurrency(csvTransaction.getPaymentCurrency());
			arLedger.setPaymentMethod(csvTransaction.getPaymentMethod());
			arLedger.setRecipientBankAccountDomicile(csvTransaction.getRecipientBankAccountDomicile());
			arLedger.setRenderDate(csvTransaction.getRenderDate());
			arLedger.setTransactionDocumentNumber(csvTransaction.getTransactionDocumentNumber());
			arLedger.setTransactionDescription(csvTransaction.getTransactionDescription());
			arLedger.setTransactionNumber(csvTransaction.getTransactionNumber());			
			/*arLedger.setCustomer(csvTransaction.getTransaction().getCustomer());
			arLedger.setEmployee(csvTransaction.getTransaction().getEmployee());
			arLedger.setVendor(csvTransaction.getTransaction().getVendor());
		*/
			arLedger.setTransaction(csvTransaction.getTransaction());
			arLedger.setTransactionAmount(csvTransaction.getTransactionAmount());
			if("+".equals(isDebit))
				arLedger.setIsDebit(1);
			else
				arLedger.setIsDebit(-1);
			return arLedger;
		}
		return null;
	}

    public String insertsql(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
        StringBuffer insert = new StringBuffer();
        insert.append("('");
        insert.append(csvTransaction.getApproverName());
        insert.append("','");
        insert.append(csvTransaction.getBankAccount());
        insert.append("','");
        insert.append(Utils.formattingDateInDb(csvTransaction.getDocumentDate()));
        insert.append("','");
        insert.append(csvTransaction.getDocumentNumber());
        insert.append("','");
        insert.append(csvTransaction.getInitiatorSubmitterName());
        insert.append("','");
        insert.append(csvTransaction.getInterCompanyTransferAmount());
        insert.append("','");
        insert.append(csvTransaction.getManualOverride());
        insert.append("','");
        insert.append(csvTransaction.getPaymentCurrency());
        insert.append("','");
        insert.append(csvTransaction.getPaymentMethod());
        insert.append("','");
        insert.append(csvTransaction.getRecipientBankAccountDomicile());
        insert.append("','");
        insert.append(Utils.formattingDateInDb(csvTransaction.getRenderDate()));
        insert.append("','");
        insert.append(csvTransaction.getTransactionDocumentNumber());
        insert.append("','");
        insert.append(csvTransaction.getTransactionDescription());
        insert.append("','");
        insert.append(csvTransaction.getTransactionNumber());
        insert.append("',");
        insert.append(csvTransaction.getTransaction().getId());
       /* insert.append(",");
        insert.append(csvTransaction.getTransaction().getCustomer());
        insert.append(",");
        insert.append(csvTransaction.getTransaction().getVendor());
        insert.append(",");
        insert.append(csvTransaction.getTransaction().getEmployee());
       */ insert.append(",");
        if("+".equals(isDebit))
            insert.append(1);
        else
            insert.append(-1);

        insert.append(")");
        return insert.toString();
    }
}
