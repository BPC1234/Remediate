package com.dsinv.abac.ledger;

import com.dsinv.abac.entity.Transaction;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 7:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderEntityLedger {

    private long id;
    private Transaction transaction;
    private double amount;
    private String approverName;
    private String businessUnit;
    private double discountAmount;
    private Date discountDate;
    private long discountNumber;
    private String entityAddress;
    private String entityName;
    private String entityType;
    private String initiatorOrSubmitterName;
    private String manualOverride;
    private double orderQuantity;
    private String orderStatus;
    private String paymentTerms;
    private String previousOrder;
    private Date previousOrderDate;
    private Date renderDate;
    private long salesOrderNumber;
    private String serviceCode ;  // product code;
    private String serviceType;  // product type;
    private String shipToAddress;
    private  long  shippedQuantity;
    private String shippingEntity;
    private String shippingNumber;
    private double transactionAmount;
    private double transactionCost;
    private Date transactionDate;
    private String transactionDocumentNumber;
    private  String transactionNarrativeOrDescription;
    private String transactionNumber;
    private double  unitOfMeasurePrice;
    private double unitOfMeasureQuantity;


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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Date getDiscountDate() {
        return discountDate;
    }

    public void setDiscountDate(Date discountDate) {
        this.discountDate = discountDate;
    }

    public long getDiscountNumber() {
        return discountNumber;
    }

    public void setDiscountNumber(long discountNumber) {
        this.discountNumber = discountNumber;
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

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
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

    public double getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(double orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public void setPreviousOrderDate(Date previousOrderDate) {
        this.previousOrderDate = previousOrderDate;
    }

    public Date getRenderDate() {
        return renderDate;
    }

    public void setRenderDate(Date renderDate) {
        this.renderDate = renderDate;
    }

    public long getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(long salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public long getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(long shippedQuantity) {
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

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(double transactionCost) {
        this.transactionCost = transactionCost;
    }

    public Date getTransactionDate() {
        return transactionDate;
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

    public double getUnitOfMeasurePrice() {
        return unitOfMeasurePrice;
    }

    public void setUnitOfMeasurePrice(double unitOfMeasurePrice) {
        this.unitOfMeasurePrice = unitOfMeasurePrice;
    }

    public double getUnitOfMeasureQuantity() {
        return unitOfMeasureQuantity;
    }

    public void setUnitOfMeasureQuantity(double unitOfMeasureQuantity) {
        this.unitOfMeasureQuantity = unitOfMeasureQuantity;
    }
}
