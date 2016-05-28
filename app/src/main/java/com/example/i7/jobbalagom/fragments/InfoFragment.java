package com.example.i7.jobbalagom.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.i7.jobbalagom.R;

/**
 * Created by Kajsa, Christoffer, Jakup, Morgan and Anton.
 * Shows the info screen.
 */
public class InfoFragment extends Fragment {

    private final String TITLE_WHO = "Vem?";
    private RelativeLayout popupLayout;
    private TextView tvPopupTitle, tvPopupText;
    private ImageView ivSelfie;

    /**
     * Initializes fragment.
     *
     * @param inflater           layout object that is used to show the layout of fragment.
     * @param container          the parent view this fragment is added to.
     * @param savedInstanceState used for saving non persistent data that get's restored if the fragment needs to be recreated.
     * @return view hierarchy associated with fragment.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
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
        Button btnWhat = (Button) v.findViewById(R.id.btnWhat);
        Button btnHow = (Button) v.findViewById(R.id.btnHow);
        Button btnWhy = (Button) v.findViewById(R.id.btnWhy);
        Button btnWho = (Button) v.findViewById(R.id.btnWho);
        Button btnExitPopup = (Button) v.findViewById(R.id.btnExitPopup);
        popupLayout = (RelativeLayout) v.findViewById(R.id.popupLayout);
        tvPopupTitle = (TextView) v.findViewById(R.id.tvPopupTitle);
        tvPopupText = (TextView) v.findViewById(R.id.tvPopupText);
        ivSelfie = (ImageView) v.findViewById(R.id.ivSelfie);
        ButtonListener btnListener = new ButtonListener();
        btnWhat.setOnClickListener(btnListener);
        btnHow.setOnClickListener(btnListener);
        btnWhy.setOnClickListener(btnListener);
        btnWho.setOnClickListener(btnListener);
        btnExitPopup.setOnClickListener(btnListener);
        hidePopupLayout();
    }


    /**
     * Shows message dialog..
     *
     * @param title string of the title of the window.
     * @param text  string of the name of the button clicked.
     */

    private void showPopupLayout(String title, String text) {
        tvPopupTitle.setText(title);
        tvPopupText.setText(text);

        if (title.equals(TITLE_WHO)) {
            ivSelfie.setVisibility(View.VISIBLE);
        } else {
            ivSelfie.setVisibility(View.INVISIBLE);
        }
        popupLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the message dialog.
     */

    private void hidePopupLayout() {
        popupLayout.setVisibility(View.INVISIBLE);
    }


    /**
     * Listener for buttons.
     */
    private class ButtonListener implements View.OnClickListener {

        /**
         * Decides what message dialog to show.
         *
         * @param v this fragment view.
         */

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnWhat) {
                Log.d("InfoFragment", "what button pressed");
                String TEXT_WHAT = "Jobba Lagom är en applikation för dig som arbetande student med stöd från Centrala "
                        + "Studiestödsnämnden. \n \nDen hjälper dig att jobba lagom mycket, med andra ord hjälper "
                        + "den dig att räkna ut vad du kommer få i lön med hänsyn till både skatt och ob-satser, "
                        + "balansera din inkomst mot dina utgifter, och berätta hur mycket mer du kan tjäna innan "
                        + "du passerar det av CSN erhållna fribeloppet.";
                String TITLE_WHAT = "Vad?";
                showPopupLayout(TITLE_WHAT, TEXT_WHAT);
            } else if (v.getId() == R.id.btnHow) {
                Log.d("InfoFragment", "how button pressed");
                String TEXT_HOW = "All information du skriver in i applikationen lagras i din egen telefon eller surfplatta, och "
                        + "är därför oåtkomlig för obehöriga.\n\nGenom att lagra hela rikets skattetabell i en "
                        + "databas kan applikationen erbjuda automatisk beräkning av inkomst efter skatt, beroende på "
                        + "vilken folkbokföringsadress som anges.\n\nApplikationen är utvecklad för Googles mobila "
                        + "operativ-\nsystem Android (Java).";
                String TITLE_HOW = "Hur?";
                showPopupLayout(TITLE_HOW, TEXT_HOW);
            } else if (v.getId() == R.id.btnWhy) {
                Log.d("InfoFragment", "why button pressed");
                String TEXT_WHY = "Jobba Lagom har utvecklats som en del av kursen Systemutveckling och Projekt l (DA336A) på Malmö "
                        + "Högskola.\n\nSyftet med kursen är att ge studenterna en introduktion till projektarbete som "
                        + "arbetssätt - skapande, deltagande och koordinering av projekt inom datavetenskap & tillämpad IT.";
                String TITLE_WHY = "Varför?";
                showPopupLayout(TITLE_WHY, TEXT_WHY);
            } else if (v.getId() == R.id.btnWho) {
                Log.d("InfoFragment", "who button pressed");
                String TEXT_WHO = "Bakom skärmen gömmer sig studenter från System-\nutvecklarprogrammet på Malmö Högskola.\n\n"
                        + "Närmare bestämt Anton Gustafsson, Christoffer Strandberg, Jakup Güven, Kajsa Ornstein "
                        + "och Morgan Hedström.";
                showPopupLayout(TITLE_WHO, TEXT_WHO);
            } else if (v.getId() == R.id.btnExitPopup) {
                hidePopupLayout();
            }
        }
    }
}
