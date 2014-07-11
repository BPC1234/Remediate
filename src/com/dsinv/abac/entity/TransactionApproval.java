package com.dsinv.abac.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Habib
 * Date: 6/18/13
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="transaction_approval")
public class TransactionApproval{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
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
    /*@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="reactive_transaction_id")
    private  ReactiveTransaction reactiveTransaction;*/

    @Transient
    private MultipartFile fileData;
    @Transient
    private List list;

    @Transient
    private String entryDate;
    @Transient
    private String trxApproveDeleteHtmlButton;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTrxApproveDeleteHtmlButton() {
        return trxApproveDeleteHtmlButton;
    }

    public void setTrxApproveDeleteHtmlButton(String trxApproveDeleteHtmlButton) {
        this.trxApproveDeleteHtmlButton = trxApproveDeleteHtmlButton;
    }

    @Override
    public String toString() {
        return "TransactionApproval{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", entryTime=" + entryTime +
                ", comment='" + comment + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", fileIconLocation='" + fileIconLocation + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
