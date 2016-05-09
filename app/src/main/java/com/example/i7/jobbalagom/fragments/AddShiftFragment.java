package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.activities.MainActivity;
import com.example.i7.jobbalagom.callback_interfaces.AddShiftFragmentCallback;

/**
 * Created by Kajsa on 2016-05-08
 */

public class AddShiftFragment extends Fragment {

    private AddShiftFragmentCallback callback;
    private String[] jobTitles;

    private Spinner jobSpinner;
    private Button btnOK;
    private EditText inputStartTime, inputEndTime, inputBreakTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addshift, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    public void initComponents(View view) {
        btnOK = (Button) view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new ButtonListener());
        inputStartTime = (EditText) view.findViewById(R.id.inputStartTime);
        inputEndTime = (EditText) view.findViewById(R.id.inputEndTime);
        inputBreakTime = (EditText) view.findViewById(R.id.inputBreakTime);

        jobTitles = ((MainActivity) getActivity()).getJobTitles();

        String log = "Lagrade jobb: ";
        for(String s : jobTitles) {
            log += s + " ";
        }
        Log.d("AddShiftFragment", log);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, jobTitles);
        jobSpinner = (Spinner) view.findViewById(R.id.jobTitleList);
        jobSpinner.setAdapter(adapter);
    }

    public void setCallBack(AddShiftFragmentCallback callback){
        this.callback = callback;
    }

    private class ButtonListener implements View.OnClickListener {



        @Override
        public void onClick(View v) {



            String jobTitle = jobSpinner.getSelectedItem().toString();
            String start = inputStartTime.getText().toString();
            String end = inputEndTime.getText().toString();
            String breaktime = inputBreakTime.getText().toString();
            int date = 160505;

            float startTime = Float.parseFloat(start.substring(0,2)) + (Float.parseFloat(start.substring(3))/60);
            float endTime = Float.parseFloat(end.substring(0,2)) + (Float.parseFloat(end.substring(3))/60);
            float breakHours = Float.parseFloat(breaktime)/60;
            float hoursWorked = endTime - startTime - breakHours;

            callback.addShift(jobTitle, startTime, endTime, hoursWorked, date);
        }
    }
}
