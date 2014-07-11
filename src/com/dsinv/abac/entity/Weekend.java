package com.dsinv.abac.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: amjad
 * Date: 12/26/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */


@Entity
@Table(name="weekend")
public class Weekend extends AbstractBaseEntity{

    @Column(name = "day_one")
    private String dayOne;

    @Column(name = "day_two")
    private String dayTwo;


    public String getDayOne() {
        return dayOne;
    }

    public void setDayOne(String dayOne) {
        this.dayOne = dayOne;
    }

    public String getDayTwo() {
        return dayTwo;
    }

    public void setDayTwo(String dayTwo) {
        this.dayTwo = dayTwo;
    }

    @Override
    public String toString() {
        return "Weekend{" +
                "dayOne='" + dayOne + '\'' +
                ", dayTwo='" + dayTwo + '\'' +
                '}';
    }
}
