package com.example.i7.jobbalagom.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;

import java.util.ArrayList;
import java.util.Collections;

public class SettingsActivity extends AppCompatActivity {
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ListView myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        setStatusbarColor();
        setContentView(R.layout.activity_settings);
        myList = (ListView)findViewById(R.id.settingListView);
        String[] values = new String[] { "Allmänt", "Jobb", "Notifikationer", "Skattesats" };

        list = new ArrayList<String>();

        Collections.addAll(list, values);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new ListListener());
}

    private class ListListener implements ListView.OnItemClickListener{
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            int itemPosition  = position;
            String itemValue = (String) myList.getItemAtPosition(position);

            if (itemValue == "Allmänt") {
                //Start intent or something
                Toast.makeText(getApplicationContext(), "Nothing here yet :)", Toast.LENGTH_LONG).show();
            }else if(itemValue =="Jobb"){
                //Start intent or something
                Toast.makeText(getApplicationContext(), "Nothing here yet :)", Toast.LENGTH_LONG).show();
            }else if(itemValue == "Notifikationer"){
                //Start intent or something
                Toast.makeText(getApplicationContext(), "Nothing here yet :)", Toast.LENGTH_LONG).show();
            }else if(itemValue == "Skattesats"){
                startActivity(new Intent(getApplicationContext(), ChangeTaxActivity.class));
            }
        }
    }



    private void setStatusbarColor() {
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
         //   actionBar.setCustomView(findViewById(R.id.actionbarAdd));

        }
    }



}
