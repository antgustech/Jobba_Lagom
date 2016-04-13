package com.example.i7.jobbalagom.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.i7.jobbalagom.R;
import com.example.i7.jobbalagom.local.Controller;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    private int progress = 20;
    private TextView textView2;
    private View btnTax;
    private View btnTime;
    private View btnWork;
    private View btnBudget;
    private ButtonListener bl = new ButtonListener();
    private TimePickerDialog timePickerDialog;
    private Controller ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusbarColor();
        ctrl = new Controller();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2 = (TextView) findViewById(R.id.textView);
        btnTime = findViewById(R.id.action_a);
        btnTax = findViewById(R.id.action_b);
        btnWork = findViewById(R.id.action_e);
        btnBudget = findViewById(R.id.action_f);
        btnWork.setOnClickListener(bl);
        btnBudget.setOnClickListener(bl);
        btnTax.setOnClickListener(bl);
        btnTime.setOnClickListener(bl);
    }

    private void setupGraph(){
        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
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
    //Klick listemer för den flytande knappens alternativ.
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.action_b) {
                textView2.setText(ctrl.getCurrentTax() + " ");
                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
                pb.setProgress(progress);
                progress++;
            }else if(v.getId() == R.id.action_f){
                startActivity(new Intent(getApplicationContext(), BudgetAcitivity.class));
            }else if(v.getId() == R.id.action_a){
                startActivity(new Intent(getApplicationContext(), AddExpenseActivity.class));

            }else if(v.getId() == R.id.action_e){
                startActivity(new Intent(getApplicationContext(), WorkRegisterActivity.class));
            }
    }
    }


}
