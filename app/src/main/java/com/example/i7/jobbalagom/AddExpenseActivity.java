package com.example.i7.jobbalagom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.i7.jobbalagom.activities.MainActivity;


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
}
