package com.example.i7.jobbalagom.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.DataHolder;

import java.io.IOException;
import java.util.ArrayList;


public class ChangeTaxActivity extends AppCompatActivity {
    private AutoCompleteTextView textViewKommun;
    private ArrayList<String> kommuner;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller  = (Controller) DataHolder.getInstance().getData();
        setContentView(R.layout.activity_change_tax);
        textViewKommun = (AutoCompleteTextView) findViewById(R.id.autoCompleteKommun);
        controller.testing();
    /*
        textViewKommun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controller.getKommun();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        */
    }

    public void setKommun(String kommun){
        //kommuner = kommun.split(" ");
    }

}