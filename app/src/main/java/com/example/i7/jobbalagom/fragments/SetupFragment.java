package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.SetupFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jakup and Kajsa on 2016-04-27.
 */

public class SetupFragment extends Fragment {

    private SetupFragmentCallback callback;
    private EditText inputName;
    private EditText inputIncomeLimit;
    private AutoCompleteTextView inputMunicipality;
    private ArrayList<String> kommuner;
    private Button btnSetup;
    private ButtonListener buttonListener;

    private Controller controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    public void initComponents(View view) {
        buttonListener = new ButtonListener();
        inputName = (EditText) view.findViewById(R.id.inputName);
        inputIncomeLimit = (EditText) view.findViewById(R.id.inputIncomeLimit);
        inputMunicipality = (AutoCompleteTextView) view.findViewById(R.id.inputMunicipality);
        btnSetup = (Button) view.findViewById(R.id.btnSetup);
        btnSetup.setOnClickListener(buttonListener);

        controller = Singleton.controller;
        updateKommuner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,kommuner);
        inputMunicipality.setAdapter(adapter);
    }

    public void updateKommuner(){
        try{
            kommuner = controller.getKommun();
        }catch(IOException e){}
        catch (ClassNotFoundException e2){}
    }

    public void setCallBack(SetupFragmentCallback callback){
        this.callback = callback;
    }

    private class ButtonListener implements View.OnClickListener {

        String errorMsg = "UPS!";
        boolean inputOK = true;

        @Override
        public void onClick(View view) {

            View[] components = new View[6];
            components[0] = inputName;
            components[1] = inputMunicipality;
            components[2] = inputIncomeLimit;

            for (View c : components) {
                if (c instanceof EditText) {
                    EditText e = (EditText) c;
                    if (e.getText().toString().equals("")) {
                        e.setError(errorMsg);
                        inputOK = false;
                    }
                } else if (c instanceof AutoCompleteTextView) {
                    AutoCompleteTextView a = (AutoCompleteTextView) c;
                    if (a.getText().toString().equals("")) {
                        a.setError(errorMsg);
                        inputOK = false;
                    }
                }
            }

            if (inputOK) {
                String name = inputName.getText().toString();
                String municipality = inputMunicipality.getText().toString();
                String incomeLimit = inputIncomeLimit.getText().toString();
                callback.addUser(name, municipality, incomeLimit);
            }
        }
    }
}
