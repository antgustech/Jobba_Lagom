package com.example.i7.jobbalagom.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.AddExpenseFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.AddJobFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.AddShiftFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.LaunchFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.SetupFragmentCallback;
import com.example.i7.jobbalagom.fragments.AddExpenseFragment;
import com.example.i7.jobbalagom.fragments.AddJobFragment;
import com.example.i7.jobbalagom.fragments.AddShiftFragment;
import com.example.i7.jobbalagom.fragments.LaunchFragment;
import com.example.i7.jobbalagom.fragments.SetupFragment;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;
import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Created by Kajsa on 2016-05-09.
 */

public class MainActivity extends Activity {

    private Controller controller;

    private final int REQUESTCODE_WORKREGISTER = 1;

    private Fragment currentFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    private FloatingActionButton btnAddShift, btnAddExpense, btnAddJob;
    private ImageButton btnSettings, btnBudget;
    private TextView tvIncome, tvCSN;
    private ProgressBar pbCSN, pbIncome, pbExpense;

    private float monthlyIncomeLimit, csnIncomeLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new Controller(this);
        Singleton.setController(controller);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        startLaunchFragment();
    }

    public void initComponents() {
        btnAddShift = (FloatingActionButton) findViewById(R.id.action_addShift);
        btnAddExpense = (FloatingActionButton) findViewById(R.id.action_addExpense);
        btnAddJob = (FloatingActionButton) findViewById(R.id.action_addJob);
        AddButtonListener addButtonListener = new AddButtonListener();
        btnAddShift.setOnClickListener(addButtonListener);
        btnAddExpense.setOnClickListener(addButtonListener);
        btnAddJob.setOnClickListener(addButtonListener);
        btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        btnBudget = (ImageButton) findViewById(R.id.btnBudget);
        btnSettings.setOnClickListener(new SettingsButtonListener());
        btnBudget.setOnClickListener(new BudgetButtonListener());
        tvIncome = (TextView) findViewById(R.id.tvIncome);
        tvCSN = (TextView) findViewById(R.id.tvCSN);
        pbCSN = (ProgressBar) findViewById(R.id.pbCSN);
        pbIncome = (ProgressBar) findViewById(R.id.pbIncome);
        pbExpense = (ProgressBar) findViewById(R.id.pbExpenses);
        fragmentManager = getFragmentManager();
        monthlyIncomeLimit = controller.getIncomeLimit()/6;         // --> 100 % av income och expense progress bar
        csnIncomeLimit = controller.getIncomeLimit();               // --> 100 % av csn progress bar
        loadProgressBars();
    }

    public void onBackPressed() {
        if(currentFragment == null || currentFragment instanceof LaunchFragment) {
            super.onBackPressed();
        } else if(currentFragment instanceof SetupFragment) {
            startLaunchFragment();
        } else if(currentFragment instanceof AddJobFragment) {
            startAddShiftFragment();
        } else {
            removeFragment(currentFragment);
        }
    }

    public void loadProgressBars() {
        updatePBcsn(controller.getTotalIncome());                   // --> csn progress bar fills up with total income
        updatePBincome(controller.getThisMonthsIncome());           // --> income progress bar fills up with this months income
        updatePBexpense(controller.getThisMonthsExpenses());        // --> expense progress bar fills up with this months expenses
    }

    /**
     * Updates CSN progress bar
     * @param income - representing an income defined in kronor
     */

    public void updatePBcsn(float income) {
        Log.d("MainActivity", "CSN progressbar before update: " + pbCSN.getProgress());
        int increase = (int)(income/ csnIncomeLimit *100);
        int total = pbCSN.getProgress() + increase;
        pbCSN.setProgress(total);

        float totalIncome = controller.getTotalIncome();
        int left = (int)(csnIncomeLimit - totalIncome);
        tvCSN.setText("Du får tjäna " + left + " kr till detta halvår.");
        Log.d("MainActivity", "CSN progressbar after update: " + pbCSN.getProgress());
    }

    /**
     * Updates income progress bar
     * @param income - representing an income defined in kronor
     */

    public void updatePBincome(float income) {
        Log.d("MainActivity", "Income progressbar before update: " + pbIncome.getProgress());
        float thisMonthsIncome = controller.getThisMonthsIncome();
        int increase = (int)(income/monthlyIncomeLimit*100);
        int total = pbIncome.getProgress() + increase;
        pbIncome.setProgress(total);
        tvIncome.setText( (int)thisMonthsIncome + "");
        Log.d("MainActivity", "Income progressbar after update: " + pbIncome.getProgress());
        // Hur ska progressbaren agera när användaren passerar månadsbudgeten? Explodera.
    }

    /**
     * Updates the expense progress bar
     * @param expense - representing an expense defined in kronor
     */

    public void updatePBexpense(float expense) {
        Log.d("MainActivity", "Expense progressbar before update: " + pbExpense.getProgress());
        float thisMonthsExpense = controller.getThisMonthsExpenses();
        int increase = (int)(expense/monthlyIncomeLimit*100);
        int total = pbExpense.getProgress() + increase;
        pbExpense.setProgress(total);
        Log.d("MainActivity", "Expense progressbar after update: " + pbExpense.getProgress());
    }

    /**
     * Replaces fragments on the display
     */

    private void changeFragment(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Removes fragments from the display
     */

    private void removeFragment(Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commit();
        currentFragment = null;
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

    public void startAddShiftFragment() {
        currentFragment = new AddShiftFragment();
        ((AddShiftFragment) currentFragment).setCallBack(new AddShiftFragmentListener());
        changeFragment(currentFragment);
    }

    /**
     * Listens to the settings icon in the main layout
     */

    private class SettingsButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }
    }

    /**
     * Listens to the budget icon in the main layout
     */

    private class BudgetButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Show budget fragment", Toast.LENGTH_LONG).show();
            // startBudgetFragment();
        }
    }

    /**
     * Listens to the add icon in the main layout
     */

    private class AddButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.action_addShift) {
                startAddShiftFragment();
            } else if (v.getId() == R.id.action_addExpense) {
                startAddExpenseFragment();
            } else if (v.getId() == R.id.action_addJob) {
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
                startSetupFragment();
            } else if(choice.equals("btnKey")) {
                removeFragment(currentFragment);
            } else if(choice.equals("btnBudget")) {
                //startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        }
    }

    /**
     * Listener for setup fragment
     */

    private class SetupFragmentListener implements SetupFragmentCallback {
        public void addUser(String municipality, float incomeLimit) {
            controller.addUser(municipality, incomeLimit);
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
        public void addOB(String jobTitle, String day, String fromTime, String toTime, float obIndex) {
            controller.addOB(jobTitle, day, fromTime, toTime, obIndex);
        }
    }

    /**
     * Listener for add shift fragment
     */

    private class AddShiftFragmentListener implements AddShiftFragmentCallback {
        public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day) {
            float income = controller.addShift(jobTitle, startTime, endTime, hoursWorked, year, month, year);
            updatePBincome(income);
            updatePBcsn(income);
        }
        public void showAddJobFragment() {

        }
    }

    /**
     * Listener for add expense fragment
     */

    private class AddExpenseListener implements AddExpenseFragmentCallback {
        public void addExpense(String title, Float amount, int date) {
            removeFragment(currentFragment);
            controller.addExpense(title,amount,date);
            Toast.makeText(getBaseContext(), "Utgift tillagd", Toast.LENGTH_LONG).show();
            updatePBexpense(amount);
        }
    }

}