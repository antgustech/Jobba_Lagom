package com.example.i7.jobbalagom.java.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.i7.jobbalagom.R;

/**
 * Created by Kajsa on 2016-05-04.
 */
public class OBFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("OBFragment", "onCreateView");
        return inflater.inflate(R.layout.fragment_ob, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
