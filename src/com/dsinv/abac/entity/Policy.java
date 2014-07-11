package com.dsinv.abac.entity;

import com.dsinv.abac.ledger.VendorMasterLedger;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 11/18/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="policy")
public class Policy extends  AbstractBaseEntity {

    private  String author;
    @Column(name = "entry_time")
    private Date entryTime;
    private  String comment;
    @Column(name = "file_location")
    private  String fileLocation;
    @Column(name = "file_icon_location")
    private  String fileIconLocation;
    @Column(name = "file_name")
    private  String fileName;
    @Column(name = "document_name")
    private  String documentName;

    @Column(name = "policy_type")
    private  String policyType;

    @Column(name = "implemented_date")
    private Date implementedDate;

    @Column(name = "audiance_code", columnDefinition = "int(11) default 0")
    private int audianceCode;


    @Transient
    private MultipartFile fileData;
    @Transient
    private List list;

    @Transient
    private String entryDate;
    @Transient
    private String policyDeleteHtmlButton;

    @Transient
    private String notificationWentOutDateStr;

    @Transient
    private int noOfEmployeeConfirmed;

    @Transient
    private int noOfEmployeeUnconfirmed;

    @Transient
    private String policyStatus;

    @Transient
    private String policyViewHtmlButton;

    @Transient
    private int totalEmployee;

    @Transient
    private int totalVendor;
    @Transient
    private int totalEmployeeWithemail;
    @Transient
    private int totalVendorWithEmail;
    @Transient
    private int totalConfirmedByEmployee;
    @Transient
    private int totalConfirmedByVendor;
    @Transient
    private int totalUnconfirmedByEmployee;
    @Transient
    private int totalUnconfirmedByVendor;

    @Transient
    private float totalConfirmed;
    @Transient
    private float totalUnconfirmed;
    @Transient
    private float totalOutstanding;

    @Transient
    private String policyDownloadHtmlButton;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileIconLocation() {
        return fileIconLocation;
    }

    public void setFileIconLocation(String fileIconLocation) {
        this.fileIconLocation = fileIconLocation;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public Date getImplementedDate() {
        return implementedDate;
    }

    public void setImplementedDate(Date implementedDate) {
        this.implementedDate = implementedDate;
    }

    public int getAudianceCode() {
        return audianceCode;
    }

    public void setAudianceCode(int audianceCode) {
        this.audianceCode = audianceCode;
    }

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getPolicyDeleteHtmlButton() {
        return policyDeleteHtmlButton;
    }

    public void setPolicyDeleteHtmlButton(String policyDeleteHtmlButton) {
        this.policyDeleteHtmlButton = policyDeleteHtmlButton;
    }

    public String getNotificationWentOutDateStr() {
        return notificationWentOutDateStr;
    }

    public void setNotificationWentOutDateStr(String notificationWentOutDateStr) {
        this.notificationWentOutDateStr = notificationWentOutDateStr;
    }

    public int getNoOfEmployeeConfirmed() {
        return noOfEmployeeConfirmed;
    }

    public void setNoOfEmployeeConfirmed(int noOfEmployeeConfirmed) {
        this.noOfEmployeeConfirmed = noOfEmployeeConfirmed;
    }

    public int getNoOfEmployeeUnconfirmed() {
        return noOfEmployeeUnconfirmed;
    }

    public void setNoOfEmployeeUnconfirmed(int noOfEmployeeUnconfirmed) {
        this.noOfEmployeeUnconfirmed = noOfEmployeeUnconfirmed;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public String getPolicyViewHtmlButton() {
        return policyViewHtmlButton;
    }

    public void setPolicyViewHtmlButton(String policyViewHtmlButton) {
        this.policyViewHtmlButton = policyViewHtmlButton;
    }

    public int getTotalEmployee() {
        return totalEmployee;
    }

    public void setTotalEmployee(int totalEmployee) {
        this.totalEmployee = totalEmployee;
    }

    public int getTotalVendor() {
        return totalVendor;
    }

    public void setTotalVendor(int totalVendor) {
        this.totalVendor = totalVendor;
    }

    public int getTotalEmployeeWithemail() {
        return totalEmployeeWithemail;
    }

    public void setTotalEmployeeWithemail(int totalEmployeeWithemail) {
        this.totalEmployeeWithemail = totalEmployeeWithemail;
    }

    public int getTotalVendorWithEmail() {
        return totalVendorWithEmail;
    }

    public void setTotalVendorWithEmail(int totalVendorWithEmail) {
        this.totalVendorWithEmail = totalVendorWithEmail;
    }

    public int getTotalConfirmedByEmployee() {
        return totalConfirmedByEmployee;
    }

    public void setTotalConfirmedByEmployee(int totalConfirmedByEmployee) {
        this.totalConfirmedByEmployee = totalConfirmedByEmployee;
    }

    public int getTotalConfirmedByVendor() {
        return totalConfirmedByVendor;
    }

    public void setTotalConfirmedByVendor(int totalConfirmedByVendor) {
        this.totalConfirmedByVendor = totalConfirmedByVendor;
    }

    public int getTotalUnconfirmedByEmployee() {
        return totalUnconfirmedByEmployee;
    }

    public void setTotalUnconfirmedByEmployee(int totalUnconfirmedByEmployee) {
        this.totalUnconfirmedByEmployee = totalUnconfirmedByEmployee;
    }

    public int getTotalUnconfirmedByVendor() {
        return totalUnconfirmedByVendor;
    }

    public void setTotalUnconfirmedByVendor(int totalUnconfirmedByVendor) {
        this.totalUnconfirmedByVendor = totalUnconfirmedByVendor;
    }

    public float getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(float totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public float getTotalUnconfirmed() {
        return totalUnconfirmed;
    }

    public void setTotalUnconfirmed(float totalUnconfirmed) {
        this.totalUnconfirmed = totalUnconfirmed;
    }

    public float getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(float totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public String getPolicyDownloadHtmlButton() {
        return policyDownloadHtmlButton;
    }

    public void setPolicyDownloadHtmlButton(String policyDownloadHtmlButton) {
        this.policyDownloadHtmlButton = policyDownloadHtmlButton;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "author='" + author + '\'' +
                ", entryTime=" + entryTime +
                ", comment='" + comment + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", fileIconLocation='" + fileIconLocation + '\'' +
                ", fileName='" + fileName + '\'' +
                ", documentName='" + documentName + '\'' +
                ", policyType='" + policyType + '\'' +
                ", implementedDate=" + implementedDate +
                ", audianceCode=" + audianceCode +
                ", fileData=" + fileData +
                ", list=" + list +
                ", entryDate='" + entryDate + '\'' +
                ", policyDeleteHtmlButton='" + policyDeleteHtmlButton + '\'' +
                ", notificationWentOutDateStr='" + notificationWentOutDateStr + '\'' +
                ", noOfEmployeeConfirmed=" + noOfEmployeeConfirmed +
                ", noOfEmployeeUnconfirmed=" + noOfEmployeeUnconfirmed +
                ", policyStatus='" + policyStatus + '\'' +
                ", policyViewHtmlButton='" + policyViewHtmlButton + '\'' +
                ", totalEmployee=" + totalEmployee +
                ", totalVendor=" + totalVendor +
                ", totalEmployeeWithemail=" + totalEmployeeWithemail +
                ", totalVendorWithEmail=" + totalVendorWithEmail +
                ", totalConfirmedByEmployee=" + totalConfirmedByEmployee +
                ", totalConfirmedByVendor=" + totalConfirmedByVendor +
                ", totalUnconfirmedByEmployee=" + totalUnconfirmedByEmployee +
                ", totalUnconfirmedByVendor=" + totalUnconfirmedByVendor +
                ", totalConfirmed=" + totalConfirmed +
                ", totalUnconfirmed=" + totalUnconfirmed +
                ", totalOutstanding=" + totalOutstanding +
                ", policyDownloadHtmlButton='" + policyDownloadHtmlButton + '\'' +
                '}';
    }
}
