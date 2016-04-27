package com.example.i7.jobbalagom.activities;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.i7.jobbalagom.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Morgan on 2016-04-25.
 */
public class MainActivityBudgetFragment extends Fragment{

    private HorizontalBarChart mainChart;
    private BarData data;
    private BarDataSet dataset;
    private ArrayList<String> labels;

    private ArrayList<BarEntry> valueSet1 = new ArrayList<>();
    private ArrayList<BarEntry> valueSet2 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.activity_main_progress_budget_fragment, container, false);


/*


        //create labels

        //description
        mainChart.setDescription("");  // set the description
        mainChart.setScaleYEnabled(false);
        mainChart.setTouchEnabled(false);

        dataset.removeEntry(0);
        setData(55350);//<-----------------Example for testing.
*/

    }
    /*
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;


        BarEntry v1e1 = new BarEntry(0, 0); // Budget
        valueSet1.add(v1e1);

        BarEntry v2e1 = new BarEntry(0, 0); // Budget
        valueSet2.add(v2e1);
       // valueSet2.add(new BarEntry(100, 0));

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Inkomst");
        barDataSet1.setColor(Color.rgb(0, 255, 0));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Utgifter");
        barDataSet2.setColor(Color.rgb(255, 0, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
       // setData(200);
        return dataSets;
    }
*/
    public void updateGraph(){
        float[] bla = {0f,20f};
        valueSet1.get(0).setVals(bla);

    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Budget");
        return xAxis;
    }

    public void setData(int sum){
        dataset.removeEntry(0);
        data.addEntry(new BarEntry(sum,0), 0);
        //dataset.setColor(getResources().getColor(R.color.colorPrimary));
        mainChart.notifyDataSetChanged(); // let the chart know it's data changed
        mainChart.invalidate(); // refresh
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        mainChart = (HorizontalBarChart) view.findViewById(R.id.mainBudgetChart);
        BarData data = new BarData(getXAxisValues(), getDataSet());
        mainChart.setData(data);
        mainChart.setDescription("");
        mainChart.animateXY(2000, 2000);
        mainChart.setPinchZoom(false);
        mainChart.setScaleYEnabled(false);
        mainChart.setTouchEnabled(false);
        mainChart.notifyDataSetChanged();
       // setData(100);
        setData(20);
        mainChart.invalidate();

       // updateGraph();
*/
        mainChart = (HorizontalBarChart) view.findViewById(R.id.mainBudgetChart);
        //create data points
        ArrayList<BarEntry> entries1 = new ArrayList<>();

        entries1.add(new BarEntry(0, 60000));
        //entries1.add(new BarEntry(0,100));
        dataset = new BarDataSet(entries1, "Inkomst");

        //create labels
        labels = new ArrayList<String>();
        labels.add("inkomst");
        //labels.add("utgifter");
        data = new BarData(labels, dataset);
        mainChart.setData(data); // set the data and list of lables into chart

        //description
        mainChart.setDescription("");  // set the description
        mainChart.setScaleYEnabled(false);
        mainChart.setTouchEnabled(false);
        mainChart.animateY(2000);

        setData(100);//<-----------------Example for testing.
    }
}
