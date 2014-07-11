package com.dsinv.abac.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="proactive_block_weight_ratio")
public class ProactiveBlockWeightRatio extends AbstractBaseEntity {

    @Column(name = "proactive_block")
    private String proactiveBlock;
    @Column(name = "ratio")
    private double ratio;


    /* non db properties*/
    @Transient
    private double cpiScore;
    @Transient
    private double revenues;
    @Transient
    private double salesModelRelationships;
    @Transient
    private double natureOfBusinessOperations;
    @Transient
    private double governmentInteraction;


    public String getProactiveBlock() {
        return proactiveBlock;
    }

    public void setProactiveBlock(String proactiveBlock) {
        this.proactiveBlock = proactiveBlock;
    }

    public double getRatio() {

        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }


   /* non db properties*/

    public double getCpiScore() {
        return cpiScore;
    }

    public void setCpiScore(double cpiScore) {
        this.cpiScore = cpiScore;
    }

    public double getRevenues() {
        return revenues;
    }

    public void setRevenues(double revenues) {
        this.revenues = revenues;
    }

    public double getSalesModelRelationships() {
        return salesModelRelationships;
    }

    public void setSalesModelRelationships(double salesModelRelationships) {
        this.salesModelRelationships = salesModelRelationships;
    }

    public double getNatureOfBusinessOperations() {
        return natureOfBusinessOperations;
    }

    public void setNatureOfBusinessOperations(double natureOfBusinessOperations) {
        this.natureOfBusinessOperations = natureOfBusinessOperations;
    }

    public double getGovernmentInteraction() {
        return governmentInteraction;
    }

    public void setGovernmentInteraction(double governmentInteraction) {
        this.governmentInteraction = governmentInteraction;
    }


    @Override
    public String toString() {
        return "ProactiveBlockWeightRatio{" +
                "proactiveBlock='" + proactiveBlock + '\'' +
                ", ratio=" + ratio +
                ", cpiScore=" + cpiScore +
                ", revenues=" + revenues +
                ", salesModelRelationships=" + salesModelRelationships +
                ", natureOfBusinessOperations=" + natureOfBusinessOperations +
                ", governmentInteraction=" + governmentInteraction +
                '}';
    }
}
