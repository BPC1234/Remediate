package com.dsinv.abac.entity;

import com.dsinv.abac.entity.*;
import com.dsinv.abac.util.Constants;
import com.dsinv.abac.util.Utils;

import org.hibernate.annotations.NotFound;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="proactive_project")
public class ProactiveProject extends AbstractBaseEntity {



    @Column(name = "transaction_type")
    private String transactionType;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="region_id")
    private Region region;

    @Column(name = "reviewer_level")
    private int reviewerLevel;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;
    //private int tempRoleColumn;

    @Transient
    private String regionName;
    @Transient
    private String dateRange;
    @Transient
    private String author;
    @Transient
    private String createdDate;

    public  Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getReviewerLevel() {
        return reviewerLevel;
    }

    public void setReviewerLevel(int reviewerLevel) {
        this.reviewerLevel = reviewerLevel;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getDateBetween(){
    	return Utils.getStringFromDate(Constants.MONTH_DAY_YEAR, this.fromDate) + " To " + Utils.getStringFromDate(Constants.MONTH_DAY_YEAR, this.toDate);
    }
    
    public String getRegionText(){
    	if(this.region != null)
    		return this.region.getName();
    	else return "";
    }
    
    public String getCreatedText() {
        return Utils.getStringFromDate(Constants.MONTH_DAY_YEAR, super.getCreated());
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ProactiveProject{" +
                "region=" + region +
                ", transactionType='" + transactionType + '\'' +
                ", reviewerLevel=" + reviewerLevel +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
