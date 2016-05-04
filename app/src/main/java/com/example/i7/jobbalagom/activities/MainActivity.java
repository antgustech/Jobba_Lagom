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
import com.example.i7.jobbalagom.activities.callback_interfaces.LaunchFragmentCallback;
import com.example.i7.jobbalagom.activities.callback_interfaces.SetupFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.DataHolder;

public class MainActivity extends AppCompatActivity {
    private View btnTax;
    private View btnTime;
    private View btnWork;
    private View btnBudget;
    private ButtonListener listener;

    private Controller controller;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private LaunchFragment launchFragment;
    private SetupFragment setupFragment;
    private AddExpenseFragment addExpenseFragment;

    private MainActivityBudgetFragment budgetFragment;
    private MainBarFragment barFragment;

    private final int REQUESTCODE_WORKREGISTER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new Controller(this);
        DataHolder.getInstance().setController(controller);
        setStatusbarColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        budgetFragment = new MainActivityBudgetFragment();
        barFragment = new MainBarFragment();

        //launchFragment = new LaunchFragment();
        //launchFragment.setCallBack(new LaunchFragmentListener());
        //changeFragment(launchFragment);
    }

    /**
     * Initializes components.
     */
    public void initComponents() {
        controller = new Controller(this);
        listener = new ButtonListener();
        btnTime = findViewById(R.id.action_a);
        btnTax = findViewById(R.id.action_b);
        btnWork = findViewById(R.id.action_e);
        btnBudget = findViewById(R.id.action_f);
        btnWork.setOnClickListener(listener);
        btnBudget.setOnClickListener(listener);
        btnTax.setOnClickListener(listener);
        btnTime.setOnClickListener(listener);
        fragmentManager = getFragmentManager();
        setStatusbarColor();
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
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
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
     * Button listener for fab
     */
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.action_b) {
                startActivity(new Intent(getApplicationContext(), ChangeTaxActivity.class));
            } else if (v.getId() == R.id.action_f) {
                startActivity(new Intent(getApplicationContext(), BudgetAcitivity.class));
            } else if (v.getId() == R.id.action_a) {
                addExpenseFragment = new AddExpenseFragment();
                addExpenseFragment.setCallBack(new AddExpenseListener());
                changeFragment(addExpenseFragment);
            } else if (v.getId() == R.id.action_e) {
                startWorkRegister();
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
        public void launch(String choice) {
            if(choice.equals("btnLogo")) {
                Log.d("MainActivity", "btnLogo pressed");
            } else if(choice.equals("btnNew")) {
                setupFragment = new SetupFragment();
                setupFragment.setCallBack(new SetupFragmentListener());
                changeFragment(setupFragment);
            } else if(choice.equals("btnKey")) {
                Log.d("MainActivity", "btnKey pressed");
            } else if(choice.equals("btnInfo")) {
                Log.d("MainActivity", "btnInfo pressed");
            }
        }
    }

    /**
     * Listener for setup fragment
     */
    private class SetupFragmentListener implements SetupFragmentCallback {
        @Override
        public void setupUser(String name, String municipality, String incomeLimit) {
            Log.d("SetupFragmentListener", "User information\nNamn: " + name + "\nKommun: " + municipality +
                    "\nFribelopp: " + incomeLimit);
            //ctrl.addUser(...);
            removeFragment(setupFragment);
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
}
