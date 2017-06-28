package com.example.hunter.planstart.Events.EventActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hunter.planstart.CoreFunctionality.GetCenter;
import com.example.hunter.planstart.R;

/**
 * Created by hunter on 13/5/17.
 */

public class PlanRoom extends Fragment {
    ListView PlanTools;
    String[] Tools = {"GetCenter Tool", "Suggestion Tool", "Merge Similiar Events","Schedule","Carpool"};

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.planroom, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PlanTools=(ListView)getActivity().findViewById(R.id.Toolslist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1,Tools);
        PlanTools.setAdapter(adapter);

        PlanTools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
if(position==0) {
    Intent myIntent = new Intent(getActivity().getApplicationContext(), GetCenter.class);
    // myIntent.putExtra("EventObject",event);

    // myIntent.putExtra("title",eventname);

    startActivity(myIntent);
}

            }

        });

    }
}