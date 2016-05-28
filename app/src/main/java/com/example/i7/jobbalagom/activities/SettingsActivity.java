package com.example.i7.jobbalagom.activities;

/**
 * Created by Anton, Jakup, Morgan, Kajsa and Christoffer.
 * Handles the fragment changing of the settings screen.
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
import com.example.i7.jobbalagom.callbacks.ChangeIncomeLimitFragmentCallback;
import com.example.i7.jobbalagom.callbacks.ChangeTaxFragmentCallback;
import com.example.i7.jobbalagom.callbacks.RemoveJobFragmentCallback;
import com.example.i7.jobbalagom.fragments.ChangeIncomeLimitFragment;
import com.example.i7.jobbalagom.fragments.ChangeTaxFragment;
import com.example.i7.jobbalagom.fragments.InfoFragment;
import com.example.i7.jobbalagom.fragments.RemoveJobFragment;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

import java.util.ArrayList;
import java.util.Collections;

public class SettingsActivity extends Activity {
    private ListView myList;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private Controller controller;

    /**
     * Initializes this activity. Sets layout.
     *
     * @param savedInstanceState used for saving non persistent data that get's restored if the mainActivity needs to be recreated.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initComponents();
    }

    /**
     * Initializes the components.
     */

    private void initComponents() {
        myList = (ListView) findViewById(R.id.settingListView);
        String[] values = new String[]{"Ändra skattesats", "Ändra fribelopp", "Ta bort jobb", "Om"};
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, values);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new ListListener());
        controller = Singleton.controller;
        fragmentManager = getFragmentManager();
    }

    /**
     * If the back button is pressed, current fragment is closed.
     */

    public void onBackPressed() {
        if (currentFragment == null) {
            super.onBackPressed();
        } else
            removeFragment(currentFragment);
    }

    /**
     * Replaces fragments on the view.
     *
     * @param fragment the new fragment to change to.
     */

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Removes current fragment.
     *
     * @param fragment to remove.
     */

    private void removeFragment(Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commit();
        currentFragment = null;
    }

    /**
     * Launches RemoveJobFragment.
     */

    private void startRemoveJobFragment() {
        currentFragment = new RemoveJobFragment();
        ((RemoveJobFragment) currentFragment).setCallBack(new RemoveJobFragmentListener());
        changeFragment(currentFragment);
    }

    /**
     * Launches ChangeTaxFragment.
     */

    private void startChangeTaxFragment() {
        currentFragment = new ChangeTaxFragment();
        ((ChangeTaxFragment) currentFragment).setCallBack(new ChangeTaxListener());
        changeFragment(currentFragment);
    }

    /**
     * launch ChangeIncomeLimit.
     */

    private void startChangeIncomeLimit() {
        currentFragment = new ChangeIncomeLimitFragment();
        ((ChangeIncomeLimitFragment) currentFragment).setCallBack(new ChangeIncomeLimitListener());
        changeFragment(currentFragment);
    }

    /**
     * Launches InfoFragment
     */

    private void startInfoFragment() {
        currentFragment = new InfoFragment();
        changeFragment(currentFragment);
    }

    /**
     * Checks whenever we have an connection to the server or not.
     *
     * @return true if we have an connection.
     */

    private boolean checkConnection() {
        boolean connection = false;
        if (controller.checkConnection()) {
            connection = true;
        }
        return connection;
    }

    /**
     * Listener for the different settings.
     */

    private class ListListener implements ListView.OnItemClickListener {

        /**
         * Checks what button is clicked.
         *
         * @param parent   the AdapterView to check on.
         * @param view     the current View.
         * @param position which position the button that was clicked have.
         * @param id       the id of the element pressed.
         */
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String itemValue = (String) myList.getItemAtPosition(position);
            if (itemValue.equals("Ändra skattesats")) {
                if (checkConnection()) {
                    startChangeTaxFragment();
                } else {
                    Toast.makeText(getBaseContext(), "Appen saknar anslutning till servern.", Toast.LENGTH_LONG).show();
                }
            } else if (itemValue.equals("Ta bort jobb")) {
                startRemoveJobFragment();
            } else if (itemValue.equals("Om")) {
                startInfoFragment();
            } else if (itemValue.equals("Ändra fribelopp")) {
                startChangeIncomeLimit();
            }
        }
    }

    /**
     * Listener for RemoveJobFragment.
     */

    private class RemoveJobFragmentListener implements RemoveJobFragmentCallback {

        /**
         * Removes job from database.
         *
         * @param jobTitle the choosen job.
         */

        @Override
        public void removeJob(String jobTitle) {
            controller.removeJob(jobTitle);
            Toast.makeText(getApplicationContext(), "Jobbet " + jobTitle + " har tagits bort!", Toast.LENGTH_LONG).show();
            removeFragment(currentFragment);
        }
    }

    /**
     * Listener for ChangeTax.
     */

    private class ChangeTaxListener implements ChangeTaxFragmentCallback {

        /**
         * Sets the tax to the database.
         *
         * @param tax the choosen tax by the user.
         */

        public void updateTax(float tax) {
            controller.setTax(tax);
        }

        /**
         * Checks whenever we have av connection to the server or not.
         *
         * @return true if we have an connection.
         */

        @Override
        public boolean checkConnection() {
            boolean connection = false;
            if (controller.checkConnection()) {
                connection = true;
            }
            return connection;
        }
    }

    /**
     * Listener for ChangeIncomeLimit.
     */

    private class ChangeIncomeLimitListener implements ChangeIncomeLimitFragmentCallback {

        /**
         * Retrives the current incomeLimit from the database.
         *
         * @return current incomeLimit.
         */

        @Override
        public float getIncomeLimit() {
            return controller.getIncomeLimit();
        }

        /**
         * Sets the incomelimit
         *
         * @param limit the choosen amount from the user.
         */

        @Override
        public void setIncomeLimit(float limit) {
            controller.setIncomeLimit(limit);
        }
    }
}

