package com.example.i7.jobbalagom.activities;

/**
 * Created by Anton Gustafsson on 2016-05-12.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.AddJobFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.ChangeTaxFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.ChangeIncomeLimitFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.RemoveJobFragmentCallback;
import com.example.i7.jobbalagom.fragments.AboutFragment;
import com.example.i7.jobbalagom.fragments.AddJobFragment;
import com.example.i7.jobbalagom.fragments.ChangeTaxFragment;
import com.example.i7.jobbalagom.fragments.ChangeIncomeLimitFragment;
import com.example.i7.jobbalagom.fragments.RemoveJobFragment;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

import java.util.ArrayList;
import java.util.Collections;

public class SettingsActivity extends Activity {
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ListView myList;
    private Fragment currentFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Controller controller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = Singleton.controller;
        fragmentManager = getFragmentManager();


        setStatusbarColor();
        setContentView(R.layout.activity_settings);
        myList = (ListView)findViewById(R.id.settingListView);
        String[] values = new String[] { "Allmänt", "Ändra skattesats","Ändra fribelopp", "Lägg till jobb", "Ta bort jobb", "Om" };
        list = new ArrayList<String>();
        Collections.addAll(list, values);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new ListListener());
    }
    private class ListListener implements ListView.OnItemClickListener{
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            int itemPosition = position;
            String itemValue = (String) myList.getItemAtPosition(position);
            if (itemValue == "Allmänt") {
            //Start intent or something
                Toast.makeText(getApplicationContext(), "Nothing here yet :)", Toast.LENGTH_LONG).show();
            }else if(itemValue =="Ändra skattesats"){
                startChangeTaxFragment();
            }else if(itemValue == "Lägg till jobb"){
                startAddJobFragment();
            }else if(itemValue == "Ta bort jobb"){
                startRemoveJobFragment();
            }else if(itemValue == "Om"){
                startAboutFragment();
            }else if(itemValue == "Ändra fribelopp"){
                startChangeIncomeLimit();
            }
        }
    }
    public void onBackPressed() {

        if(currentFragment == null) {
            super.onBackPressed();
        } else
            removeFragment(currentFragment);
    }

    private void setStatusbarColor() {
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    private void changeFragment(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }
    private void removeFragment(Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commit();
        currentFragment = null;
    }

    public void startAddJobFragment() {
        currentFragment = new AddJobFragment();
        ((AddJobFragment) currentFragment).setCallBack(new AddJobFragmentListener());
        changeFragment(currentFragment);
    }
    private class AddJobFragmentListener implements AddJobFragmentCallback {
        public void addJob(String title, Float wage) {
            controller.addJob(title, wage);
        }
        public void addOB(String jobTitle, String day, String fromTime, String toTime, float obIndex) {
            controller.addOB(jobTitle, day, fromTime, toTime, obIndex);
        }
    }

    public void startRemoveJobFragment(){
        currentFragment = new RemoveJobFragment();
        ((RemoveJobFragment) currentFragment).setCallBack(new RemoveJobFragmentListener());
        changeFragment(currentFragment);
    }
    private class RemoveJobFragmentListener implements RemoveJobFragmentCallback {
        @Override
        public void removeJob(String jobTitle) {
            controller.removeJob(jobTitle);
            Toast.makeText(getApplicationContext(), jobTitle+"removed", Toast.LENGTH_LONG).show();
        }
    }

    public void startChangeTaxFragment() {
        currentFragment = new ChangeTaxFragment();
        ((ChangeTaxFragment) currentFragment).setCallBack(new ChangeTaxListener());
        changeFragment(currentFragment);
    }
    private class ChangeTaxListener implements ChangeTaxFragmentCallback {
        public void updateTax(float tax) {
            controller.setTax(tax);
        }
    }

    public void startAboutFragment(){
        currentFragment = new AboutFragment();
        changeFragment(currentFragment);
    }

    public void startChangeIncomeLimit(){
        currentFragment = new ChangeIncomeLimitFragment();
        ((ChangeIncomeLimitFragment) currentFragment).setCallBack(new ChangeIncomeLimitListener());
        changeFragment(currentFragment);
    }
    private class ChangeIncomeLimitListener implements ChangeIncomeLimitFragmentCallback {

        @Override
        public void setIncomeLimit(float limit) {
            controller.setIncomeLimit(limit);
        }

        @Override
        public float getIncomeLimit() {
            return controller.getIncomeLimit();

        }
    }


}

