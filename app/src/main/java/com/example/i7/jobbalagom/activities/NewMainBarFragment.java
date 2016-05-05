package com.example.i7.jobbalagom.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.i7.jobbalagom.R;
import com.numetriclabz.numandroidcharts.BarChart;
import com.numetriclabz.numandroidcharts.ChartData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Gustafsson on 2016-05-05.
 * Handles new main bar
 */
public class NewMainBarFragment extends Fragment {
    private BarChart mainBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_main_bar_fragment, container, false);
        mainBar = (BarChart) view.findViewById(R.id.newBarchart);

        List<ChartData> value = new ArrayList<>();
        value.add(new ChartData(60000f, "")); //values.add(new ChartData(y,x));

        mainBar.setData(value);
        mainBar.setDescription("Fribelopp");

        setBarData(2500f);


        return view;
    }

    public void setBarData(float newData){
        List<ChartData> value = new ArrayList<>();
        value.add(new ChartData(newData, "")   ); //values.add(new ChartData(y,x));
        mainBar.setData(value);
    }


}
