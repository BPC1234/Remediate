package com.dsinv.abac.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */


@Entity
@Table(name = "real_time_transaction")
public class RealTimeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;


    @ManyToOne
    @JoinColumn(name = "real_time_project_id")
    private RealTimeProject realTimeProject;


    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Column(name = "admin_reviewed" , columnDefinition="bit(1) default 0")
    private boolean adminReviewed;

    @Column(name = "ia_manager_reviewed" , columnDefinition="bit(1) default 0")
    private boolean iaManagerReviewed;

    @Column(name = "ia_analyst_reviewed" , columnDefinition="bit(1) default 0")
    private boolean iaAnalystReviewed;

    @Column(name = "legal_reviewed", columnDefinition = "bit(1) default 0")
    private boolean legalReviewed;



    @Transient
    private String trxDate;
    @Transient
    private String trxId;
    @Transient
    private String dateStr;
    @Transient
    private String amountStr;
    @Transient
    private String docCreator;
    @Transient
    private String approver;
    @Transient
    private String dateOfApprover;
    @Transient
    private String ruleViolated;
    @Transient
    private String createdDateStr;

    @Transient
    private String trxCount;
    @Transient
    private String rule;
    @Transient
    private String transactionId;
    @Transient
    private String ruleIdStr;

    @Transient
    private String ruleExplanation;

    @Transient
    private String ruleTitle;

    @Transient
    private String projectType;

    @Transient
    private String projectId;

    @Transient
    private String pageNoStr;

    @Transient
    private String reviewed;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealTimeProject getRealTimeProject() {
        return realTimeProject;
    }

    public void setRealTimeProject(RealTimeProject realTimeProject) {
        this.realTimeProject = realTimeProject;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public String getDocCreator() {
        return docCreator;
    }

    public void setDocCreator(String docCreator) {
        this.docCreator = docCreator;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getDateOfApprover() {
        return dateOfApprover;
    }

    public void setDateOfApprover(String dateOfApprover) {
        this.dateOfApprover = dateOfApprover;
    }

    public String getRuleViolated() {
        return ruleViolated;
    }

    public void setRuleViolated(String ruleViolated) {
        this.ruleViolated = ruleViolated;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getTrxCount() {
        return trxCount;
    }

    public void setTrxCount(String trxCount) {
        this.trxCount = trxCount;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRuleIdStr() {
        return ruleIdStr;
    }

    public void setRuleIdStr(String ruleIdStr) {
        this.ruleIdStr = ruleIdStr;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRuleExplanation() {
        return ruleExplanation;
    }

    public void setRuleExplanation(String ruleExplanation) {
        this.ruleExplanation = ruleExplanation;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPageNoStr() {
        return pageNoStr;
    }

    public void setPageNoStr(String pageNoStr) {
        this.pageNoStr = pageNoStr;
    }

    public boolean isAdminReviewed() {
        return adminReviewed;
    }

    public void setAdminReviewed(boolean adminReviewed) {
        this.adminReviewed = adminReviewed;
    }

    public boolean isIaManagerReviewed() {
        return iaManagerReviewed;
    }

    public void setIaManagerReviewed(boolean iaManagerReviewed) {
        this.iaManagerReviewed = iaManagerReviewed;
    }

    public String getReviewed() {
        return reviewed;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public boolean isLegalReviewed() {
        return legalReviewed;
    }

    public void setLegalReviewed(boolean legalReviewed) {
        this.legalReviewed = legalReviewed;
    }

    public boolean isIaAnalystReviewed() {
        return iaAnalystReviewed;
    }

    public void setIaAnalystReviewed(boolean iaAnalystReviewed) {
        this.iaAnalystReviewed = iaAnalystReviewed;
    }

    @Override
    public String toString() {
        return "RealTimeTransaction{" +
                "id=" + id +
                ", realTimeProject=" + realTimeProject +
                ", transaction=" + transaction +
                ", trxDate='" + trxDate + '\'' +
                ", trxId='" + trxId + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", amountStr='" + amountStr + '\'' +
                ", docCreator='" + docCreator + '\'' +
                ", approver='" + approver + '\'' +
                ", dateOfApprover='" + dateOfApprover + '\'' +
                ", ruleViolated='" + ruleViolated + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", trxCount='" + trxCount + '\'' +
                ", rule='" + rule + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", ruleIdStr='" + ruleIdStr + '\'' +
                ", ruleExplanation='" + ruleExplanation + '\'' +
                ", ruleTitle='" + ruleTitle + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
