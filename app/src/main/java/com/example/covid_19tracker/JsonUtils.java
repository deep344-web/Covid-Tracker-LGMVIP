package com.example.covid_19tracker;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class JsonUtils {

    public static ArrayList<CovidDetails> getStateData(JSONObject response){
        ArrayList<CovidDetails> stateData = new ArrayList<>();

        for(Iterator<String> iter = response.keys(); iter.hasNext();) {
            String state = iter.next();
            ArrayList<String> cities = new ArrayList<>();
            if(state.equals("State Unassigned"))
                continue;

            String stateCode;

            try {
                JSONObject stateObject = response.getJSONObject(state);
                JSONObject citiesObject = stateObject.getJSONObject("districtData");


                stateCode = stateObject.getString("statecode");

                for(Iterator<String> itr2 = citiesObject.keys();itr2.hasNext();) {
                    String city = itr2.next();
                    if(city.equals("Unknown"))
                        continue;

                    Log.i("Data", city);
                    cities.add(city);
                }

                CovidDetails covidDetails = new CovidDetails(state, cities, stateCode);
                stateData.add(covidDetails);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return stateData;

    }



}
