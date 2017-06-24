package com.example.hunter.planstart.Events;


import com.example.hunter.planstart.User.UserOne;

import java.util.ArrayList;

/**
 * Created by hunter on 4/5/17.
 */

public class EventsOne {

    public ArrayList<UserOne> UsersList;
    public int event_id;
    public String event_name;

    public EventsOne(int event_id,String event_name)
    {

        this.event_id=event_id;
        this.event_name=event_name;

    }


    public String toString()
    {
        return this.event_name;
    }

    public String getEvent_name() {
        return event_name;
    }

    public int getEvent_id()
    {
        return event_id;
    }


}
