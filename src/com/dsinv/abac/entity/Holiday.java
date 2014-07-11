package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 12/19/13
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="holiday")
public class Holiday extends AbstractBaseEntity{

    @Column(name = "description")
    private String description;
   @Column(name = "holiday_date")
    private Date holidayDate;
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="region_id")
    private Region region;

    @Transient
    private Weekend weekend;

    @Transient
    private String dateStr;

    @Transient
    private String editButtonHtml;

    @Transient
    private String deleteButtonHtml;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Weekend getWeekend() {
        return weekend;
    }

    public void setWeekend(Weekend weekend) {
        this.weekend = weekend;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getEditButtonHtml() {
        return editButtonHtml;
    }

    public void setEditButtonHtml(String editButtonHtml) {
        this.editButtonHtml = editButtonHtml;
    }

    public String getDeleteButtonHtml() {
        return deleteButtonHtml;
    }

    public void setDeleteButtonHtml(String deleteButtonHtml) {
        this.deleteButtonHtml = deleteButtonHtml;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "description='" + description + '\'' +
                ", holidayDate=" + holidayDate +
                ", region=" + region +
                ", weekend=" + weekend +
                ", dateStr='" + dateStr + '\'' +
                '}';
    }
}
