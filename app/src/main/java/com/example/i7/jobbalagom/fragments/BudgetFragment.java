package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callback_interfaces.BudgetFragmentCallback;

/**
 * Created by Kajsa, Anton, Morgan, Jakup och Christoffer.
 * TODO: Class not yet finished.
 */

public class BudgetFragment extends Fragment {

    private BudgetFragmentCallback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_budget, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    /**
     * Initializes components.
     */

    public void initComponents(View view) {
    }

    /**
     * Sets Callback
     * @param callback listener for this class.
     */

    public void setCallBack(BudgetFragmentCallback callback) {
        this.callback = callback;
    }
}
