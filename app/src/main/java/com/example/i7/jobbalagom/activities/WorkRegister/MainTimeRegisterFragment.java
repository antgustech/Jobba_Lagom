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

import com.example.i7.jobbalagom.R;

import org.w3c.dom.Text;

/**
 * Created by Strandberg95 on 2016-04-18.
 */
public class MainTimeRegisterFragment extends Fragment {

    private View btnView;
    private View textView_View;
    private View mainView;

    private EditText textView_Text;
    private MainTimeRegisterCallback btnCallback;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_time_register_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        this.mainView = view;

        btnView = view.findViewById(R.id.dateButton_Start);
        btnView.setOnClickListener(new ButtonListener());

        textView_View = view.findViewById(R.id.dateTextView);
        textView_View.setOnClickListener(new ButtonListener());
        textView_Text = (EditText)view.findViewById(R.id.dateTextView);

        //textView_Text.setText("hej");

    }

    public void setTextView(String text){
        textView_Text.append(text);
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
