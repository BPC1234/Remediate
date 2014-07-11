package com.dsinv.abac.bean;

import java.util.ArrayList;
import java.util.List;
import com.dsinv.abac.bean.Cell;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 11/29/13
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class JasonBean {

    private int page;
    private int total;
    private List<Cell> rows = new ArrayList<Cell>();
    private List dbColumnHeader;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Cell> getRows() {
        return rows;
    }

    public void setRows(List<Cell> rows) {
        this.rows = rows;
    }

    public List getDbColumnHeader() {
        return dbColumnHeader;
    }

    public void setDbColumnHeader(List dbColumnHeader) {
        this.dbColumnHeader = dbColumnHeader;
    }

    @Override
    public String toString() {
        return "JasonBean{" +
                "page=" + page +
                ", total=" + total +
                ", rows=" + rows +
                ", dbColumnHeader=" + dbColumnHeader +
                '}';
    }
}
