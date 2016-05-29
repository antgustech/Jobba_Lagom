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
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.ChangeTaxFragmentCallback;
import com.example.i7.jobbalagom.callbacks.UpdateTaxCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

import java.util.ArrayList;

/**
 * Created by Christoffer, Kajsa, Anton, Morgan and Jakup.
 * Handles the change tax screen.
 */
public class ChangeTaxFragment extends Fragment {

    private AutoCompleteTextView textViewKommun;
    private ArrayList<String> municipalities;
    private Controller controller;
    private Button calculateTaxBtn;
    private CheckBox churchCheckbox;
    private TextView newTaxText, oldTaxText;


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
        return inflater.inflate(R.layout.fragment_change_tax, container, false);
    }


    /**
     * Called after the onCreateView has executed makes final UI initializations.
     *
     * @param view               this fragment view.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        setupCalculateTax();
        setupMunicipalityView(view);
    }


    /**
     * Initializes components.
     *
     * @param v this fragment v.
     */
    private void initComponents(View v) {
        newTaxText = (TextView) v.findViewById(R.id.newTaxText);
        oldTaxText = (TextView) v.findViewById(R.id.oldTaxText);
        calculateTaxBtn = (Button) v.findViewById(R.id.calculateTaxBtn);
        churchCheckbox = (CheckBox) v.findViewById(R.id.churchCheckbox);
        controller = Singleton.controller;
        setOldTax();
    }

    /**
     * Listener for calculateTax button
     */

    public void setupCalculateTax() {
        calculateTaxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTaxListener callback = new SetTaxListener();
                if (v == v.findViewById(R.id.calculateTaxBtn)) {

                    if(textViewKommun.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Du glömde fylla i kommun!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (churchCheckbox.isChecked()) {
                        controller.getChurchTax(textViewKommun.getText() + "", callback);
                    } else {
                        controller.getUserTax(textViewKommun.getText() + "", callback);
                    }
                }
            }
        });
    }

    public void setTaxText(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newTaxText.setText(text);
                oldTaxText.setText(text);
                Toast.makeText(getActivity(), "Skatten är ändrad!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setOldTax() {
        oldTaxText.setText(String.valueOf(controller.getUserTax()));
    }

    /**
     * Set Callback
     *
     * @param callback
     */

    public void setCallBack(ChangeTaxFragmentCallback callback) {
    }

    /**
     * Setup the textview to show municipalities.
     */

    public void setupMunicipalityView(View view) {
        textViewKommun = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteKommun);
        municipalities = controller.getMunicipalities();
        if (municipalities != null) {
        } else {
            textViewKommun.setEnabled(false);
            calculateTaxBtn.setEnabled(false);
            churchCheckbox.setEnabled(false);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, municipalities);
        textViewKommun.setAdapter(adapter);
    }

    /**
     * Listener fir setting the tax rate.
     */
    private class SetTaxListener implements UpdateTaxCallback {

        /**
         * Updates the tax rate text view and sets the tax in teh database.
         *
         * @param tax the new tax rate as a float.
         */
        @Override
        public void UpdateTax(float tax) {
            setTaxText(tax + " ");
            controller.setTax(tax);
        }
    }

}
