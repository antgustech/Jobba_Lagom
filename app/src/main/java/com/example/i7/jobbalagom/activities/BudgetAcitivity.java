package com.example.i7.jobbalagom.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.i7.jobbalagom.R;

public class BudgetAcitivity extends AppCompatActivity {
    private View listIncome;
    private View listExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_acitivity);
    }

}
