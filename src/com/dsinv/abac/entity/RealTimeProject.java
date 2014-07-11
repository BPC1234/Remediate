package com.dsinv.abac.entity;

import com.dsinv.abac.entity.User;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "real_time_project")
public class RealTimeProject extends AbstractBaseEntity {

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "status")
    private String status;

 
    @Column(name = "assign_to")
    private String assignTo;

    @Transient
    private  int noOfTransaction;

    @Transient
    private int review;

    @Transient
    private String unAssignLabel;

    @Transient
    private int outstandingTransactions;

    @Transient
    private String transactionIds;

    @Transient
    private String total;

    @Transient
    private String projectType;

    @Transient
    private String projectId;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }   

    public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public int getNoOfTransaction() {
        return noOfTransaction;
    }

    public void setNoOfTransaction(int noOfTransaction) {
        this.noOfTransaction = noOfTransaction;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }
    public String getUnAssignLabel() {
        return unAssignLabel;
    }

    public void setUnAssignLabel(String unAssignLabel) {
        this.unAssignLabel = unAssignLabel;
    }

    public int getOutstandingTransactions() {
        return outstandingTransactions;
    }

    public void setOutstandingTransactions(int outstandingTransactions) {
        this.outstandingTransactions = outstandingTransactions;
    }

    public String getTransactionIds() {
        return transactionIds;
    }

    public void setTransactionIds(String transactionIds) {
        this.transactionIds = transactionIds;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    @Override
    public String toString() {
        return "RealTimeProject{" +
                "projectName='" + projectName + '\'' +
                ", status='" + status + '\'' +
                ", assignTo='" + assignTo + '\'' +
                ", noOfTransaction=" + noOfTransaction +
                ", review=" + review +
                ", unAssignLabel='" + unAssignLabel + '\'' +
                ", outstandingTransactions=" + outstandingTransactions +
                ", transactionIds='" + transactionIds + '\'' +
                ", total='" + total + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
