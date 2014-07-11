package com.dsinv.abac.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private static Map<String, List<Person>> data = new HashMap<String, List<Person>>();

    static{
        populateBS217RHData();
        populateBS18QTData();
    }

    public List<Person> getData(String postcode){
        return data.get(postcode.toUpperCase());
    }

    private static void populateBS217RHData(){
        List<Person> personList = new ArrayList<Person>();

        personList.add(new Person("Adam", "Davies", "18 Knowles Road Clevedon"));
        personList.add(new Person("David", "Smith", "185 Old Kent Road, Clevedon"));
        personList.add(new Person("Jane", "Adams", "216 Park Road, Clevedon"));
        personList.add(new Person("Sue", "Green", "207 Old Stree, Clevedon"));

        data.put("BS21 7RH", personList);
    }

    private static void populateBS18QTData(){
        List<Person> personList = new ArrayList<Person>();

        personList.add(new Person("Sarah", "Jones", "1354 Ashley Road, Bristol"));
        personList.add(new Person("Jayne", "Peters", "254 ALma Vale Road, Bristol"));
        personList.add(new Person("Peter", "Richards", "Flat 4, 567 Clifton Road, Bristol"));
        personList.add(new Person("Andrew", "Love", "324 Regent Street, Bristol"));

        data.put("BS1 8QT", personList);
    }
}