package com.example.i7.jobbalagom.local;

import android.util.Log;

/**
 * Created by Anton Gustafsson on 2016-04-21.
 * Should get the tax and caluclate it with the hours.
 */
public class Calculator {
    private Controller controller;
     private float tax, startTime,endTime,res,pay,ob,obStart,obEnd;

    public Calculator(){
        controller  = Singleton.controller;
        //getPay();
        //getStart();
        //getEnd();
        //getTax();
        //calculateGraphData();

    }

    //receives wage like 33.2f
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

    //Revcieve tax like 33.4f
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

    //Result=hours*pay*tax
    //TODO May need a overlook
    public void calculateGraphData(){
        res = (((endTime-startTime) * pay) * (1-tax));
        Log.e("Calc", "RES:" + res);
    }
}