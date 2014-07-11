package com.dsinv.abac.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Role {

    ADMIN( 1, "Admin", "ROLE_ADMIN")
    , LEGAL( 2, "Legal", "LEGAL")
    , COMPLIANCE( 3, "Compliance", "COMPLIANCE")
    , IA_ANALYST( 4, "IA Analyst", "IA_ANALYST")
    , IA_MANAGER( 5, "IA Manager", "IA_MANAGER")
    , EMPLOYEE( 6, "Employee", "EMPLOYEE");


    private  String label;
    private String value;
    private int code;

    private Role(int code, String value, String label) {
        this.code = code;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static String getLabelForValue(String value) {
        for (Role type : values()) {
            if (type.getValue().equals(value)) {
                return type.getLabel();
            }
        }
        return value;
    }
/*
    public static Role[] getRoles() {
        return values();
    }*/
    public static List getRoles() {
        List<Role> rules = new ArrayList();
        rules.add(ADMIN);
        rules.add(LEGAL);
        rules.add(COMPLIANCE);
        rules.add(IA_ANALYST);
        rules.add(IA_MANAGER);
        rules.add(EMPLOYEE);
        return rules;
    }
}