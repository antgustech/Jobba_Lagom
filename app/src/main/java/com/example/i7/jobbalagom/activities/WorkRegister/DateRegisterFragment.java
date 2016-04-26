package com.example.i7.jobbalagom.activities.WorkRegister;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import com.example.i7.jobbalagom.R;

/**
 * Created by Strandberg95 on 2016-04-18.
 */
public class DateRegisterFragment extends Fragment {

    private DatePicker datePicker;
    private View dateButton;
    private DateRegisterCallback dateCallback;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return inflater.inflate(R.layout.date_register_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datePicker = (DatePicker)view.findViewById(R.id.datePicker);
        dateButton = view.findViewById(R.id.dateButton_Done);
        dateButton.setOnClickListener(new ButtonListener());


        //datePicker.
    }
    public void setCallback(DateRegisterCallback callback){
        this.dateCallback = callback;
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            dateCallback.UpdateRegisteredDate(datePicker.getYear(),datePicker.getMonth() + 1,datePicker.getDayOfMonth());
        }
    }
}
