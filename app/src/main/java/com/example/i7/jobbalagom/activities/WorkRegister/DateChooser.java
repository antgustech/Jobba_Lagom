package com.example.i7.jobbalagom.activities.WorkRegister;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;

import com.example.i7.jobbalagom.R;

import android.util.Log;

public class DateChooser extends AppCompatActivity {

    //private
    private String dateText_String;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intentExtras = getIntent();
        //Bundle extrasBundle = intentExtras.getExtras();

        dateText_String = intentExtras.getStringExtra("dateText_String");
        dateText_String = "Ay lmao";
        datePicker = (DatePicker)findViewById(R.id.datePicker);
       // onAttachedToWindow();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                this.getWindow().setLayout(900, 755);
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                this.getWindow().setLayout(1080, 1000); //width x height
                break;
        }
    }

    private class ButtonListener implements View.OnClickListener{

        public void onClick(View v) {
            if(v.getId() == R.id.buttonDone){
                dateText_String = datePicker.toString();
                Log.d("myTag", dateText_String);
                System.out.println("hewo");
            }
        }
    }
}
