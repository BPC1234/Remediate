package com.dsinv.abac.ledger;

import com.dsinv.abac.entity.Transaction;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class DelegationOfAuthorityLedger {

    private long id;
    private Transaction transaction;
    private double  approvalLimit;

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

    public double getApprovalLimit() {
        return approvalLimit;
    }

    public void setApprovalLimit(double approvalLimit) {
        this.approvalLimit = approvalLimit;
    }
}
