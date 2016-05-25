package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.ChangeTaxFragmentCallback;
import com.example.i7.jobbalagom.callback_interfaces.TaxCallbacks.UpdateTaxCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

import java.util.ArrayList;

/**
 * Created by Christoffer, Kajsa, Anton, Morgan och Jakup.
 */
public class ChangeTaxFragment extends Fragment {

    private AutoCompleteTextView textViewKommun;
    private ArrayList<String> kommuner;
    private Controller controller;
    private View calculateTaxBtn, chooseTaxBtn;
    private CheckBox churchCheckbox;
    private TextView taxTextView;
    private float currentTax = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_tax, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        setupCalculateTax();
        setupKommunView(view);
    }

    private void initComponents(View v){
        taxTextView = (TextView)v.findViewById(R.id.taxText);
        calculateTaxBtn = v.findViewById(R.id.calculateTaxBtn);
        chooseTaxBtn = v.findViewById(R.id.chooseTaxBtn);
        churchCheckbox = (CheckBox)v.findViewById(R.id.churchCheckbox);
        controller  = Singleton.controller;
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

    /**
     *
     */

    private class SetTaxListener implements UpdateTaxCallback {
        @Override
        public void UpdateTax(float tax) {
            setTaxText(tax + "");
        }
    }

    public void setTaxText(final String text){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                taxTextView.setText(text);
            }
        });
    }


    /**
     * Listener for setTaxbutton.
     */

    public void setupChooseTax(){
        chooseTaxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //taxCallback.updateTax(Float.parseFloat(tax.getText().toString()));
            }
        });
    }

    /**
     * Set Callback
     * @param callback
     */

    public void setCallBack(ChangeTaxFragmentCallback callback){
    }

    /**
     * Setup the textview to show kommuner.
     */

    public void setupKommunView(View view){
        textViewKommun = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteKommun);
        updateKommuner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,kommuner);
        textViewKommun.setAdapter(adapter);
    }

    /**
     * Get's Municipalities as a list.
     */

    public void updateKommuner(){
        kommuner = controller.getMunicipalities();
        if(kommuner != null){

        }else{
            textViewKommun.setEnabled(false);
            calculateTaxBtn.setEnabled(false);
            chooseTaxBtn.setEnabled(false);
            churchCheckbox.setEnabled(false);
        }
    }
}
