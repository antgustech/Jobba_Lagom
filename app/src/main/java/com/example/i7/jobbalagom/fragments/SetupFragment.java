package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

    private EditText inputIncomeLimit;
    private AutoCompleteTextView inputMunicipality;
    private CheckBox churchCheckboxSetup;
    private TextView csnURL;

    private SetupFragmentCallback callback;


    /**
     * Initializes fragment.
     *
     * @param inflater           layout object that is used to show the layout of fragment.
     * @param container          the parent view this fragment is added to.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchy associated with fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup, container, false);
    }

    /**
     * Called after the onCreateView has executed makes final UI initializations.
     *
     * @param view               this fragment view.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     */

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    /**
     * Initializes components.
     *
     * @param v this fragment view.
     */

    private void initComponents(View v) {
        churchCheckboxSetup = (CheckBox) v.findViewById(R.id.churchCheckboxSetup);
        inputIncomeLimit = (EditText) v.findViewById(R.id.inputIncomeLimit);
        inputMunicipality = (AutoCompleteTextView) v.findViewById(R.id.inputMunicipality);
        csnURL = (TextView) v.findViewById(R.id.csnURL);

        ButtonListener btnListener = new ButtonListener();
        Button btnSetup = (Button) v.findViewById(R.id.btnSetup);
        btnSetup.setOnClickListener(btnListener);
        csnURL.setOnClickListener(btnListener);
        Button btnExit = (Button) v.findViewById(R.id.btnExit);

        Controller controller = Singleton.controller;
        ArrayList<String> municipalities = controller.getMunicipalities();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, municipalities);
        inputMunicipality.setAdapter(adapter);
    }

    /**
     * Set callback.
     *
     * @param callback listener.
     */

    public void setCallBack(SetupFragmentCallback callback) {
        this.callback = callback;
    }

    /**
     * Listener for buttons.
     */

    private class ButtonListener implements View.OnClickListener {

        /**
         * Checks for valid input and displays error message if it's not. Sends data via callback to MainActivity.
         *
         * @param v this fragment v.
         */

        @Override
        public void onClick(View v) {
            String municipality = inputMunicipality.getText().toString();
            String incomeLimit = inputIncomeLimit.getText().toString();
            if(v.getId() == R.id.csnURL){
                Uri uri = Uri.parse("http://www.csn.se/hogskola/hur-mycket-kan-du-fa/inkomst-fribelopp/1.2568");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

            if (incomeLimit.equals("")) {
                Toast.makeText(getActivity(), "Vänligen ange hur hög inkomst du får ha detta halvåret enligt Centrala Studiestödnämnen.", Toast.LENGTH_LONG).show();
                return;

            } else if (Float.parseFloat(incomeLimit) < 0f || Float.parseFloat(incomeLimit) > 200000f) {
                Toast.makeText(getActivity(), "Fribeloppet måste vara mellan 0-200 000kr.", Toast.LENGTH_LONG).show();
                return;
            } else if (municipality.equals("")) {
                Toast.makeText(getActivity(), "Vänligen ange vilken kommun du är folkbokförd i.", Toast.LENGTH_LONG).show();
                return;
            } else if (municipality == null) {
                Toast.makeText(getActivity(), "Vänligen ange en giltig kommun.", Toast.LENGTH_LONG).show();
                return;
            }
            boolean churchTax = true;
            if (churchCheckboxSetup.isChecked()) {
                churchTax = true;
            } else {
                churchTax = false;
            }
            callback.addUser(municipality, Float.parseFloat(incomeLimit), churchTax);
            Toast.makeText(getActivity(), "Applikationen är redo att användas!", Toast.LENGTH_LONG).show();
        }
    }
}
