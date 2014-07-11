package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="proactive_transaction_audit_trial")
public class ProactiveTransactionAuditTrial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="proactive_transaction_id")
    private ProactiveTransaction proactiveTransaction;
    @Column(name = "change_author")
    private String changeAuthor;
    @Column(name = "change_date")
    private Date changeDate;
    @Column(name = "change_action")
    private String changeAction;

    public ProactiveTransaction   getProactiveTransaction() {
        return proactiveTransaction;
    }

    public void setProactiveTransaction(ProactiveTransaction   proactiveTransaction) {
        this.proactiveTransaction = proactiveTransaction;
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
        return "ProactiveTransactionAuditTrial{" +
                "proactiveTransaction=" + proactiveTransaction+
                ", changeAuthor='" + changeAuthor + '\'' +
                ", changeDate=" + changeDate +
                ", changeAction='" + changeAction + '\'' +
                '}';
    }
}
