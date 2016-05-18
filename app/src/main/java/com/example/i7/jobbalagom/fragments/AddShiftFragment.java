package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.AddShiftFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

/**
 * Created by Kajsa on 2016-05-08
 */

public class AddShiftFragment extends Fragment {

    private AddShiftFragmentCallback callback;
    private Controller controller;
    private String[] jobTitles;

    private Spinner jobSpinner;
    private Button btnAddShift, btnAddJob;
    private EditText inputStart, inputEnd, inputBreak, inputDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addshift, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    public void initComponents(View view) {
        controller = Singleton.controller;
        btnAddShift = (Button) view.findViewById(R.id.btnAddShift);
        btnAddShift.setOnClickListener(new ButtonAddShiftListener());
        inputStart = (EditText) view.findViewById(R.id.inputStart);
        inputEnd = (EditText) view.findViewById(R.id.inputEnd);
        inputBreak = (EditText) view.findViewById(R.id.inputBreak);
        inputDate = (EditText) view.findViewById(R.id.inputDate);

        jobTitles = controller.getJobTitles();

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row, R.id.text, jobTitles);
        jobSpinner = (Spinner) view.findViewById(R.id.jobSpinner);
        jobSpinner.setAdapter(adapter);

        if (jobTitles.length == 0) {
            Toast toast = Toast.makeText(getActivity(), "Du måste lägga till ett jobb först!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }

    public void setCallBack(AddShiftFragmentCallback callback) {
        this.callback = callback;
    }

    public void clearAll() {
        inputStart.setText("");
        inputEnd.setText("");
        inputBreak.setText("");
    }

    private class ButtonAddShiftListener implements View.OnClickListener {

        String errorMsg;

        @Override
        public void onClick(View v) {

            String jobTitle = jobSpinner.getSelectedItem().toString();
            String start = inputStart.getText().toString();
            String end = inputEnd.getText().toString();
            String breaktime = inputBreak.getText().toString();
            String date = inputDate.getText().toString();

            // Check for invalid input

            errorMsg = "Vänligen ange";

            if (start.equals("") || start.length() != 5 || start.charAt(2) != ':') {
                addError("starttid i formatet HH:MM");
            }
            if (end.equals("") || end.length() != 5 || end.charAt(2) != ':') {
                addError("sluttid i formatet HH:MM");
            }
            if (breaktime.equals("") || breaktime.contains(":")) {
                addError("rast i minuter");
            }

            if (date.length() != 6) {
                Toast.makeText(getActivity(), "Du måste ange datum på formatet ÅÅMMDD", Toast.LENGTH_LONG).show();
                return;
            }

            if (!errorMsg.equals("Vänligen ange")) {
                errorMsg += ".";
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                return;
            }

            int startHour = Integer.parseInt(start.substring(0, 2));
            int startMin = Integer.parseInt(start.substring(3));
            int endHour = Integer.parseInt(end.substring(0, 2));
            int endMin = Integer.parseInt(end.substring(3));
            int breakMinutes = Integer.parseInt(breaktime);

            if (startHour > 23 || startMin > 59 || endHour > 23 || endMin > 59) {
                addError("en giltig tid");
            }
            if (startHour > endHour || (startHour == endHour && startMin > endMin)) {
                addError("en sluttid som är senare än starttiden");
            }

            if (!errorMsg.equals("Vänligen ange")) {
                errorMsg += ".";
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                return;
            }

            float startTime = Float.parseFloat(start.substring(0, 2)) + (Float.parseFloat(start.substring(3)) / 60);
            float endTime = Float.parseFloat(end.substring(0, 2)) + (Float.parseFloat(end.substring(3)) / 60);
            float breakHours = Float.parseFloat(breaktime) / 60;
            float hoursWorked = endTime - startTime - breakHours;
            int year = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(2, 4));
            int day = Integer.parseInt(date.substring(4, 6));
            Log.e("Calculation ", "result from editext:" + year + "," + month + "," + day);

            if (hoursWorked <= 0) {
                Toast.makeText(getActivity(), "Tiden du har arbetat är mindre än noll, stämmer verkligen det?", Toast.LENGTH_LONG).show();
                return;
            }

            callback.addShift(jobTitle, startTime, endTime, hoursWorked, year, month, day, breakMinutes);
            clearAll();
            Toast.makeText(getActivity(), "Arbetspasset har registrerats.", Toast.LENGTH_LONG).show();

        }

        public void addError(String error) {
            if (errorMsg.charAt(errorMsg.length() - 1) == 'e') {
                errorMsg = errorMsg + " " + error;
            } else {
                errorMsg = errorMsg + ", " + error;
            }
        }
    }
}
