package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 12/17/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="execution_activity")

public class ExecutionActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "execution_time")
    private Date executionTime;

    @Column(name = "comment")
    private String comment;

    @Column(name = "job_Id")
    private int jobId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "ExecutionActivity{" +
                "id=" + id +
                ", executionTime=" + executionTime +
                ", comment='" + comment +

                '}';
    }
}
