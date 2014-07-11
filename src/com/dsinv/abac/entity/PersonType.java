package com.dsinv.abac.entity;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PersonType {

    EMPLOYEE("Employee", "EMPLOYEE"), VENDOR("Vendor", "VENDOR"),CUSTOMER("Customer", "CUSTOMER"),ADMIN("Admin", "ADMIN");


    private  String label;
    private String value;

    private PersonType(String value, String label) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getLabelForValue(String value) {
        for (PersonType type : values()) {
            if (type.getValue().equals(value)) {
                return type.getLabel();
            }
        }
        return value;
    }

    public static PersonType[] getPersonTypes() {
        return values();
    }
}