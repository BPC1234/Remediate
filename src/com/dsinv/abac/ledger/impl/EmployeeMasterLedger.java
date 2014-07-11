package com.dsinv.abac.ledger.impl;

import com.dsinv.abac.entity.Training;
import com.dsinv.abac.entity.Transaction;
import com.dsinv.abac.ledger.Ledger;
import com.dsinv.abac.vo.admin.CSVTransaction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 6:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="employee_master_ledger")
public class EmployeeMasterLedger extends Ledger {

    @Column(name="entity_name")
    private String entityName;
    @Column(name="employee_address")
    private String employeeAddress;
    @Column(name="last_name")
    private String lastName;
    @Column(name="first_name")
    private String firstName;
    private String city;
    private String zip;
    @Column(name="region_state")
    private String regionState;
    @Column(name="country_name")
    private String countryName;
    @Column(name="lat_long")
    private String  LatLong;
    private String tin;
    private String title;
    @Column(name="bank_name")
    private String bankName;
    @Column(name="bank_domicile")
    private String bankDomicile;
    @Column(name="bank_account_no")
    private String bankAccountNo;
    @Column(name="bank_routing")
    private String bankRouting;
    private String status;
    @Column(name="start_date")
    private Date startDate;
    @Column(name="end_date")
    private Date endDate;
    private String department;
    private String supervisor;
    private String salary;
    private String currency;
    private String gender;
    private String dob;
    private String ethnicity;
    @Column(name="spend_limit")
    private String spendLimit;
    @Column(name="approval_limit")
    private double approvalLimit;
    @Column(name="business_unit")
    private String businessUnit;
    @Column(name="employee_id")
    private String employeeId;
    private String email;


    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getRegionState() {
        return regionState;
    }

    public void setRegionState(String regionState) {
        this.regionState = regionState;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLatLong() {
        return LatLong;
    }

    public void setLatLong(String latLong) {
        LatLong = latLong;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankDomicile() {
        return bankDomicile;
    }

    public void setBankDomicile(String bankDomicile) {
        this.bankDomicile = bankDomicile;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankRouting() {
        return bankRouting;
    }

    public void setBankRouting(String bankRouting) {
        this.bankRouting = bankRouting;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public double getApprovalLimit() {
        return approvalLimit;
    }

    public void setApprovalLimit(double approvalLimit) {
        this.approvalLimit = approvalLimit;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Ledger process(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
        if(ledger instanceof EmployeeMasterLedger){
            EmployeeMasterLedger employeeMasterLedger = (EmployeeMasterLedger)ledger;

            employeeMasterLedger.setEmployeeAddress(csvTransaction.getEmployeeAddress());
            employeeMasterLedger.setEntityName(csvTransaction.getEntityName());


            employeeMasterLedger.setTransaction(csvTransaction.getTransaction());
            employeeMasterLedger.setTransactionAmount(csvTransaction.getTransactionAmount());

            if("+".equals(isDebit))
                employeeMasterLedger.setIsDebit(1);
            else
                employeeMasterLedger.setIsDebit(-1);
            return employeeMasterLedger;
        }

            return null;
    }

    /**
     *   in append statement statement must maintain serial according to insert sql field selection
     *   please follow CUSTOMER_MASTER_LEDGER_QUERY in constant class.
     * @param ledger
     * @param csvTransaction
     * @param isDebit
     * @return
     */
    public String insertsql(Ledger ledger, CSVTransaction csvTransaction, String isDebit) {
        StringBuffer insert = new StringBuffer();
        insert.append("('");
        insert.append(csvTransaction.getEmployeeName());
        insert.append("','");
        insert.append(csvTransaction.getEmployeeAddress());
        insert.append("','");
        insert.append(csvTransaction.getEmployeeCity());
        insert.append("','");
        insert.append(csvTransaction.getEmployeeZipCode());
        insert.append("','");
        insert.append(csvTransaction.getEmployeeCountry());
        insert.append("','");
        insert.append(csvTransaction.getTin());
        insert.append("','");
        insert.append(csvTransaction.getRecipientBankAccountDomicile());
        insert.append("','");
        insert.append(csvTransaction.getEmployeeBankAccountNo());
        insert.append("','");
        insert.append(csvTransaction.getEntityStatus());
        insert.append("','");
        insert.append(csvTransaction.getApprovalLimit());
        insert.append("',");
        insert.append(csvTransaction.getEntityId());
        insert.append(",");
       /* insert.append(csvTransaction.getTransaction().getId());
        insert.append(",");
     */   if ("+".equals(isDebit))
            insert.append(1);
        else
            insert.append(-1);
        insert.append(")");

        return insert.toString();
    }
}
