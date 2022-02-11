package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    String stateName, cityName;

    TextView active, confirmed, recovered, deceased, heading;
    String activeC, confirmedC, recoveredC, deceasedC;

    private final String url = "https://data.covid19india.org/state_district_wise.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

      //  getSupportActionBar().hide();

        active = findViewById(R.id.activeCases_TextView);
        confirmed = findViewById(R.id.confirmedCases_TextView);
        recovered = findViewById(R.id.recoveredCases_TextView);
        deceased = findViewById(R.id.deceasedCases_TextView);
        heading = findViewById(R.id.headingTextView);

        Intent intent = getIntent();
        if (intent.hasExtra("Statename")){
            stateName = intent.getStringExtra("Statename");
        }

        if (intent.hasExtra("Cityname")){
            cityName = intent.getStringExtra("Cityname");
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url , null,
                new ResponseListener(), new ErrorListener());

        queue.add(request);

    }

    private class ResponseListener implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONObject stateObject = response.getJSONObject(stateName);
                JSONObject city = stateObject.getJSONObject("districtData").getJSONObject(cityName);

                activeC = city.getString("active");
                recoveredC = city.getString("recovered");
                deceasedC = city.getString("deceased");
                confirmedC = city.getString("confirmed");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            showData();

        }


    }

    private class ErrorListener implements Response.ErrorListener{
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
    private void showData(){
        heading.setText(cityName + ", " + stateName);
        active.setText(activeC);
        confirmed.setText(confirmedC);
        recovered.setText(confirmedC);
        deceased.setText(deceasedC);
    }
}