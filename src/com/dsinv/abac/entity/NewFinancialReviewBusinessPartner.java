package com.dsinv.abac.entity;

import java.util.Date;

public class NewFinancialReviewBusinessPartner extends AbstractBaseEntity{

	private String name;
	private String addressLine;
	private String city;
	private String state;
	private String zip;
	private String countryName;
	private String bankAccountNo;
	private String bankAccountLocation;
	private String bankName;
	private String tin;
	private String keyOfficer;
	private String lastReviewer;
	private Date lastReviewDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getKeyOfficer() {
		return keyOfficer;
	}
	public void setKeyOfficer(String keyOfficer) {
		this.keyOfficer = keyOfficer;
	}
	public String getLastReviewer() {
		return lastReviewer;
	}
	public void setLastReviewer(String lastReviewer) {
		this.lastReviewer = lastReviewer;
	}
	public Date getLastReviewDate() {
		return lastReviewDate;
	}
	public void setLastReviewDate(Date lastReviewDate) {
		this.lastReviewDate = lastReviewDate;
	}
}
