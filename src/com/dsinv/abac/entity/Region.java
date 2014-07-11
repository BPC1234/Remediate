package com.dsinv.abac.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */


@Entity
@Table(name="region")
public class Region extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="weekend_id")
    private Weekend weekend;

    @Transient
    private String weightedScore;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Weekend getWeekend() {
        return weekend;
    }

    public void setWeekend(Weekend weekend) {
        this.weekend = weekend;
    }

// non db properties

    public String getWeightedScore() {
        return weightedScore;
    }

    public void setWeightedScore(String weightedScore) {
        this.weightedScore = weightedScore;
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", weekend=" + weekend +
                ", weightedScore='" + weightedScore + '\'' +
                '}';
    }
}
