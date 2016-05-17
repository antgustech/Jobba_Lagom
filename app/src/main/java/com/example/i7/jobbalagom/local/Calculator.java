package com.example.i7.jobbalagom.local;

import android.util.Log;

/**
 * Created by Anton Gustafsson on 2016-04-21.
 * Should get the tax and caluclate it with the hours.
 * TODO Fix the calculation so it returns the correct wage and associated ob.
 */
public class Calculator {
    private Controller controller;
     private float tax, startTime,endTime,res,pay,ob,obStart,obEnd;

    public Calculator(){
        controller  = Singleton.controller;
        //getWage();
        //getStart();
        //getEnd();
        //getTaxFromServer();
        //calculateGraphData();
        //
    }
    private void getWage(String jobTitle){
        pay = controller.getWage(jobTitle);
    }
    private void getStart(){
        startTime = controller.getStartTime();
    }
    private void getEnd() {
        endTime = controller.getEndTime();
    }
    private void getTax(){
        tax = controller.getTax();
    }
    private void getOB(){
        ob = controller.getOB();
    }
    private void getOBStart(){
        obStart= controller.getOBStart();
    }
    private void getOBEnd(){
        obEnd = controller.getOBEnd();
    }

    //TODO May need an overlook
    public void calculateGraphData(){
        res = (((endTime-startTime) * pay) * (1-tax));
        Log.e("Calc", "RES:" + res);
    }
}