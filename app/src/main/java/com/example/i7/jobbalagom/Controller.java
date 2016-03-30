package com.example.i7.jobbalagom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Strandberg95 on 2016-03-21.
 */
public class Controller  {

    //private CSNActivity viewer;
    private Calculator calc;
    private Socket socket;

    public Controller(){
        //viewer = new CSNActivity();
        calc = new Calculator();

    }

    public float calculatePercentage(float earned){
        return calc.calculatePercentage(earned);
    }

}
