package com.example.i7.jobbalagom.java.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.java.callback_interfaces.AddJobFragmentCallback;
import com.example.i7.jobbalagom.java.local.Controller;
import com.example.i7.jobbalagom.java.local.Singleton;

/**
 * Created by Kajsa on 2016-05-04.
 */

public class AddJobFragment extends Fragment {

    private AddJobFragmentCallback callback;
    private EditText inputTitle;
    private EditText inputWage;
    private TextView tvOBweekday;
    private TextView tvOBsaturday;
    private TextView tvOBsunday;
    private Button btnAdd;
    private View obFragment;

    private Controller controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        controller = Singleton.controller;
        return inflater.inflate(R.layout.fragment_addjob, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    public void initComponents(View view) {
        inputTitle = (EditText) view.findViewById(R.id.inputTitle);
        inputWage = (EditText) view.findViewById(R.id.inputWage);
        tvOBweekday = (TextView) view.findViewById(R.id.tvOBweekday);
        tvOBsaturday = (TextView) view.findViewById(R.id.tvOBsaturday);
        tvOBsunday = (TextView) view.findViewById(R.id.tvOBsunday);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        OBListener obListener = new OBListener();
        tvOBweekday.setOnClickListener(obListener);
        tvOBsaturday.setOnClickListener(obListener);
        tvOBsunday.setOnClickListener(obListener);
        btnAdd.setOnClickListener(new ButtonListener());
        obFragment = view.findViewById(R.id.obFragment);
        obFragment.setOnKeyListener(new ReturnListener());
        obFragment.setVisibility(View.INVISIBLE);
    }

    public void setCallBack(AddJobFragmentCallback callback){
        this.callback = callback;
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d("AddJobFragment", "Button pressed");
            controller.addJob(inputTitle.toString(), Float.parseFloat(inputWage.getText().toString()),3.55f);
            Toast.makeText(getContext(), "Jobb tillagt", Toast.LENGTH_LONG).show();

        }
    }

    private class OBListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.tvOBweekday) {
                obFragment.setVisibility(View.VISIBLE);
                callback.update("weekday OB pressed");
            }

            else if(v.getId() == R.id.tvOBsaturday) {
                obFragment.setVisibility(View.VISIBLE);
                callback.update("saturday OB pressed");
            }

            else if(v.getId() == R.id.tvOBsunday) {
                obFragment.setVisibility(View.VISIBLE);
                callback.update("sunday OB pressed");
            }
        }
    }

    private class ReturnListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View v, int key, KeyEvent e) {
            if(key == KeyEvent.KEYCODE_BACK && obFragment.getVisibility() == View.VISIBLE) {
                obFragment.setVisibility(View.INVISIBLE);
            }
            return false;
        }
    }
}
