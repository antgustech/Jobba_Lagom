package com.example.i7.jobbalagom.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.i7.jobbalagom.callback_interfaces.BudgetFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.InitialFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.LaunchFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.SetupFragmentCallback;
import com.example.i7.jobbalagom.fragments.AddExpenseFragment;
import com.example.i7.jobbalagom.fragments.AddJobFragment;
import com.example.i7.jobbalagom.fragments.AddShiftFragment;
import com.example.i7.jobbalagom.fragments.BudgetFragment;
import com.example.i7.jobbalagom.fragments.InitialFragment;
import com.example.i7.jobbalagom.fragments.LaunchFragment;
import com.example.i7.jobbalagom.fragments.SetupFragment;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Calendar;

/**
 * Created by Kajsa on 2016-05-09.
 */

public class MainActivity extends Activity {

    private Controller controller;

    private final int REQUESTCODE_WORKREGISTER = 1;

    private Fragment currentFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    private FloatingActionsMenu floatingMenu;
    private FloatingActionButton btnAddShift, btnAddExpense, btnAddJob;
    private ImageButton btnSettings, btnBudget;
    private TextView tvCSN, tvIncome, tvExpense, tvBalance;
    private ProgressBar pbCSN, pbIncome, pbExpense;

    private float monthlyIncomeLimit, csnIncomeLimit;
    private int pbMaxProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new Controller(this);
        Singleton.setController(controller);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        userCheck();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProgressBars();

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
        floatingMenu = (FloatingActionsMenu) findViewById(R.id.floatingMenu);
        tvCSN = (TextView) findViewById(R.id.tvCSN);
        tvIncome = (TextView) findViewById(R.id.tvIncome);
        tvExpense = (TextView) findViewById(R.id.tvExpense);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        pbCSN = (ProgressBar) findViewById(R.id.pbCSN);
        pbIncome = (ProgressBar) findViewById(R.id.pbIncome);
        pbExpense = (ProgressBar) findViewById(R.id.pbExpenses);
        fragmentManager = getFragmentManager();
    }

    public void userCheck() {
        if(!controller.isUserCreated()) {
            startInitialFragment();
        } else {
            loadProgressBars();
            if(controller.checkConnection()){
                checkForTaxUpdate();
            }
            //Log.e("SomeTag",controller.getMunicipality());
        }
    }

    private void checkForTaxUpdate(){
        Log.e("SomeTag","Updating tax");
        String kommun = controller.getMunicipality();
        float newTax = controller.getTax(kommun);
        float oldTax = controller.getTax();
        if(newTax != oldTax){
            controller.setTax(newTax);
            Toast.makeText(this,"The tax for " + kommun +" is now updated",Toast.LENGTH_LONG);
        }
        Log.e("SomeTag",newTax + "");

    }

    public void onBackPressed() {
        if(currentFragment == null || currentFragment instanceof LaunchFragment || currentFragment instanceof InitialFragment) {
            super.onBackPressed();
        } else if(currentFragment instanceof SetupFragment) {
            startLaunchFragment();
        } else {
            removeFragment(currentFragment);
        }
    }

    public void loadProgressBars() {
        monthlyIncomeLimit = controller.getIncomeLimit()/6;    // --> 100 % av income och expense progress bar
        csnIncomeLimit = controller.getIncomeLimit();          // --> 100 % av csn progress bar
        pbMaxProgress = 100;
        updatePBcsn(controller.getHalfYearIncome());
        updatePBincome(controller.getThisMonthsIncome());      // --> income progress bar fills up with this months income
        updatePBexpense(controller.getThisMonthsExpenses());   // --> expense progress bar fills up with this months expenses
    }

    /**
     * Updates CSN progress bar
     * @param income - representing an income defined in kronor
     */

    public void updatePBcsn(float income) {
        int increase = (int)(income / csnIncomeLimit *100);
        int total = pbCSN.getProgress() + increase;
        pbCSN.setProgress(total);
        float totalIncome = controller.getHalfYearIncome();
        float left = csnIncomeLimit - totalIncome;
        if(left < 0) {
            tvCSN.setText("Du har överskridit det av CSN erhållna fribeloppet med " + (int)left*-1 + " kr");
        } else {
            tvCSN.setText("Du kan tjäna " + (int)left + " kr innan det av CSN erhållna fribeloppet passeras");
        }
    }

    /**
     * Updates income progress bar
     * @param income - representing an income defined in kronor
     */

    public void updatePBincome(float income) {
        int increase = (int)(income/monthlyIncomeLimit*100);
        int totalProgress = pbIncome.getProgress() + increase;
        expandProgressBar(pbIncome, totalProgress);
    }

    /**
     * Updates the expense progress bar
     * @param expense - representing an expense defined in kronor
     */

    public void updatePBexpense(float expense) {
        int increase = (int)(expense/monthlyIncomeLimit*100);
        int totalProgress = pbExpense.getProgress() + increase;
        expandProgressBar(pbExpense, totalProgress);
    }

    public void expandProgressBar(ProgressBar pb, int totalProgress) {
        if(totalProgress > pbMaxProgress) {
            pbMaxProgress = totalProgress;
            pbIncome.setMax(pbMaxProgress);
            pbExpense.setMax(pbMaxProgress);
        }
        pb.setProgress(totalProgress);

        float thisMonthsIncome = controller.getThisMonthsIncome();
        float thisMonthsExpenses = controller.getThisMonthsExpenses();
        float balance = thisMonthsIncome-thisMonthsExpenses;
        tvIncome.setText("Inkomst " + (int)thisMonthsIncome);
        tvExpense.setText("Utgifter " + (int)thisMonthsExpenses);
        tvBalance.setText((int) balance + "");
       /**
        if(balance < 0) {
            tvBalance.setTextColor(Color.parseColor("#F67280"));
        } else {
            tvBalance.setTextColor(Color.parseColor("#71B238"));
        }
        **/
    }

    /**
     * Replaces fragments on the display
     */

    private void changeFragment(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private void changeFragmentWithFade(Fragment fragment) {
        // something
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

    public void startInitialFragment() {

        currentFragment = new InitialFragment();
        ((InitialFragment) currentFragment).setCallBack(new InitialFragmentListener());
        changeFragment(currentFragment);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startLaunchFragment();
            }
        }, 3000);
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

    public void startBudgetFragment() {
        currentFragment = new BudgetFragment();
        ((BudgetFragment) currentFragment).setCallBack(new BudgetFragmentListener());
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
            startBudgetFragment();
        }
    }

    /**
     * Listens to the add icon in the main layout
     */

    private class AddButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            floatingMenu.collapse();
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
                //TODO REMOVE THIS CALL WHEN APP IS FINISHED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
               removeFragment(currentFragment);
                Log.d("MainActivity", "Key button pressed");
            } else if(choice.equals("btnInfo")) {
                Log.d("MainActivity", "Info button pressed");
                //startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        }
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
     * Listener for setup fragment
     */

    private class SetupFragmentListener implements SetupFragmentCallback {
        public void addUser(String municipality, float incomeLimit) {

            controller.addUser(municipality, incomeLimit);
            loadProgressBars();
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
        public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day, float breakMinutes) {
            float income = 1000;
            // float income = controller.caculateShift(jobTitle, startTime, endTime, year, month, day, breakMinutes );
            Log.d("MainActivity", "Inkomst av shift: " + income);
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int currentYear = Calendar.getInstance().get(Calendar.YEAR)%100;

            if(month == currentMonth && year == currentYear) {
                updatePBincome(income);
            }

            if( ((currentMonth <= 6 && month <= 6) || (currentMonth > 6 && month > 6)) && year == currentYear) {
                updatePBcsn(income);
            }

            loadProgressBars();
        }


    }

    /**
     * Listener for add expense fragment
     */

    private class AddExpenseListener implements AddExpenseFragmentCallback {
        public void addExpense(String title, float amount, int year, int month, int day) {
            controller.addExpense(title, amount, year, month, day);
            if(month == Calendar.getInstance().get(Calendar.MONTH) + 1) {
                updatePBexpense(amount);
            }

            loadProgressBars();
        }
    }

    private class InitialFragmentListener implements InitialFragmentCallback {
        public void showLaunchFragment() {
            startLaunchFragment();
        }
    }

    private class BudgetFragmentListener implements BudgetFragmentCallback {
        // method to communicate with budget fragment
    }
}