package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.i7.jobbalagom.R;

/**
 * Created by Anton, Kajsa, Jakup, Morgan, Christoffer.
 */
public class AboutFragment extends Fragment {

    /**
     * Handles the AboutFragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
