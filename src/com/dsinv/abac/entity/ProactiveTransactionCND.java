package com.dsinv.abac.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="proactive_transaction_CND")
public class ProactiveTransactionCND extends AbstractBaseEntity {

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="proactive_transaction_id")
    private ProactiveTransaction proactiveTransaction;
    @Column(name = "control_Ids")
    private String controlIds;
    @Column(name = "control_person_involve")
    private  String controlPersonInvolve;
    private String decision;
    @Column(name = "decision_email_address")
    private String decisionEmailAddress;
    @Column(name = "control_comment")
    private String controlComment;
    @Column(name = "decision_comment")
    private String decisionComment;

    // ----- non db properties  ----------//
    @Transient
    String previousDecission;



    public ProactiveTransaction getProactiveTransaction() {
        return proactiveTransaction;
    }

    public void setProactiveTransaction(ProactiveTransaction  proactiveTransaction) {
        this.proactiveTransaction = proactiveTransaction;
    }

    public String getControlIds() {
        return controlIds;
    }

    public void setControlIds(String controlIds) {
        this.controlIds = controlIds;
    }

    public String getControlPersonInvolve() {
        return controlPersonInvolve;
    }

    public void setControlPersonInvolve(String controlPersonInvolve) {
        this.controlPersonInvolve = controlPersonInvolve;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getDecisionEmailAddress() {
        return decisionEmailAddress;
    }

    public void setDecisionEmailAddress(String decisionEmailAddress) {
        this.decisionEmailAddress = decisionEmailAddress;
    }

    public String getControlComment() {
        return controlComment;
    }

    public void setControlComment(String controlComment) {
        this.controlComment = controlComment;
    }

    public String getDecisionComment() {
        return decisionComment;
    }

    public void setDecisionComment(String decisionComment) {
        this.decisionComment = decisionComment;
    }

    public String getPreviousDecission() {
        return previousDecission;
    }

    public void setPreviousDecission(String previousDecission) {
        this.previousDecission = previousDecission;
    }


    @Override
    public String toString() {
        return "ProactiveTransactionCND{" +
                "proactiveTransactionId=" + proactiveTransaction +
                ", controlIds='" + controlIds + '\'' +
                ", controlPersonInvolve='" + controlPersonInvolve + '\'' +
                ", decision='" + decision + '\'' +
                ", decisionEmailAddress='" + decisionEmailAddress + '\'' +
                ", controlComment='" + controlComment + '\'' +
                ", decisionComment='" + decisionComment + '\'' +
                '}';
    }
}
