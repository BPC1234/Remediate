package com.dsinv.abac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="lookup")
public class Lookup extends AbstractBaseEntity {

    @Column(name = "ledger_name")
    private String ledgerName;
    @Column(name = "ledger")
    private String ledger;
    private boolean active;


    @Transient
    private boolean isSelectTableName;


    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getLedger() {
        return ledger;
    }

    public void setLedger(String ledger) {
        this.ledger = ledger;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSelectTableName() {
        return isSelectTableName;
    }

    public void setSelectTableName(boolean selectTableName) {
        isSelectTableName = selectTableName;
    }

    @Override
    public String toString() {
        return "Lookup{" +
                "ledgerName='" + ledgerName + '\'' +
                ", ledger='" + ledger + '\'' +
                ", active=" + active +
                ", isSelectTableName=" + isSelectTableName +
                '}';
    }
}
