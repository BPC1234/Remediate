package com.dsinv.abac.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */

/*@Entity
@Table(name = "transaction_li_label")*/
public class TransactionLiLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_module")
    private String transactionModule;

    @Column(name = "headName")
    private String headName;

    @Column(name = "serial")
    private long serial;

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

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public long getSerial() {
        return serial;
    }

    public void setSerial(long serial) {
        this.serial = serial;
    }


    public String toString() {
        return "TransactionLiLabel{" +
                "id=" + id +
                ", transactionType='" + transactionType + '\'' +
                ", transactionModule='" + transactionModule + '\'' +
                ", headName='" + headName + '\'' +
                ", serial=" + serial +
                '}';
    }
}
