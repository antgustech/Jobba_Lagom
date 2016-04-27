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
 * Created by Kajsa and Jakup on 2016-04-25.
 */
public class SetupFragment extends Fragment {
    private Button okbutton;
    private EditText inputName;
    private EditText inputFreeSum;
    private EditText inputTitle;
    private EditText inputHWage;
    private AutoCompleteTextView inputArea;
    private AutoCompleteTextView inputCB;
    private CheckBox checkbox;
    private SetupFragmentCallback callback;



    public void setCallBack(SetupFragmentCallback callback){
        this.callback = callback;
    }
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
        okbutton.setOnClickListener(new ButtonListener());
        inputName = (EditText) view.findViewById(R.id.inputNamn);
        inputFreeSum = (EditText) view.findViewById(R.id.inputFribelopp);
        inputTitle = (EditText) view.findViewById(R.id.inputTitel);
        inputHWage = (EditText) view.findViewById(R.id.inputTimlon);
        inputArea = (AutoCompleteTextView) view.findViewById(R.id.inputKommun);
        inputCB = (AutoCompleteTextView) view.findViewById(R.id.inputKollektivavtal);

    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            String ERROR_MESSAGE = "UPS!";

            View[] components = new View[6];
            components[0] = inputName;
            components[1] = inputArea;
            components[2] = inputFreeSum;
            components[3] = inputArea;
            components[4] = inputHWage;
            components[5] = inputCB;

            boolean inputValuesOK = true;

            for(View c : components) {
                if(c instanceof EditText) {
                    EditText e = (EditText) c;
                    if(e.getText().toString().equals("")) {
                        e.setError(ERROR_MESSAGE);
                        inputValuesOK = false;
                    }
                } else if(c instanceof AutoCompleteTextView) {
                    AutoCompleteTextView a = (AutoCompleteTextView) c;
                    if(a.getText().toString().equals("")) {
                        a.setError(ERROR_MESSAGE);
                        inputValuesOK = false;
                    }
                }
            }

            if(inputValuesOK) {
                String name = inputName.getText().toString();
                String area = inputArea.getText().toString();
                String freeSum = inputFreeSum.getText().toString();
                String title = inputTitle.getText().toString();
                String hWage = inputHWage.getText().toString();
                String cb = inputCB.getText().toString();
                callback.setupUser(name, area, freeSum, title, hWage, cb);
            }






        }
    }
}
