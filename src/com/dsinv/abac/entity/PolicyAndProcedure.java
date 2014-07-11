package com.dsinv.abac.entity;


import com.dsinv.abac.ledger.CustomerMasterLedger;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "policy_and_procedure")
public class PolicyAndProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "vendor_master_ledger_FK")
    private VendorMasterLedger vendorMasterLedger;

    @ManyToOne
    @JoinColumn(name = "employee_master_ledger_FK")
    private EmployeeMasterLedger employeeMasterLedger;

    @ManyToOne
    @JoinColumn(name = "policy_FK")
    private Policy policy;

    @Column(name = "is_confirmed", columnDefinition = "bit(1) default 0")
    private boolean isConfirmed;

    @Column(name = "downloaded")
    private Date downloaded;

    @Column(name = "confirm_date")
    private Date confirmDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VendorMasterLedger getVendorMasterLedger() {
        return vendorMasterLedger;
    }

    public void setVendorMasterLedger(VendorMasterLedger vendorMasterLedger) {
        this.vendorMasterLedger = vendorMasterLedger;
    }

    public EmployeeMasterLedger getEmployeeMasterLedger() {
        return employeeMasterLedger;
    }

    public void setEmployeeMasterLedger(EmployeeMasterLedger employeeMasterLedger) {
        this.employeeMasterLedger = employeeMasterLedger;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Date getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Date downloaded) {
        this.downloaded = downloaded;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    @Override
    public String toString() {
        return "PolicyAndProcedure{" +
                "id=" + id +
                ", vendorMasterLedger=" + vendorMasterLedger +
                ", employeeMasterLedger=" + employeeMasterLedger +
                ", policy=" + policy +
                ", isConfirmed=" + isConfirmed +
                ", downloaded=" + downloaded +
                ", confirmDate=" + confirmDate +
                '}';
    }
}
