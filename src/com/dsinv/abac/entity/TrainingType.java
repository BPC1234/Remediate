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
public enum TrainingType {

    HUMAN_RESOURCES("Human Resources", "HUMAN_RESOURCES") ,LEGAL("Legal", "LEGAL"),FINANCE("Finance", "FINANCE");


    private  String label;
    private String value;

    private TrainingType(String value, String label) {
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
        for (TrainingType type : values()) {
            if (type.getValue().equals(value)) {
                return type.getLabel();
            }
        }
        return value;
    }

    public static List getTrainingTypes() {
        List policyTypes = new ArrayList();
        policyTypes.add(FINANCE);
        policyTypes.add(LEGAL);
        policyTypes.add(HUMAN_RESOURCES);
        return policyTypes;
    }
}