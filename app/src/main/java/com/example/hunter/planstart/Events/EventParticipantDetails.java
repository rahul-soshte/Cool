package com.example.hunter.planstart.Events;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.R;
import com.example.hunter.planstart.User.AddPeopleActivity;

import java.util.HashMap;

public class EventParticipantDetails extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_participant_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session=new SessionManager(this);

TextView tv=(TextView)findViewById(R.id.addpeep);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventParticipantDetails.this, AddPeopleActivity.class);
                startActivity(intent);
            }
        });

        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // email
        String email = user.get(SessionManager.KEY_EMAIL);
    }

    private class ListPeople extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            return null;

        }
                                                                                                                                                                                                                                                                     }

}
