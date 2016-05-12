package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.SettingsFragmentCallback;

/**
 * Created by Kajsa on 2016-05-09.
 */
public class SettingsFragment extends Fragment {

    private SettingsFragmentCallback callback;
    private Button btnSettings, btnAbout, btnChangeTax;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    public void initComponents(View view) {
        btnSettings = (Button) view.findViewById(R.id.btnSettings);
        btnAbout = (Button) view.findViewById(R.id.btnAbout);
        btnChangeTax = (Button) view.findViewById(R.id.btnChangeTax);
        ButtonListener btnListener = new ButtonListener();
        btnSettings.setOnClickListener(btnListener);
        btnAbout.setOnClickListener(btnListener);
        btnChangeTax.setOnClickListener(btnListener);
    }
    public void setCallBack(SettingsFragmentCallback callback){
        this.callback = callback;
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnSettings) {
                callback.showFragment("settings");
            } else if (v.getId() == R.id.btnAbout) {
                callback.showFragment("about");
            } else if (v.getId() == R.id.btnChangeTax) {
                callback.showFragment("changeTax");
            }
        }
    }
}
