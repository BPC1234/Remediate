package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="real_time_transaction_audit_trial")
public class RealTimeTransactionAuditTrial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="real_time_transaction_id")
    private RealTimeTransaction realTimeTransaction;

    @Column(name = "change_author")
    private String changeAuthor;

    @Column(name = "change_date")
    private Date changeDate;

    @Column(name = "change_action")
    private String changeAction;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealTimeTransaction getRealTimeTransaction() {
        return realTimeTransaction;
    }

    public void setRealTimeTransaction(RealTimeTransaction realTimeTransaction) {
        this.realTimeTransaction = realTimeTransaction;
    }

    public String getChangeAuthor() {
        return changeAuthor;
    }

    public void setChangeAuthor(String changeAuthor) {
        this.changeAuthor = changeAuthor;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeAction() {
        return changeAction;
    }

    public void setChangeAction(String changeAction) {
        this.changeAction = changeAction;
    }

    @Override
    public String toString() {
        return "RealTimeTransactionAuditTrial{" +
                "id=" + id +
                ", realTimeTransaction=" + realTimeTransaction +
                ", changeAuthor='" + changeAuthor + '\'' +
                ", changeDate=" + changeDate +
                ", changeAction='" + changeAction + '\'' +
                '}';
    }
}
