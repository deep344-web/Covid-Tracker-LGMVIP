package com.example.covid_19tracker;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListViewAdapter  extends ArrayAdapter<CovidDetails> {

    ArrayList<CovidDetails> arrayList;

    public ListViewAdapter(Activity context, ArrayList<CovidDetails> arrayList){
        super(context, 0, arrayList);
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listview  = convertView;
        if(listview == null){
            listview = LayoutInflater.from(getContext()).inflate(R.layout.item_view, parent, false);
        }

        CovidDetails stateData = arrayList.get(position);

        Log.i("data1", String.valueOf(stateData.equals(null)));

        TextView stateNameTextView = listview.findViewById(R.id.stateName_TextView);
        TextView stateCodeTextView = listview.findViewById(R.id.stateCode_TextView);


        stateNameTextView.setText(stateData.getStateName());
        stateCodeTextView.setText(stateData.getStateCode());

        return listview;
    }

    public void updateReceiptsList(ArrayList<CovidDetails> newlist) {
        arrayList.clear();
        arrayList.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
