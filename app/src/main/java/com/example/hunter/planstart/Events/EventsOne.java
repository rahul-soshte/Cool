package com.example.hunter.planstart.Events;


import com.example.hunter.planstart.User.UserOne;

import java.util.ArrayList;

/**
 * Created by hunter on 4/5/17.
 */

public class EventsOne {

    public ArrayList<UserOne> UsersList;
    public String event_name;

    public EventsOne(String event_name)
    {

        this.event_name=event_name;

    }

    public static final EventsOne[] event = {
            new EventsOne("Event 1"),
            new EventsOne("Event 2"),
            new EventsOne("Event 3"),
            new EventsOne("Event 4"),
            new EventsOne("Event 5")

    };

    public String toString()
    {
        return this.event_name;
    }

    public String getEvent_name() {
        return event_name;
    }



}
