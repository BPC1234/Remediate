package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 12/17/13
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "suspicious_transaction")
public class SuspiciousTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "rule_id")
    private long ruleId;

    @Column(name = "transaction_id")
    private long transactionId;

    @Column(name = "job_Id")
    private int jobId;

    @Column(name = "rule_title")
    private String ruleTitle;

    @Column(name = "rule_code")
    private String ruleCode;

    @Column(name="rule_explanation")
    private String ruleExplanation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleExplanation() {
        return ruleExplanation;
    }

    public void setRuleExplanation(String ruleExplanation) {
        this.ruleExplanation = ruleExplanation;
    }

    @Override
    public String toString() {
        return "SuspiciousTransaction{" +
                "id=" + id +
                ", ruleId=" + ruleId +
                ", transactionId=" + transactionId +
                ", jobId=" + jobId +
                ", ruleTitle='" + ruleTitle + '\'' +
                ", ruleCode='" + ruleCode + '\'' +
                ", ruleExplanation='" + ruleExplanation + '\'' +
                '}';
    }
}
