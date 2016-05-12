package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.ChangeTaxFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

import java.util.ArrayList;

/**
 * Created by Strandberg95 on 2016-05-12.
 */
public class ChangeTaxFragment extends Fragment {

    private AutoCompleteTextView textViewKommun;
    private ArrayList<String> kommuner;
    private Controller controller;
    private View calculateTaxBtn;
    private View chooseTaxBtn;
    private CheckBox churchCheckbox;
    private TextView tax;

    private float currentTax = 0;

    private ChangeTaxFragmentCallback taxCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_tax, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        calculateTaxBtn = view.findViewById(R.id.calculateTaxBtn);
        chooseTaxBtn = view.findViewById(R.id.chooseTaxBtn);
        churchCheckbox = (CheckBox)view.findViewById(R.id.churchCheckbox);
        tax = (TextView)view.findViewById(R.id.taxText);
        controller  = Singleton.controller;

        setupKommunView(view);

        calculateTaxBtn.setOnClickListener(new ButtonListener());


    }

    public void setCallBack(ChangeTaxFragmentCallback callback){
        taxCallback = callback;
    }

    /*
    private void getChurchTax(){
        float taxFloat = controller.getChurchTax(textViewKommun.toString());
        tax.setText(Float.toString(taxFloat));
        currentTax = taxFloat;
    }
    */

    /**
     * Retrives tax and sets the textview to it.
     */
    /*
    private void getTax(){
        float taxFloat = controller.getTax(textViewKommun.getText() + "");
        tax.setText(Float.toString(taxFloat));
        currentTax = taxFloat;
    }
    */

    public void setTax(float newTax){
        this.currentTax = newTax;
        tax.setText(newTax + "");
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


    public void updateKommuner(){
        kommuner = controller.getMunicipalities();
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v == v.findViewById(R.id.calculateTaxBtn)){
                if(churchCheckbox.isChecked()){
                    //getChurchTax();
                }
                else{
                    taxCallback.updateTax(controller.getTax(textViewKommun.getText() + ""));
                }
            }
        }
    }

}
