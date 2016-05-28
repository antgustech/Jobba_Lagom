package com.example.i7.jobbalagom.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.AddShiftFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

/**
 * Created by Kajsa, Jakup, Christoffer, Morgan and Anton.
 * Handles the add shift screen.
 */

public class AddShiftFragment extends Fragment {
    private AddShiftFragmentCallback callback;
    private Controller controller;
    private String[] jobTitles;
    private Spinner jobSpinner;
    private Button btnAddShift, btnAddJob;
    private ImageButton btnBreakInfo;
    private EditText inputStart, inputEnd, inputBreak, inputDate;


    /**
     * Initializes fragment.
     * @param inflater layout object that is used to show the layout of fragment.
     * @param container the parent view this fragment is added to.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchu associated with fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addshift, container, false);
    }


    /**
     * Called after the onCreateView has executed makes final UI initializations.
     * @param  view  this fragment view.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchu associated with fragment.
     */

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    /**
     * Initializes components.
     * @param  v  this fragment v.
     */

    public void initComponents(View v) {
        controller = Singleton.controller;
        btnAddShift = (Button) v.findViewById(R.id.btnAddShift);
        btnBreakInfo =(ImageButton) v.findViewById(R.id.btnBreakInfo);
        inputStart = (EditText) v.findViewById(R.id.inputStart);
        inputEnd = (EditText) v.findViewById(R.id.inputEnd);
        inputBreak = (EditText) v.findViewById(R.id.inputBreak);
        inputDate = (EditText) v.findViewById(R.id.inputDate);
        btnAddShift.setOnClickListener(new ButtonAddShiftListener());
        btnBreakInfo.setOnClickListener(new MessageDialogListener());
        jobTitles = controller.getJobTitles();

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row, R.id.text, jobTitles);
        jobSpinner = (Spinner) v.findViewById(R.id.jobSpinner);
        jobSpinner.setAdapter(adapter);

        if (jobTitles.length == 0) {
            Toast toast = Toast.makeText(getActivity(), "Du måste lägga till ett jobb först!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }

    /**
     * Listener for the DialogBox.
     */

    private class MessageDialogListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle)
                    .setTitle("Info")
                    .setMessage("Rasten du lägger till kommer att dras från mitten utav ditt arbetspass.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    /**
     * Sets the callback.
     * @param callback Listenre for this fragment.
     */

    public void setCallBack(AddShiftFragmentCallback callback) {
        this.callback = callback;
    }

    /**
     * Clears all input fields.
     */

    public void clearAll() {
        inputStart.setText("");
        inputEnd.setText("");
        inputBreak.setText("");
    }

    /**
     * Listener for the button to add shift. It checks for valid input and
     * converts the input to tangible datatypes for the methods it later calls.
     */

    private class ButtonAddShiftListener implements View.OnClickListener {
        String errorMsg;
        @Override
        public void onClick(View v) {
            String jobTitle = jobSpinner.getSelectedItem().toString();
            String start = inputStart.getText().toString();
            String end = inputEnd.getText().toString();
            String breaktime = inputBreak.getText().toString();
            String date = inputDate.getText().toString();
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
            if (hoursWorked <= 0) {
                Toast.makeText(getActivity(), "Tiden du har arbetat är mindre än noll, stämmer verkligen det?", Toast.LENGTH_LONG).show();
                return;
            }
            callback.addShift(jobTitle, startTime, endTime, hoursWorked, year, month, day, breakHours);
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
