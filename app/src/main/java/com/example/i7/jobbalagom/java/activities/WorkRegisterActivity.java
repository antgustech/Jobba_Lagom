package com.example.i7.jobbalagom.java.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.java.callback_interfaces.DateRegisterCallback;
import com.example.i7.jobbalagom.java.callback_interfaces.MainTimeRegisterCallback;
import com.example.i7.jobbalagom.java.callback_interfaces.TimePickerCallback;
import com.example.i7.jobbalagom.java.fragments.DateRegisterFragment;
import com.example.i7.jobbalagom.java.fragments.MainTimeRegisterFragment;
import com.example.i7.jobbalagom.java.fragments.TimePickerFragment;
import com.example.i7.jobbalagom.java.local.Controller;
import com.example.i7.jobbalagom.java.local.Singleton;

public class WorkRegisterActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainTimeRegisterFragment mainTimeRegisterFragment;
    private DateRegisterFragment dateRegisterFragment;

    private TimePickerFragment timePickerFromFragment;
    private TimePickerCallback timePickerFromCallback;

    private TimePickerFragment timePickerToFragment;
    private TimePickerCallback timePickerToCallback;

    private MainTimeRegisterCallback mainTimeRegisterCallback;
    private DateRegisterCallback dateRegisterCallback;

    private Controller controller;




    public WorkRegisterActivity(){

        fragmentManager = getFragmentManager();

        mainTimeRegisterFragment = new MainTimeRegisterFragment();
        dateRegisterFragment = new DateRegisterFragment();
        timePickerFromFragment = new TimePickerFragment();
        timePickerToFragment = new TimePickerFragment();

        mainTimeRegisterCallback = new MainRegisterListener();
        mainTimeRegisterFragment.setCallback(mainTimeRegisterCallback);

        dateRegisterCallback = new DateRegisterListener();
        dateRegisterFragment.setCallback(dateRegisterCallback);

        timePickerFromCallback = new TimePickerFromListener();
        timePickerFromFragment.setCallback(timePickerFromCallback);

        timePickerToCallback = new TimePickerToListener();
        timePickerToFragment.setCallback(timePickerToCallback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller  = Singleton.controller;
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_work_register);

        setStatusbarColor();
        setupActionBar();
        changeFragment(mainTimeRegisterFragment);


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

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private class MainRegisterListener implements MainTimeRegisterCallback {

        @Override
        public void buttonPressed(View view) {

            if(view.equals(view.findViewById(R.id.dateTextView)))
                changeFragment(dateRegisterFragment);

            if(view.equals(view.findViewById(R.id.timeTextView_From))){
                changeFragment(timePickerFromFragment);
                timePickerFromFragment.setHeadLine("Välj tid då du började jobba");
            }

            if(view.equals(view.findViewById(R.id.timeTextView_To))){
                changeFragment(timePickerToFragment);
                timePickerToFragment.setHeadLine("Välj tid då du slutade jobba");
            }

            if(view.equals(view.findViewById(R.id.mainTimeButton_Done))){//BUTTON PRESSED.....................................................................

                //Parses data from the user.
                String timeFrom = mainTimeRegisterFragment.getTimeFrom_TextContainer();
                String newTimeFrom1 = timeFrom.substring(0,2);
                String newTimeFrom2 = timeFrom.substring(3,5);
                String newTimeFrom3 = newTimeFrom1 + newTimeFrom2;

                String timeTo = mainTimeRegisterFragment.getTimeTo_TextContainer();
                String newTimeTo1 = timeTo.substring(0,2);
                String newTimeTo2 = timeTo.substring(3,5);
                String newTimeTo3 = newTimeTo1 + newTimeTo2;

                String date = mainTimeRegisterFragment.getDate_TextContainer();
                String newDate1 = date.substring(0,4);
                String newDate2 = date.substring(5,6);
                String newDate3 = date.substring(7,8);
                String newDate4 = newDate1+newDate2+newDate3;
                //TODO Måste ordna så att det kommer upp tillagda jobb till autotextediten.
                controller.addShift("TEST", Float.parseFloat(newTimeFrom3.toString()), Float.parseFloat(newTimeTo3.toString()),Integer.parseInt(newDate4.toString()));
                Toast.makeText(getBaseContext(), "Jobb tillagt", Toast.LENGTH_LONG).show();


                finish();





            }
        }
    }

    private class DateRegisterListener implements DateRegisterCallback{

        @Override
        public void UpdateRegisteredDate(int year, int month, int day) {
            //Log.d("DateTag", year + "-" + month + "-" + day);
            changeFragment(mainTimeRegisterFragment);
            mainTimeRegisterFragment.setDate_TextContainer(year + "-" + month + "-" + day);

        }
    }

    private class TimePickerFromListener implements TimePickerCallback{

        @Override
        public void updateTime(String time) {
            Log.d("TimeTag", "From" + time);

            changeFragment(mainTimeRegisterFragment);
            mainTimeRegisterFragment.setTimeFrom_TextContainer(time);


        }
    }
    private class TimePickerToListener implements TimePickerCallback{

        @Override
        public void updateTime(String time) {
            Log.d("TimeTag","To:" + time);

            changeFragment(mainTimeRegisterFragment);
            mainTimeRegisterFragment.setTimeTo_TextContainer(time);

        }
    }
}
