package com.dsinv.abac.entity;







import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Date;

@Entity
@Table(name="reactive_project")
public class ReactiveProject extends AbstractBaseEntity {

    @Column(name = "project_name")
    private String projectName;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="region_id")
    private Region region;

    @Column(name = "amount")
    private double amount;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "transaction_type")
    private String transactionType;

    @Transient
    private String regionName;
    @Transient
    private String paymentDateStr;

    @Transient
    private String reactiveProjectEditHtmlButton;

    @Transient
    private String reactiveProjectDeleteHtmlButton;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getPaymentDateStr() {
        return paymentDateStr;
    }

    public void setPaymentDateStr(String paymentDateStr) {
        this.paymentDateStr = paymentDateStr;
    }

    public String getReactiveProjectEditHtmlButton() {
        return reactiveProjectEditHtmlButton;
    }

    public void setReactiveProjectEditHtmlButton(String reactiveProjectEditHtmlButton) {
        this.reactiveProjectEditHtmlButton = reactiveProjectEditHtmlButton;
    }

    public String getReactiveProjectDeleteHtmlButton() {
        return reactiveProjectDeleteHtmlButton;
    }

    public void setReactiveProjectDeleteHtmlButton(String reactiveProjectDeleteHtmlButton) {
        this.reactiveProjectDeleteHtmlButton = reactiveProjectDeleteHtmlButton;
    }

    @Override
    public String toString() {
        return "ReactiveProject{" +
                "projectName='" + projectName + '\'' +
                ", region=" + region +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
