package com.dsinv.abac.entity;

/**
 * Created with IntelliJ IDEA.
 * User: habib
 * Date: 4/24/14
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class policyBean {
    private String subjectName;
    private Integer marks;
    private String subjectName2;
    private Integer marks2;

    public policyBean(String subjectName, Integer marks, String subjectName2, Integer marks2) {
        this.subjectName = subjectName;
        this.marks = marks;
        this.subjectName2 = subjectName2;
        this.marks2 = marks2;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public String getSubjectName2() {
        return subjectName2;
    }

    public void setSubjectName2(String subjectName2) {
        this.subjectName2 = subjectName2;
    }

    public Integer getMarks2() {
        return marks2;
    }

    public void setMarks2(Integer marks2) {
        this.marks2 = marks2;
    }
}