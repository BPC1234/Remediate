package com.dsinv.abac.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;

import com.dsinv.abac.util.Utils;


/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */

@MappedSuperclass
public abstract class AbstractBaseEntity implements Lifecycle{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Override
	public boolean onSave(Session session) throws CallbackException{
        setCreated(new Date());
        setCreatedBy(Utils.getLoggedUserName());
        return false;
    }

    @Override
	public boolean onUpdate(Session session)throws CallbackException {
        setModified(new Date());
        setModifiedBy(Utils.getLoggedUserName());
        return false;
    }

    @Override
	public boolean onDelete(Session session) throws CallbackException {
        return false;
    }

    @Override
	public void onLoad(Session session, Serializable serializable) {

    }

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

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
