package com.dsinv.abac.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public enum TransactionType {

    CUSTOMER_TRANSACTION(1, "Customer Transaction"),
    THIRD_PARTY_TRANSACTION(2, "Third Party Transaction"),
    GENERAL_LEDGER(3, "General Ledger Transaction");

    private String value;
    private int code;

    private TransactionType(int code, String value) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public static TransactionType getTransactionType(int code) {

        if (code == 1) {
            return TransactionType.CUSTOMER_TRANSACTION;
        } else if (code == 2) {
            return TransactionType.THIRD_PARTY_TRANSACTION;
        }else if (code == 3) {
            return TransactionType.GENERAL_LEDGER;
        }
            return null;
    }


    public static List getTransactionTypes() {
        List transactionList = new ArrayList();
        transactionList.add(CUSTOMER_TRANSACTION);
        transactionList.add(THIRD_PARTY_TRANSACTION);
        transactionList.add(GENERAL_LEDGER);
        return transactionList;
    }
}
