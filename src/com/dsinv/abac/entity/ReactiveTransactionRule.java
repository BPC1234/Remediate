package com.dsinv.abac.entity;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReactiveTransactionRule  {

    private  long id;
    private Rule rule;
    private  ReactiveTransaction reactiveTransaction;

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

    public void setReactiveTransaction( ReactiveTransaction reactiveTransaction){
        this.reactiveTransaction = reactiveTransaction;
    }

    public ReactiveTransaction getReactiveTransaction() {
        return this.reactiveTransaction;
    }

    @Override
    public String toString() {
        return "ReactiveTransactionRule{" +
                "id=" + id +
                ", rule=" + rule +
                ", reactiveTransaction=" + reactiveTransaction +
                '}';
    }
}
