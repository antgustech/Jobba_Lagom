package com.example.i7.jobbalagom.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.local.Controller;

import java.io.IOException;
import java.util.ArrayList;


public class ChangeTaxActivity extends AppCompatActivity implements  Runnable {
    private AutoCompleteTextView textViewKommun;
    private ArrayList<String> kommuner;
    private Controller controller = new Controller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_tax);
        textViewKommun = (AutoCompleteTextView) findViewById(R.id.autoCompleteKommun);
        Thread thread = new Thread(new ChangeTaxActivity());
        thread.start();

        textViewKommun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //Do something
            }
        });
    }
    public void setKommun(String kommun){
        //kommuner = kommun.split(" ");
    }


    @Override
    public void run() {
        try {
            controller.getKommun();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kommuner);
        textViewKommun.setAdapter(adapter);
    }
}
