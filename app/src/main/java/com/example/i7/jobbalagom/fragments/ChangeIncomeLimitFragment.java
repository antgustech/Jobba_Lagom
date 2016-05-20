package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.ChangeIncomeLimitFragmentCallback;

/**
 * Created by Anton Gustafsson on 2016-05-13.
 */
//TODO Not done yet!
public class ChangeIncomeLimitFragment extends Fragment {
    private ChangeIncomeLimitFragmentCallback callback;
    private TextView currentIncomeLimitText;
    private View btnChangeIncomeLimit;
    private EditText newIncomeLimitField;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_income_limit, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);

    }

    public void initComponents(View view) {
        currentIncomeLimitText = (TextView) view.findViewById(R.id.currentIncomeLimitText);
        btnChangeIncomeLimit = (View) view.findViewById(R.id.btnChangeIncomeLimit);
        newIncomeLimitField = (EditText) view.findViewById(R.id.newIncomeLimitField);
        setTextEditTax();


        btnChangeIncomeLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newLimit = Float.parseFloat(newIncomeLimitField.getText().toString());

                if(newLimit >0f && newLimit<200000f) {
                    callback.setIncomeLimit(newLimit);
                    setTextEditTax();
                    Toast.makeText(getActivity(), "Fribeloppet ändrat", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Fribeloppet måste vara mellan 0-200 000kr.", Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    public void setCallBack(ChangeIncomeLimitFragmentCallback callback){
        this.callback=callback;
    }

    private void setTextEditTax(){
        currentIncomeLimitText.setText(String.valueOf(callback.getIncomeLimit()));
    }
}
