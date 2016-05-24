package com.example.i7.jobbalagom.activities;

/**
 * Created by Anton, Jakup, Morgan, kajsa, Christoffer.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
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
        setContentView(R.layout.activity_settings);
        initComponents();
    }

    private void initComponents(){
        myList = (ListView)findViewById(R.id.settingListView);
        String[] values = new String[] { "Allmänt", "Ändra skattesats","Ändra fribelopp", "Lägg till jobb", "Ta bort jobb", "Om" };
        list = new ArrayList<String>();
        Collections.addAll(list, values);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new ListListener());
        controller = Singleton.controller;
        fragmentManager = getFragmentManager();
    }

    /**
     * Listener for the different settings.
     */

    private class ListListener implements ListView.OnItemClickListener{
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int itemPosition = position;
            String itemValue = (String) myList.getItemAtPosition(position);
            if (itemValue == "Allmänt") {
                Toast.makeText(getApplicationContext(), "Nothing here yet :)", Toast.LENGTH_LONG).show();
            }else if(itemValue =="Ändra skattesats"){
                if (checkConnection()) {
                    startChangeTaxFragment();
                }else{
                    Toast.makeText(getBaseContext(), "Appen saknar anslutning till servern.", Toast.LENGTH_LONG).show();
                }
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

    /**
     * Handlelr for back button pressed.
     */

    public void onBackPressed() {
        if(currentFragment == null) {
            super.onBackPressed();
        } else
            removeFragment(currentFragment);
    }

    /**
     * Changes fragment.
     * @param fragment fragment to change to.
     */

    private void changeFragment(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Removes current fragment.
     * @param fragment to remove.
     */

    private void removeFragment(Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commit();
        currentFragment = null;
    }

    /**
     * Launches AddJobFragment.
     */

    public void startAddJobFragment() {
        currentFragment = new AddJobFragment();
        ((AddJobFragment) currentFragment).setCallBack(new AddJobFragmentListener());
        changeFragment(currentFragment);
    }

    /**
     *Listener for AddJobFragment.
     */

    private class AddJobFragmentListener implements AddJobFragmentCallback {

        /**
         * adds job to database.
         * @param title the choosen name for the job.
         * @param wage the choosen hourly wage for the job.
         */

        public void addJob(String title, Float wage) {
            controller.addJob(title, wage);
        }

        /**
         * Adds ob rate to the added job.
         * @param jobTitle name o the job that the ob will refer to.
         * @param day the day the ob is registered for.
         * @param fromTime starttime for the ob.
         * @param toTime endtime for the ob.
         * @param obIndex the actual rate
         */

        public void addOB(String jobTitle, String day, String fromTime, String toTime, float obIndex) {
            controller.addOB(jobTitle, day, fromTime, toTime, obIndex);
        }
    }

    /**
     * Launches RemoveJobFragment.
     */

    public void startRemoveJobFragment(){
        currentFragment = new RemoveJobFragment();
        ((RemoveJobFragment) currentFragment).setCallBack(new RemoveJobFragmentListener());
        changeFragment(currentFragment);
    }

    /**
     *Listener for RemoveJobFragment.
     */

    private class RemoveJobFragmentListener implements RemoveJobFragmentCallback {

        /**
         * removes job from database.
         * @param jobTitle the choosen job.
         */

        @Override
        public void removeJob(String jobTitle) {
            controller.removeJob(jobTitle);
            Toast.makeText(getApplicationContext(), jobTitle+"removed", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Launches ChangeTaxFragment.
     */

    public void startChangeTaxFragment() {
        currentFragment = new ChangeTaxFragment();
        ((ChangeTaxFragment) currentFragment).setCallBack(new ChangeTaxListener());
        changeFragment(currentFragment);
    }

    /**
     * Listener for ChangeTax.
     */

    private class ChangeTaxListener implements ChangeTaxFragmentCallback {

        /**
         * Sets the tax to the database.
         * @param tax the choosen tax by the user.
         */

        public void updateTax(float tax) {
            controller.setTax(tax);
        }

        /**
         * Checks whenever we have av connection to the server or not.
         * @return true if we have an connection.
         */

        @Override
        public boolean checkConnection() {
            boolean connection = false;
            if(controller.checkConnection()){
                connection = true;
            }
            return connection;
        }
    }

    /**
     * Launch AboutFragment.
     */

    public void startAboutFragment(){
        currentFragment = new AboutFragment();
        changeFragment(currentFragment);
    }

    /**
     * launch ChangeIncomeLimit.
     */

    public void startChangeIncomeLimit(){
        currentFragment = new ChangeIncomeLimitFragment();
        ((ChangeIncomeLimitFragment) currentFragment).setCallBack(new ChangeIncomeLimitListener());
        changeFragment(currentFragment);
    }

    /**
     * Listener for ChangeIncomeLimit.
     */

    private class ChangeIncomeLimitListener implements ChangeIncomeLimitFragmentCallback {

        /**
         * Sets the incomelimit
         * @param limit the choosen amount from the user.
         */

        @Override
        public void setIncomeLimit(float limit) {
            controller.setIncomeLimit(limit);
        }

        /**
         * Retrives the current incomeLimit from the database.
         * @return current incomeLimit.
         */

        @Override
        public float getIncomeLimit() {
            return controller.getIncomeLimit();
        }
    }

    /**
     * Checks whenever we have an connection to the server or not.
     * @return true if we have an connection.
     */

    public boolean checkConnection() {
        boolean connection = false;
        if(controller.checkConnection()){
            connection = true;
        }
        return connection;
    }
}

