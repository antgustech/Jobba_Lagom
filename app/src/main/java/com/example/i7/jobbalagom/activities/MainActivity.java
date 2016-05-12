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
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.AddExpenseFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.AddJobFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.AddShiftFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.ChangeTaxFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.LaunchFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.SetupFragmentCallback;
import com.example.i7.jobbalagom.fragments.AddExpenseFragment;
import com.example.i7.jobbalagom.fragments.AddJobFragment;
import com.example.i7.jobbalagom.fragments.AddShiftFragment;
import com.example.i7.jobbalagom.fragments.ChangeTaxFragment;
import com.example.i7.jobbalagom.fragments.LaunchFragment;
import com.example.i7.jobbalagom.fragments.SetupFragment;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;
import com.example.i7.jobbalagom.localDatabase.DBHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private View btnAddExpense;
    private View btnAddShift;
    private View btnBudget;
    private View btnAddJob;
    private View btnChangeTax;

    private ButtonListener btnListener;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private Controller controller;
    private DBHelper dbHelper;
    private final int REQUESTCODE_WORKREGISTER = 1;

    //Mainbar
    private BarChart mainChart;
    private BarData data;
    private BarDataSet dataset;
    private ArrayList<String> labels;

    //Budgetbar
    private HorizontalBarChart mainDudgetChart;
    private BarData budgetData;
    private BarDataSet incomeSet;
    private BarDataSet expenseSet;

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
     * Setups both graphs with data from database
     */

    public void setupGraphs(){
        setupMainBar();
        updateCSNChart(controller.getTotalIncome());
        setupBudgetBar();
        updateExpenseChart(controller.getTotalExpense());
        updateIncomeChart(controller.getTotalIncome());
    }

    /**
     * Initializes components.
     */

    public void initComponents() {
        btnAddExpense = findViewById(R.id.btnAddExpense);

        //btnAddShift = findViewById(R.id.btnAddShift);
        btnAddShift = findViewById(R.id.action_addshift);
       // btnBudget = findViewById(R.id.btnBudget);
        btnAddJob = findViewById(R.id.action_addjob);
        btnChangeTax = findViewById(R.id.action_changeActivity);

        btnListener = new ButtonListener();
        btnAddShift.setOnClickListener(btnListener);
//        btnBudget.setOnClickListener(btnListener);
        // btnChangeTax.setOnClickListener(btnListener);
        btnAddExpense.setOnClickListener(btnListener);
        btnAddJob.setOnClickListener(btnListener);
        btnChangeTax.setOnClickListener(btnListener);

        fragmentManager = getFragmentManager();
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

    public void startChangeTaxFragment() {
        currentFragment = new ChangeTaxFragment();
        ((ChangeTaxFragment) currentFragment).setCallBack(new ChangeTaxListener());
        changeFragment(currentFragment);
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

    public void startAddShiftFragment() {
       currentFragment = new AddShiftFragment();
      ((AddShiftFragment) currentFragment).setCallBack(new AddShiftFragmentListener());
      changeFragment(currentFragment);
    }

    /**
     * Button btnListener for fab
     */

    private class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            if (v.getId() == R.id.btnAddExpense) {
                startAddExpenseFragment();
            } else if (v.getId() == R.id.action_addshift) {
                startAddShiftFragment();
            } else if (v.getId() == R.id.action_addjob) {
                startAddJobFragment();
            } else if (v.getId() == R.id.action_changeActivity){
                startChangeTaxFragment();
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
                setupGraphs();
                removeFragment(currentFragment);
            } else if(choice.equals("btnInfo")) {
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        }
    }

    /**
     * Listener for setup fragment
     */

    private class SetupFragmentListener implements SetupFragmentCallback {
        public void addUser(String name, String municipality, float incomeLimit) {
            Log.d("MainActivity", "User information\nNamn: " + name + "\nKommun: " + municipality + "\nFribelopp: " + incomeLimit);
            controller.addUser(name, municipality, incomeLimit);
            setupGraphs();
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

    private class AddShiftFragmentListener implements AddShiftFragmentCallback {
        public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int date) {
            float income = controller.addShift(jobTitle, startTime, endTime, hoursWorked, date);
            Log.d("MainActivity", "Uppdaterar csn- och inkomstgrafer: " + income + " kr");
            updateIncomeChart(income);
            updateCSNChart(income);
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
            updateExpenseChart(amount);
        }
    }

    //ChangeTaxListener

    private class ChangeTaxListener implements ChangeTaxFragmentCallback {

        @Override
        public void updateTax(float tax) {
                ((ChangeTaxFragment) currentFragment).setTax(tax);
        }
    }


    //METHODS FOR GRAPHS

    public void setupMainBar(){

        mainChart = (BarChart) findViewById(R.id.mainChart);

        //create data points
        ArrayList<BarEntry> entries = new ArrayList<>();
        //entries.add(new BarEntry(60000f, 10));
        entries.add(new BarEntry(controller.getIncomeLimit(), 10));
        dataset = new BarDataSet(entries, "Intjänade pengar");

        //create labels
        labels = new ArrayList<>();
        labels.add(" ");
        data = new BarData(labels, dataset);
        mainChart.setData(data); // set the data and list of lables into chart

        //description
        mainChart.setDescription("");  // set the description
        mainChart.setScaleYEnabled(false);
        mainChart.setTouchEnabled(false);
        mainChart.animateY(2000);

    }

    //Updates the chart with specified sum.
    public void updateCSNChart(float sum){
        dataset.removeEntry(0);
        data.addEntry(new BarEntry(sum,0), 0);
        dataset.setColor(getResources().getColor(R.color.colorPrimary));
        mainChart.notifyDataSetChanged(); // let the chart know it's data changed
        mainChart.invalidate(); // refresh
    }


    //BUDGET FRAGMENT METHODS:


    public void setupBudgetBar(){
        mainDudgetChart = (HorizontalBarChart) findViewById(R.id.mainBudgetChart);

        //create data points
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        entries1.add(new BarEntry(10000, 5));
        entries2.add(new BarEntry(10000, 5));

        incomeSet = new BarDataSet(entries1, "Inkomst");
        expenseSet = new BarDataSet(entries2, "Utgifter");

        //X-axis labels
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Inkomst"); xVals.add("Utgift");;

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(incomeSet);
        dataSets.add(expenseSet);

        //Add to chart
        budgetData = new BarData(xVals, dataSets);
        mainDudgetChart.setData(budgetData);

        //Description and animation
        mainDudgetChart.setDescription("");  // set the description
        mainDudgetChart.setScaleYEnabled(false);
        mainDudgetChart.setTouchEnabled(false);
        mainDudgetChart.animateY(2000);
    }

    //setdata methods:

    public void updateExpenseChart(float expenseSum){
        expenseSet.removeEntry(1);
        budgetData.addEntry(new BarEntry(expenseSum, 1), 1);
        expenseSet.setColor(getResources().getColor(R.color.orange));
        mainDudgetChart.notifyDataSetChanged(); // let the chart know it's data changed
        mainDudgetChart.invalidate(); // refresh
    }

    public void updateIncomeChart(float incomeSum){
        incomeSet.removeEntry(0);
        budgetData.addEntry(new BarEntry(incomeSum, 0), 0);
        incomeSet.setColor(getResources().getColor(R.color.green));
        mainDudgetChart.notifyDataSetChanged(); // let the chart know it's data changed
        mainDudgetChart.invalidate(); // refresh
    }

    public String[] getJobTitles() {
        return controller.getJobTitles();
    }
}
