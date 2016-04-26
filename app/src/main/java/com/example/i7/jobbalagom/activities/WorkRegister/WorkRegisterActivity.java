package com.example.i7.jobbalagom.activities.WorkRegister;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.example.i7.jobbalagom.R;

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
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private class MainRegisterListener implements MainTimeRegisterCallback {

        @Override
        public void buttonPressed(View view) {
            Log.d("ButtonTag", "Ay lmao");

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

            if(view.equals(view.findViewById(R.id.mainTimeButton_Done))){
                Intent intent = new Intent();
                intent.putExtra("Date",mainTimeRegisterFragment.getDate_TextContainer());
                intent.putExtra("TimeFrom",mainTimeRegisterFragment.getTimeFrom_TextContainer());
                intent.putExtra("TimeTo",mainTimeRegisterFragment.getTimeTo_TextContainer());
                setResult(RESULT_OK,intent);
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
