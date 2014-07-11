package com.dsinv.abac.ledger;

import com.dsinv.abac.entity.AbstractBaseEntity;
import com.dsinv.abac.entity.Transaction;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/19/13
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class Customer extends AbstractBaseEntity {


    private Transaction transaction;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String tanEinNo;
    private String bankAccountNo;
    private String bankAccountLocation;
    private String customerType;
    private String countryId;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTanEinNo() {
        return tanEinNo;
    }

    public void setTanEinNo(String tanEinNo) {
        this.tanEinNo = tanEinNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountLocation() {
        return bankAccountLocation;
    }

    public void setBankAccountLocation(String bankAccountLocation) {
        this.bankAccountLocation = bankAccountLocation;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
