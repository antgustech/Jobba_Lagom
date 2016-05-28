package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.AddJobFragmentCallback;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by Kajsa, Anton, Christoffer, Jakup and Morgan.
 * Handles the add job screen.
 */

public class AddJobFragment extends Fragment {
    private AddJobFragmentCallback callback;
    private RelativeLayout obLayout;
    private EditText inputOB, inputTitle, inputWage;
    private TextView inputFromTime, inputToTime;
    private RadioButton rbWorkday, rbSaturday, rbSunday;
    private RadioGroup rgDay, rgType;
    private LinkedList<String> obRates;


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
        return inflater.inflate(R.layout.fragment_addjob, container, false);
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
     * Initialzes componenets.
     *
     * @param v this fragment v.
     */

    public void initComponents(View v) {


        obLayout = (RelativeLayout) v.findViewById(R.id.obLayout);
        inputTitle = (EditText) v.findViewById(R.id.inputTitle);
        inputWage = (EditText) v.findViewById(R.id.inputWage);
        Button btnAddJob = (Button) v.findViewById(R.id.btnAddShift);
        inputFromTime = (TextView) v.findViewById(R.id.inputFromTime);
        inputToTime = (TextView) v.findViewById(R.id.inputToTime);
        inputOB = (EditText) v.findViewById(R.id.inputOB);
        rbWorkday = (RadioButton) v.findViewById(R.id.rbWorkday);
        rbSaturday = (RadioButton) v.findViewById(R.id.rbSaturday);
        rbSunday = (RadioButton) v.findViewById(R.id.rbSunday);
        rgDay = (RadioGroup) v.findViewById(R.id.rgDay);
        rgType = (RadioGroup) v.findViewById(R.id.rgType);
        Button inputCreateOB = (Button) v.findViewById(R.id.btnCreateOB);
        Button btnAddOB = (Button) v.findViewById(R.id.btnAddOB);
        Button btnExit = (Button) v.findViewById(R.id.btnExit);
        obRates = new LinkedList<>();
        obLayout.setVisibility(View.INVISIBLE);
        inputCreateOB.setOnClickListener(new BtnCreateOBListener());
        btnExit.setOnClickListener(new ReturnListener());
        btnAddJob.setOnClickListener(new BtnAddJobListener());
        btnAddOB.setOnClickListener(new BtnAddOBListener());
        inputFromTime.setOnClickListener(new SetTime(inputFromTime, getActivity()));
        inputToTime.setOnClickListener(new SetTime(inputToTime, getActivity()));


    }

    /**
     * Sets callback for this class
     *
     * @param callback a new AddJobFragmentListener.
     */

    public void setCallBack(AddJobFragmentCallback callback) {
        this.callback = callback;
    }

    /**
     * Clears all inputfields.
     */

    public void clearAll() {
        inputTitle.setText("");
        inputWage.setText("");
        obRates.clear();
    }

    /**
     * Shows the OB view.
     */

    public void showOBLayout() {
        obLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the OB view.
     */

    public void hideOBLayout() {
        obLayout.setVisibility(View.INVISIBLE);
        inputFromTime.setText("");
        inputToTime.setText("");
        inputOB.setText("");
        rgDay.clearCheck();
        rgType.clearCheck();
    }

    /**
     * Adds buttonlistener for the ob buttons and also checks if input is valid.
     */

    @SuppressWarnings("deprecation")
    private class BtnAddOBListener implements View.OnClickListener {
        String emptyInputMsg;
        String invalidInputMsg;

        public void onClick(View v) {

            emptyInputMsg = "Vänta lite, du glömde fylla i";

            if (rgDay.getCheckedRadioButtonId() == -1) {
                addError("dag");
            }
            if (inputFromTime.getText().toString().equals("") || inputToTime.getText().toString().equals("")) {
                addError("tid");
            }
            if (inputOB.getText().toString().equals("") || rgType.getCheckedRadioButtonId() == -1) {
                addError("OB");
            }
            emptyInputMsg = emptyInputMsg + ".";
            if (!emptyInputMsg.equals("Vänta lite, du glömde fylla i.")) {
                Toast.makeText(getActivity(), emptyInputMsg, Toast.LENGTH_LONG).show();
                return;
            }
            String fromTime = inputFromTime.getText().toString();
            String toTime = inputToTime.getText().toString();

            if (fromTime.length() != 5 || toTime.length() != 5) {
                invalidInputMsg = "Vänligen ange tid i formatet HH:MM";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                return;
            }
            if (fromTime.charAt(2) != ':' || toTime.charAt(2) != ':') {
                invalidInputMsg = "Vänligen ange tid i formatet HH:MM.";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                return;
            }
            if (fromTime.equals(toTime)) {
                invalidInputMsg = "De angivna tiderna stämmer inte.";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                return;
            }

            int fromTimeHour = Integer.parseInt(fromTime.substring(0, 2));
            int fromTimeMin = Integer.parseInt(fromTime.substring(3));
            int toTimeHour = Integer.parseInt(toTime.substring(0, 2));
            int toTimeMin = Integer.parseInt(toTime.substring(3));

            if (fromTimeHour > 24 || fromTimeMin > 59 || toTimeHour > 24 || toTimeMin > 59) {
                invalidInputMsg = "Tiden som angetts är inte en giltig tid.";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                return;
            }
            if (fromTimeHour > toTimeHour || (fromTimeHour == toTimeHour && fromTimeMin > toTimeMin)) {
                invalidInputMsg = "De angivna tiderna stämmer inte.";
                Toast.makeText(getActivity(), invalidInputMsg, Toast.LENGTH_LONG).show();
                return;
            }

            int id = rgDay.getCheckedRadioButtonId();
            RadioButton rb = (RadioButton) rgDay.findViewById(id);
            String day = rb.getText().toString();

            int id2 = rgType.getCheckedRadioButtonId();
            RadioButton rb2 = (RadioButton) rgType.findViewById(id2);
            String type = rb2.getText().toString();
            String ob = inputOB.getText().toString();

            String obRate = day + "," + fromTime + "," + toTime + "," + ob + "," + type;
            obRates.add(obRate);
            hideOBLayout();
            Toast.makeText(getActivity(), "OB har registrerats: " + ob + " " + type + " tillägg, " + day + ", från " + fromTime
                    + " till " + toTime, Toast.LENGTH_LONG).show();
            if (day.equals("Vardag")) {
                rbWorkday.setEnabled(false);
                rbWorkday.setTextColor(getResources().getColor(R.color.grey));
            } else if (day.equals("Lördag")) {
                rbSaturday.setEnabled(false);
                rbWorkday.setTextColor(getResources().getColor(R.color.grey));
            } else if (day.equals("Söndag")) {
                rbSunday.setEnabled(false);
                rbWorkday.setTextColor(getResources().getColor(R.color.grey));
            }
        }

        /**
         * Sets the error message.
         *
         * @param error the string containing the error message.
         */

        public void addError(String error) {
            if (emptyInputMsg.charAt(emptyInputMsg.length() - 1) == 'i') {
                emptyInputMsg = emptyInputMsg + " " + error;
            } else {
                emptyInputMsg = emptyInputMsg + ", " + error;
            }
        }
    }

    /**
     * Listener for adding ob.
     */

    private class BtnCreateOBListener implements View.OnClickListener {
        /**
         * Show OB layout if clicked.
         *
         * @param v this fragment v.
         */
        @Override
        public void onClick(View v) {
            showOBLayout();
        }
    }

    /**
     * Listener for abortiing adding ob.
     */

    private class ReturnListener implements View.OnClickListener {
        /**
         * If the user clicks x button, hide ob layout.
         *
         * @param v this fragment v.
         */
        @Override
        public void onClick(View v) {
            hideOBLayout();
        }
    }

    /**
     * listener for the add job button.
     */

    private class BtnAddJobListener implements View.OnClickListener {

        /**
         * Checks for valid input and displays errors messages if it's not. Then sends data via callback to MainActivity.
         *
         * @param v this fragment v.
         */
        @Override
        public void onClick(View v) {
            CharSequence emptyInputMsg = null;
            String jobTitle = inputTitle.getText().toString();
            String wage = inputWage.getText().toString();
            if (jobTitle.equals("")) {
                emptyInputMsg = "Vänta lite, du glömde fylla i jobbtitel.";
            }
            if (wage.equals("")) {
                emptyInputMsg = "Vänta lite, du glömde fylla i din timlön.";
            }
            if (jobTitle.equals("") && wage.equals("")) {
                emptyInputMsg = "Vänta lite, du glömde fylla i jobbtitel och timlön.";
            }

            if (emptyInputMsg != null) {
                Toast.makeText(getActivity(), emptyInputMsg, Toast.LENGTH_LONG).show();
                return;
            }
            callback.addJob(jobTitle, Float.parseFloat(wage));
            for (String obRate : obRates) {
                String[] parts = obRate.split(",");
                String day = parts[0];
                String fromTime = parts[1];
                String toTime = parts[2];
                String ob = parts[3];
                String type = parts[4];
                float obIndex = 0;

                if (type.equals("Kronor")) {
                    obIndex = 1 + Float.parseFloat(ob) / Float.parseFloat(wage);
                } else if (type.equals("Procent")) {
                    obIndex = 1 + Float.parseFloat(ob) / 100;
                }
                callback.addOB(jobTitle, day, fromTime, toTime, obIndex);
            }
            clearAll();
            Toast.makeText(getActivity(), jobTitle + " är tillagt som ett nytt jobb.", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Listener for when pressing the time field.
     */

    class SetTime implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

        private TextView v;
        private Calendar myCalendar;
        private Context ctx;

        /**
         * Initialize this class.
         *
         * @param v   The textView to change.
         * @param ctx context of this fragment.
         */

        public SetTime(TextView v, Context ctx) {
            this.v = v;
            this.v.setOnClickListener(this);
            this.myCalendar = Calendar.getInstance();
            this.ctx = ctx;
        }

        /**
         * When clicked, show the time picker dialog.
         *
         * @param v the view to show the dialog in.
         */
        @Override
        public void onClick(View v) {
            int sec = myCalendar.get(Calendar.SECOND);
            int min = myCalendar.get(Calendar.MINUTE);
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            TimePickerDialog dpd = TimePickerDialog.newInstance(this, hour, min, sec, true);
            dpd.show(getFragmentManager(), "Timepickerdialog");
        }


        /**
         * When time is choosen check for errors.
         *
         * @param view      the datepicker itself.
         * @param hourOfDay choosen hour.
         * @param minute    choosen minute.
         * @param second    choosen second.
         */
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
            String sHour = "";
            if (hourOfDay < 10) {
                sHour = "0" + String.valueOf(hourOfDay);
            } else {
                sHour = String.valueOf(hourOfDay);
            }

            String sMin = "";
            if (minute < 10) {
                sMin = "0" + String.valueOf(minute);
            } else {
                sMin = String.valueOf(minute);
            }
            String time = sHour + ":" + sMin;
            v.setText(time);
            Log.e("TP", "Time: " + time);
        }


    }
}
