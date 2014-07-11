package com.dsinv.abac.entity;

import javax.persistence.*;


/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "transaction_li")
public class TransactionLi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name="transaction_id")
    private Transaction transaction;

    @Column(name = "headName")
    private String headName;

    @Column(name = "head_id")
    private int headerId;

/*
    @JoinColumn(name="transaction_li_label_id")
    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private TransactionLiLabel transactionLiLabel;
*/

    @Column(name = "value")
    private String value;

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
/*
    public TransactionLiLabel getTransactionLiLabel() {
        return transactionLiLabel;
    }

    public void setTransactionLiLabel(TransactionLiLabel transactionLiLabel) {
        this.transactionLiLabel = transactionLiLabel;
    }

 */   public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getHeaderId() {
        return headerId;
    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String toString() {
        return "TransactionLi{" +
                "id=" + id +
                ", transaction=" + transaction +
//                ", transactionLiLabel=" + transactionLiLabel +
                ", value='" + value + '\'' +
                '}';
    }
}
