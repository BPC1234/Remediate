package com.dsinv.abac.vo.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TransactionDetailVO {

	private String txDetailComment;
	private String additionalInfo = "";
	private String nonResponsive = "";
	private String furtherReview = "";
	List<Property> additionalInfoList = new ArrayList<Property>();
	List<Property> emplyeeInfoList = new ArrayList<Property>();
	List<Property> customerInfoList = new ArrayList<Property>();
	List<Property> vendorInfoList = new ArrayList<Property>();

	// tab information
    private String tab1 = "\n\nTransaction Details";
    private String tab2 = "\n\nControls";
    private String tab3 = "\n\nAudit Trail"; 
    private String tab1Selected = "\n\nTransaction Details";
    private String tab2Selected = "\n\nControls";
    private String tab3Selected = "\n\nAudit Trail"; 

	public String getTxDetailComment() {
		return txDetailComment;
	}
	public void setTxDetailComment(String txDetailComment) {
		this.txDetailComment = txDetailComment;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getNonResponsive() {
		return nonResponsive;
	}
	public void setNonResponsive(String nonResponsive) {
		this.nonResponsive = nonResponsive;
	}
	public String getFurtherReview() {
		return furtherReview;
	}
	public void setFurtherReview(String furtherReview) {
		this.furtherReview = furtherReview;
	}
	public String getTab1() {
		return tab1;
	}
	public void setTab1(String tab1) {
		this.tab1 = tab1;
	}
	public String getTab2() {
		return tab2;
	}
	public void setTab2(String tab2) {
		this.tab2 = tab2;
	}
	public String getTab3() {
		return tab3;
	}
	public void setTab3(String tab3) {
		this.tab3 = tab3;
	}
	public String getTab1Selected() {
		return tab1Selected;
	}
	public void setTab1Selected(String tab1Selected) {
		this.tab1Selected = tab1Selected;
	}
	public String getTab2Selected() {
		return tab2Selected;
	}
	public void setTab2Selected(String tab2Selected) {
		this.tab2Selected = tab2Selected;
	}
	public String getTab3Selected() {
		return tab3Selected;
	}
	public void setTab3Selected(String tab3Selected) {
		this.tab3Selected = tab3Selected;
	}
	public List<Property> getEmplyeeInfoList() {
		return emplyeeInfoList;
	}
	public void setEmplyeeInfoList(List<HashMap> emplyeeInfoList) {
		Property property = null;
		for (HashMap aHashMap : emplyeeInfoList) {
			property = new Property();
			property.setHeader((String)aHashMap.get("header"));			
			property.setValue((String)aHashMap.get("value"));
			property.setSubTab1("\n\nAdditional Info.");
			property.setSubTab2("\n\nEmployee Info.");
			property.setSubTab3("\n\nCustomer Master File");
			property.setSubTab4("\n\nVendor Master File");
			property.setSubTab1Selected("\n\nAdditional Info.");
			property.setSubTab2Selected("\n\nEmployee Info.");
			property.setSubTab3Selected("\n\nCustomer Master File");
			property.setSubTab4Selected("\n\nVendor Master File");
			this.emplyeeInfoList.add(property);
		}
	}
	
	public List<Property> getCustomerInfoList() {
		return customerInfoList;
	}
	public void setCustomerInfoList(List<HashMap> customerInfoList) {
		Property property = null;
		for (HashMap aHashMap : customerInfoList) {
			property = new Property();
			property.setHeader((String)aHashMap.get("header"));			
			property.setValue((String)aHashMap.get("value"));
			property.setSubTab1("\n\nAdditional Info.");
			property.setSubTab2("\n\nEmployee Info.");
			property.setSubTab3("\n\nCustomer Master File");
			property.setSubTab4("\n\nVendor Master File");
			property.setSubTab1Selected("\n\nAdditional Info.");
			property.setSubTab2Selected("\n\nEmployee Info.");
			property.setSubTab3Selected("\n\nCustomer Master File");
			property.setSubTab4Selected("\n\nVendor Master File");
			this.customerInfoList.add(property);
		}
	}

	public List<Property> getVendorInfoList() {
		return vendorInfoList;
	}
	public void setVendorInfoList(List<HashMap> vendorInfoList) {
		Property property = null;
		for (HashMap aHashMap : vendorInfoList) {
			property = new Property();
			property.setHeader((String)aHashMap.get("header"));			
			property.setValue((String)aHashMap.get("value"));
			property.setSubTab1("\n\nAdditional Info.");
			property.setSubTab2("\n\nEmployee Info.");
			property.setSubTab3("\n\nCustomer Master File");
			property.setSubTab4("\n\nVendor Master File");
			property.setSubTab1Selected("\n\nAdditional Info.");
			property.setSubTab2Selected("\n\nEmployee Info.");
			property.setSubTab3Selected("\n\nCustomer Master File");
			property.setSubTab4Selected("\n\nVendor Master File");
			this.vendorInfoList.add(property);
		}
	}

	public List<Property> getAdditionalInfoList() {
		return additionalInfoList;
	}
	public void setAdditionalInfoList(List<HashMap> additionalInfoList) {
		Property property = null;
		for (HashMap aHashMap : additionalInfoList) {
			property = new Property();
			property.setHeader((String)aHashMap.get("header"));			
			property.setValue((String)aHashMap.get("value"));
			property.setSubTab1("\n\nAdditional Info.");
			property.setSubTab2("\n\nEmployee Info.");
			property.setSubTab3("\n\nCustomer Master File");
			property.setSubTab4("\n\nVendor Master File");
			property.setSubTab1Selected("\n\nAdditional Info.");
			property.setSubTab2Selected("\n\nEmployee Info.");
			property.setSubTab3Selected("\n\nCustomer Master File");
			property.setSubTab4Selected("\n\nVendor Master File");
			this.additionalInfoList.add(property);
		}
	}
}
