package com.dsinv.abac.entity;

import com.dsinv.abac.ledger.CustomerMasterLedger;
import com.dsinv.abac.ledger.VendorMasterLedger;
import com.dsinv.abac.ledger.impl.EmployeeMasterLedger;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 2/28/14
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "training")
public class Training extends AbstractBaseEntity{

    @Column(name = "document_name")
    private String documentName;
    @Column(name = "author")
    private  String author;
    @Column(name = "entry_time")
    private Date entryTime;
    @Column(name = "comment")
    private  String comment;
    @Column(name = "file_location")
    private  String fileLocation;
    @Column(name = "file_icon_location")
    private  String fileIconLocation;
    @Column(name = "file_name")
    private  String fileName;
    @Column(name = "training_type")
    private  String trainingType;

    @Column(name = "implemented_date")
    private Date implementedDate;

    @Column(name = "audiance_code",columnDefinition = "int(11) default 1")
    private int audianceCode;

    @Column(name = "no_of_question")
    private int noOfQuestion;

    @Column(name = "pass_number" )
    private int passNumber;

    @Transient
    private MultipartFile fileData;
    @Transient
    private List list;

    @Transient
    private String entryDate;
    @Transient
    private String policyDeleteHtmlButton;
    @Transient
    private String questionSetHtmlButton;
    @Transient
    private String testStartHtmlButton;
    @Transient
    private String testStatus;
    @Transient
    private int totalEmployeeWithemail;

    @Transient
    private int totalParticipantEmployee;
    @Transient
    private int totalEmployeeNeedRetake;

    @Transient
    private float totalOutstanding;

    @Transient
    private String downloadCertificateHtmlButton;

    @Transient
    private int totalEmployee;


    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

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

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getQuestionSetHtmlButton() {
        return questionSetHtmlButton;
    }

    public void setQuestionSetHtmlButton(String questionSetHtmlButton) {
        this.questionSetHtmlButton = questionSetHtmlButton;
    }

    public String getTestStartHtmlButton() {
        return testStartHtmlButton;
    }

    public void setTestStartHtmlButton(String testStartHtmlButton) {
        this.testStartHtmlButton = testStartHtmlButton;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
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

    public int getTotalEmployeeWithemail() {
        return totalEmployeeWithemail;
    }

    public void setTotalEmployeeWithemail(int totalEmployeeWithemail) {
        this.totalEmployeeWithemail = totalEmployeeWithemail;
    }

    public int getTotalParticipantEmployee() {
        return totalParticipantEmployee;
    }

    public void setTotalParticipantEmployee(int totalParticipantEmployee) {
        this.totalParticipantEmployee = totalParticipantEmployee;
    }

    public int getTotalEmployeeNeedRetake() {
        return totalEmployeeNeedRetake;
    }

    public void setTotalEmployeeNeedRetake(int totalEmployeeNeedRetake) {
        this.totalEmployeeNeedRetake = totalEmployeeNeedRetake;
    }

    public float getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(float totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public int getNoOfQuestion() {
        return noOfQuestion;
    }

    public void setNoOfQuestion(int noOfQuestion) {
        this.noOfQuestion = noOfQuestion;
    }

    public int getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(int passNumber) {
        this.passNumber = passNumber;
    }

    public String getDownloadCertificateHtmlButton() {
        return downloadCertificateHtmlButton;
    }

    public void setDownloadCertificateHtmlButton(String downloadCertificateHtmlButton) {
        this.downloadCertificateHtmlButton = downloadCertificateHtmlButton;
    }

    public int getTotalEmployee() {
        return totalEmployee;
    }

    public void setTotalEmployee(int totalEmployee) {
        this.totalEmployee = totalEmployee;
    }
}
