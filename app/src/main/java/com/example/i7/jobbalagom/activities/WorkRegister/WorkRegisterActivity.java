package com.example.i7.jobbalagom.activities.WorkRegister;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.EditText;

import com.example.i7.jobbalagom.R;

public class WorkRegisterActivity extends AppCompatActivity {

    private EditText dateText;
    private Intent dateSelecter;
    private String dateText_String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_register);
        setStatusbarColor();
        setupActionBar();
        dateText_String = "ayo";
        dateText = (EditText) findViewById((R.id.dateText));
        dateSelecter = new Intent(getApplicationContext(), DateChooser.class);
        dateSelecter.putExtra("dateText_String", dateText_String);
        startActivity(dateSelecter);

        //dateText.setText(dateText_String);
      //  new DateUpdater().start();
        //dateSelecter.onAttachedToWindow();
    }


    private  void setStatusbarColor(){
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

  //  private class DateUpdater extends Thread {
    //    public void run(){
      //      while(true)
        //    dateText.setText(dateText_String);
        //}
    //}

}
