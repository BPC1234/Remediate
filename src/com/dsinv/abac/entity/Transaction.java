package com.dsinv.abac.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.dsinv.abac.ledger.CustomerMasterLedger;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    
    @Column(name = "transaction_type")
    private String transactionType;
    
    @Column(name = "transaction_module")
    private String transactionModule;
    
    @Column(name = "created")
    private Date created;
    
    @Column(name = "transaction_date")
    private Date transactionDate;    
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "real_time_touched")
    private boolean realTimeTouched;
    
    @Column(name = "amount")
    private double amount;


    @Column(name = "approver")
    private String approver;

    @Column(name = "approve_date")
    private Date approveDate;

    @ManyToOne
    @JoinColumn(name = "customer_master_ledger_FK")
    private CustomerMasterLedger customerMasterLedger;

    @ManyToOne
    @JoinColumn(name = "vendor_master_ledger_FK")
    private VendorMasterLedger vendorMasterLedger;

    @ManyToOne
    @JoinColumn(name = "employee_master_ledger_FK")
    private EmployeeMasterLedger employeeMasterLedger;

    @Column(name = "job_Id")
    private int jobId;

    @Transient
    private List<TransactionLi> transactionLiList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionModule() {
        return transactionModule;
    }

    public void setTransactionModule(String transactionModule) {
        this.transactionModule = transactionModule;
    }

    public Date getCreated() {
        return created;
    }
    
    public String getCreatedText() {
        return Utils.getStringFromDate(Constants.MONTH_DAY_YEAR, this.created);
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isRealTimeTouched() {
        return realTimeTouched;
    }

    public void setRealTimeTouched(boolean realTimeTouched) {
        this.realTimeTouched = realTimeTouched;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CustomerMasterLedger getCustomerMasterLedger() {
        return customerMasterLedger;
    }

    public void setCustomerMasterLedger(CustomerMasterLedger customerMasterLedger) {
        this.customerMasterLedger = customerMasterLedger;
    }

    public VendorMasterLedger getVendorMasterLedger() {
        return vendorMasterLedger;
    }

    public void setVendorMasterLedger(VendorMasterLedger vendorMasterLedger) {
        this.vendorMasterLedger = vendorMasterLedger;
    }

    public EmployeeMasterLedger getEmployeeMasterLedger() {
        return employeeMasterLedger;
    }

    public void setEmployeeMasterLedger(EmployeeMasterLedger employeeMasterLedger) {
        this.employeeMasterLedger = employeeMasterLedger;
    }

    public List<TransactionLi> getTransactionLiList() {
		return transactionLiList;
	}

	public void setTransactionLiList(List<TransactionLi> transactionLiList) {
		this.transactionLiList = transactionLiList;
	}

	@Override
	public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionType='" + transactionType + '\'' +
                ", transactionModule='" + transactionModule + '\'' +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                ", realTimeTouched=" + realTimeTouched +
                '}';
    }

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }
}
