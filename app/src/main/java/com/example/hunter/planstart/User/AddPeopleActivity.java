package com.example.hunter.planstart.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hunter.planstart.BackgroundWorker;
import com.example.hunter.planstart.CustomAdapter.UserAdapter;
import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.R;

import java.util.ArrayList;

public class AddPeopleActivity extends Activity {
    EditText etSearchbox;
    ListView lvFirst;
    //ArrayAdapter<String> adapter1;
    String[] data = {""};
Button DoneButton;

//private UserAdapter m_adapter;

    ArrayList<String> people;
ArrayList<UserOne> ToBeAdded=new ArrayList<>();
EventsOne event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);

        Intent intent=getIntent();
        event=(EventsOne)intent.getSerializableExtra("EventObject");


    DoneButton=(Button)findViewById(R.id.donebutton);
        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type="addpeep";
                BackgroundWorker backgroundWorker=new BackgroundWorker(getApplicationContext(),ToBeAdded);
                backgroundWorker.execute(type,Integer.toString(event.getEvent_id()),event.getEvent_name());
            }
        });
        lvFirst=(ListView)findViewById(R.id.AddedListView);

        final AutoCompleteTextView actv;
        //final SearchPeep searchPeep = new SearchPeep();
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setTextColor(Color.RED);


        final UserAdapter adapter = new UserAdapter(this,android.R.layout.simple_dropdown_item_1line);
        actv.setAdapter(adapter);
        //when autocomplete is clicked
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               int user_id=adapter.getItem(position).getUser_id();
                String firstname =adapter.getItem(position).getFirstName();
                String lastname =adapter.getItem(position).getLastName();
                String username =adapter.getItem(position).getUsername();
                String email_id =adapter.getItem(position).getemailid();
                String password =adapter.getItem(position).getPassword();
                UserOne user = new UserOne(user_id, firstname, lastname, email_id, username, password);
                ToBeAdded.add(user);
                UserAdapter adapter2=new UserAdapter(getApplicationContext(),R.layout.user_add_peep,ToBeAdded);
                lvFirst.setAdapter(adapter2);
                actv.setText("");
            }
        });
    }

    public boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


}

