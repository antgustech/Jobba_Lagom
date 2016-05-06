package com.example.i7.jobbalagom.java.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.java.callback_interfaces.AddExpenseFragmentCallback;
import com.example.i7.jobbalagom.java.local.Controller;
import com.example.i7.jobbalagom.java.local.Singleton;

public class AddExpenseFragment extends Fragment {

    private EditText inputTitle;
    private EditText inputAmount;
    private EditText inputDate;
    private ImageButton btnOK;
    private AddExpenseFragmentCallback callback;
    private Controller controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        controller= Singleton.controller;
        return inflater.inflate(R.layout.fragment_add_expense, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputTitle = (EditText) view.findViewById(R.id.inputTitle);
        inputAmount = (EditText) view.findViewById(R.id.inputWage);
        inputDate = (EditText) view.findViewById(R.id.inputDate);
        btnOK = (ImageButton) view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new ButtonListener());

    }

    public void setCallBack(AddExpenseFragmentCallback callback){
        this.callback = callback;
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        //TODO input checking
        public void onClick(View v) {
            Log.d("AddExpenseFragment", "Button pressed");
            controller.addExpense(inputTitle.toString(), Float.parseFloat(inputAmount.getText().toString()),  Integer.parseInt(inputDate.getText().toString()));
            Toast.makeText(getContext(), "Utgift tillagd", Toast.LENGTH_LONG).show();



        }
    }
}
