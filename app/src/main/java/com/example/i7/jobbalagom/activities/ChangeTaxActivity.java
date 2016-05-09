package com.example.i7.jobbalagom.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

import java.io.IOException;
import java.util.ArrayList;


public class ChangeTaxActivity extends AppCompatActivity {
    private AutoCompleteTextView textViewKommun;
    private ArrayList<String> kommuner;
    private Controller controller;
    private View calculateTaxBtn;
    private View chooseTaxBtn;
    private View churchCheckbox;
    private TextView tax;
    private Float currentTax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculateTaxBtn = findViewById(R.id.calculateTaxBtn);
        chooseTaxBtn = findViewById(R.id.chooseTaxBtn);
        churchCheckbox = findViewById(R.id.churchCheckbox);
        tax = (TextView)findViewById(R.id.taxText);
        controller  = Singleton.controller;
        setContentView(R.layout.activity_change_tax);

        textViewKommun = (AutoCompleteTextView) findViewById(R.id.autoCompleteKommun);
        updateKommuner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,kommuner);

        calculateTaxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(churchCheckbox.isEnabled()){
                    getChurhTax();
                }else{
                    getTax();
                }
                chooseTaxBtn.setEnabled(true);
            }
        });

        chooseTaxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.setTax(currentTax);
            }
        });
    }


    /**
     * Retrives Churchtax and sets the textview to it.
     */
    public void getChurhTax(){
        float taxFloat = controller.getChurchTax(textViewKommun.toString());
        tax.setText(Float.toString(taxFloat));
        currentTax = taxFloat;
    }

    /**
     * Retrives tax and sets the textview to it.
     */
    public void getTax(){
        float taxFloat = controller.getTax(textViewKommun.toString());
        tax.setText(Float.toString(taxFloat));
        currentTax = taxFloat;
    }


    /**
     * Setup the textview to show kommuner.
     */
    public void setupKommunView(){
        textViewKommun = (AutoCompleteTextView) findViewById(R.id.autoCompleteKommun);
        updateKommuner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,kommuner);
        textViewKommun.setAdapter(adapter);
        textViewKommun.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //something
            }
        });
    }


    public void updateKommuner(){
        try{
            kommuner = controller.getKommun();
        }catch(IOException e){}
        catch (ClassNotFoundException e2){}
    }

    /**
     * Setups the statusbar color for older android versions
     */
    private  void setStatusbarColor(){
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

}