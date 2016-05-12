package com.example.i7.jobbalagom.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.i7.jobbalagom.callback_interfaces.ChangeTaxFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.LaunchFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.SettingsFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.SetupFragmentCallback;
import com.example.i7.jobbalagom.fragments.AddExpenseFragment;
import com.example.i7.jobbalagom.fragments.AddJobFragment;
import com.example.i7.jobbalagom.fragments.AddShiftFragment;
import com.example.i7.jobbalagom.fragments.ChangeTaxFragment;
import com.example.i7.jobbalagom.fragments.LaunchFragment;
import com.example.i7.jobbalagom.fragments.SettingsFragment;
import com.example.i7.jobbalagom.fragments.SetupFragment;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

/**
 * Created by Kajsa on 2016-05-09.
 */

public class MainActivity extends Activity {

    private Controller controller;

    private final int REQUESTCODE_WORKREGISTER = 1;

    private Fragment currentFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    private ImageButton btnSettings, btnInfo, btnExpense, btnShift;
    private TextView tvIncome, tvCSN;
    private ProgressBar pbCSN, pbIncome, pbExpense;

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
        btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnExpense = (ImageButton) findViewById(R.id.btnExpense);
        btnShift = (ImageButton) findViewById(R.id.btnShift);
        ButtonListener btnListener = new ButtonListener();
        btnSettings.setOnClickListener(btnListener);
        btnInfo.setOnClickListener(btnListener);
        btnExpense.setOnClickListener(btnListener);
        btnShift.setOnClickListener(btnListener);
        tvIncome = (TextView) findViewById(R.id.tvIncome);
        tvCSN = (TextView) findViewById(R.id.tvCSN);
        pbCSN = (ProgressBar) findViewById(R.id.pbCSN);
        pbIncome = (ProgressBar) findViewById(R.id.pbIncome);
        pbExpense = (ProgressBar) findViewById(R.id.pbExpenses);
        fragmentManager = getFragmentManager();
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
        updatePBcsn(controller.getTotalIncome());
        updatePBincome(controller.getThisMonthIncome());
        updatePBexpense(controller.getTotalExpense());
    }

    /**
     * Updates CSN progress bar
     */

    public void updatePBcsn(float income) {
        Log.d("MainActivity", "CSN progressbar before update: " + pbCSN.getProgress());
        float totalIncome = controller.getTotalIncome();
        //float incomeLimit = controller.getIncomeLimit();
        float incomeLimit = 60000;
        int increase = (int)(income/incomeLimit*100);
        int total = pbCSN.getProgress() + increase;
        pbCSN.setProgress(total);
        Log.d("MainActivity", "CSN progressbar after update: " + pbCSN.getProgress());

        int left = (int)(incomeLimit - totalIncome);
        tvCSN.setText("Du får tjäna " + left + " kr till detta halvår.");
    }

    /**
     * Updates income progress bar
     */

    public void updatePBincome(float income) {
        Log.d("MainActivity", "Income progressbar before update: " + pbIncome.getProgress());
        float thisMonthsIncome = controller.getThisMonthIncome();
        tvIncome.setText( (int)thisMonthsIncome + "");
        float monthlyBudget = controller.getIncomeLimit()/6;
        //float monthlyBudget = 60000/6;
        int increase = (int)(income/monthlyBudget*100);
        int total = pbIncome.getProgress() + increase;
        pbIncome.setProgress(total);
        Log.d("MainActivity", "Income progressbar after update: " + pbIncome.getProgress());
        // Hur ska progressbaren agera när användaren passerar månadsbudgeten?
    }

    /**
     * Updates the expenses progress bar
     */

    public void updatePBexpense(float expense) {
        Log.d("MainActivity", "Expense progressbar before update: " + pbExpense.getProgress());
        float monthlyBudget = controller.getIncomeLimit()/6;
        float thisMonthsExpense = controller.getTotalExpense();     // <-- change to getThisMonthExpense();
        int increase = (int)(expense/monthlyBudget*100);
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

    public void startSettingsFragment() {
        currentFragment = new SettingsFragment();
        ((SettingsFragment) currentFragment).setCallBack((new SettingsFragmentListener()));
        changeFragment(currentFragment);
    }

    public void startAddShiftFragment() {
        currentFragment = new AddShiftFragment();
        ((AddShiftFragment) currentFragment).setCallBack(new AddShiftFragmentListener());
        changeFragment(currentFragment);
    }

    public void startChangeTaxFragment() {
        currentFragment = new ChangeTaxFragment();
        ((ChangeTaxFragment) currentFragment).setCallBack(new ChangeTaxListener());
        changeFragment(currentFragment);
    }

    /**
     * Listener for the four main buttons in main activity
     */

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            if (v.getId() == R.id.btnSettings) {
                startSettingsFragment();
            } else if (v.getId() == R.id.btnInfo) {
                startAddJobFragment();
            } else if(v.getId() == R.id.btnExpense) {
                startAddExpenseFragment();
            } else if(v.getId() == R.id.btnShift) {
                startAddShiftFragment();
            }
        }
    }

    /**
     * Listener for launch fragment
     */

    private class LaunchFragmentListener implements LaunchFragmentCallback {
        public void navigate(String choice) {
            if(choice.equals("btnLogo")) {
                Log.d("MainActivity", "Logo button pressed");
                // Something if we want, or nothing
            } else if(choice.equals("btnNew")) {
                Log.d("MainActivity", "New button pressed");
                startSetupFragment();
            } else if(choice.equals("btnKey")) {
                Log.d("MainActivity", "Key button pressed");
                removeFragment(currentFragment);
            } else if(choice.equals("btnInfo")) {
                Log.d("MainActivity", "Info button pressed");
                //startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        }
    }

    /**
     * Listener for setup fragment
     */

    private class SetupFragmentListener implements SetupFragmentCallback {
        public void addUser(String municipality, float incomeLimit) {
            Log.d("MainActivity", "User information\nKommun: " + municipality + "\nFribelopp: " + incomeLimit);
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
            Log.d("MainActivity", "Uppdaterar csn- och inkomstgrafer: " + income + " kr");
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
            Log.d("MainActivity", "Uppdaterar utgiftsgraf med " + amount + " kr");
            updatePBexpense(amount);
            // uppdatera utgiftsgraf
        }
    }

    /**
     * Listener for settings fragment
     */

    private class SettingsFragmentListener implements SettingsFragmentCallback {
        public void showFragment(String fragment) {
            if(fragment.equals("about")) {
                Log.d("MainActivity", "Button about pressed");
                //startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            } else if(fragment.equals("changeTax")) {
                Log.d("MainActivity", "Button change tax pressed");
                startChangeTaxFragment();
            }
        }
    }

    /**
     * Listener for change tax fragment
     */

    private class ChangeTaxListener implements ChangeTaxFragmentCallback {
        public void updateTax(float tax) {
            ((ChangeTaxFragment) currentFragment).setTax(tax);
        }
    }
}