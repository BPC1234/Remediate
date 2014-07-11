package com.dsinv.abac.entity;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ProactiveBlock {

    CPI_SCORE("CPI Score"),
    REVENUES("Revenues"),
    SALES_MODEL_RELATIONSHIPS("Sales Model Relationships"),
    NATURE_OF_BUSINESS_OPERATIONS("Nature Of Business Operations"),
    GOVERNMENT_INTERACTION("Government Interactions");


    private String value;

    private ProactiveBlock(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ProactiveBlock[] getProactiveBlocks() {
        return values();
    }

}
