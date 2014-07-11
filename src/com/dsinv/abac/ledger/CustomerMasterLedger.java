package com.dsinv.abac.ledger;

import com.dsinv.abac.entity.Training;
import com.dsinv.abac.entity.Transaction;
import com.dsinv.abac.vo.admin.CSVTransaction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
 @Entity
 @Table(name="customer_master_ledger")
 public class CustomerMasterLedger extends Ledger{

    @Column(name="credit_limit")
    private double creditLimit;

    @Column(name="entity_address")
    private String entityAddress;

    @Column(name="entity_address1")
    private String entityAddress1;

    @Column(name="entity_name")
    private String entityName;

    @Column(name="entity_status")
    private String entityStatus;

    @Column(name="entity_type")
    private String entityType;

    @Column(name="recipient_bank_account_domicile")
    private String recipientBankAccountDomicile;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    private String tin;

    @Column(name="customer_id")
    private long customerId;
    private String regionState;
    private String latLong;
    private String title;
    private String bankDomicile;
    private String bankRouting;
    private String startDt;
    private String endDt;
    private String department;
    private String supervisor;
    private String salary;
    private String currency;
    private String gender;
    private String DOB;
    private String ethnicity;
    private String spendLimit;
    private String approvalLimit;
    private String businessUnit;


    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getEntityAddress() {
        return entityAddress;
    }

    public void setEntityAddress(String entityAddress) {
        this.entityAddress = entityAddress;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(String entityStatus) {
        this.entityStatus = entityStatus;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getRecipientBankAccountDomicile() {
        return recipientBankAccountDomicile;
    }

    public void setRecipientBankAccountDomicile(String recipientBankAccountDomicile) {
        this.recipientBankAccountDomicile = recipientBankAccountDomicile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getEntityAddress1() {
        return entityAddress1;
    }

    public void setEntityAddress1(String entityAddress1) {
        this.entityAddress1 = entityAddress1;
    }

    public String getRegionState() {
        return regionState;
    }

    public void setRegionState(String regionState) {
        this.regionState = regionState;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBankDomicile() {
        return bankDomicile;
    }

    public void setBankDomicile(String bankDomicile) {
        this.bankDomicile = bankDomicile;
    }

    public String getBankRouting() {
        return bankRouting;
    }

    public void setBankRouting(String bankRouting) {
        this.bankRouting = bankRouting;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getSpendLimit() {
        return spendLimit;
    }

    public void setSpendLimit(String spendLimit) {
        this.spendLimit = spendLimit;
    }

    public String getApprovalLimit() {
        return approvalLimit;
    }

    public void setApprovalLimit(String approvalLimit) {
        this.approvalLimit = approvalLimit;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CustomerMasterLedger{" +
                "creditLimit=" + creditLimit +
                ", entityAddress='" + entityAddress + '\'' +
                ", entityAddress1='" + entityAddress1 + '\'' +
                ", entityName='" + entityName + '\'' +
                ", entityStatus='" + entityStatus + '\'' +
                ", entityType='" + entityType + '\'' +
                ", recipientBankAccountDomicile='" + recipientBankAccountDomicile + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", tin='" + tin + '\'' +
                ", customerId=" + customerId +
                ", regionState='" + regionState + '\'' +
                ", latLong='" + latLong + '\'' +
                ", title='" + title + '\'' +
                ", bankDomicile='" + bankDomicile + '\'' +
                ", bankRouting='" + bankRouting + '\'' +
                ", startDt='" + startDt + '\'' +
                ", endDt='" + endDt + '\'' +
                ", department='" + department + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", salary='" + salary + '\'' +
                ", currency='" + currency + '\'' +
                ", gender='" + gender + '\'' +
                ", DOB='" + DOB + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", spendLimit='" + spendLimit + '\'' +
                ", approvalLimit='" + approvalLimit + '\'' +
                ", businessUnit='" + businessUnit + '\'' +
                '}';
    }

    @Override
    public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
        if(ledger instanceof CustomerMasterLedger){
            CustomerMasterLedger cmLedger = (CustomerMasterLedger)ledger;
            cmLedger.setEntityType(csvTransaction.getEntityType());
            cmLedger.setEntityStatus(csvTransaction.getEntityStatus());
            cmLedger.setRecipientBankAccountDomicile(csvTransaction.getRecipientBankAccountDomicile());
            cmLedger.setEntityName(csvTransaction.getEntityName());
            cmLedger.setEntityAddress(csvTransaction.getEntityAddress());
            cmLedger.setCreditLimit(csvTransaction.getCreditLimit());

            cmLedger.setTransaction(csvTransaction.getTransaction());
            cmLedger.setTransactionAmount(csvTransaction.getTransactionAmount());

            if("+".equals(isDebit))
                cmLedger.setIsDebit(1);
            else
                cmLedger.setIsDebit(-1);
            return cmLedger;
        }

            return null;
    }



    /**
     *   in append statement statement must maintain serial according to insert sql field selection
     *   please follow EMPLOYEE_MASTER_LEDGER_QUERY in constant class.
     * @param ledger
     * @param csvTransaction
     * @param isDebit
     * @return
     */
    public String insertsql(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
        StringBuffer insert = new StringBuffer();

        insert.append("('");
        insert.append(csvTransaction.getCustomerAddress());   // P.O Box address
        insert.append("','");
        insert.append(csvTransaction.getCustomerAddress1()); // physical address
        insert.append("','");
        insert.append(csvTransaction.getCustomerName());
        insert.append("','");
        insert.append(csvTransaction.getEntityStatus());
        insert.append("','");
        insert.append(csvTransaction.getEntityType());
        insert.append("','");
        insert.append(csvTransaction.getRecipientBankAccountDomicile());
        insert.append("',");
      /*  insert.append(csvTransaction.getTransaction().getId());
        insert.append(",'");
     */   if ("+".equals(isDebit))
            insert.append(1);
        else
            insert.append(-1);
        insert.append(")");

        return insert.toString();
    }

}
