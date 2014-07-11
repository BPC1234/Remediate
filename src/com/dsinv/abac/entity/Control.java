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
@Table(name="control")
public class Control extends AbstractBaseEntity {

    @Column(name = "name",columnDefinition = "TEXT")
    private String name;
    @Column(name = "transaction_type")
    private String transactionType;
    private boolean active;

    @Transient
    private int optionValue;
    @Transient
    private String activeCheckBoxHtml;
    @Transient
    private String  editButtonHtml;
    @Transient
    private String deleteButtonHtml;
    @Transient
    private long totalUsed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getSelected(){
    	if(active) return "X";
    	else return "";
    }

    public int getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(int optionValue) {
        this.optionValue = optionValue;
    }

    public String getActiveCheckBoxHtml() {
        return activeCheckBoxHtml;
    }

    public void setActiveCheckBoxHtml(String activeCheckBoxHtml) {
        this.activeCheckBoxHtml = activeCheckBoxHtml;
    }

    public String getEditButtonHtml() {
        return editButtonHtml;
    }

    public void setEditButtonHtml(String editButtonHtml) {
        this.editButtonHtml = editButtonHtml;
    }

    public String getDeleteButtonHtml() {
        return deleteButtonHtml;
    }

    public void setDeleteButtonHtml(String deleteButtonHtml) {
        this.deleteButtonHtml = deleteButtonHtml;
    }

    public long getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(long totalUsed) {
        this.totalUsed = totalUsed;
    }

    @Override
    public String toString() {
        return "Control{" +
                "name='" + name + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", active=" + active +
                '}';
    }
}
