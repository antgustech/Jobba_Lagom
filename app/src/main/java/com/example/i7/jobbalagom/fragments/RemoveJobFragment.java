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
    private View btnRemoveJob;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_removejob, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    private void initComponents( View v){
        controller = Singleton.controller;
        btnRemoveJob = v.findViewById(R.id.btnRemoveJob);
        btnRemoveJob.setOnClickListener(new ButtonListner());
        jobTitles = controller.getJobTitles();

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row, R.id.text, jobTitles);
        jobSpinner = (Spinner) v.findViewById(R.id.jobSpinner);
        jobSpinner.setAdapter(adapter);
    }

    /**
     * Listener for the remove job button.
     */

    private class ButtonListner implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            final String jobTitle = jobSpinner.getSelectedItem().toString();
            callback.removeJob(jobTitle);
        }
    }

    /**
     * Sets the callback.
     * @param callback listener.
     */

    public void setCallBack(RemoveJobFragmentCallback callback){
        this.callback=callback;
    }
}
