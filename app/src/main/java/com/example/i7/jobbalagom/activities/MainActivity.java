package com.example.i7.jobbalagom.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.activities.WorkRegister.WorkRegisterActivity;
import com.example.i7.jobbalagom.activities.callback_interfaces.AddExpenseFragmentCallback;
import com.example.i7.jobbalagom.activities.callback_interfaces.AddJobFragmentCallback;
import com.example.i7.jobbalagom.activities.callback_interfaces.LaunchFragmentCallback;
import com.example.i7.jobbalagom.activities.callback_interfaces.SetupFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;
import com.example.i7.jobbalagom.localDatabase.DBHelper;

public class MainActivity extends AppCompatActivity {

    private View btnChangeTax;
    private View btnAddExpense;
    private View btnAddShift;
    private View btnBudget;
    private View btnAddJob;
    private ButtonListener btnListener;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private AddExpenseFragment addExpenseFragment;
    private MainActivityBudgetFragment budgetFragment;
    private MainBarFragment barFragment;
    private Controller controller;
    private DBHelper dbHelper;
    private final int REQUESTCODE_WORKREGISTER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new Controller(this);
        Singleton.setController(controller);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        startLaunchFragment();
    }

    /**
     * Initializes components.
     */
    public void initComponents() {
        btnAddExpense = findViewById(R.id.btnAddExpense);
        // btnChangeTax = findViewById(R.id.btnChangeTax);
        btnAddShift = findViewById(R.id.btnAddShift);
        btnBudget = findViewById(R.id.btnBudget);
        btnAddJob = findViewById(R.id.action_addjob);
        btnListener = new ButtonListener();
        btnAddShift.setOnClickListener(btnListener);
        btnBudget.setOnClickListener(btnListener);
        // btnChangeTax.setOnClickListener(btnListener);
        btnAddExpense.setOnClickListener(btnListener);
        btnAddJob.setOnClickListener(btnListener);
        fragmentManager = getFragmentManager();
        budgetFragment = new MainActivityBudgetFragment();
        barFragment = new MainBarFragment();
        setStatusbarColor();
    }

    /**
     * Setups the statusbar color for older android versions
     */

    private  void setStatusbarColor(){
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void onBackPressed() {
        if(currentFragment == null || currentFragment instanceof LaunchFragment) {
            super.onBackPressed();
        } else if(currentFragment instanceof SetupFragment) {
            startLaunchFragment();
        } else {
            removeFragment(currentFragment);
        }
    }

    /**
     * Controls hamburger button
     * @param menu
     * @return
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *  Handler for buttons on the hamburger button
     * @param item
     * @return
     */

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        } else if(id == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            return true;
        }else if(id == R.id.action_vote){
        }
        return super.onOptionsItemSelected(item);
    }

    /**Used for calculating and showing the data in the graph
     * ToDO: DOES NOT WORK!
     * @param requestCode
     * @param resultCode
     * @param data
     */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_WORKREGISTER){
            if(resultCode == RESULT_OK){
                String date = data.getStringExtra("Date");
                String timeFrom = data.getStringExtra("TimeFrom");
                String timeTo = data.getStringExtra("TimeTo");

                //barFragment.setData(200);
                budgetFragment.setDataExpense(100);
               // budgetFragment.updateDataExpense();
                Log.d("SomeTag",budgetFragment.getDataExpense() + "");

                //Log.d("ResultTag",date);
                //Log.d("ResultTag",timeFrom);
                //Log.d("ResultTag",timeTo);
            }
        }
    }


    /**
     * Changes fragments
     * @param fragment
     */

    private void changeFragment(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Removes fragments from fragmentManager
     * @param fragment
     */

    private void removeFragment(Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commit();
        currentFragment = null;
    }

    public void startWorkRegister(){
        Intent workRegisterActivity =  new Intent(this, WorkRegisterActivity.class);
        startActivityForResult(workRegisterActivity, REQUESTCODE_WORKREGISTER);
    }

    public void startAddExpenseFragment() {
        currentFragment = new AddExpenseFragment();
        ((AddExpenseFragment) currentFragment).setCallBack(new AddExpenseListener());
        changeFragment(currentFragment);
    }

    public void startAddJobFragment() {
        currentFragment = new AddJobFragment();
        ((AddJobFragment) currentFragment).setCallBack(new AddJobFragmentListener());
        changeFragment(currentFragment);
    }

    public void startSetupFragment() {
        currentFragment = new SetupFragment();
        ((SetupFragment) currentFragment).setCallBack(new SetupFragmentListener());
        changeFragment(currentFragment);
    }

    public void startLaunchFragment() {
        currentFragment = new LaunchFragment();
        ((LaunchFragment) currentFragment).setCallBack(new LaunchFragmentListener());
        changeFragment(currentFragment);
    }

    /**
     * Button btnListener for fab
     */

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            //if (v.getId() == R.id.btnChangeTax) {
            //  startActivity(new Intent(getApplicationContext(), ChangeTaxActivity.class));
            //} else
            if (v.getId() == R.id.btnBudget) {
                startActivity(new Intent(getApplicationContext(), BudgetAcitivity.class));
            } else if (v.getId() == R.id.btnAddExpense) {
                startAddExpenseFragment();
            } else if (v.getId() == R.id.btnAddShift) {
                startWorkRegister();
            } else if (v.getId() == R.id.action_addjob) {
                startAddJobFragment();
            }
        }
    }

    /**
     * Listener for launch fragment
     */

    private class LaunchFragmentListener implements LaunchFragmentCallback {
        public void navigate(String choice) {
            if(choice.equals("btnLogo")) {
                // Something if we want, or nothing
            } else if(choice.equals("btnNew")) {
                Log.d("MainActivity", "Changing fragment to SetupFragment");
                startSetupFragment();
            } else if(choice.equals("btnKey")) {
                Log.d("MainActivity", "Removing LaunchFragment");
                removeFragment(currentFragment);
            } else if(choice.equals("btnInfo")) {
                Log.d("MainActivity", "Changing fragment to InfoFragment");
                // Change fragment to infoFragment
            }
        }
    }

    /**
     * Listener for setup fragment
     */

    private class SetupFragmentListener implements SetupFragmentCallback {
        public void addUser(String name, String municipality, String incomeLimit) {
            Log.d("SetupFragmentListener", "User information\nNamn: " + name + "\nKommun: " + municipality +
                    "\nFribelopp: " + incomeLimit);
            //ctrl.addUser(...);
            removeFragment(currentFragment);
        }
    }

    /**
     * Listener for add job fragment
     */

    private class AddJobFragmentListener implements AddJobFragmentCallback {
        public void addJob(String title, Float wage) {
            controller.addJob(title, wage);
        }
        public void addOB(String jobTitle, String day, String fromTime, String toTime) {
            controller.addOB(jobTitle, day, fromTime, toTime);
        }
    }

    /**
     * Listener for add expense fragment
     */

    private class AddExpenseListener implements AddExpenseFragmentCallback {
        public void addExpense(String title, String amount, String date) {
            removeFragment(currentFragment);
            Log.d("AddExpenseListener", "Button pressed");
            // --> Send new expense to client database
        }
    }
}
