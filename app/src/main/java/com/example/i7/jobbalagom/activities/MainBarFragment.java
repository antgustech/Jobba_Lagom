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
        entries.add(new BarEntry(60000, 10));
        dataset = new BarDataSet(entries, "Intjänade pengar");

        //create labels
        labels = new ArrayList<String>();
        labels.add(" ");
        data = new BarData(labels, dataset);
        mainChart.setData(data); // set the data and list of lables into chart

        //description
        mainChart.setDescription("");  // set the description
        mainChart.setScaleYEnabled(false);
        mainChart.setTouchEnabled(false);
        mainChart.animateY(2000);

        setData(35233);//<-----------------Example for testing.
        return view;
    }
    //Updates the chart with specified sum.
    public void setData(int sum){
        dataset.removeEntry(0);
        data.addEntry(new BarEntry(sum,0), 0);
        dataset.setColor(getResources().getColor(R.color.colorPrimary));
        mainChart.notifyDataSetChanged(); // let the chart know it's data changed
        mainChart.invalidate(); // refresh
    }
}
