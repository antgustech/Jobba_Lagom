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
import android.widget.Toast;

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
    private ArrayList<String> municipalities;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, municipalities);
        inputMunicipality.setAdapter(adapter);
    }

    public void updateKommuner() {
        try {
            municipalities = controller.getKommun();
        } catch (IOException e) {
        } catch (ClassNotFoundException e2) {
        }
    }

    public void setCallBack(SetupFragmentCallback callback) {
        this.callback = callback;
    }

    private class ButtonListener implements View.OnClickListener {

        String errorMsg = "";

        @Override
        public void onClick(View view) {

            String name = inputName.getText().toString();
            String municipality = inputMunicipality.getText().toString();
            String incomeLimit = inputIncomeLimit.getText().toString();

            // Check for invalid input

            errorMsg = "Vänligen ange";

            if(name.equals("")) {
                addError("ditt namn");
            }
            if(municipality.equals("")) {
                addError("vilken kommun du bor i");
            }
            if(incomeLimit.equals("")) {
                addError("hur hög din inkomst får vara det här halvåret enligt Centrala Studienämnen");
            }

            if (!errorMsg.equals("Vänligen ange")) {
                errorMsg += ".";
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                return;
            }

            callback.addUser(name, municipality, incomeLimit);
        }

        public void addError(String error) {
            if (errorMsg.charAt(errorMsg.length() - 1) == 'e') {
                errorMsg += " " + error;
            } else {
                errorMsg += ", " + error;
            }
        }
    }
}
