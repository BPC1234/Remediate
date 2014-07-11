package com.dsinv.abac.entity;

import com.dsinv.abac.ledger.CustomerMasterLedger;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 2/28/14
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "employee_training")
public class EmployeeTraining extends AbstractBaseEntity {


    @ManyToOne
    @JoinColumn(name = "training_FK")
    private Training training;

    @ManyToOne
    @JoinColumn(name = "employee_master_ledger_id")
    private EmployeeMasterLedger employeeMasterLedger;

    private String status;

    @Column(name = "participated")
    private Date participated;

    private int obtainedMarks;


    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public EmployeeMasterLedger getEmployeeMasterLedger() {
        return employeeMasterLedger;
    }

    public void setEmployeeMasterLedger(EmployeeMasterLedger employeeMasterLedger) {
        this.employeeMasterLedger = employeeMasterLedger;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getParticipated() {
        return participated;
    }

    public void setParticipated(Date participated) {
        this.participated = participated;
    }

    public int getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(int obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }
}
