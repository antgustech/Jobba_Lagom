package com.example.i7.jobbalagom.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i7.jobbalagom.client.Controller;
import com.example.i7.jobbalagom.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {

    private TextView textView2;
    private Button btnCalc;
    private ButtonListener bl = new ButtonListener();

   // private MessageListener listener;

    private Controller ctrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        ctrl = new Controller();
        //setIconActionBar(); //Lägger till ikon i actionbar, dock med för mycket margin.
        floatingButton();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2 = (TextView) findViewById(R.id.textView2);
        btnCalc =  (Button) findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(bl);


        //connection = new ServerConnection();
       // listener = new MessageListener();

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

        }
        return super.onOptionsItemSelected(item);
    }

    //Funktionen visar den flytande knappen, ta inte bort!
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

    //När knappen blir klickad händer detta
    //Här borde vi alltså hämta en siffra från servern och visa.

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            btnCalc.setText(ctrl.getCurrentTax() + " ");

           // serverConnection.sendMessage("hej");
            //ctrl.sendMessage("");
                //connection.sendMessage("ehlo");
              //  textView2.setText(connection.readMessage());

        }
    }

    private void setIconActionBar(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.appicon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);
    }
}
