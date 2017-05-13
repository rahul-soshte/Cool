package com.example.hunter.planstart.Events.EventActivityClass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hunter.planstart.R;
/**
 * Created by hunter on 13/5/17.
 */

public class ChatTab1 extends Fragment{

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.chattab1, container, false);
    }

}
