package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.AddExpenseFragmentCallback;

/**
 * Created by Kajsa, Anton, Christoffer, Jakup and Morgan.
 * Handles the add expense screen.
 */

public class AddExpenseFragment extends Fragment {
    private EditText inputTitle;
    private EditText inputExpense;
    private EditText inputDate;
    private Button btnAddExpense;
    private AddExpenseFragmentCallback callback;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_expense, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        initComponents(view);
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Initializes components.
     * @param v the view this class has.
     */

    private void initComponents(View v){
        inputTitle = (EditText) v.findViewById(R.id.inputTitle);
        inputExpense = (EditText) v.findViewById(R.id.inputExpense);
        inputDate = (EditText) v.findViewById(R.id.inputDate);
        btnAddExpense = (Button) v.findViewById(R.id.btnAddExpense);
        btnAddExpense.setOnClickListener(new ButtonListener());
    }

    /**
     * Sets callback for this class.
     * @param callback the listener.
     */

    public void setCallBack(AddExpenseFragmentCallback callback){
        this.callback = callback;
    }

    /**
     * Listens for button clicks, and check for valid input.
     */

    private class ButtonListener implements View.OnClickListener {
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
            if(date.length() != 6){
                Toast.makeText(getActivity(), "Du måste ange datum på formatet ÅÅMMDD", Toast.LENGTH_LONG).show();
                return;
            }
            int year = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(2, 4));
            int day = Integer.parseInt(date.substring(4, 6));
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

    public void clearAll(){
        inputTitle.setText("");
        inputExpense.setText("");
        inputDate.setText("");
    }
}
