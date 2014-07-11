package com.dsinv.abac.vo.admin;

public class UserVo {

    private String userName;
	private String mobileNumber;
	private String role;
	private String isActive;
	
	public UserVo(){
		this.userName = null;
		this.mobileNumber = null;
		this.role = null;
		this.isActive = null;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
}
