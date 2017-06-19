package com.example.hunter.planstart.Events;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hunter.planstart.BackgroundWorker;
import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.R;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateEventActivity extends AppCompatActivity{
 //   String[] SPINNERLIST = {"Hangout","Trip"};
    SessionManager session;
    Button nextbutton;
    String email;
    @Bind(R.id.input_eventname)EditText eventname;
    @Bind(R.id.next_button)Button createbutton;
   //@Bind(R.id.material_spinner1)MaterialBetterSpinner eventtype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        session = new SessionManager(getApplicationContext());

        ButterKnife.bind(this);
        createbutton.setEnabled(false);
     //   ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
       //         android.R.layout.simple_dropdown_item_1line,SPINNERLIST);
//        eventtype = (MaterialBetterSpinner)
  //              findViewById(R.id.material_spinner1);
        //eventtype.setAdapter(arrayAdapter);

        HashMap<String, String> user = session.getUserDetails();

        // name
        //String name = user.get(SessionManager.KEY_NAME);

        // email
         email = user.get(SessionManager.KEY_EMAIL);

        eventname.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableSubmitIfReady();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    public void enableSubmitIfReady() {

        boolean isReady =eventname.getText().toString().length()>0;
        createbutton.setEnabled(isReady);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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




    public void onClickCreate(View v)
    {

String event= eventname.getText().toString();

        //String eventdesc=eventtype.get
//        String eventdesc=String.valueOf(eventtype.getListSelection());
        String type="CreateEvent";

if(isOnline()) {
    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
    backgroundWorker.execute(type, event,email);
}
else{
    Toast.makeText(this,"No Internet!",Toast.LENGTH_LONG).show();
    }

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
