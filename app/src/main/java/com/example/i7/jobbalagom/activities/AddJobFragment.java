package com.example.i7.jobbalagom.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.activities.callback_interfaces.AddJobFragmentCallback;

/**
 * Created by Kajsa on 2016-05-04.
 */

public class AddJobFragment extends Fragment {

    private AddJobFragmentCallback callback;
    private EditText inputTitle;
    private EditText inputWage;
    private TextView tvAddOB;
    private Button btnAddJob;
    private LinearLayout obLayout;
    private EditText inputFromTime;
    private EditText inputToTime;
    private EditText inputOB;
    private RadioButton rbWorkday, rbSaturday, rbSunday;
    private RadioButton rbPercent, rbKronor;
    private RadioGroup rgDay, rgType;
    private Button btnAddOB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addjob, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    public void initComponents(View view) {
        inputTitle = (EditText) view.findViewById(R.id.inputTitle);
        inputWage = (EditText) view.findViewById(R.id.inputWage);
        btnAddJob = (Button) view.findViewById(R.id.btnAdd);
        btnAddJob.setOnClickListener(new BtnAddJobListener());
        tvAddOB = (TextView) view.findViewById(R.id.tvAddOB);
        tvAddOB.setOnClickListener(new BtnDisplayOBListener());
        obLayout = (LinearLayout) view.findViewById(R.id.obLayout);
        obLayout.setOnClickListener(new ReturnListener());
        obLayout.setVisibility(View.INVISIBLE);
        inputFromTime = (EditText) view.findViewById(R.id.inputFromTime);
        inputToTime = (EditText) view.findViewById(R.id.inputToTime);
        inputOB = (EditText) view.findViewById(R.id.inputOB);
        rbWorkday = (RadioButton) view.findViewById(R.id.rbWorkday);
        rbSaturday = (RadioButton) view.findViewById(R.id.rbSaturday);
        rbSunday = (RadioButton) view.findViewById(R.id.rbSunday);
        rbPercent = (RadioButton) view.findViewById(R.id.rbPercent);
        rbKronor = (RadioButton) view.findViewById(R.id.rbKronor);
        rgDay = (RadioGroup) view.findViewById(R.id.rgDay);
        rgType = (RadioGroup) view.findViewById(R.id.rgType);
        btnAddOB = (Button) view.findViewById(R.id.btnAddOB);
        btnAddOB.setOnClickListener(new BtnAddOBListener());
    }


    public void setCallBack(AddJobFragmentCallback callback){
        this.callback = callback;
    }

    /**
     * Gathers input information and sends to MainActivity via callback
     */

    private class BtnAddJobListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("AddJobFragment", "Add button pressed");
            // callback.addUser(...);
        }
    }

    private class BtnAddOBListener implements View.OnClickListener {
        CharSequence emptyInputMsg;
        CharSequence invalidInputMsg;

        public void onClick(View v) {

            // Check for empty input fields

            emptyInputMsg = "Vänta lite, du glömde fylla i";

            if (rgDay.getCheckedRadioButtonId() == -1) {
                Log.d("OB layout", "rdDay: " + rgDay.getCheckedRadioButtonId());
                addError("typ av dag");
            }
            if (inputFromTime.getText().toString().equals("")) {
                addError("från tid");
            }
            if (inputToTime.getText().toString().equals("")) {
                addError("till tid");
            }
            if (inputOB.getText().toString().equals("")) {
                addError("OB");
            }
            if (rgType.getCheckedRadioButtonId() == -1) {
                Log.d("OB layout", "rdType: " + rgType.getCheckedRadioButtonId());
                addError("enhet");
            }
            emptyInputMsg = emptyInputMsg + ".";

            if(!emptyInputMsg.equals("Vänta lite, du glömde fylla i.")) {
                Toast.makeText(getActivity(), emptyInputMsg, Toast.LENGTH_LONG).show();
                return;
            }

            // Check for invalid input

            String fromTime = inputFromTime.getText().toString();
            String toTime = inputToTime.getText().toString();

            if(fromTime.length() != 5 || toTime.length() != 5) {
                invalidInputMsg = "Vänligen ange tid i formatet HH:MM";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                return;
            }
            if(fromTime.charAt(2) != ':' || toTime.charAt(2) != ':') {
                invalidInputMsg = "Vänligen ange tid i formatet HH:MM.";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                return;
            }

            int fromTimeHour = Integer.parseInt(fromTime.substring(0,2));
            int fromTimeMin = Integer.parseInt(fromTime.substring(3));
            int toTimeHour = Integer.parseInt(toTime.substring(0,2));
            int toTimeMin = Integer.parseInt(toTime.substring(3));

            if(fromTimeHour > 23 || fromTimeMin > 59 || toTimeHour > 23 || toTimeMin > 59) {
                invalidInputMsg = "Tiden som angetts är inte en giltig tid.";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                return;
            }
            if(fromTimeHour > toTimeHour) {
                invalidInputMsg = "De angivna tiderna stämmer inte överens.";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                return;
            }

            Log.d("obLayout", "Input giltig");
        }

        public void addError(String error) {
            if(emptyInputMsg.charAt(emptyInputMsg.length()-1) == 'i') {
                emptyInputMsg = emptyInputMsg + " " + error;
            } else {
                emptyInputMsg = emptyInputMsg + ", " + error;
            }
        }
    }

    /**
     * Displays a layout box where the user can provide information about the OB of the new job
     */

    private class BtnDisplayOBListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            obLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Enables the user to press outside of the OB layout box to return to the main AddJobFragment
     */

    private class ReturnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            obLayout.setVisibility(View.INVISIBLE);
            inputFromTime.setText("");
            inputToTime.setText("");
            inputOB.setText("");
            rgDay.clearCheck();
            rgType.clearCheck();
        }
    }
}
