package com.example.i7.jobbalagom.local;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.R.id;
import com.example.i7.jobbalagom.R;
import android.util.Log;


import com.example.i7.jobbalagom.activities.SettingsActivity;

/**
 * Created by Anton Gustafsson on 2016-04-04.
 */
public class TimePickerDialogFragment extends DialogFragment {
    protected Log log;
    private ButtonListener bl = new ButtonListener();
    private View startTime;
    private View endTime;
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflater.inflate(R.layout.time_picker_dialog, null));
        View v1 = inflater.inflate(R.layout.time_picker_dialog, null);
        endTime = v1.findViewById(R.id.start);//Fel view!
        endTime.setOnClickListener(bl);


        builder.setMessage(R.string.worked_hours)


        //noinspection SimplifiableIfStatement

                //Controls add and cancel buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // user pressed add
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


        // Create the AlertDialog object and return it
        return builder.create();
    }

    private class ButtonListener implements View.OnClickListener {
        public ButtonListener(){
            log.d("Hej","lol");
        }
        @Override
        public void onClick(View v) {
            log.d("Hej","lol");
            if (v.getId() == R.id.start) {
                 DialogFragment newFragment;
                newFragment = new TimePickerFragment();
                 newFragment.show(getFragmentManager(), "timePicker");


            }else if(v.getId() == R.id.action_about){


            }

        }
    }


}
