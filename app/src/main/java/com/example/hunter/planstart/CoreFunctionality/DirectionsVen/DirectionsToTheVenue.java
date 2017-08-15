package com.example.hunter.planstart.CoreFunctionality.DirectionsVen;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.R;

public class DirectionsToTheVenue extends AppCompatActivity {

    EventsOne event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_to_the_venue);
        Intent intent=getIntent();
        event=(EventsOne)intent.getSerializableExtra("EventObject");

    }
}
