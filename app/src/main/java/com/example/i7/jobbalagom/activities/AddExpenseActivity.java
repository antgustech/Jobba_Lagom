package com.example.i7.jobbalagom.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.local.Expense;

/**
 * Created by Jakup on some date I don't remember
 */

public class AddExpenseActivity extends AppCompatActivity {
    private EditText inDate;
    private EditText inTitle;
    private EditText inSum;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        inDate = (EditText)findViewById(R.id.inDate);
        inTitle = (EditText)findViewById(R.id.inTitle);
        inSum = (EditText)findViewById(R.id.inSum);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new ButtonListener());
        setupActionBar();
        setStatusbarColor();
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(inDate.getText().toString() == null || inTitle.getText().toString() == null || inSum.getText().toString() == null){
                Expense expense = new Expense(inDate.getText().toString(), inTitle.getText().toString(), inSum.getText().toString());
                }else{

            }
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private  void setStatusbarColor(){
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}
