package com.dsinv.abac.entity;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProactiveTransactionRule extends AbstractBaseEntity {

    private ProactiveTransaction proactiveTransaction;
    private Rule rule;

    public ProactiveTransaction getProactiveTransaction() {
        return proactiveTransaction;
    }

    public void setProactiveTransaction(ProactiveTransaction proactiveTransaction) {
        this.proactiveTransaction = proactiveTransaction;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "ProactiveTransactionRule{" +
                "proactiveTransaction=" + proactiveTransaction +
                ", rule=" + rule +
                '}';
    }
}
