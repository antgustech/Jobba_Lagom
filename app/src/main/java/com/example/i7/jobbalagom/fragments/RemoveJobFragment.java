package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.RemoveJobFragmentCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

/**
 * Created by Anton, Christoffer, Kajsa, Morgan and Jakup.
 * Handles the removeJob screen.
 */
public class RemoveJobFragment extends Fragment {
    private RemoveJobFragmentCallback callback;
    private Spinner jobSpinner;

    /**
     * Initializes fragment.
     *
     * @param inflater           layout object that is used to show the layout of fragment.
     * @param container          the parent view this fragment is added to.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchy associated with fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_removejob, container, false);
    }

    /**
     * Called after the onCreateView has executed makes final UI initializations.
     *
     * @param view               this fragment view.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     */

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    /**
     * Initializes components.
     *
     * @param v this fragment view.
     */

    private void initComponents(View v) {
        Controller controller = Singleton.controller;
        Button btnRemoveJob = (Button) v.findViewById(R.id.btnRemoveJob);
        btnRemoveJob.setOnClickListener(new ButtonListner());
        String[] jobTitles = controller.getJobTitles();

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_row, R.id.text, jobTitles);
        jobSpinner = (Spinner) v.findViewById(R.id.jobSpinner);
        jobSpinner.setAdapter(adapter);
    }

    /**
     * Sets the callback.
     *
     * @param callback listener.
     */

    public void setCallBack(RemoveJobFragmentCallback callback) {
        this.callback = callback;
    }

    /**
     * Listener for the remove job button.
     */

    private class ButtonListner implements View.OnClickListener {
        /**
         * Sends the selected job to be removed via callback to SettingsActivity.
         *
         * @param v this fragment view.
         */
        @Override
        public void onClick(View v) {
            final String jobTitle = jobSpinner.getSelectedItem().toString();
            callback.removeJob(jobTitle);
        }
    }
}
