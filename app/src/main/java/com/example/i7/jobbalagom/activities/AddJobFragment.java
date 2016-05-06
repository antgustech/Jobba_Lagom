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

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.activities.callback_interfaces.AddJobFragmentCallback;

/**
 * Created by Kajsa on 2016-05-04.
 */

public class AddJobFragment extends Fragment {

    private AddJobFragmentCallback callback;
    private EditText inputTitle;
    private EditText inputWage;
    private TextView tvOB;
    private Button btnAdd;

    private LinearLayout obLayout;
    private EditText inputFromTime;
    private EditText inputToTime;
    private EditText inputOB;
    private RadioButton rbWorkday, rbSaturday, rbSunday;
    private RadioButton rbPercent, rbKronor;
    private RadioGroup rgDay, rgType;

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
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new ButtonListener());
        OBListener obListener = new OBListener();
        tvOB = (TextView) view.findViewById(R.id.tvOB);
        tvOB.setOnClickListener(obListener);
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
    }


    public void setCallBack(AddJobFragmentCallback callback){
        this.callback = callback;
    }

    /**
     * Gathers input information and sends to MainActivity via callback
     */

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d("AddJobFragment", "Add button pressed");
            // callback.update(...);
        }
    }

    /**
     * Displays a layout box where the user can provide information about the OB of the new job
     */

    private class OBListener implements View.OnClickListener {
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
