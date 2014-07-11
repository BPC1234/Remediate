package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="reactive_transaction_audit_trial")
public class ReactiveTransactionAuditTrial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="reactive_transaction_id")
    private  ReactiveTransaction reactiveTransaction;

    @Column(name = "change_date")
    private Date changeDate;

    @Column(name = "change_author")
    private String changeAuthor;

    @Column(name = "change_action")
    private String changeAction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ReactiveTransaction getReactiveTransaction() {
        return reactiveTransaction;
    }

    public void setReactiveTransaction(ReactiveTransaction reactiveTransaction) {
        this.reactiveTransaction = reactiveTransaction;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeAuthor() {
        return changeAuthor;
    }

    public void setChangeAuthor(String changeAuthor) {
        this.changeAuthor = changeAuthor;
    }

    public String getChangeAction() {
        return changeAction;
    }

    public void setChangeAction(String changeAction) {
        this.changeAction = changeAction;
    }

    @Override
    public String toString() {
        return "ReactiveTransactionAuditTrial{" +
                "id=" + id +
                ", reactiveTransaction=" + reactiveTransaction +
                ", changeDate=" + changeDate +
                ", changeAuthor='" + changeAuthor + '\'' +
                ", changeAction='" + changeAction + '\'' +
                '}';
    }
}