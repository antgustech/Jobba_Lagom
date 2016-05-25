package com.example.i7.jobbalagom.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.AddExpenseFragmentCallback;
import com.example.i7.jobbalagom.callbacks.AddJobFragmentCallback;
import com.example.i7.jobbalagom.callbacks.AddShiftFragmentCallback;
import com.example.i7.jobbalagom.callbacks.BudgetFragmentCallback;
import com.example.i7.jobbalagom.callbacks.InitialFragmentCallback;
import com.example.i7.jobbalagom.callbacks.LaunchFragmentCallback;
import com.example.i7.jobbalagom.callbacks.SetupFragmentCallback;
import com.example.i7.jobbalagom.callbacks.UpdateTaxCallback;
import com.example.i7.jobbalagom.fragments.AddExpenseFragment;
import com.example.i7.jobbalagom.fragments.AddJobFragment;
import com.example.i7.jobbalagom.fragments.AddShiftFragment;
import com.example.i7.jobbalagom.fragments.BudgetFragment;
import com.example.i7.jobbalagom.fragments.InfoFragment;
import com.example.i7.jobbalagom.fragments.InitialFragment;
import com.example.i7.jobbalagom.fragments.LaunchFragment;
import com.example.i7.jobbalagom.fragments.SetupFragment;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Calendar;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Handles the main functionality of the applikation. Responsible for changing fragments, and setting graphs.
 */

public class MainActivity extends Activity {
    private Controller controller;
    private Fragment currentFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private FloatingActionsMenu floatingMenu;
    private FloatingActionButton btnAddShift, btnAddExpense, btnAddJob;
    private ImageButton btnSettings, btnBudget;
    private TextView tvCSN, tvIncome, tvExpense, tvBalance, tvDate;
    private ProgressBar pbCSN, pbIncome, pbExpense;
    private float monthlyIncomeLimit, csnIncomeLimit;
    private int pbMaxProgress;
    private int selectedMonth, selectedYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new Controller(this);
        Singleton.setController(controller);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        userCheck();
        taxCheck();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProgressBars();
    }

    /**
     * Initializes the components.
     */

    public void initComponents() {
        floatingMenu = (FloatingActionsMenu) findViewById(R.id.floatingMenu);
        btnAddShift = (FloatingActionButton) findViewById(R.id.action_addShift);
        btnAddExpense = (FloatingActionButton) findViewById(R.id.action_addExpense);
        btnAddJob = (FloatingActionButton) findViewById(R.id.action_addJob);
        btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        btnBudget = (ImageButton) findViewById(R.id.btnBudget);
        tvCSN = (TextView) findViewById(R.id.tvCSN);
        tvIncome = (TextView) findViewById(R.id.tvIncome);
        tvExpense = (TextView) findViewById(R.id.tvExpense);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvDate = (TextView) findViewById(R.id.tvDate);
        pbCSN = (ProgressBar) findViewById(R.id.pbCSN);
        pbIncome = (ProgressBar) findViewById(R.id.pbIncome);
        pbExpense = (ProgressBar) findViewById(R.id.pbExpenses);
        AddButtonListener addButtonListener = new AddButtonListener();
        btnSettings.setOnClickListener(new SettingsButtonListener());
        btnBudget.setOnClickListener(new BudgetButtonListener());
        btnAddShift.setOnClickListener(addButtonListener);
        btnAddExpense.setOnClickListener(addButtonListener);
        btnAddJob.setOnClickListener(addButtonListener);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(getParent()));

        selectedMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        selectedYear = Calendar.getInstance().get(Calendar.YEAR) % 100;

        fragmentManager = getFragmentManager();
    }

    /**
     * Check if there already is an registered user.
     */

    public void userCheck() {
        if(!controller.isUserCreated()) {
            startInitialFragment();
        } else {
            loadProgressBars();
        }
    }

    public void taxCheck() {
        if(controller.checkConnection()){
            checkForTaxUpdate();
        }
    }

    /**
     * Check if there is a new tax rate available.
     * TODO: DEN UPPDATERAR INTE FÖR KYRKOSKATT. ELLER?
     */

    private void checkForTaxUpdate(){
        String municipality = controller.getMunicipality();
        TaxUpdateListener listener = new TaxUpdateListener(municipality);
        controller.getTax(municipality, listener);
    }

    /**
     * Listener for updating the tax.
     */

    private class TaxUpdateListener implements UpdateTaxCallback {
        String municipality;
        public TaxUpdateListener(String kommun){
            this.municipality = kommun;
        }
        @Override
        public void UpdateTax(float tax) {
            float oldTax = controller.getTax();
            if(tax != oldTax){
                controller.setTax(tax);
                //Toast.makeText(getBaseContext(), "The tax for " + municipality + " is now updated", Toast.LENGTH_LONG);
            }
        }
    }

    /**
     * If the back button is pressed, current fragment is closed.
     */

    public void onBackPressed() {
        if(currentFragment == null || currentFragment instanceof LaunchFragment || currentFragment instanceof InitialFragment) {
            super.onBackPressed();
        } else if(currentFragment instanceof SetupFragment || currentFragment instanceof InfoFragment) {
            startLaunchFragment();
        } else {
                removeFragment(currentFragment);
        }
    }

    /**
     * Loads all three progressbars.
     */

    public void loadProgressBars() {
        monthlyIncomeLimit = controller.getIncomeLimit()/6;
        csnIncomeLimit = controller.getIncomeLimit();
        pbMaxProgress = 100;
        tvDate.setText(getDate());

        updatePBcsn(controller.getHalfYearIncome(selectedMonth, selectedYear));
        Log.d("MainActivity", "loadProgressBars, (" + selectedYear + "." + selectedMonth + ") total half year income: " + controller.getHalfYearIncome(selectedMonth, selectedYear));

        updatePBincome(controller.getMonthlyIncome(selectedMonth, selectedYear));
        Log.d("MainActivity", "loadProgressBars, (" + selectedYear + "." + selectedMonth + ") monthly income: " + controller.getMonthlyIncome(selectedMonth, selectedYear));

        updatePBexpense(controller.getMonthlyExpenses(selectedMonth, selectedYear));
        Log.d("MainActivity", "loadProgressBars, (" + selectedYear + "." + selectedMonth + ") monthly expenses: " + controller.getMonthlyExpenses(selectedMonth, selectedYear));
    }

    /**
     * Updates CSN progress bar
     */

    public void updatePBcsn(float halfYearIncome) {
        int totalProgress = (int)(halfYearIncome / csnIncomeLimit *100);
        pbCSN.setProgress(totalProgress);
        float left = csnIncomeLimit - halfYearIncome;

        if(left < 0) {
            tvCSN.setText("Du har överskridit det av CSN erhållna fribeloppet med " + (int) left * -1 + " kr");
        } else {
            tvCSN.setText("Du kan tjäna " + (int) left + " kr innan det av CSN erhållna fribeloppet passeras");
        }
    }

    /**
     * Updates income progress bar
     */

    public void updatePBincome(float thisMonthsIncome) {
        int totalProgress = (int)(thisMonthsIncome/monthlyIncomeLimit*100);
        expandProgressBar(pbIncome, totalProgress);
    }

    /**
     * Updates the expense progress bar
     */

    public void updatePBexpense(float thisMonthsExpenses) {
        int totalProgress = (int)(thisMonthsExpenses/monthlyIncomeLimit*100);
        expandProgressBar(pbExpense, totalProgress);
    }

    public void expandProgressBar(ProgressBar pb, int totalProgress) {

        if(totalProgress > pbMaxProgress) {
            pbMaxProgress = totalProgress;
            pbIncome.setMax(pbMaxProgress);
            pbExpense.setMax(pbMaxProgress);
        }
        pb.setProgress(totalProgress);

        float monthlyIncome = controller.getMonthlyIncome(selectedMonth, selectedYear);
        float monthlyExpenses = controller.getMonthlyExpenses(selectedMonth, selectedYear);
        float balance = monthlyIncome-monthlyExpenses;

        tvIncome.setText("Inkomst " + (int)monthlyIncome);
        tvExpense.setText("Utgift " + (int)monthlyExpenses);
        tvBalance.setText((int) balance + "");
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
     * Removes fragments from the view.
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

    public void startInfoFragment() {
        currentFragment = new InfoFragment();
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
            if(choice.equals("btnNew")) {
                startSetupFragment();
            } else if(choice.equals("btnInfo")) {
                startInfoFragment();
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
        public void addUser(String municipality, float incomeLimit, boolean church) {
            controller.addUser(municipality, incomeLimit, church);

            Log.d("MainActivity", "Add user: " + municipality + ", " + incomeLimit + ", " + church);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            loadProgressBars();
            removeFragment(currentFragment);
            Toast.makeText(getBaseContext(), "Appen är redo att börja användas.", Toast.LENGTH_LONG);        }
    }

    /**
     * Listener for add job fragment
     */

    private class AddJobFragmentListener implements AddJobFragmentCallback {
        public void addJob(String title, Float wage) {
            controller.addJob(title, wage);
            removeFragment(currentFragment);
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
            controller.caculateShift(jobTitle, startTime, endTime, year, month, day, breakMinutes );
            loadProgressBars();
        }
    }

    /**
     * Listener for add expense fragment
     */

    private class AddExpenseListener implements AddExpenseFragmentCallback {
        public void addExpense(String title, float amount, int year, int month, int day) {
            controller.addExpense(title, amount, year, month, day);
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

    public String getDate() {
        String month = "";
        switch (selectedMonth) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "Maj";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Okt";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
        }
        return month + " " + selectedYear;
    }

    private class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
            if(selectedMonth == 1) {
                selectedYear--;
                selectedMonth = 12;
                Toast.makeText(getParent(), "Notera att fribeloppet som nu visas representerar perioden Jul-Dec " + selectedYear + ".", Toast.LENGTH_LONG);
            } else {
                selectedMonth--;
            }
            loadProgressBars();
        }

        public void onSwipeLeft() {
            if(selectedMonth == 12) {
                selectedYear++;
                selectedMonth = 1;
                Toast.makeText(getParent(), "Notera att fribeloppet som nu visas representerar perioden Jan-Jun " + selectedYear + ".", Toast.LENGTH_LONG);
            } else {
                selectedMonth++;
            }
            loadProgressBars();
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
}