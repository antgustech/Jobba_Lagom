package com.example.i7.jobbalagom.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.i7.jobbalagom.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;


/**
 * Created by Anton on 2016-04-25.
 */


public class MainBarFragment  extends Fragment{
    private BarChart mainChart;
    private BarData data;
    private BarDataSet dataset;
    private ArrayList<String> labels;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_bar_fragment, container, false);
        mainChart = (BarChart) view.findViewById(R.id.mainChart);
        //create data points
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(6, 0));
        dataset = new BarDataSet(entries, "Intjänade pengar");
        dataset.setColor(getResources().getColor(R.color.colorPrimary));

        //create labels
        labels = new ArrayList<String>();
        labels.add("60 000");
        data = new BarData(labels, dataset);
        mainChart.setData(data); // set the data and list of lables into chart

        //description
        mainChart.setDescription("");  // set the description
        mainChart.setScaleYEnabled(false);
        mainChart.setTouchEnabled(false);
       // setData();
        return view;

    }

    public void setData(){
        dataset.removeFirst();
        //dataset.addEntry(new BarEntry(9f,2));

        data.addEntry(new BarEntry(9f,2), 0);
        mainChart.setData(data);
        dataset.setColor(getResources().getColor(R.color.half_black));
    }




}
