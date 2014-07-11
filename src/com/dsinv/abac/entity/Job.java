package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 1/2/14
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "job_id")
    private int jobId;
    @Column(name = "date")
    private Date created;
    @Column(name = "created_by ")
    private String createdBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
