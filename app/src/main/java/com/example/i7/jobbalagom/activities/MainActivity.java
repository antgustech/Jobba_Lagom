package com.example.i7.jobbalagom.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.activities.WorkRegister.WorkRegisterActivity;
import com.example.i7.jobbalagom.local.Controller;
import com.github.mikephil.charting.charts.BarChart;

public class MainActivity extends AppCompatActivity {
    private View btnTax;
    private View btnTime;
    private View btnWork;
    private View btnBudget;
    private ButtonListener bl = new ButtonListener();
    private TimePickerDialog timePickerDialog;
    private Controller ctrl;
    private BarChart mainChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusbarColor();
        ctrl = new Controller();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainChart = (BarChart) findViewById(R.id.freeSumBar);
        btnTime = findViewById(R.id.action_a);
        btnTax = findViewById(R.id.action_b);
        btnWork = findViewById(R.id.action_e);
        btnBudget = findViewById(R.id.action_f);

        btnWork.setOnClickListener(bl);
        btnBudget.setOnClickListener(bl);
        btnTax.setOnClickListener(bl);
        btnTime.setOnClickListener(bl);


        //      FragmentManager fragmentManager = getFragmentManager();

//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//        TimeRegisterFragment timeRegisterFragment = new TimeRegisterFragment();

        //fragmentTransaction.replace(android.R.id.content, timeRegisterFragment);

        // fragmentTransaction.commit();

    }

    private void setupMainBar(){
        mainChart.setBorderColor(323235);

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

    //Klick listemer för den flytande knappens alternativ.
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
                startActivity(new Intent(getApplicationContext(), WorkRegisterActivity.class));
            }
    }
    }
}
