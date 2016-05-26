package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.ChangeTaxFragmentCallback;
import com.example.i7.jobbalagom.callbacks.UpdateTaxCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

import java.util.ArrayList;

/**
 * Created by Christoffer, Kajsa, Anton, Morgan and Jakup.
 * Handles the change tax screen.
 */
public class ChangeTaxFragment extends Fragment {

    private AutoCompleteTextView textViewKommun;
    private ArrayList<String> municipalities;
    private Controller controller;
    private Button calculateTaxBtn;
    private CheckBox churchCheckbox;
    private TextView newTaxText, oldTaxText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_tax, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        setupCalculateTax();
        setupMunicipalityView(view);
    }

    private void initComponents(View v){
        newTaxText = (TextView)v.findViewById(R.id.newTaxText);
        oldTaxText = (TextView)v.findViewById(R.id.oldTaxText);
        calculateTaxBtn = (Button)v.findViewById(R.id.calculateTaxBtn);
        churchCheckbox = (CheckBox)v.findViewById(R.id.churchCheckbox);
        controller  = Singleton.controller;
        setOldTax();
    }

    /**
     * Listener for calculateTax button
     */

    public void setupCalculateTax(){
        calculateTaxBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                SetTaxListener callback = new SetTaxListener();
                if(v == v.findViewById(R.id.calculateTaxBtn)){
                    if(churchCheckbox.isChecked()) {
                        controller.getChurchTax(textViewKommun.getText() + "", callback);
                    }
                    else {
                        controller.getTax(textViewKommun.getText() + "", callback);
                    }
                }
            }
        });
    }

    private class SetTaxListener implements UpdateTaxCallback {
        @Override
        public void UpdateTax(float tax) {
            setTaxText(tax + " ");
            controller.setTax(tax);
        }
    }

    public void setTaxText(final String text){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newTaxText.setText(text);
                oldTaxText.setText(text);
            }
        });
    }

    public void setOldTax(){
        oldTaxText.setText(String.valueOf(controller.getTax()));
    }


    /**
     * Set Callback
     * @param callback
     */

    public void setCallBack(ChangeTaxFragmentCallback callback){
    }

    /**
     * Setup the textview to show municipalities.
     */

    public void setupMunicipalityView(View view){
        textViewKommun = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteKommun);
        municipalities = controller.getMunicipalities();
        if(municipalities != null){
        }else{
            textViewKommun.setEnabled(false);
            calculateTaxBtn.setEnabled(false);
            churchCheckbox.setEnabled(false);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,municipalities);
        textViewKommun.setAdapter(adapter);
    }

}
