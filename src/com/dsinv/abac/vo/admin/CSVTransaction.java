package com.dsinv.abac.vo.admin;

import java.util.Date;

import com.dsinv.abac.entity.Transaction;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;

public class CSVTransaction {
	private String transactionType;
	private String transactionModule;
	private String transactionCreatedBy;
	private Double accountsPayableBalance;
	private Double accountsReceivableBalance;
	private Double advanceBalance;
	private String advanceType;
	private Double amount;
	private Double approvalLimit;
	private String approverName;
	private Date approveDate;
	private String bankAccount;
	private String businessUnit;
	private String contractIdentification;
	private Double creditLimit;
	private Double discountAmount;
	private Date documentDate;
	private String documentNumber;
	private String expenseAccount;
	private String expenseType;
	private String initiatorSubmitterName;
	private Double interCompanyTransferAmount;
	private Double inventoryAmount;
	private String manualOverride;
	private Integer orderQuantity;
	private String orderStatus;
	private String payeeName;
	private String paymentCurrency;
	private String paymentMethod;
	private String paymentTerms;
	private String previousOrder;
	private Date previousOrderDate;
	private Double purchaseOrderAmount;
	private Date purchaseOrderDate;
	private String recipientBankAccountDomicile;
	private String recipientName;
	private Date renderDate;
	private Integer salesOrderNumber;
	private String productCompletionStatus;
	private String productStockCode;
	private String productType;
	private String shipToAddress;
	private Integer shippedQuantity;
	private String shippingEntity;
	private String shippingNumber;
	private Integer shippingQuantity;
	private String tin;
	private Double transactionAmount;
	private Double transactionCost;
	private Date transactionDate;
	private String transactionDocumentNumber;
	private String transactionNarrative;
	private String transactionDescription;
	private String transactionNumber;
	private Double unitOfMeasureCost;
	private Double unitOfMeasurePrice;
	private Integer unitOfMeasureQuantity;
	private String ledgerType;
	private String customerName; 
	private String customerAddress;
	private String customerAddress1;
	private String customerCity;
	private String customerState;
	private String customerZipCode;
	private String customerTaxEinNo;
	private String customerBankAccountNo;
	private String customerBankAccountLocation;
	private String customerType;
	private String customerCountry;
	private String vendorName;
	private String vendorAddress;
	private String vendorAddress1;
	private String vendorCity;
	private String vendorState;
	private String vendorZipCode;
	private String vendorTaxEinNo;
	private String vendorBankAccountNo;
	private String vendorBankAccountLocation;
	private String vendorType;
	private String vendorCountry;
	private String employeeName;
	private String employeeAddress;
	private String employeeAddress1;
	private String employeeCity;
	private String employeeState;
	private String employeeZipCode;
	private String employeeTaxEinNo;
	private String employeeBankAccountNo;
	private String employeeBankAccountLocation;
	private String employeeType;
	private String employeeCountry;
    private long entityId;
    private String entityName;
    private String entityStatus;
    private String entityType;
    private String entityAddress;
    private String transactionApprover;

	
	// For internal use.
	private Transaction tx;
	
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
	public String getTransactionCreatedBy() {
		return transactionCreatedBy;
	}
	public void setTransactionCreatedBy(String transactionCreatedBy) {
		this.transactionCreatedBy = transactionCreatedBy;
	}
	public Double getAccountsPayableBalance() {
		return accountsPayableBalance;
	}
	public void setAccountsPayableBalance(Double accountsPayableBalance) {
		this.accountsPayableBalance = accountsPayableBalance;
	}
	public Double getAccountsReceivableBalance() {
		return accountsReceivableBalance;
	}
	public void setAccountsReceivableBalance(Double accountsReceivableBalance) {
		this.accountsReceivableBalance = accountsReceivableBalance;
	}
	public Double getAdvanceBalance() {
		return advanceBalance;
	}
	public void setAdvanceBalance(Double advanceBalance) {
		this.advanceBalance = advanceBalance;
	}
	public String getAdvanceType() {
		return advanceType;
	}
	public void setAdvanceType(String advanceType) {
		this.advanceType = advanceType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getApprovalLimit() {
		return approvalLimit;
	}
	public void setApprovalLimit(Double approvalLimit) {
		this.approvalLimit = approvalLimit;
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
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getContractIdentification() {
		return contractIdentification;
	}
	public void setContractIdentification(String contractIdentification) {
		this.contractIdentification = contractIdentification;
	}
	public Double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Date getDocumentDate() {
		return documentDate;
	}

	public String getDocumentDateText() {
		return Utils.getStringFromDate(Constants.MONTH_DAY_YEAR, this.documentDate);
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
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
	public String getInitiatorSubmitterName() {
		return initiatorSubmitterName;
	}
	public void setInitiatorSubmitterName(String initiatorSubmitterName) {
		this.initiatorSubmitterName = initiatorSubmitterName;
	}
	public Double getInterCompanyTransferAmount() {
		return interCompanyTransferAmount;
	}
	public void setInterCompanyTransferAmount(Double interCompanyTransferAmount) {
		this.interCompanyTransferAmount = interCompanyTransferAmount;
	}
	public Double getInventoryAmount() {
		return inventoryAmount;
	}
	public void setInventoryAmount(Double inventoryAmount) {
		this.inventoryAmount = inventoryAmount;
	}
	public String getManualOverride() {
		return manualOverride;
	}
	public void setManualOverride(String manualOverride) {
		this.manualOverride = manualOverride;
	}
	public Integer getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(Integer orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getPreviousOrder() {
		return previousOrder;
	}
	public void setPreviousOrder(String previousOrder) {
		this.previousOrder = previousOrder;
	}
	public Date getPreviousOrderDate() {
		return previousOrderDate;
	}

	public String getPreviousOrderDateText() {
		 return Utils.getStringFromDate(Constants.MONTH_DAY_YEAR, this.previousOrderDate);
	}

	public void setPreviousOrderDate(Date previousOrderDate) {
		this.previousOrderDate = previousOrderDate;
	}
	public Double getPurchaseOrderAmount() {
		return purchaseOrderAmount;
	}
	public void setPurchaseOrderAmount(Double purchaseOrderAmount) {
		this.purchaseOrderAmount = purchaseOrderAmount;
	}
	public Date getPurchaseOrderDate() {
		return purchaseOrderDate;
	}
	
	public String getPurchaseOrderDateText() {
		return Utils.getStringFromDate(Constants.MONTH_DAY_YEAR, this.purchaseOrderDate);
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
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public Date getRenderDate() {
		return renderDate;
	}

	public String getRenderDateText() {
		return Utils.getStringFromDate(Constants.MONTH_DAY_YEAR, this.renderDate);
	}

	public void setRenderDate(Date renderDate) {
		this.renderDate = renderDate;
	}
	public Integer getSalesOrderNumber() {
		return salesOrderNumber;
	}
	public void setSalesOrderNumber(Integer salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}
	public String getProductCompletionStatus() {
		return productCompletionStatus;
	}
	public void setProductCompletionStatus(String productCompletionStatus) {
		this.productCompletionStatus = productCompletionStatus;
	}
	public String getProductStockCode() {
		return productStockCode;
	}
	public void setProductStockCode(String productStockCode) {
		this.productStockCode = productStockCode;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public Integer getShippedQuantity() {
		return shippedQuantity;
	}
	public void setShippedQuantity(Integer shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}
	public String getShippingEntity() {
		return shippingEntity;
	}
	public void setShippingEntity(String shippingEntity) {
		this.shippingEntity = shippingEntity;
	}
	public String getShippingNumber() {
		return shippingNumber;
	}
	public void setShippingNumber(String shippingNumber) {
		this.shippingNumber = shippingNumber;
	}
	public Integer getShippingQuantity() {
		return shippingQuantity;
	}
	public void setShippingQuantity(Integer shippingQuantity) {
		this.shippingQuantity = shippingQuantity;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Double getTransactionCost() {
		return transactionCost;
	}
	public void setTransactionCost(Double transactionCost) {
		this.transactionCost = transactionCost;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public String getTransactionDateText() {
		 return Utils.getStringFromDate(Constants.MONTH_DAY_YEAR, this.transactionDate);
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionDocumentNumber() {
		return transactionDocumentNumber;
	}
	public void setTransactionDocumentNumber(String transactionDocumentNumber) {
		this.transactionDocumentNumber = transactionDocumentNumber;
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
	public Double getUnitOfMeasureCost() {
		return unitOfMeasureCost;
	}
	public void setUnitOfMeasureCost(Double unitOfMeasureCost) {
		this.unitOfMeasureCost = unitOfMeasureCost;
	}
	public Double getUnitOfMeasurePrice() {
		return unitOfMeasurePrice;
	}
	public void setUnitOfMeasurePrice(Double unitOfMeasurePrice) {
		this.unitOfMeasurePrice = unitOfMeasurePrice;
	}
	public Integer getUnitOfMeasureQuantity() {
		return unitOfMeasureQuantity;
	}
	public void setUnitOfMeasureQuantity(Integer unitOfMeasureQuantity) {
		this.unitOfMeasureQuantity = unitOfMeasureQuantity;
	}
	public String getLedgerType() {
		return ledgerType;
	}
	public void setLedgerType(String ladgerType) {
		this.ledgerType = ladgerType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getCustomerState() {
		return customerState;
	}
	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}
	public String getCustomerZipCode() {
		return customerZipCode;
	}
	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}
	public String getCustomerTaxEinNo() {
		return customerTaxEinNo;
	}
	public void setCustomerTaxEinNo(String customerTaxEinNo) {
		this.customerTaxEinNo = customerTaxEinNo;
	}
	public String getCustomerBankAccountNo() {
		return customerBankAccountNo;
	}
	public void setCustomerBankAccountNo(String customerBankAccountNo) {
		this.customerBankAccountNo = customerBankAccountNo;
	}
	public String getCustomerBankAccountLocation() {
		return customerBankAccountLocation;
	}
	public void setCustomerBankAccountLocation(String customerBankAccountLocation) {
		this.customerBankAccountLocation = customerBankAccountLocation;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerCountry() {
		return customerCountry;
	}
	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorAddress() {
		return vendorAddress;
	}
	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}
	public String getVendorCity() {
		return vendorCity;
	}
	public void setVendorCity(String vendorCity) {
		this.vendorCity = vendorCity;
	}
	public String getVendorState() {
		return vendorState;
	}
	public void setVendorState(String vendorState) {
		this.vendorState = vendorState;
	}
	public String getVendorZipCode() {
		return vendorZipCode;
	}
	public void setVendorZipCode(String vendorZipCode) {
		this.vendorZipCode = vendorZipCode;
	}
	public String getVendorTaxEinNo() {
		return vendorTaxEinNo;
	}
	public void setVendorTaxEinNo(String vendorTaxEinNo) {
		this.vendorTaxEinNo = vendorTaxEinNo;
	}
	public String getVendorBankAccountNo() {
		return vendorBankAccountNo;
	}
	public void setVendorBankAccountNo(String vendorBankAccountNo) {
		this.vendorBankAccountNo = vendorBankAccountNo;
	}
	public String getVendorBankAccountLocation() {
		return vendorBankAccountLocation;
	}
	public void setVendorBankAccountLocation(String vendorBankAccountLocation) {
		this.vendorBankAccountLocation = vendorBankAccountLocation;
	}
	public String getVendorType() {
		return vendorType;
	}
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}
	public String getVendorCountry() {
		return vendorCountry;
	}
	public void setVendorCountry(String vendorCountry) {
		this.vendorCountry = vendorCountry;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeAddress() {
		return employeeAddress;
	}
	public void setEmployeeAddress(String employeeAddress) {
		this.employeeAddress = employeeAddress;
	}
	public String getEmployeeCity() {
		return employeeCity;
	}
	public void setEmployeeCity(String employeeCity) {
		this.employeeCity = employeeCity;
	}
	public String getEmployeeState() {
		return employeeState;
	}
	public void setEmployeeState(String employeeState) {
		this.employeeState = employeeState;
	}
	public String getEmployeeZipCode() {
		return employeeZipCode;
	}
	public void setEmployeeZipCode(String employeeZipCode) {
		this.employeeZipCode = employeeZipCode;
	}
	public String getEmployeeTaxEinNo() {
		return employeeTaxEinNo;
	}
	public void setEmployeeTaxEinNo(String employeeTaxEinNo) {
		this.employeeTaxEinNo = employeeTaxEinNo;
	}
	public String getEmployeeBankAccountNo() {
		return employeeBankAccountNo;
	}
	public void setEmployeeBankAccountNo(String employeeBankAccountNo) {
		this.employeeBankAccountNo = employeeBankAccountNo;
	}
	public String getEmployeeBankAccountLocation() {
		return employeeBankAccountLocation;
	}
	public void setEmployeeBankAccountLocation(String employeeBankAccountLocation) {
		this.employeeBankAccountLocation = employeeBankAccountLocation;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getEmployeeCountry() {
		return employeeCountry;
	}
	public void setEmployeeCountry(String employeeCountry) {
		this.employeeCountry = employeeCountry;
	}

	public Transaction getTransaction() {
		return tx;
	}
	public void setTransaction(Transaction aTx) {
		this.tx=aTx;
	}

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Transaction getTx() {
        return tx;
    }

    public void setTx(Transaction tx) {
        this.tx = tx;
    }

    public String getEntityAddress() {
        return entityAddress;
    }

    public void setEntityAddress(String entityAddress) {
        this.entityAddress = entityAddress;
    }

    public String getTransactionApprover() {
        return transactionApprover;
    }

    public void setTransactionApprover(String transactionApprover) {
        this.transactionApprover = transactionApprover;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getCustomerAddress1() {
        return customerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }

    public String getVendorAddress1() {
        return vendorAddress1;
    }

    public void setVendorAddress1(String vendorAddress1) {
        this.vendorAddress1 = vendorAddress1;
    }

    public String getEmployeeAddress1() {
        return employeeAddress1;
    }

    public void setEmployeeAddress1(String employeeAddress1) {
        this.employeeAddress1 = employeeAddress1;
    }
}
