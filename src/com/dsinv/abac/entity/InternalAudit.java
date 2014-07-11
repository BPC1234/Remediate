package com.dsinv.abac.entity;

import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;

import javax.persistence.*;

/**
 * Created by amjad on 4/17/14.
 */

@Entity
@Table(name = "internal_audit")
public class InternalAudit extends  AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_FK")
    private User user;

    @ManyToOne
    @JoinColumn(name = "employee_FK")
    private EmployeeMasterLedger employee;

    @Column(name = "project_transaction_id")
    private long projectTransactionId;

    @Column(name = "project_type")
    private String projectType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmployeeMasterLedger getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeMasterLedger employee) {
        this.employee = employee;
    }

    public long getProjectTransactionId() {
        return projectTransactionId;
    }

    public void setProjectTransactionId(long projectTransactionId) {
        this.projectTransactionId = projectTransactionId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    @Override
    public String toString() {
        return "InternalAudit{" +
                "user=" + user +
                ", employee=" + employee +
                ", projectTransactionId=" + projectTransactionId +
                ", projectType='" + projectType + '\'' +
                '}';
    }
}
