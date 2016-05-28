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
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.AddExpenseFragmentCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Kajsa, Anton, Christoffer, Jakup and Morgan.
 * Handles the add expense screen.
 */

public class AddExpenseFragment extends Fragment {
    private EditText inputTitle, inputExpense;
    private TextView inputDate;
    private AddExpenseFragmentCallback callback;

    /**
     * Initializes fragment.
     * @param inflater layout object that is used to show the layout of fragment.
     * @param container the parent view this fragment is added to.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchy associated with fragment.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_expense, container, false);

    }

    /**
     * Called after the onCreateView has executed makes final UI initializations.
     * @param  view  this fragment view.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     */

    public void onViewCreated(View view, Bundle savedInstanceState) {
        initComponents(view);
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Initializes components.
     * @param  v  this fragment view.
     */

    private void initComponents(View v){
        inputTitle = (EditText) v.findViewById(R.id.inputTitle);
        inputExpense = (EditText) v.findViewById(R.id.inputExpense);
        inputDate = (TextView) v.findViewById(R.id.inputDate);
        Button btnAddExpense = (Button) v.findViewById(R.id.btnAddExpense);
        btnAddExpense.setOnClickListener(new ButtonListener());
        inputDate.setOnClickListener(new SetDate(inputDate, getActivity()));
    }

    /**
     * Sets callback for this class.
     * @param callback the listener.
     */

    public void setCallBack(AddExpenseFragmentCallback callback){
        this.callback = callback;
    }

    /**
     * Listener for buttons.
     */

    private class ButtonListener implements View.OnClickListener {

        /**
         * Checks for valid input and displays error messages if it's not.
         * @param  v  this fragment view.
         */
        @Override
        public void onClick(View v) {
            CharSequence emptyInputMsg = null;
            String title = inputTitle.getText().toString();
            String expense = inputExpense.getText().toString();
            String date = inputDate.getText().toString();
            try {
                float expenseF = Float.parseFloat(inputExpense.getText().toString());
            } catch(NumberFormatException e){
                Toast.makeText(getActivity(), "Ogiltlig summa", Toast.LENGTH_LONG).show();
            }
            if(title.equals("")) {
                emptyInputMsg = "Vänta lite, du glömde fylla i utgiftstitle.";
            }
            if(expense.equals("")) {
                emptyInputMsg = "Vänta lite, du glömde fylla i utgiftsbelopp.";
            }
            if(date.equals("")) {
                emptyInputMsg = "Vänta lite, du glömde fylla i datumet.";
            }
            if(emptyInputMsg != null) {
                Toast.makeText(getActivity(), emptyInputMsg, Toast.LENGTH_LONG).show();
                return;
            }
            int year = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(4, 5));
            int day = Integer.parseInt(date.substring(6, 8));
            Toast.makeText(getActivity(), "Utgiften " + title + " är tillagd", Toast.LENGTH_LONG).show();
            callback.addExpense(title, Float.parseFloat(expense), year, month, day);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
            clearAll();
        }
    }

    /**
     * Clears all inputfields.
     */

    private void clearAll(){
        inputTitle.setText("");
        inputExpense.setText("");
        inputDate.setText("");
    }

    /**
     * Listener for the Date field.
     */
    class SetDate implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

        private TextView v;
        private Calendar myCalendar;
        private Context ctx;

        /**
         * Initialize this class.
         *
         * @param v   The textView to change.
         * @param ctx context of this fragment.
         */

        public SetDate(TextView v, Context ctx) {
            this.v = v;
            this.v.setOnClickListener(this);
            this.myCalendar = Calendar.getInstance();
            this.ctx = ctx;
        }

        /**
         * When clicked, show the date picker dialog.
         *
         * @param v the view to show the dialog in.
         */
        @Override
        public void onClick(View v) {
            int day = myCalendar.get(Calendar.DAY_OF_MONTH);
            int month = myCalendar.get(Calendar.MONTH);
            int year = myCalendar.get(Calendar.YEAR);
            DatePickerDialog dpd = DatePickerDialog.newInstance(this, year, month, day);
            dpd.show(getFragmentManager(), "Datepickerdialog");
        }

        /**
         * When date is choosen check for errors.
         *
         * @param view        the datepicker itself.
         * @param year        choosen year.
         * @param monthOfYear choosen month.
         * @param dayOfMonth  choosen day.
         */
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear++;

            String sDay = "";
            if (dayOfMonth < 10) {
                sDay = "0" + String.valueOf(dayOfMonth);
            } else {
                sDay = String.valueOf(dayOfMonth);
            }


            String sMonth = "";
            if (monthOfYear < 10) {
                sMonth = "0" + String.valueOf(monthOfYear);
            } else {
                sMonth = String.valueOf(monthOfYear);
            }
            String date = String.valueOf(year).substring(2, 4) + "/" + sMonth + "/" + sDay;
            int nyear = Integer.parseInt(date.substring(0, 2));
            int nmonth = Integer.parseInt(date.substring(4, 5));
            int nday = Integer.parseInt(date.substring(6, 8));
            Log.e("new date substring", "new date substring 8:" + nyear + nmonth + nday);
            Log.e("new date substring", "new date substring 8:" + " Year:" + nyear + " month: " + nmonth + " day: " + nday);
            v.setText(date);
        }


    }
}
