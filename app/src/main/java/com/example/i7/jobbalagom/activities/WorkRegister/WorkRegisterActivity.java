package com.example.i7.jobbalagom.activities.WorkRegister;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.i7.jobbalagom.R;

public class WorkRegisterActivity extends AppCompatActivity {

    //private EditText dateText;
    //private Intent dateSelecter;
   // private String dateText_String;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainTimeRegisterFragment mainTimeRegisterFragment;
    private DateRegisterFragment dateRegisterFragment;

    private MainTimeRegisterCallback mainTimeRegisteCallback;
    private DateRegisterCallback dateRegisterCallback;

    private int state = 0;

    public WorkRegisterActivity(){
        fragmentManager = getFragmentManager();
        mainTimeRegisterFragment = new MainTimeRegisterFragment();
        dateRegisterFragment = new DateRegisterFragment();

        mainTimeRegisteCallback = new MainRegisterListener();
        mainTimeRegisterFragment.setCallback(mainTimeRegisteCallback);

        dateRegisterCallback = new DateRegisterListener();
        dateRegisterFragment.setCallback(dateRegisterCallback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_work_register);

        setStatusbarColor();
        setupActionBar();
        setUpStartFragment();
        changeFragment(mainTimeRegisterFragment);

       // dateText_String = "ayo";


        //fragmentTransaction.replace()

       // fragmentTransaction.show(dateRegisterFragment);

        //fragmentTransaction.addToBackStack("main");

        //fragmentTransaction.commit();




        //dateText = (EditText) findViewById((R.id.dateText));

        //dateSelecter = new Intent(getApplicationContext(), DateChooser.class);
        //dateSelecter.putExtra("dateText_String", dateText_String);
        //startActivity(dateSelecter);

        //dateText.setText(dateText_String);
      //  new DateUpdater().start();
        //dateSelecter.onAttachedToWindow();
    }

    private void setUpStartFragment(){

    }

    private void changeFragment(Fragment fragment){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private void setStatusbarColor(){
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

    private class MainRegisterListener implements MainTimeRegisterCallback {

        @Override
        public void buttonPressed(View view) {
            Log.d("ButtonTag", "Ay lmao");
            changeFragment(dateRegisterFragment);
        }
    }

    private class DateRegisterListener implements DateRegisterCallback{
        @Override
        public void UpdateRegisteredDate(int year, int month, int day) {
            mainTimeRegisterFragment.setTextView(year + "-" + month + "-" + day);
            Log.d("DateTag",year + "-" + month + "-" + day);
            changeFragment(mainTimeRegisterFragment);
        }
    }


  //  private class DateUpdater extends Thread {
    //    public void run(){
      //      while(true)
        //    dateText.setText(dateText_String);
        //}
    //}

}
