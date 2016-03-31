package com.example.i7.jobbalagom;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {
    //jfhdhfjd
    private TextView textView2;
    // hej
    private Button btnCalc;
    private ButtonListener bl = new ButtonListener();

    private ServerConnection serverConnection;
    // private DataInputStream dis;
    // private DataOutputStream dos;

   // private ServerConnection connection;

    //private Controller ctrl = new Controller();
    //private Server server = new Server(port);


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView2 =(TextView) findViewById(R.id.textView2);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(bl);

        //connection = new ServerConnection();

        serverConnection = new ServerConnection();


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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Funktionen visar den flytande knappen, ta inte bort!
    private void floatingButton(){
       /* final View actionB = findViewById(R.id.action_b);

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
                actionA.setTitle("Något händer");
            }
        });
*/
    }

    //När knappen blir klickad händer detta
    //Här borde vi alltså hämta en siffra från servern och visa.
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            btnCalc.setText("Klickad");
            serverConnection.sendMessage("hej");
                //connection.sendMessage("ehlo");
              //  textView2.setText(connection.readMessage());

        }
    }
}
