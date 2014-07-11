package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Habib
 * Date: 11/20/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class GlobalTransactionSearch {
   private Long transactionId;
   private String nameOfThirdParty;
   private Double amount;
   private Date transactionDate;
   private String transactionType;
   private String freeText;


   private List trxList;

   private String trxDateStr;
   private String trxAmountStr;
   private String trxIdStr;
   private String projectName;
   private String trxModule;
   private String region;
   private String customerName;
   private String employeeName;
   private String vendorName;
   private String approver;
   private String realTimeProjectIdStr;

   private String businessName;
   private String userName;
   private String createdDateStr;
   private String otherDoc;


    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getNameOfThirdParty() {
        return nameOfThirdParty;
    }

    public void setNameOfThirdParty(String nameOfThirdParty) {
        this.nameOfThirdParty = nameOfThirdParty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public List getTrxList() {
        return trxList;
    }

    public void setTrxList(List trxList) {
        this.trxList = trxList;
    }

    public String getTrxDateStr() {
        return trxDateStr;
    }

    public void setTrxDateStr(String trxDateStr) {
        this.trxDateStr = trxDateStr;
    }

    public String getTrxAmountStr() {
        return trxAmountStr;
    }

    public void setTrxAmountStr(String trxAmountStr) {
        this.trxAmountStr = trxAmountStr;
    }

    public String getTrxIdStr() {
        return trxIdStr;
    }

    public void setTrxIdStr(String trxIdStr) {
        this.trxIdStr = trxIdStr;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTrxModule() {
        return trxModule;
    }

    public void setTrxModule(String trxModule) {
        this.trxModule = trxModule;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getRealTimeProjectIdStr() {
        return realTimeProjectIdStr;
    }

    public void setRealTimeProjectIdStr(String realTimeProjectIdStr) {
        this.realTimeProjectIdStr = realTimeProjectIdStr;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getOtherDoc() {
        return otherDoc;
    }

    public void setOtherDoc(String otherDoc) {
        this.otherDoc = otherDoc;
    }

    @Override
    public String toString() {
        return "GlobalTransactionSearch{" +
                "id=" + transactionId +
                ", nameOfThirdParty='" + nameOfThirdParty + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", transactionType='" + transactionType + '\'' +
                ", freeText='" + freeText + '\'' +
                '}';
    }
}
