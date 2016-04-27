package com.example.i7.jobbalagom.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
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
import com.example.i7.jobbalagom.activities.WorkRegister.SetupFragment;
import com.example.i7.jobbalagom.activities.WorkRegister.SetupFragmentCallback;
import com.example.i7.jobbalagom.activities.WorkRegister.WorkRegisterActivity;
import com.example.i7.jobbalagom.local.Controller;

public class MainActivity extends AppCompatActivity {
    private View btnTax;
    private View btnTime;
    private View btnWork;
    private View btnBudget;
    private ButtonListener bl = new ButtonListener();
    private TimePickerDialog timePickerDialog;
    private Controller ctrl;
    private FragmentManager fragmentManager;
    private SetupFragment setupFragment;
    private FragmentTransaction fragmentTransaction;

    private final int REQUESTCODE_WORKREGISTER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusbarColor();
        ctrl = new Controller();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnTime = findViewById(R.id.action_a);
        btnTax = findViewById(R.id.action_b);
        btnWork = findViewById(R.id.action_e);
        btnBudget = findViewById(R.id.action_f);

        btnWork.setOnClickListener(bl);
        btnBudget.setOnClickListener(bl);
        btnTax.setOnClickListener(bl);
        btnTime.setOnClickListener(bl);

        fragmentManager = getFragmentManager();
        setupFragment = new SetupFragment();
        setupFragment.setCallBack(new SetupUpdater());
        changeFragment(setupFragment);

        //      FragmentManager fragmentManager = getFragmentManager();

//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        TimeRegisterFragment timeRegisterFragment = new TimeRegisterFragment();

        //fragmentTransaction.replace(android.R.id.content, timeRegisterFragment);

        // fragmentTransaction.commit();

    }

    public void startWorkRegister(){
        Intent workRegisterActivity =  new Intent(this, WorkRegisterActivity.class);
        startActivityForResult(workRegisterActivity,REQUESTCODE_WORKREGISTER);
    }

    private void changeFragment(Fragment fragment){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private  void setStatusbarColor(){
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_WORKREGISTER){
            if(resultCode == RESULT_OK){
                String date = data.getStringExtra("Date");
                String timeFrom = data.getStringExtra("TimeFrom");
                String timeTo = data.getStringExtra("TimeTo");

                Log.d("ResultTag",date);
                Log.d("ResultTag",timeFrom);
                Log.d("ResultTag",timeTo);

                //TODO
                //The information above is the info that is going
                //to be used in the calculations to depict our graph
            }
        }

    }

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

        }else if(id == R.id.action_about){
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Click listener for floating aciton button
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.action_b) {
                startActivity(new Intent(getApplicationContext(), ChangeTaxActivity.class));
            }else if(v.getId() == R.id.action_f){
                startActivity(new Intent(getApplicationContext(), BudgetAcitivity.class));
            }else if(v.getId() == R.id.action_a){
                startActivity(new Intent(getApplicationContext(), AddExpenseActivity.class));
            }else if(v.getId() == R.id.action_e){
                startWorkRegister();
            }
    }
    }

    private class SetupUpdater implements SetupFragmentCallback{
        public void setupUser(String name, String area, String freeSum, String title, String hWage, String cb){
            String logMsg = name + area + freeSum + title + hWage + cb;
            Log.d("SetupUpdater", logMsg);
        }

    }
}
