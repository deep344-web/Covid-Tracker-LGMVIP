package com.example.covid_19tracker;

import java.util.ArrayList;

public class CovidDetails {
    private String stateName;
    private String stateCode;

    private ArrayList<String> cities;



    public CovidDetails(String stateName, ArrayList<String> cities, String stateCode){
        this.stateName = stateName;
        this.cities = cities;
        this.stateCode = stateCode;
    }





    public String getStateName() {
        return stateName;
    }

    public String getStateCode() {
        return stateCode;
    }


    public ArrayList<String> getCities() {
        return cities;
    }
}
