package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 7/16/13
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */


@Entity
@Table(name="proactive_block_weight")
public class ProactiveBlockWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "cpi_score")
    private double cpiScore;

    @Column(name = "revenues")
    private double revenues;

    @Column(name = "sales_model_relationships")
    private double salesModelRelationships;

    @Column(name = "nature_of_business_operations")
    private double natureOfBusinessOperations;

    @Column(name = "government_interaction")
    private double governmentInteraction;


    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="region_id")
    private Region region;

    @Column(name = "date")
    private int date;

    @Column(name = "ti_cpi_risk_index")
    private double tiCpiRiskIndex;

    @Column(name = "entity_or_rip_name")
    private String entityOrRipName;

    @Column(name = "annual_revenue")
    private double annualRevenue;

    @Column(name = "percentage_of_government_revenue")
    private String percentageOfGovRevenue;

    @Column(name = "sales_to_government_customers")
    private String salesToGovCustomers ;

    @Column(name = "sale_to_cpi_and_government_customers")
    private int saleToCpiAndGovCustomers;


    @Transient
    private String cpiScoreStr;
    @Transient
    private String revenuesStr;
    @Transient
    private String noOfCustomBrokers;
    @Transient
    private String noOfAgents;
    @Transient
    private String revenueAttributableToAgents;
    @Transient
    private String noOfGovtCustomers;
    @Transient
    private String dateLastFCPAAuditInvestigation;
    @Transient
    private String weightedScoreStr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getCpiScoreStr() {
        return cpiScoreStr;
    }

    public void setCpiScoreStr(String cpiScoreStr) {
        this.cpiScoreStr = cpiScoreStr;
    }

    public String getRevenuesStr() {
        return revenuesStr;
    }

    public void setRevenuesStr(String revenuesStr) {
        this.revenuesStr = revenuesStr;
    }

    public String getNoOfCustomBrokers() {
        return noOfCustomBrokers;
    }

    public void setNoOfCustomBrokers(String noOfCustomBrokers) {
        this.noOfCustomBrokers = noOfCustomBrokers;
    }

    public String getNoOfAgents() {
        return noOfAgents;
    }

    public void setNoOfAgents(String noOfAgents) {
        this.noOfAgents = noOfAgents;
    }

    public String getRevenueAttributableToAgents() {
        return revenueAttributableToAgents;
    }

    public void setRevenueAttributableToAgents(String revenueAttributableToAgents) {
        this.revenueAttributableToAgents = revenueAttributableToAgents;
    }

    public String getNoOfGovtCustomers() {
        return noOfGovtCustomers;
    }

    public void setNoOfGovtCustomers(String noOfGovtCustomers) {
        this.noOfGovtCustomers = noOfGovtCustomers;
    }

    public String getDateLastFCPAAuditInvestigation() {
        return dateLastFCPAAuditInvestigation;
    }

    public void setDateLastFCPAAuditInvestigation(String dateLastFCPAAuditInvestigation) {
        this.dateLastFCPAAuditInvestigation = dateLastFCPAAuditInvestigation;
    }

    public double getTiCpiRiskIndex() {
        return tiCpiRiskIndex;
    }

    public void setTiCpiRiskIndex(double tiCpiRiskIndex) {
        this.tiCpiRiskIndex = tiCpiRiskIndex;
    }

    public String getEntityOrRipName() {
        return entityOrRipName;
    }

    public void setEntityOrRipName(String entityOrRipName) {
        this.entityOrRipName = entityOrRipName;
    }

    public double getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(double annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public String getPercentageOfGovRevenue() {
        return percentageOfGovRevenue;
    }

    public void setPercentageOfGovRevenue(String percentageOfGovRevenue) {
        this.percentageOfGovRevenue = percentageOfGovRevenue;
    }

    public String getSalesToGovCustomers() {
        return salesToGovCustomers;
    }

    public void setSalesToGovCustomers(String salesToGovCustomers) {
        this.salesToGovCustomers = salesToGovCustomers;
    }

    public int getSaleToCpiAndGovCustomers() {
        return saleToCpiAndGovCustomers;
    }

    public void setSaleToCpiAndGovCustomers(int saleToCpiAndGovCustomers) {
        this.saleToCpiAndGovCustomers = saleToCpiAndGovCustomers;
    }

    public String getWeightedScoreStr() {
        return weightedScoreStr;
    }

    public void setWeightedScoreStr(String weightedScoreStr) {
        this.weightedScoreStr = weightedScoreStr;
    }

    @Override
    public String toString() {
        return "ProactiveBlockWeight{" +
                "id=" + id +
                ", cpiScore=" + cpiScore +
                ", revenues=" + revenues +
                ", salesModelRelationships=" + salesModelRelationships +
                ", natureOfBusinessOperations=" + natureOfBusinessOperations +
                ", governmentInteraction=" + governmentInteraction +
                ", region=" + region +
                ", date=" + date +
                ", tiCpiRiskIndex='" + tiCpiRiskIndex + '\'' +
                ", entityOrRipName='" + entityOrRipName + '\'' +
                ", annualRevenue='" + annualRevenue + '\'' +
                ", percentageOfGovRevenue='" + percentageOfGovRevenue + '\'' +
                ", salesToGovCustomers='" + salesToGovCustomers + '\'' +
                ", saleToCpiAndGovCustomers='" + saleToCpiAndGovCustomers + '\'' +
                ", cpiScoreStr='" + cpiScoreStr + '\'' +
                ", revenuesStr='" + revenuesStr + '\'' +
                ", noOfCustomBrokers='" + noOfCustomBrokers + '\'' +
                ", noOfAgents='" + noOfAgents + '\'' +
                ", revenueAttributableToAgents='" + revenueAttributableToAgents + '\'' +
                ", noOfGovtCustomers='" + noOfGovtCustomers + '\'' +
                ", dateLastFCPAAuditInvestigation='" + dateLastFCPAAuditInvestigation + '\'' +
                '}';
    }
}
