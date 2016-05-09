package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.activities.MainActivity;
import com.example.i7.jobbalagom.callback_interfaces.AddShiftFragmentCallback;

/**
 * Created by Kajsa on 2016-05-08
 */

public class AddShiftFragment extends Fragment {

    private AddShiftFragmentCallback callback;
    private String[] jobTitles;

    private Spinner jobSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addshift, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    public void initComponents(View view) {
       jobTitles = ((MainActivity) getActivity()).getJobTitles();

        String log = "Jobb: ";
        for(String s : jobTitles) {
            log += s + ", ";
        }
        Log.d("AddShiftFragment", log);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, jobTitles);
        jobSpinner = (Spinner) view.findViewById(R.id.jobTitleList);
        jobSpinner.setAdapter(adapter);
    }

    public void setCallBack(AddShiftFragmentCallback callback){
        this.callback = callback;
    }


}