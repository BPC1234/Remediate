package com.dsinv.abac.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 6/18/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="rule")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name="rule_title")
    private String ruleTitle;

    @Column(name="select_clause")
    private String selectClause;

    @Column(name="from_clause")
    private String fromClause;

    @Column(name="where_clause")
    private String whereClause;

    @Column(name="expected_result")
    private String expectedResult;

    @Column(name="associated_transaction_type")
    private String associatedTransactionType;

    @Column(name="is_active")
    private boolean isActive;

    @Column(name="rule_code")
    private String ruleCode;

    @Column(name="rule_explanation")
    private String ruleExplanation;



    @Transient
    private List columnNameList;

    @Transient
    private String tableName;

    @Transient
    private String columnName;
    @Transient
    private boolean isAggregateFnSelect;
    @Transient
    private String aggregateFnValue;
    @Transient
    private String groupBySelect;
    @Transient
    private String havingClauseSelect;
    @Transient
    private String havingAggregateFn;
    @Transient
    private String havingCondition;
    @Transient
    private String havingCompareValue;
    @Transient
    private String orderBySelect;
    @Transient
    private String tableNameWithColumnName;
    @Transient
    private String havingExtraCondition;
    @Transient
    private String whereClauseFirstSelectionValue;
    @Transient
    private String whereClauseSecondSelectionValue;
    @Transient
    private String whereClauseFirstTextValue;
    @Transient
    private String whereClauseSecondTextValue;
    @Transient
    private String whereFirstCondition;
    @Transient
    private String whereExtraCondition;
    @Transient
    private String whereColumnValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getAssociatedTransactionType() {
        return associatedTransactionType;
    }

    public void setAssociatedTransactionType(String associatedTransactionType) {
        this.associatedTransactionType = associatedTransactionType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List getColumnNameList() {
        return columnNameList;
    }

    public void setColumnNameList(List columnNameList) {
        this.columnNameList = columnNameList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isAggregateFnSelect() {
        return isAggregateFnSelect;
    }

    public void setAggregateFnSelect(boolean aggregateFnSelect) {
        isAggregateFnSelect = aggregateFnSelect;
    }

    public String getAggregateFnValue() {
        return aggregateFnValue;
    }

    public void setAggregateFnValue(String aggregateFnValue) {
        this.aggregateFnValue = aggregateFnValue;
    }

    public String getGroupBySelect() {
        return groupBySelect;
    }

    public void setGroupBySelect(String groupBySelect) {
        this.groupBySelect = groupBySelect;
    }

    public String getHavingClauseSelect() {
        return havingClauseSelect;
    }

    public void setHavingClauseSelect(String havingClauseSelect) {
        this.havingClauseSelect = havingClauseSelect;
    }

    public String getHavingAggregateFn() {
        return havingAggregateFn;
    }

    public void setHavingAggregateFn(String havingAggregateFn) {
        this.havingAggregateFn = havingAggregateFn;
    }

    public String getHavingCondition() {
        return havingCondition;
    }

    public void setHavingCondition(String havingCondition) {
        this.havingCondition = havingCondition;
    }

    public String getHavingCompareValue() {
        return havingCompareValue;
    }

    public void setHavingCompareValue(String havingCompareValue) {
        this.havingCompareValue = havingCompareValue;
    }

    public String getOrderBySelect() {
        return orderBySelect;
    }

    public void setOrderBySelect(String orderBySelect) {
        this.orderBySelect = orderBySelect;
    }

    public String getTableNameWithColumnName() {
        return tableNameWithColumnName;
    }

    public void setTableNameWithColumnName(String tableNameWithColumnName) {
        this.tableNameWithColumnName = tableNameWithColumnName;
    }

    public String getHavingExtraCondition() {
        return havingExtraCondition;
    }

    public void setHavingExtraCondition(String havingExtraCondition) {
        this.havingExtraCondition = havingExtraCondition;
    }

    public String getWhereClauseFirstSelectionValue() {
        return whereClauseFirstSelectionValue;
    }

    public void setWhereClauseFirstSelectionValue(String whereClauseFirstSelectionValue) {
        this.whereClauseFirstSelectionValue = whereClauseFirstSelectionValue;
    }

    public String getWhereClauseSecondSelectionValue() {
        return whereClauseSecondSelectionValue;
    }

    public void setWhereClauseSecondSelectionValue(String whereClauseSecondSelectionValue) {
        this.whereClauseSecondSelectionValue = whereClauseSecondSelectionValue;
    }

    public String getWhereClauseFirstTextValue() {
        return whereClauseFirstTextValue;
    }

    public void setWhereClauseFirstTextValue(String whereClauseFirstTextValue) {
        this.whereClauseFirstTextValue = whereClauseFirstTextValue;
    }

    public String getWhereClauseSecondTextValue() {
        return whereClauseSecondTextValue;
    }

    public void setWhereClauseSecondTextValue(String whereClauseSecondTextValue) {
        this.whereClauseSecondTextValue = whereClauseSecondTextValue;
    }

    public String getWhereFirstCondition() {
        return whereFirstCondition;
    }

    public void setWhereFirstCondition(String whereFirstCondition) {
        this.whereFirstCondition = whereFirstCondition;
    }

    public String getWhereExtraCondition() {
        return whereExtraCondition;
    }

    public void setWhereExtraCondition(String whereExtraCondition) {
        this.whereExtraCondition = whereExtraCondition;
    }

    public String getWhereColumnValue() {
        return whereColumnValue;
    }

    public void setWhereColumnValue(String whereColumnValue) {
        this.whereColumnValue = whereColumnValue;
    }

    public String getSelectClause() {
        return selectClause;
    }

    public void setSelectClause(String selectClause) {
        this.selectClause = selectClause;
    }

    public String getFromClause() {
        return fromClause;
    }

    public void setFromClause(String fromClause) {
        this.fromClause = fromClause;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleExplanation() {
        return ruleExplanation;
    }

    public void setRuleExplanation(String ruleExplanation) {
        this.ruleExplanation = ruleExplanation;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", ruleTitle='" + ruleTitle + '\'' +
                ", selectClause='" + selectClause + '\'' +
                ", fromClause='" + fromClause + '\'' +
                ", whereClause='" + whereClause + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", associatedTransactionType='" + associatedTransactionType + '\'' +
                ", isActive=" + isActive +
                ", ruleCode='" + ruleCode + '\'' +
                ", ruleExplanation='" + ruleExplanation + '\'' +
                ", columnNameList=" + columnNameList +
                ", tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", isAggregateFnSelect=" + isAggregateFnSelect +
                ", aggregateFnValue='" + aggregateFnValue + '\'' +
                ", groupBySelect='" + groupBySelect + '\'' +
                ", havingClauseSelect='" + havingClauseSelect + '\'' +
                ", havingAggregateFn='" + havingAggregateFn + '\'' +
                ", havingCondition='" + havingCondition + '\'' +
                ", havingCompareValue='" + havingCompareValue + '\'' +
                ", orderBySelect='" + orderBySelect + '\'' +
                ", tableNameWithColumnName='" + tableNameWithColumnName + '\'' +
                ", havingExtraCondition='" + havingExtraCondition + '\'' +
                ", whereClauseFirstSelectionValue='" + whereClauseFirstSelectionValue + '\'' +
                ", whereClauseSecondSelectionValue='" + whereClauseSecondSelectionValue + '\'' +
                ", whereClauseFirstTextValue='" + whereClauseFirstTextValue + '\'' +
                ", whereClauseSecondTextValue='" + whereClauseSecondTextValue + '\'' +
                ", whereFirstCondition='" + whereFirstCondition + '\'' +
                ", whereExtraCondition='" + whereExtraCondition + '\'' +
                ", whereColumnValue='" + whereColumnValue + '\'' +
                '}';
    }
}

