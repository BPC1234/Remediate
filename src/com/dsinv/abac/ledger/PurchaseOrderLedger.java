package com.dsinv.abac.ledger;

import com.dsinv.abac.entity.Transaction;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class PurchaseOrderLedger {

    private long id;
    private Transaction transaction;
    private double purchaseOrderAmount;
    private Date purchaseOrderdate;
    private double transactionAmount;


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

    public double getPurchaseOrderAmount() {
        return purchaseOrderAmount;
    }

    public void setPurchaseOrderAmount(double purchaseOrderAmount) {
        this.purchaseOrderAmount = purchaseOrderAmount;
    }

    public Date getPurchaseOrderdate() {
        return purchaseOrderdate;
    }

    public void setPurchaseOrderdate(Date purchaseOrderdate) {
        this.purchaseOrderdate = purchaseOrderdate;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
