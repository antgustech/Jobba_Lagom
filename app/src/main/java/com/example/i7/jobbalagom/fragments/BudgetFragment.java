package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.BudgetFragmentCallback;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Handles the Budget screen.
 * TODO: Class not yet finished.
 */

public class BudgetFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_budget, container, false);
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
    }

    /**
     * Sets Callback
     *
     * @param callback listener for this class.
     */

    public void setCallBack(BudgetFragmentCallback callback) {
        BudgetFragmentCallback callback1 = callback;
    }
}
