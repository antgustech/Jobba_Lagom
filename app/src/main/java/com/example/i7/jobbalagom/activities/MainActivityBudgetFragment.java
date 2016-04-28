package com.example.i7.jobbalagom.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.i7.jobbalagom.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by Morgan on 2016-04-25.
 */
public class MainActivityBudgetFragment extends Fragment{

    private HorizontalBarChart mainChart;
    private BarData data;
    private BarDataSet dataset1;
    private BarDataSet dataset2;

    private int expenseSum = 0;
    private int incomeSum = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_progress_budget_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainChart = (HorizontalBarChart) view.findViewById(R.id.mainBudgetChart);

        //create data points
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        entries1.add(new BarEntry(10000, 5));
        entries2.add(new BarEntry(10000, 5));

        dataset1 = new BarDataSet(entries1, "Inkomst");
        dataset2 = new BarDataSet(entries2, "Utgifter");

        //X-axis labels
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Inkomst"); xVals.add("Utgift");;

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(dataset1);
        dataSets.add(dataset2);

        //Add to chart
        data = new BarData(xVals, dataSets);
        mainChart.setData(data);

        //Description and animation
        mainChart.setDescription("");  // set the description
        mainChart.setScaleYEnabled(false);
        mainChart.setTouchEnabled(false);
        mainChart.animateY(2000);

        //setDataExpense(expenseSum);//<-----------------Example for testing.
        //setDataIncome(incomeSum);//<-----------------Example for testing.

        //setDataExpense(200);//<-----------------Example for testing.
        //setDataIncome(200);//<-----------------Example for testing.

    }

    @Override
    public void onResume() {
        updateDataExpense();
        updateDataIncome();
        super.onResume();
       // Log.d("ResumeTag", "Fragment Resumed");
       // Log.d("ResumeTag", "current Expense: " + expenseSum);
       // Log.d("ResumeTag","current income: " + incomeSum);
        //setDataExpense(100);
        //setDataIncome(100);
    }

    @Override
    public void onPause() {
        updateDataExpense();
        updateDataIncome();
        super.onPause();
    }

    public void updateDataExpense(){
        dataset2.removeEntry(1);
        data.addEntry(new BarEntry(expenseSum,1), 1);
        dataset2.setColor(getResources().getColor(R.color.orange));
        mainChart.notifyDataSetChanged(); // let the chart know it's data changed
        mainChart.invalidate(); // refresh
    }

    public void updateDataIncome(){
        dataset1.removeEntry(0);
        data.addEntry(new BarEntry(incomeSum,0), 0);
        dataset1.setColor(getResources().getColor(R.color.green));
        mainChart.notifyDataSetChanged(); // let the chart know it's data changed
        mainChart.invalidate(); // refresh
    }

    public void setDataExpense(int sum){
        expenseSum = (expenseSum + sum);
        Log.d("ResumeTag", "expense set at " + expenseSum);
    }

    public void setDataIncome(int sum){
        incomeSum = (incomeSum + sum);
        Log.d("ResumeTag", "income set at " + incomeSum);
    }

    public int getDataExpense(){
        return expenseSum;
    }

    public int getDataIncome(){
        return incomeSum;
    }
}