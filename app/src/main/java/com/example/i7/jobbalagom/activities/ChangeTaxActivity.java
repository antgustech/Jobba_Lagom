package com.example.i7.jobbalagom.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.DataHolder;

import java.io.IOException;
import java.util.ArrayList;


public class ChangeTaxActivity extends AppCompatActivity {
    private AutoCompleteTextView textViewKommun;
    private ArrayList<String> kommuner;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller  =  DataHolder.getInstance().getController();
        setContentView(R.layout.activity_change_tax);
        textViewKommun = (AutoCompleteTextView) findViewById(R.id.autoCompleteKommun);

        updateKommuner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,kommuner);

        textViewKommun.setAdapter(adapter);

        textViewKommun.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                /*TODO
                    Lagra den nya informationen i databasen
                 */
            }
        });

    }
    public void updateKommuner(){
        try{
            kommuner = controller.getKommun();
        }catch(IOException e){}
        catch (ClassNotFoundException e2){}
    }

    public void setKommun(String kommun){
        //kommuner = kommun.split(" ");
    }

}