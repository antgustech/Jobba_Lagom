package com.example.i7.jobbalagom.activities.WorkRegister;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.i7.jobbalagom.R;

/**
 * Created by Kajsa on 2016-04-25.
 */
public class SetupFragment extends Fragment {
    private Button okbutton;
    private EditText inputNamn;
    private EditText inputFribelopp;
    private EditText inputTitel;
    private EditText inputTimlön;
    private AutoCompleteTextView inputKommun;
    private AutoCompleteTextView inputKollektivavtal;
    private CheckBox checkbox;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.setup_fragment, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        okbutton = (Button) view.findViewById(R.id.btnSetup);
        inputNamn = (EditText) view.findViewById(R.id.inputNamn);
        inputFribelopp = (EditText) view.findViewById(R.id.inputFribelopp);
        inputTitel = (EditText) view.findViewById(R.id.inputTitel);
        inputTimlön = (EditText) view.findViewById(R.id.inputTimlon);
        inputKommun = (AutoCompleteTextView) view.findViewById(R.id.inputKommun);
        inputKollektivavtal = (AutoCompleteTextView) view.findViewById(R.id.inputKollektivavtal);

    }
}
