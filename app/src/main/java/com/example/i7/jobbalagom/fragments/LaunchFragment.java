package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.LaunchFragmentCallback;

/**
 * Created by Kajsa, Christoffer, Morgan, Anton and Jakup.
 * Handles the Launch screen.
 */

public class LaunchFragment extends Fragment {

    private LaunchFragmentCallback callback;
    private ButtonListener btnListener;
    private ImageButton btnNew, btnInfo;

    /**
     * Initializes fragment.
     * @param inflater layout object that is used to show the layout of fragment.
     * @param container the parent view this fragment is added to.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchu associated with fragment.
     */

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_launch, container, false);
    }

    /**
     * Called after the onCreateView has executed makes final UI initializations.
     * @param  view  this fragment view.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchu associated with fragment.
     */

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
    }

    /**
     * Initializes components.
     * @param  v  this fragment v.
     */

    public void initComponents(View v) {
        btnNew = (ImageButton) v.findViewById(R.id.btnNew);
        btnInfo = (ImageButton) v.findViewById(R.id.btnInfo);
        btnListener = new ButtonListener();
        btnNew.setOnClickListener(btnListener);
        btnInfo.setOnClickListener(btnListener);
    }

    /**
     * Sets callback.
     * @param callback listener.
     */

    public void setCallBack(LaunchFragmentCallback callback){
        this.callback = callback;
    }

    /**
     * Listener for the buttons. Navigates to the right fragment.
     */

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnNew) {
                if(callback.checkConnection()) {
                    callback.navigate("btnNew");
                } else {
                    Toast.makeText(getActivity(), "Appen saknar anslutning till servern.", Toast.LENGTH_LONG).show();
                }
            }
            if(v.getId() == R.id.btnInfo) {
                callback.navigate("btnInfo");
            }
        }
    }
}
