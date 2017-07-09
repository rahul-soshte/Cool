package com.example.hunter.planstart.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.R;

import java.util.ArrayList;

/**
 * Created by hunter on 4/5/17.
 */

public class EventAdapter extends ArrayAdapter {

    private ArrayList<EventsOne> events;

    public EventAdapter(Context context, int textViewResourceId, ArrayList<EventsOne> events)
    {
      super(context,textViewResourceId,events);

        this.events=events;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v=convertView;
        if(v==null)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.event_listitem_layout,null);
        }
        EventsOne i=events.get(position);
        if(i!=null)
        {
            TextView tn=(TextView)v.findViewById(R.id.event_name);
            
            if(tn!=null)
            {
                tn.setText(i.getEvent_name());

            }

        }
        return v;
    }

}
