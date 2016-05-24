package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import com.example.i7.jobbalagom.callback_interfaces.SetupFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;
import java.util.ArrayList;

/**
 * Created by Jakup and Kajsa on 2016-04-27.
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
        inputEmail = (EditText) view.findViewById(R.id.inputEmail);
        inputPassword = (EditText) view.findViewById(R.id.inputPassword);
        inputConfirm = (EditText) view.findViewById(R.id.inputConfirm);
        btnSetup = (Button) view.findViewById(R.id.btnSetup);
        tvBackup = (TextView) view.findViewById(R.id.tvBackup);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnExit = (Button) view.findViewById(R.id.btnExit);
        registerLayout = (RelativeLayout) view.findViewById(R.id.registerLayout);
        registerLayout.setVisibility(View.INVISIBLE);
        ButtonListener btnListener = new ButtonListener();
        BackupListener backupListener = new BackupListener();
        btnSetup.setOnClickListener(btnListener);
        btnRegister.setOnClickListener(backupListener);
        tvBackup.setOnClickListener(backupListener);
        btnExit.setOnClickListener(new ReturnListener());
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

    /**
     * Show the register layout.
     */

    public void showRegisterLayout() {
        registerLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the register layout.
     */

    public void hideRegisterLayout() {
        inputEmail.setText("");
        inputPassword.setText("");
        inputConfirm.setText("");
        registerLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * Listener for the continue button that checks for input validation.
     */
    private class ButtonListener implements View.OnClickListener {
        String errorMsg = "";
        @Override
        public void onClick(View view) {
            String municipality = inputMunicipality.getText().toString();
            String incomeLimit = inputIncomeLimit.getText().toString();
            if(incomeLimit.equals("")) {
                Toast.makeText(getActivity(), "Vänligen ange hur hög inkomst du får ha detta halvåret enligt Centrala Studiestödnämnen.", Toast.LENGTH_LONG).show();
                return;
            }else if(Float.parseFloat(incomeLimit) <0f || Float.parseFloat(incomeLimit)>200000f){
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

    /**
     * Listener for the backupbutton also checks for valid input. Not currently in use.
     */

    private class BackupListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.tvBackup) {
                showRegisterLayout();
            } else if (v.getId() == R.id.btnRegister) {

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String confirm = inputConfirm.getText().toString();
                Log.d("SetupFragment", "Register button pressed, e-mail: " + email + ", password: " + password);
                hideRegisterLayout();
                Toast.makeText(getActivity(), "Din data kommer att lagras i molnet.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Listener that hides the Register layout when pressed.
     */
    private class ReturnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            hideRegisterLayout();
        }
    }
}
