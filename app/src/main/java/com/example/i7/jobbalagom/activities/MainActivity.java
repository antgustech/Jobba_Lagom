package com.example.i7.jobbalagom.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.AddExpenseFragmentCallback;
import com.example.i7.jobbalagom.callbacks.AddJobFragmentCallback;
import com.example.i7.jobbalagom.callbacks.AddShiftFragmentCallback;
import com.example.i7.jobbalagom.callbacks.BudgetFragmentCallback;
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
 * Responsible for changing fragments, and setting graphs at the main screen-
 */

public class MainActivity extends Activity {
    private Controller controller;
    private Fragment currentFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private FloatingActionsMenu floatingMenu;
    private ImageView ivSwipe;
    private TextView tvCSN, tvIncome, tvExpense, tvBalance, tvDate;
    private ProgressBar pbCSN, pbIncome, pbExpense;
    private float monthlyIncomeLimit, csnIncomeLimit;
    private int pbMaxProgress, selectedMonth, selectedYear;
    private Context context;

    /**
     * Initializes this activity. Sets layout.
     *
     * @param savedInstanceState used for saving non persistent data that get's restored if the mainActivity needs to be recreated.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        controller = new Controller(this);
        Singleton.setController(controller);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        userCheck();


    }

    /**
     * Called when the user is interacting with the activity to load the progressbars.
     */

    @Override
    protected void onResume() {
        super.onResume();
        loadProgressBars();
    }

    /**
     * Initializes the components.
     */

    private void initComponents() {
        floatingMenu = (FloatingActionsMenu) findViewById(R.id.floatingMenu);
        FloatingActionButton btnAddShift = (FloatingActionButton) findViewById(R.id.action_addShift);
        FloatingActionButton btnAddExpense = (FloatingActionButton) findViewById(R.id.action_addExpense);
        FloatingActionButton btnAddJob = (FloatingActionButton) findViewById(R.id.action_addJob);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        tvCSN = (TextView) findViewById(R.id.tvCSN);
        tvIncome = (TextView) findViewById(R.id.tvIncome);
        tvExpense = (TextView) findViewById(R.id.tvExpense);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvDate = (TextView) findViewById(R.id.tvDate);
        ivSwipe = (ImageView) findViewById(R.id.ivSwipe);
        pbCSN = (ProgressBar) findViewById(R.id.pbCSN);
        pbIncome = (ProgressBar) findViewById(R.id.pbIncome);
        pbExpense = (ProgressBar) findViewById(R.id.pbExpenses);
        ImageView leftIcon = (ImageView) findViewById(R.id.left_icon);
        ImageView rightIcon = (ImageView) findViewById(R.id.right_icon);
        ButtonListener btnListener = new ButtonListener();
        btnSettings.setOnClickListener(btnListener);
        btnAddShift.setOnClickListener(btnListener);
        btnAddExpense.setOnClickListener(btnListener);
        btnAddJob.setOnClickListener(btnListener);
        tvBalance.setOnClickListener(btnListener);
        tvDate.setOnClickListener(btnListener);
        leftIcon.setOnClickListener(btnListener);
        rightIcon.setOnClickListener(btnListener);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(getParent()));
        selectedMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        selectedYear = Calendar.getInstance().get(Calendar.YEAR) % 100;
        fragmentManager = getFragmentManager();
    }

    /**
     * Check if there already is an registered user.
     */

    private void userCheck() {
        if (!controller.isUserCreated()) {
            startLaunchFragment(true);
        } else {
            loadProgressBars();
            checkForTaxUpdate();
        }
    }

    /**
     * Check if there is a new tax rate available.
     */

    private void checkForTaxUpdate() {
        if (controller.checkConnection()) {
            String municipality = controller.getUserMunicipality();
            TaxUpdateListener listener = new TaxUpdateListener(municipality);
            controller.getUserTax(municipality, listener);
        }
    }

    /**
     * If the back button is pressed, current fragment is closed.
     */

    public void onBackPressed() {
        if (currentFragment == null || currentFragment instanceof LaunchFragment || currentFragment instanceof InitialFragment) {
            super.onBackPressed();
        } else if (currentFragment instanceof SetupFragment || currentFragment instanceof InfoFragment) {
            startLaunchFragment(false);
        } else {
            removeFragment(currentFragment);
        }
    }

    /**
     * Load and update all three progressbars.
     */

    private void loadProgressBars() {
        monthlyIncomeLimit = controller.getIncomeLimit() / 6;
        csnIncomeLimit = controller.getIncomeLimit();
        pbMaxProgress = 100;
        tvDate.setText(controller.getDate(selectedMonth, selectedYear));
        updatePBcsn(controller.getHalfYearIncome(selectedMonth, selectedYear));
        updatePBincome(controller.getMonthlyIncome(selectedMonth, selectedYear));
        updatePBexpense(controller.getMonthlyExpenses(selectedMonth, selectedYear));
    }

    /**
     * Updates CSN progress bar
     */

    private void updatePBcsn(float halfYearIncome) {
        int totalProgress = (int) (halfYearIncome / csnIncomeLimit * 100);
        pbCSN.setProgress(totalProgress);
        float left = csnIncomeLimit - halfYearIncome;
        if (left < 0) {
            tvCSN.setText("Du har överskridit fribeloppet med " + (int) left * -1 + " kr.");
        } else {
            tvCSN.setText((int) left + " kronor tills fribeloppet nås");
        }
    }

    /**
     * Updates income progress bar
     */

    private void updatePBincome(float thisMonthsIncome) {
        int totalProgress = (int) (thisMonthsIncome / monthlyIncomeLimit * 100);
        expandProgressBar(pbIncome, totalProgress);
    }

    /**
     * Updates the expense progress bar
     */

    private void updatePBexpense(float thisMonthsExpenses) {
        int totalProgress = (int) (thisMonthsExpenses / monthlyIncomeLimit * 100);
        expandProgressBar(pbExpense, totalProgress);
    }

    /**
     * Increases the max limit of the progressbars when higher sums are used.
     *
     * @param pb            the progressbar to expand.
     * @param totalProgress the current progressbars max value.
     */

    private void expandProgressBar(ProgressBar pb, int totalProgress) {

        if (totalProgress > pbMaxProgress) {
            pbMaxProgress = totalProgress;
            pbIncome.setMax(pbMaxProgress);
            pbExpense.setMax(pbMaxProgress);
        }
        pb.setProgress(totalProgress);

        float monthlyIncome = controller.getMonthlyIncome(selectedMonth, selectedYear);
        float monthlyExpenses = controller.getMonthlyExpenses(selectedMonth, selectedYear);
        float balance = monthlyIncome - monthlyExpenses;

        tvIncome.setText("Inkomst " + (int) monthlyIncome);
        tvExpense.setText("Utgifter " + (int) monthlyExpenses);
        tvBalance.setText((int) balance + "");
    }

    /**
     * Shows the swipe image when pressing arrow buttons.
     */

    private void showSwipeHint() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_fast);
        ivSwipe.startAnimation(fadeInAnimation);
        ivSwipe.setVisibility(View.INVISIBLE);
    }

    /**
     * Replaces fragments on the view.
     *
     * @param  fragment the new fragment to change to.
     */

    private void changeFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Adds a fragment on top of the stack of fragments
     *
     * @param fragment the fragment to add.
     */

    private void addFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Removes fragments from the view.
     *
     * @param fragment to remove.
     */

    private void removeFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            currentFragment = null;
        }
    }

    /**
     * Launches AddExpenseFragment.
     */

    private void startAddExpenseFragment() {
        currentFragment = new AddExpenseFragment();
        ((AddExpenseFragment) currentFragment).setCallBack(new AddExpenseListener());
        changeFragment(currentFragment);
    }

    /**
     * Launches AddJobFragment.
     */

    private void startAddJobFragment() {
        currentFragment = new AddJobFragment();
        ((AddJobFragment) currentFragment).setCallBack(new AddJobFragmentListener());
        changeFragment(currentFragment);
    }

    /**
     * Launches SetupFragment.
     */

    private void startSetupFragment() {
        currentFragment = new SetupFragment();
        ((SetupFragment) currentFragment).setCallBack(new SetupFragmentListener());
        changeFragment(currentFragment);
    }

    /**
     * Launches LaunchFragment.
     *
     * @param firstTime boolean that checks if this is a first time launch.
     */

    private void startLaunchFragment(boolean firstTime) {
        currentFragment = new LaunchFragment();
        ((LaunchFragment) currentFragment).setCallBack(new LaunchFragmentListener());
        changeFragment(currentFragment);

        if (firstTime) {
            final InitialFragment initialFragment = new InitialFragment();
            addFragment(initialFragment);

            new Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            removeFragment(initialFragment);
                        }
                    }, 4000);
        }
    }

    /**
     * Launches AddShiftFragment.
     */

    private void startAddShiftFragment() {
        currentFragment = new AddShiftFragment();
        ((AddShiftFragment) currentFragment).setCallBack(new AddShiftFragmentListener());
        changeFragment(currentFragment);
    }

    /**
     * Launches BudgetFragment.
     * TODO: Currently not used.
     */

    public void startBudgetFragment() {
        currentFragment = new BudgetFragment();
        ((BudgetFragment) currentFragment).setCallBack(new BudgetFragmentListener());
        changeFragment(currentFragment);
    }

    /**
     * Launches InfoFragment.
     */

    private void startInfoFragment() {
        currentFragment = new InfoFragment();
        changeFragment(currentFragment);
    }

    /**
     * Listener for updating the tax rate.
     */

    private class TaxUpdateListener implements UpdateTaxCallback {
        final String municipality;

        public TaxUpdateListener(String municipality) {
            this.municipality = municipality;
        }

        /**
         * Implemented method checks for tax rate version.
         *
         * @param tax the new tax rate.
         */
        @Override
        public void UpdateTax(float tax) {
            float oldTax = controller.getUserTax();
            if (tax != oldTax) {
                controller.setTax(tax);
            }
        }
    }

    /**
     * Listener for all buttons in the main view.
     */

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            floatingMenu.collapse();
            if (v.getId() == R.id.action_addShift) {
                startAddShiftFragment();
            } else if (v.getId() == R.id.action_addExpense) {
                startAddExpenseFragment();
            } else if (v.getId() == R.id.action_addJob) {
                startAddJobFragment();
            } else if (v.getId() == R.id.btnSettings) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            } else if (v.getId() == R.id.tvBalance) {
                showSwipeHint();
            } else if (v.getId() == R.id.tvDate) {
                showSwipeHint();
            } else if (v.getId() == R.id.left_icon) {
                showSwipeHint();
            } else if (v.getId() == R.id.right_icon) {
                showSwipeHint();
            }
        }
    }

    /**
     * Listener for launchFragment
     */

    private class LaunchFragmentListener implements LaunchFragmentCallback {

        /**
         * Checks which fragment to start.
         *
         * @param choice the pressed button.
         */

        public void navigate(String choice) {
            if (choice.equals("btnNew")) {
                startSetupFragment();
            } else if (choice.equals("btnInfo")) {
                startInfoFragment();
            }
        }

        /**
         * Checks if the app has connection to server or not.
         *
         * @return true if there is a connection.
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
     * Listener for setupFragment
     */

    private class SetupFragmentListener implements SetupFragmentCallback {

        /**
         * Adds a new user to the database in the initial fragment.
         *
         * @param municipality choosen municipality.
         * @param incomeLimit  choosen incomeLimit.
         * @param church       returns true if in the swedish church.
         */

        public void addUser(String municipality, float incomeLimit, boolean church) {
            controller.addUser(municipality, incomeLimit, church);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loadProgressBars();
            removeFragment(currentFragment);
        }
    }

    /**
     * Listener for add jobFragment
     */

    private class AddJobFragmentListener implements AddJobFragmentCallback {

        /**
         * Adds a new job to the database.
         *
         * @param title name of the job.
         * @param wage  the hourly wage of the job.
         */

        public void addJob(String title, Float wage) {
            controller.addJob(title, wage);
            removeFragment(currentFragment);
        }

        /**
         * Adds a new obRate for the addedJob.
         *
         * @param jobTitle  the name of the job.
         * @param day       the day the ob rate is valid.
         * @param startTime the time the ob rate starts.
         * @param endTime   the time the ob rate ends.
         * @param obIndex   the actual ob rate.
         */

        public void addOB(String jobTitle, String day, String startTime, String endTime, float obIndex) {
            controller.addOB(jobTitle, day, startTime, endTime, obIndex);
        }
    }

    /**
     * Listener for add shiftFragment
     */

    private class AddShiftFragmentListener implements AddShiftFragmentCallback {

        /**
         * Adds a new shift to the database.
         *
         * @param jobTitle     name of the job.
         * @param startTime    the time the shift starts.
         * @param endTime      the time the shift ends.
         * @param hoursWorked  endTime-startTime.
         * @param year         the year the shift was done.
         * @param month        the month the shift was done.
         * @param day          the day the shift was done.
         * @param breakMinutes the break in minutes that the user had during the shift.
         */

        public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day, float breakMinutes) {
            controller.caculateShift(jobTitle, startTime, endTime, year, month, day, breakMinutes);
            loadProgressBars();
        }
    }

    /**
     * Listener for add expenseFragment
     */

    private class AddExpenseListener implements AddExpenseFragmentCallback {

        /**
         * Adds an expense to the database.
         *
         * @param title  name of the expense.
         * @param amount sum of the expense.
         * @param year   the year the expense was done.
         * @param month  the month the expense was done.
         * @param day    the day the expense was done.
         */

        public void addExpense(String title, float amount, int year, int month, int day) {
            controller.addExpense(title, amount, year, month, day);
            loadProgressBars();
        }
    }

    /**
     * Listener for budgetFragment.
     * TODO: Currently not used.
     */

    private class BudgetFragmentListener implements BudgetFragmentCallback {
    }

    /**
     * Listener for touch motions on the main graph view.
     */

    private class OnSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;

        /**
         * Creates a new GestureDetector in the given context.
         *
         * @param ctx the current context.
         */

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }


        /**
         * Listener for event.
         *
         * @param v     the view the even occurred in.
         * @param event the kind of event.
         * @return true if a touchevent happend.
         */

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        /**
         * Shows new data if the swipe went right.
         */

        public void onSwipeRight() {
            if (selectedMonth == 1) {
                selectedYear--;
                selectedMonth = 12;
            } else {
                selectedMonth--;
            }
            loadProgressBars();
        }

        /**
         * Shows new data if the swipe went left.
         */

        public void onSwipeLeft() {
            if (selectedMonth == 12) {
                selectedYear++;
                selectedMonth = 1;
            } else {
                selectedMonth++;
            }
            loadProgressBars();
        }

        /**
         * On swipeTop, do nothing.
         */

        public void onSwipeTop() {
        }

        /**
         * On swipeBotton, do nothing.
         */

        public void onSwipeBottom() {
        }

        /**
         * Listener for swipe motions on the main graph view.
         */

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            /**
             * If you swipe down, nothing happends.
             *
             * @param e where the swipe ocurred.
             * @return true if there was a swipe going downwards.
             */

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            /**
             * Checks if the swipe is valid and which direction it happend.
             *
             * @param e1        where the swipe started.
             * @param e2        where the swipe ended.
             * @param velocityX the speed of the swipe motion on the x axis.
             * @param velocityY the speed of the swipe motion on the y axis.
             * @return true of the swipe was valid.
             */

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
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
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
    }
}