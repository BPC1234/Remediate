package com.dsinv.abac.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="real_time_interval")
public class RealTimeInterval extends AbstractBaseEntity {

    @Column(name = "execute_time")
    private int executeTime;

    @Column(name = "last_project_created")
    public Date lastProjectCreated;

    @Column(name = "assignment_size")
    public int assignmentSize;

    @Transient
    private MultipartFile importFile;

    public int getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(int executeTime) {
        this.executeTime = executeTime;
    }

    public Date getLastProjectCreated() {
        return lastProjectCreated;
    }

    public void setLastProjectCreated(Date lastProjectCreated) {
        this.lastProjectCreated = lastProjectCreated;
    }

    public int getAssignmentSize() {
        return assignmentSize;
    }

    public void setAssignmentSize(int assignmentSize) {
        this.assignmentSize = assignmentSize;
    }


    public MultipartFile getImportFile() {
        return importFile;
    }

    public void setImportFile(MultipartFile importFile) {
        this.importFile = importFile;
    }

    public String toString() {
        return "RealTimeInterval{" +
                "executeTime=" + executeTime +
                ", lastProjectCreated=" + lastProjectCreated +
                '}';
    }
}
