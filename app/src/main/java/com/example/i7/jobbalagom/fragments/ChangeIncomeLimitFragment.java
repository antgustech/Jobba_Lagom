package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.ChangeIncomeLimitFragmentCallback;

/**
 * Created by Anton, Christoffer, Kajsa, Jakup and Morgan.
 * Handles the change income limit screen.
 */

public class ChangeIncomeLimitFragment extends Fragment {
    private ChangeIncomeLimitFragmentCallback callback;
    private TextView currentIncomeLimitText, csnURLChange;
    private EditText newIncomeLimitField;


    /**
     * Initializes fragment.
     * @param inflater layout object that is used to show the layout of fragment.
     * @param container the parent view this fragment is added to.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchy associated with fragment.
     */

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_income_limit, container, false);
    }


    /**
     * Called after the onCreateView has executed makes final UI initializations.
     * @param  view  this fragment view.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     */
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        setTextEditTax();
    }

    /**
     * Initializes components.
     * @param  v  this fragment view.
     */

    private void initComponents(View v) {

        csnURLChange = (TextView) v.findViewById(R.id.csnURLChange);
        currentIncomeLimitText = (TextView) v.findViewById(R.id.currentIncomeLimitText);
        Button btnChangeIncomeLimit = (Button) v.findViewById(R.id.btnChangeIncomeLimit);
        newIncomeLimitField = (EditText) v.findViewById(R.id.newIncomeLimitField);
        btnChangeIncomeLimit.setOnClickListener(new IncomeLimitListener());
        csnURLChange.setOnClickListener(new IncomeLimitListener());
    }

    /**
     * Listener for changing the incomeLimit.
     */

    private class IncomeLimitListener implements View.OnClickListener{

        /**
         * Check for valid input displays error message if it isn't Sends data via cllback to SettingsActivity.
         * @param  v  this fragment view.
         */
        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.csnURLChange){
                Uri uri = Uri.parse("http://www.csn.se/hogskola/hur-mycket-kan-du-fa/inkomst-fribelopp/1.2568");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

            if(newIncomeLimitField.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Du glömde fylla i fribelopp!", Toast.LENGTH_LONG).show();
                return;
            }
            float newLimit = Float.parseFloat(newIncomeLimitField.getText().toString());
            if(newLimit >0f && newLimit<200000f) {
                callback.setIncomeLimit(newLimit);
                setTextEditTax();
                Toast.makeText(getActivity(), "Fribeloppet är ändrat!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getActivity(), "Fribeloppet måste vara mellan 0-200 000kr.", Toast.LENGTH_LONG).show();
            }



        }
    }

    /**
     * Sets callback.
     * @param callback listener.
     */

    public void setCallBack(ChangeIncomeLimitFragmentCallback callback){
        this.callback=callback;
    }

    /**
     * Sets the current income limit to the income limit retrieved from the internal database.
     */

    private void setTextEditTax(){
        currentIncomeLimitText.setText(String.valueOf(callback.getIncomeLimit()));
    }
}