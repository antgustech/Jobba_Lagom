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
import android.widget.Toast;

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

    public void clearAll() {
        inputStartTime.setText("");
        inputEndTime.setText("");
        inputBreakTime.setText("");
    }

    private class ButtonListener implements View.OnClickListener {

        String invalidInputMsg;

        @Override
        public void onClick(View v) {

            String jobTitle = jobSpinner.getSelectedItem().toString();
            String start = inputStartTime.getText().toString();
            String end = inputEndTime.getText().toString();
            String breaktime = inputBreakTime.getText().toString();
            int date = 160505;

            // Check for invalid input

            invalidInputMsg = "Vänligen ange ";

            if(start.equals("") || start.length() != 5 || start.charAt(2) != ':') {
                addError("starttid i formatet HH:MM");
            } else if(end.equals("") || end.length() != 5 || end.charAt(2) != ':') {
                addError("sluttid i formatet HH:MM");
            } else if(breaktime.equals("") || breaktime.contains(":")) {
                invalidInputMsg += "rast i minuter";
            }

            if(!invalidInputMsg.equals("Vänligen ange ")) {
                invalidInputMsg += ".";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                invalidInputMsg = "";
                return;
            }

            int startHour = Integer.parseInt(start.substring(0,2));
            int startMin = Integer.parseInt(start.substring(3));
            int endHour = Integer.parseInt(end.substring(0,2));
            int endMin = Integer.parseInt(end.substring(3));

            if(startHour > 23 || startMin > 59 || endHour > 23 || endMin > 59) {
                invalidInputMsg += "en tid som är giltig";
            } if(startHour > endHour || (startHour == endHour && startMin > endMin)) {
                invalidInputMsg += "en sluttid som är senare än starttiden";
            }

            if(!invalidInputMsg.equals("Vänligen ange ")) {
                invalidInputMsg += ".";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                invalidInputMsg = "";
                return;
            }

            float startTime = Float.parseFloat(start.substring(0,2)) + (Float.parseFloat(start.substring(3))/60);
            float endTime = Float.parseFloat(end.substring(0,2)) + (Float.parseFloat(end.substring(3))/60);
            float breakHours = Float.parseFloat(breaktime)/60;
            float hoursWorked = endTime - startTime - breakHours;

            if(hoursWorked <= 0) {
                Toast.makeText(getActivity(), "Den arbetade tiden är mindre än noll, ska den verkligen vara det?", Toast.LENGTH_LONG).show();
                return;
            }

            callback.addShift(jobTitle, startTime, endTime, hoursWorked, date);
            clearAll();
            Toast.makeText(getActivity(), "Arbetspasset är registrerat.", Toast.LENGTH_LONG).show();
        }

        public void addError(String error) {
            if(invalidInputMsg.charAt(invalidInputMsg.length()-1) == 'e') {
                invalidInputMsg = invalidInputMsg + " " + error;
            } else {
                invalidInputMsg = invalidInputMsg + ", " + error;
            }
        }
    }
}
