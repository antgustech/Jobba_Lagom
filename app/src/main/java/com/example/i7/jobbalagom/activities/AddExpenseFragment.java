package com.example.i7.jobbalagom.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.i7.jobbalagom.R;

public class AddExpenseFragment extends Fragment {

    private EditText inputTitle;
    private EditText inputAmount;
    private EditText inputDate;
    private AddExpenseFragmentCallback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_expense, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputTitle = (EditText) view.findViewById(R.id.inputTitle);
        inputAmount = (EditText) view.findViewById(R.id.inputAmount);
        inputDate = (EditText) view.findViewById(R.id.inputDate);
    }

    public void setCallBack(AddExpenseFragmentCallback callback){
        this.callback = callback;
    }
}
