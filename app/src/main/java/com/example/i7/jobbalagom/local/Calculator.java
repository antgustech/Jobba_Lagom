package com.example.i7.jobbalagom.local;

import android.util.Log;

/**
 * Created by Anton Gustafsson on 2016-04-21.
 * Should get the tax and caluclate it with the hours.
 */
public class Calculator {
    private Controller controller;
    private float tax;
    private float startTime;
    private float endTime;
    private float res;
    private float pay;


    public Calculator(){
        controller  = Singleton.controller;
        //getPay();
        //getStart();
        //getEnd();
        // getTax();
        //  calculateGraphData();

    }

    //receives tax like 33.2f
    private void getPay(){
        pay = controller.getJobPay();
    }

    //recive time like 0900f
    private void getStart(){
        startTime = controller.getStartTime();
    }

    //recive time like 1700f
    private void getEnd() {
        endTime = controller.getEndTime();
    }

    private void getTax(){
        tax = controller.getTax();
    }

    //Result=hours*pay*tax
    //TODO May need a overlook
    public void calculateGraphData(){
        res = (((endTime-startTime) * pay) * (1-tax));
        Log.e("Calc", "RES:" + res);
    }



}