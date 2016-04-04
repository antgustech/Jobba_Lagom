package com.example.i7.jobbalagom.activities;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.local.TimePickerDialogFragment;
import com.example.i7.jobbalagom.local.TimePickerFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {

    private int progress = 20;
    private TextView textView2;
    private View btnTax;
    private View btnTime;
    private ButtonListener bl = new ButtonListener();
    private FloatingButtonListenerA bl1 = new FloatingButtonListenerA();
    private TimePickerListener timePickerListener = new TimePickerListener();
    private TimePickerDialog timePickerDialog;

   // private MessageListener listener;

    private Controller ctrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusbarColor();


        ctrl = new Controller();


        //setIconActionBar(); //Lägger till ikon i actionbar, dock med för mycket margin.
        //floatingButton();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2 = (TextView) findViewById(R.id.textView);
        btnTax =  findViewById(R.id.action_b);
        btnTime = findViewById(R.id.action_a);
        btnTax.setOnClickListener(bl);
        btnTime.setOnClickListener(timePickerListener);

        //connection = new ServerConnection();
       // listener = new MessageListener();

    }

    private  void setStatusbarColor(){
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;

        }else if(id == R.id.action_about){
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Funktionen kan behövas för flytande knapp. Ta inte bort
    private void floatingButton(){
        final View actionB = findViewById(R.id.action_b);

        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
        actionC.setTitle("Hide/Show Action above");
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        menuMultipleActions.addButton(actionC);


        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(getResources().getColor(R.color.white));


        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    //Ny kommentar
    //När knappen blir klickad händer detta
    //Här borde vi alltså hämta en siffra från servern och visa.

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            textView2.setText(ctrl.getCurrentTax() + " ");
            ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
            pb.setProgress(progress);
            progress++;

           // serverConnection.sendMessage("hej");
            //ctrl.sendMessage("");
                //connection.sendMessage("ehlo");
              //  textView2.setText(connection.readMessage());

        }
    }
    private class TimePickerListener implements  View.OnClickListener{

        @Override
        public void onClick(View v) {
            DialogFragment newFragment;
            newFragment = new TimePickerDialogFragment();
            newFragment.show(getFragmentManager(), "timePicker");

           // DialogFragment newFragment;
          //  newFragment = new TimePickerFragment();
           // newFragment.show(getFragmentManager(), "timePicker");
        }
    }

    private class FloatingButtonListenerA implements View.OnClickListener {
        @Override
        public void onClick(View v) {



        }
    }

}
