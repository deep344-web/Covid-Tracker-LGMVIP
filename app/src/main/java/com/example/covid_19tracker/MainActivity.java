package com.example.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.GpsStatus;
import android.os.Bundle;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

   ListView listview;

   ArrayList<CovidDetails> stateData = new ArrayList<>();
   private final String url = "https://data.covid19india.org/state_district_wise.json";

    ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listview = findViewById(R.id.listview);
        listViewAdapter = new ListViewAdapter(this, stateData);
        listview.setAdapter(listViewAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showCityList(position);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url , null,
                new ResponseListener(), new ErrorListener());

        queue.add(request);
        findViewById(R.id.SHOW_PROGRESS).setVisibility(View.VISIBLE);
    }


    private void showCityList(int position){
        CovidDetails state = stateData.get(position);
        ArrayList<String> cities = state.getCities();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Select your city");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.addAll(cities);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
            }
        });

        alertDialog.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String cityName = arrayAdapter.getItem(which);
                Toast.makeText(MainActivity.this, cityName + " selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Statename", state.getStateName());
                intent.putExtra("Cityname", cityName);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        alertDialog.show();
    }


    private class ResponseListener implements Response.Listener<JSONObject> {



        @Override
        public void onResponse(JSONObject response) {

            stateData = JsonUtils.getStateData(response);
            Log.i("hey", String.valueOf(stateData.size()));
            listViewAdapter.updateReceiptsList(stateData);
            findViewById(R.id.SHOW_PROGRESS).setVisibility(View.INVISIBLE);
        }
    }


    private class ErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }

}