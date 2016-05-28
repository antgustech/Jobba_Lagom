package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.callbacks.InitialFragmentCallback;

/**
 * Created by Kajsa, Morgan, Christoffer, Jakup and Anton.
 * Handles the intial screen.
 */

public class InitialFragment extends Fragment {
    private RelativeLayout welcomeLayout;
    private InitialFragmentCallback callback;

    /**
     * Initializes fragment.
     *
     * @param inflater           layout object that is used to show the layout of fragment.
     * @param container          the parent view this fragment is added to.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchy associated with fragment.
     */

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_initial, container, false);
    }


    /**
     * Called after the onCreateView has executed makes final UI initializations.
     *
     * @param view               this fragment view.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     */
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        welcomeLayout = (RelativeLayout) view.findViewById(R.id.welcomeLayout);
        fadeWelcomeLayout();
    }

    /**
     * Fades the Welcome screen fragment.
     */

    private void fadeWelcomeLayout() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_slow);
        welcomeLayout.startAnimation(fadeInAnimation);
        welcomeLayout.setVisibility(View.INVISIBLE);
    }
}
