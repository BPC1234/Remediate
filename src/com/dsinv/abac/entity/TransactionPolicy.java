package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by amjad on 4/2/14.
 */

@Entity
@Table(name="transaction_policy")
public class TransactionPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    private  String uploadedBy;

    private Date uploaded;

    private  String comment;

    @Column(name = "file_location")
    private  String fileLocation;

    @Column(name = "file_icon_location")
    private  String fileIconLocation;

    @Column(name = "file_name")
    private  String fileName;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="transaction_id")
    private Transaction transaction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Date getUploaded() {
        return uploaded;
    }

    public void setUploaded(Date uploaded) {
        this.uploaded = uploaded;
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "RealTimeTransactionPolicy{" +
                "id=" + id +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", uploaded=" + uploaded +
                ", comment='" + comment + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", fileIconLocation='" + fileIconLocation + '\'' +
                ", fileName='" + fileName + '\'' +
                ", Transaction=" + transaction +
                '}';
    }
}
