package com.dsinv.abac.entity;

import com.dsinv.abac.entity.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="real_time_transaction_CND")
public class RealTimeTransactionCND extends AbstractBaseEntity {

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="real_time_transaction_id")
    private RealTimeTransaction realTimeTransaction;

    @Column(name = "control_Ids")
    private String controlIds;

    @Column(name = "control_person_involve")
    private  String controlPersonInvolve;

    @Column(name = "decision")
    private String decision;

    @Column(name = "decision_email_address")
    private String decisionEmailAddress;

    @Column(name = "control_comment")
    private String controlComment;

    @Column(name = "decision_comment")
    private String decisionComment;

    public RealTimeTransaction getRealTimeTransaction() {
        return realTimeTransaction;
    }

    public void setRealTimeTransaction(RealTimeTransaction realTimeTransaction) {
        this.realTimeTransaction = realTimeTransaction;
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

    @Override
    public String toString() {
        return "RealTimeTransactionCND{" +
                "realTimeTransaction=" + realTimeTransaction +
                ", controlIds='" + controlIds + '\'' +
                ", controlPersonInvolve='" + controlPersonInvolve + '\'' +
                ", decision='" + decision + '\'' +
                ", decisionEmailAddress='" + decisionEmailAddress + '\'' +
                ", controlComment='" + controlComment + '\'' +
                ", decisionComment='" + decisionComment + '\'' +
                '}';
    }
}
