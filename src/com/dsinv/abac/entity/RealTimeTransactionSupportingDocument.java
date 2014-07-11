package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Habib
 * Date: 20/8/13
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="real_time_transaction_supporting_document")
public class RealTimeTransactionSupportingDocument {

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
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="real_time_transaction_id")
    private  RealTimeTransaction realTimeTransaction;

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

    public RealTimeTransaction getRealTimeTransaction() {
        return realTimeTransaction;
    }

    public void setRealTimeTransaction(RealTimeTransaction realTimeTransaction) {
        this.realTimeTransaction = realTimeTransaction;
    }

    @Override
    public String toString() {
        return "ReactiveTransactionSupportingDocument{" +
                "author='" + author + '\'' +
                ", entryTime='" + entryTime + '\'' +
                ", comment='" + comment + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", fileIconLocation='" + fileIconLocation + '\'' +
                ", fileName='" + fileName + '\'' +
                ", realTimeTransaction=" + realTimeTransaction +
                '}';
    }
}
