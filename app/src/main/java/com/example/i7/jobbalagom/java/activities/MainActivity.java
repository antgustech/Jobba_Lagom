
package com.example.i7.jobbalagom.java.activities;
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
import com.example.i7.jobbalagom.java.fragments.AddExpenseFragment;
import com.example.i7.jobbalagom.java.fragments.AddJobFragment;
import com.example.i7.jobbalagom.java.fragments.LaunchFragment;
import com.example.i7.jobbalagom.java.fragments.SetupFragment;
import com.example.i7.jobbalagom.java.callback_interfaces.AddExpenseFragmentCallback;
import com.example.i7.jobbalagom.java.callback_interfaces.AddJobFragmentCallback;
import com.example.i7.jobbalagom.java.callback_interfaces.LaunchFragmentCallback;
import com.example.i7.jobbalagom.java.callback_interfaces.SetupFragmentCallback;
import com.example.i7.jobbalagom.java.local.Controller;
import com.example.i7.jobbalagom.java.local.Singleton;
import com.example.i7.jobbalagom.java.localDatabase.DBHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private View btnTax;
    private View btnTime;
    private View btnWork;
    private View btnBudget;
    private View btnAddJob;
    private ButtonListener listener;

    private Controller controller;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    private Fragment currentFragment;

    private AddExpenseFragment addExpenseFragment;
    private AddJobFragment addJobFragment;


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
    private BarDataSet dataset1;
    private BarDataSet dataset2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new Controller(this);
        Singleton.setController(controller);

        setStatusbarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

      //  currentFragment = new LaunchFragment();
      //  ((LaunchFragment) currentFragment).setCallBack(new LaunchFragmentListener());
       // changeFragment(currentFragment);
        // ((LaunchFragment) currentFragment).setCallBack(new LaunchFragmentListener());
        //  changeFragment(currentFragment);
        setupGraphs();
    }

    /**
     * Setups both graphs with data from database
     */
    public void setupGraphs(){
        setupMainBar();
        setData(controller.getUserEarned());
        setupBudgetBar();
        updateDataExpense(controller.getExpenseSum());
        updateDataIncome(controller.getUserIncome());
        currentFragment = new LaunchFragment();
    }

    /**
     * Initializes components.
     */
    public void initComponents() {
        listener = new ButtonListener();
        btnTime = findViewById(R.id.action_a);
        btnWork = findViewById(R.id.action_e);
        btnBudget = findViewById(R.id.action_f);
        btnAddJob = findViewById(R.id.action_addjob);
        btnWork.setOnClickListener(listener);
        btnBudget.setOnClickListener(listener);
        btnTime.setOnClickListener(listener);
        btnAddJob.setOnClickListener(listener);
        fragmentManager = getFragmentManager();
        setStatusbarColor();
    }

    public void onBackPressed() {
        if(currentFragment == null || currentFragment instanceof LaunchFragment) {
            super.onBackPressed();
        } else if(currentFragment instanceof SetupFragment) {
            currentFragment = new LaunchFragment();
            ((LaunchFragment) currentFragment).setCallBack(new LaunchFragmentListener());
            changeFragment(currentFragment);
        } else {
            removeFragment(currentFragment);
        }
    }

    /**
     * Changes fragments
     * @param fragment
     */
    private void changeFragment(Fragment fragment){
       // fragmentManager = getFragmentManager();
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


    /**
     * Setups the statusbar color for older android versions
     */
    private  void setStatusbarColor(){
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    /**
     * Controls hamburger button
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *  Handler for buttons on the hamburger button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_WORKREGISTER){
            if(resultCode == RESULT_OK){

                String timeFrom = data.getStringExtra("TimeFrom");
                String newTimeFrom1 = timeFrom.substring(0,2);
                String newTimeFrom2 = timeFrom.substring(3,5);
                String newTimeFrom3 = newTimeFrom1 + newTimeFrom2;

                String timeTo = data.getStringExtra("TimeTo");
                String newTimeTo1 = timeTo.substring(0,2);
                String newTimeTo2 = timeTo.substring(3,5);
                String newTimeTo3 = newTimeTo1 + newTimeTo2;

                String date = data.getStringExtra("Date");
                String newDate1 = date.substring(0,4);
                String newDate2 = date.substring(5,6);
                String newDate3 = date.substring(7,8);
                String newDate4 = newDate1+newDate2+newDate3;
                //TODO Måste ordna så att det kommer ett jobb namn från textedit.
                controller.addShift("TEST", Float.parseFloat(newTimeFrom3.toString()), Float.parseFloat(newTimeTo3.toString()),Integer.parseInt(newDate4.toString()));
                Toast.makeText(this, "Jobb tillagt", Toast.LENGTH_LONG).show();


            }
        }
    }

    /**
     * Button listener for fab
     */
    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //if (v.getId() == R.id.action_b) {
              //  startActivity(new Intent(getApplicationContext(), ChangeTaxActivity.class));
            //} else

            if (v.getId() == R.id.action_f) {
                startActivity(new Intent(getApplicationContext(), BudgetAcitivity.class));
            } else if (v.getId() == R.id.action_a) {
                currentFragment = new AddExpenseFragment();
                ((AddExpenseFragment) currentFragment).setCallBack(new AddExpenseListener());
                changeFragment(currentFragment);
            } else if (v.getId() == R.id.action_e) {
                startWorkRegister();
            } else if (v.getId() == R.id.action_addjob) {
                currentFragment = new AddJobFragment();
                ((AddJobFragment) currentFragment).setCallBack(new AddJobFragmentListener());
                changeFragment(currentFragment);
            }
        }
    }

    /**
     * Starts the work register activity
     */
    public void startWorkRegister(){
        Intent workRegisterActivity =  new Intent(this, WorkRegisterActivity.class);
        startActivityForResult(workRegisterActivity, REQUESTCODE_WORKREGISTER);
    }

    /**
     * Listener for launch fragment
     */
    private class LaunchFragmentListener implements LaunchFragmentCallback {

        @Override
        public void update(String choice) {
            if(choice.equals("btnLogo")) {
                return;
            } else if(choice.equals("btnNew")) {
                Log.d("MainActivity", "Changing fragment to SetupFragment");
                currentFragment = new SetupFragment();
                ((SetupFragment) currentFragment).setCallBack(new SetupFragmentListener());
                changeFragment(currentFragment);
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

        @Override
        public void update(String name, String municipality, String incomeLimit) {
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

        @Override
        public void update(String info) {
            Log.d("MainActivity", info);
        }
    }

    /**
     * Listener for add expense fragment
     */
    private class AddExpenseListener implements AddExpenseFragmentCallback {
        public void addExpense(String title, String amount, String date) {
            removeFragment(addExpenseFragment);
            Log.d("AddExpenseListener", "Button pressed");
            // --> Send new shift to client database
        }
    }

    //METHODS FOR GRAPHS

    //setup mainbar fragment

    public void setupMainBar(){

        mainChart = (BarChart) findViewById(R.id.mainChart);
        //create data points
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(60000f, 10));
        dataset = new BarDataSet(entries, "Intjänade pengar");

        //create labels
        labels = new ArrayList<String>();
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
    public void setData(Float sum){
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

        dataset1 = new BarDataSet(entries1, "Inkomst");
        dataset2 = new BarDataSet(entries2, "Utgifter");

        //X-axis labels
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Inkomst"); xVals.add("Utgift");;

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(dataset1);
        dataSets.add(dataset2);

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

    public void updateDataExpense(Float expenseSum){
        dataset2.removeEntry(1);
        budgetData.addEntry(new BarEntry(expenseSum,1), 1);
        dataset2.setColor(getResources().getColor(R.color.orange));
        mainDudgetChart.notifyDataSetChanged(); // let the chart know it's data changed
        mainDudgetChart.invalidate(); // refresh
    }

    public void updateDataIncome(Float incomeSum){
        dataset1.removeEntry(0);
        budgetData.addEntry(new BarEntry(incomeSum,0), 0);
        dataset1.setColor(getResources().getColor(R.color.green));
        mainDudgetChart.notifyDataSetChanged(); // let the chart know it's data changed
        mainDudgetChart.invalidate(); // refresh
    }

}
