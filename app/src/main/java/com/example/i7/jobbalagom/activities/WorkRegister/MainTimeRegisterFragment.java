package com.example.i7.jobbalagom.activities.WorkRegister;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.view.WindowManager;

import android.util.Log;

import com.example.i7.jobbalagom.R;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by Strandberg95 on 2016-04-18.
 */
public class MainTimeRegisterFragment extends Fragment {

   // private View btnView;
    //private View textView_View;
    private View mainView;

    private SynchedTextContainer synchedTextContainer;

    private EditText textView_Text;
    private MainTimeRegisterCallback btnCallback;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_time_register_fragment, container, false);
    }

    public MainTimeRegisterFragment(){
        Calendar c = Calendar.getInstance();
        synchedTextContainer = new SynchedTextContainer(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) +"-"+ c.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        this.mainView = view;

        //btnView.setOnClickListener(new ButtonListener());

        //textView_View = view.findViewById(R.id.dateTextView);
       // textView_View.setOnClickListener(new ButtonListener());
        textView_Text = (EditText)view.findViewById(R.id.dateTextView);
        textView_Text.setOnClickListener(new ButtonListener());


        //textView_Text.setText("hej");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("bla",synchedTextContainer.getText() + " On Resume");
        while(!synchedTextContainer.getText().equals(textView_Text.getText() + "")){
            textView_Text.setText(synchedTextContainer.getText());
        }
    }

    public void setTextContainer(String text){

        Log.d("setTextTag", text + " IN");
        synchedTextContainer.setText(text);
        textView_Text.setText(text);
        Log.d("setTextTag", synchedTextContainer.getText() + " OUT");

    }

    public void setCallback(MainTimeRegisterCallback btnCallback){

        this.btnCallback = btnCallback;
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            btnCallback.buttonPressed(v);
        }
    }
}
