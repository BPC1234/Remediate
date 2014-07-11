package com.dsinv.abac.ledger;

import com.dsinv.abac.entity.Transaction;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryLedger {



    private long id;
    private Transaction transaction;
    private double inventoryAmount;

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

    public double getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(double inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }
}
