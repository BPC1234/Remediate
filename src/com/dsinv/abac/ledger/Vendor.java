package com.dsinv.abac.ledger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dsinv.abac.entity.AbstractBaseEntity;
import com.dsinv.abac.entity.Transaction;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/19/13
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="vendor")
public class Vendor extends AbstractBaseEntity {
   
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="transaction_id")
    private Transaction transaction;
	
	@Column(name = "address")
    private String address;
	
	@Column(name = "city")
    private String city;
	
	@Column(name = "state")
    private String state;
	
	@Column(name = "zip_code")
    private String zipCode;
	
	@Column(name = "tan_ein_no")
    private String tanEinNo;
	
	@Column(name = "bank_account_no")
    private String bankAccountNo;
	
	@Column(name = "bank_account_location")
    private String bankAccountLocation;
	
	@Column(name = "customer_type")
    private String customerType;
	
	@Column(name = "country_code")
    private String countryCode;


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
    
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
