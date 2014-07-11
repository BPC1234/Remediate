package com.dsinv.abac.entity;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class RealTimeTransactionRule {

    private  long id;
    private Rule rule;
    private RealTimeTransaction realTimeTransaction;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public RealTimeTransaction getRealTimeTransaction() {
        return realTimeTransaction;
    }

    public void setRealTimeTransaction(RealTimeTransaction realTimeTransaction) {
        this.realTimeTransaction = realTimeTransaction;
    }

    @Override
    public String toString() {
        return "RealTimeTransactionRule{" +
                "id=" + id +
                ", rule=" + rule +
                ", realTimeTransaction=" + realTimeTransaction +
                '}';
    }
}
