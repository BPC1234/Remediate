package com.dsinv.abac.entity;

import com.dsinv.abac.ledger.CustomerMasterLedger;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="user")
public class User extends AbstractBaseEntity {


    @Column(name = "user_name",unique=true)
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "email")
    private String email;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "user_type_id")
    private long userTypeId;


    @Transient
    private String givenPassword;
    @Transient
    private String confirmPassword;

    @Transient
    private String userActiveCheckBoxHtml;
    @Transient
    private String  userEditButtonHtml;
    @Transient
    private String userDeleteButtonHtml;

    @Transient
    private String personType;

    @Transient
    private String errorMessage;
    @Transient
    private String fieldName;

    @Transient
    private List<User> errorList;

    @Transient
    private String employeeName;


    public User() {
        active = true;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserActiveCheckBoxHtml() {
        return userActiveCheckBoxHtml;
    }

    public void setUserActiveCheckBoxHtml(String userActiveCheckBoxHtml) {
        this.userActiveCheckBoxHtml = userActiveCheckBoxHtml;
    }

    public String getUserEditButtonHtml() {
        return userEditButtonHtml;
    }

    public void setUserEditButtonHtml(String userEditButtonHtml) {
        this.userEditButtonHtml = userEditButtonHtml;
    }

    public String getUserDeleteButtonHtml() {
        return userDeleteButtonHtml;
    }

    public void setUserDeleteButtonHtml(String userDeleteButtonHtml) {
        this.userDeleteButtonHtml = userDeleteButtonHtml;
    }

    public String getGivenPassword() {
        return givenPassword;
    }

    public void setGivenPassword(String givenPassword) {
        this.givenPassword = givenPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<User> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<User> errorList) {
        this.errorList = errorList;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", userTypeId=" + userTypeId +
                ", givenPassword='" + givenPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", userActiveCheckBoxHtml='" + userActiveCheckBoxHtml + '\'' +
                ", userEditButtonHtml='" + userEditButtonHtml + '\'' +
                ", userDeleteButtonHtml='" + userDeleteButtonHtml + '\'' +
                ", personType='" + personType + '\'' +
                '}';
    }
}
