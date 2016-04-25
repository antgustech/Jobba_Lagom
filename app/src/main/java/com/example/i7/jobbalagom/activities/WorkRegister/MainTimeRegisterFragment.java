package com.example.i7.jobbalagom.activities.WorkRegister;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import android.util.Log;

import com.example.i7.jobbalagom.R;

import java.util.Calendar;

/**
 * Created by Strandberg95 on 2016-04-18.
 */
public class MainTimeRegisterFragment extends Fragment {

   // private View btnView;
    //private View textView_View;
    private View mainView;

    private SynchedTextContainer date_TextContainer;
    private SynchedTextContainer timeFrom_TextContainer;
    private SynchedTextContainer timeTo_TextContainer;

    private EditText textView_Date;
    private EditText textView_TimeFrom;
    private EditText textView_TimeTo;

    private MainTimeRegisterCallback btnCallback;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_time_register_fragment, container, false);
    }

    public MainTimeRegisterFragment(){
        Calendar c = Calendar.getInstance();
        date_TextContainer = new SynchedTextContainer(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) +"-"+ c.get(Calendar.DAY_OF_MONTH));
        timeFrom_TextContainer = new SynchedTextContainer("00:00");
        timeTo_TextContainer = new SynchedTextContainer("00:00");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        this.mainView = view;

        textView_Date = (EditText)view.findViewById(R.id.dateTextView);
        textView_Date.setOnClickListener(new ButtonListener());
        textView_Date.setInputType(0);

        textView_TimeFrom = (EditText)view.findViewById(R.id.timeTextView_From);
        textView_TimeFrom.setOnClickListener(new ButtonListener());
        textView_TimeFrom.setInputType(0);

        textView_TimeTo = (EditText)view.findViewById(R.id.timeTextView_To);
        textView_TimeTo.setOnClickListener(new ButtonListener());
        textView_TimeTo.setInputType(0);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {

        super.onResume();
        Log.d("bla", date_TextContainer.getText() + " On Resume");
        updateTextViews();

    }

    public void setDate_TextContainer(String text){

        Log.d("setTextTag", text + " IN");
        date_TextContainer.setText(text);
        textView_Date.setText(text);
        Log.d("setTextTag", date_TextContainer.getText() + " OUT");

    }

    public void setTimeTo_TextContainer(String text){

        Log.d("setTextTag", text + " IN");
        timeTo_TextContainer.setText(text);
        textView_TimeTo.setText(text);
        Log.d("setTextTag", date_TextContainer.getText() + " OUT");

    }

    public void setTimeFrom_TextContainer(String text){

        Log.d("setTextTag", text + " IN");
        timeFrom_TextContainer.setText(text);
        textView_TimeFrom.setText(text);
        Log.d("setTextTag", date_TextContainer.getText() + " OUT");

    }

    public void setCallback(MainTimeRegisterCallback btnCallback){
        this.btnCallback = btnCallback;
    }

    private void updateTextViews(){

        if(!date_TextContainer.getText().equals(textView_Date.getText() + "")){
            textView_Date.setText(date_TextContainer.getText());
        }
        if(!timeTo_TextContainer.getText().equals(textView_TimeTo.getText() + "")){
            textView_TimeTo.setText(timeTo_TextContainer.getText());
        }
        if(!timeFrom_TextContainer.getText().equals(textView_TimeFrom.getText() + "")){
            textView_TimeFrom.setText(timeFrom_TextContainer.getText());
        }
    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            btnCallback.buttonPressed(v);
        }
    }
}
