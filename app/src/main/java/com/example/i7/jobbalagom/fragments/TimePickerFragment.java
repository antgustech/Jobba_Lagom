package com.example.i7.jobbalagom.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.TimePickerCallback;

/**
 * Created by Strandberg95 on 2016-04-25.
 */
public class TimePickerFragment extends Fragment {

    private TimePicker timePicker;
    private String headLine = "Välj tid";
    private TextView textView_HeadLine;

    private View button_Done;

    private TimePickerCallback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time_picker_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        timePicker = (TimePicker)view.findViewById(R.id.timePicker);
        textView_HeadLine = (TextView)view.findViewById(R.id.textView_HeadLine);

        if(!textView_HeadLine.getText().equals(headLine))
            textView_HeadLine.setText(headLine);

        button_Done = view.findViewById(R.id.timePicking_Done);
        button_Done.setOnClickListener(new ButtonListener());
        timePicker.setIs24HourView(true);

    }

    public void setHeadLine(String text){
        headLine = text;
    }

    public void setCallback(TimePickerCallback callback){
        this.callback = callback;
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            String hours = timePicker.getHour() + "";
            String minutes = timePicker.getMinute() + "";

            if(Integer.parseInt(hours) < 10){
                hours = new StringBuilder(hours + "").insert(hours.length() - 1,"0").toString();
            }
            if(Integer.parseInt(minutes) < 10){
                minutes = new StringBuilder(minutes + "").insert(minutes.length() - 1,"0").toString();
            }
            callback.updateTime(hours + ":" + minutes);
        }
    }
}
