package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.RemoveJobFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

/**
 * Created by Anton Gustafsson on 2016-05-12.
 */
public class RemoveJobFragment extends Fragment {
    private RemoveJobFragmentCallback callback;

    private Controller controller;
    private String[] jobTitles;
    private Spinner jobSpinner;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_removejob, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }




    private void initComponents(View view){
        controller = Singleton.controller;
        jobTitles = controller.getJobTitles();
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, jobTitles);
        jobSpinner = (Spinner) view.findViewById(R.id.jobTitleList);
        jobSpinner.setAdapter(adapter);

    }


    public void setCallBack(RemoveJobFragmentCallback callback){
        this.callback=callback;

    }
}
