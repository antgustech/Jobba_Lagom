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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.SetupFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;
import java.util.ArrayList;

/**
 * Created by Jakup, Kajsa, Morgan, Anton and Christoffer.
 * Handles the setup screen.
 */

public class SetupFragment extends Fragment {

    private TextView tvBackup;
    private EditText inputIncomeLimit,inputEmail, inputPassword, inputConfirm;
    private AutoCompleteTextView inputMunicipality;
    private CheckBox churchCheckboxSetup;
    private boolean churchTax = true;
    private Button btnSetup, btnRegister, btnExit;
    private RelativeLayout registerLayout;

    private Controller controller;
    private SetupFragmentCallback callback;
    private ArrayList<String> municipalities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    /**
     * Initializes components.
     */

    public void initComponents(View view) {
        churchCheckboxSetup =(CheckBox) view.findViewById(R.id.churchCheckboxSetup);
        inputIncomeLimit = (EditText) view.findViewById(R.id.inputIncomeLimit);
        inputMunicipality = (AutoCompleteTextView) view.findViewById(R.id.inputMunicipality);
        ButtonListener btnListener = new ButtonListener();
        btnSetup = (Button) view.findViewById(R.id.btnSetup);
        btnSetup.setOnClickListener(btnListener);
        btnExit = (Button) view.findViewById(R.id.btnExit);

        controller = Singleton.controller;
        municipalities = controller.getMunicipalities();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, municipalities);
        inputMunicipality.setAdapter(adapter);
    }

    /**
     * Set callback.
     * @param callback listener.
     */

    public void setCallBack(SetupFragmentCallback callback) {
        this.callback = callback;
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String municipality = inputMunicipality.getText().toString();
            String incomeLimit = inputIncomeLimit.getText().toString();
            if(incomeLimit.equals("")) {
                Toast.makeText(getActivity(), "Vänligen ange hur hög inkomst du får ha detta halvåret enligt Centrala Studiestödnämnen.", Toast.LENGTH_LONG).show();
                return;

            } else if(Float.parseFloat(incomeLimit) <0f || Float.parseFloat(incomeLimit)>200000f){
                Toast.makeText(getActivity(), "Fribeloppet måste vara mellan 0-200 000kr.", Toast.LENGTH_LONG).show();
                return;
            } else if(municipality.equals("")) {
                Toast.makeText(getActivity(), "Vänligen ange vilken kommun du är folkbokförd i.", Toast.LENGTH_LONG).show();
                return;
            }
            else if(municipality == null ) {
                Toast.makeText(getActivity(), "Vänligen ange en giltig kommun.", Toast.LENGTH_LONG).show();
                return;
            }
            if(churchCheckboxSetup.isChecked()) {
                churchTax= true;
            }
            else {
                churchTax= false;
            }
            callback.addUser(municipality, Float.parseFloat(incomeLimit), churchTax);
        }
    }
}
