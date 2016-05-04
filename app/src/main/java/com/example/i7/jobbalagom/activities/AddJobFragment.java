package com.example.i7.jobbalagom.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.i7.jobbalagom.R;

/**
 * Created by Kajsa on 2016-05-04.
 */

public class AddJobFragment extends Fragment {

    private EditText inputTitle;
    private EditText inputWage;
    private EditText inputOB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addjob, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    public void initComponents(View view) {

    }
/**
    public void setCallBack(LaunchFragmentCallback callback){
        this.callback = callback;
    }
 */

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}
