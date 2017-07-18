package com.example.hunter.planstart.CoreFunctionality;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.example.hunter.planstart.CustomAdapter.ThingsAdapter;
import com.example.hunter.planstart.CustomAdapter.UserAdapter;
import com.example.hunter.planstart.R;
import com.example.hunter.planstart.Things;
import com.example.hunter.planstart.User.UserOne;

import java.util.ArrayList;

public class Renting extends AppCompatActivity {
ListView lvborrow;

    Button DoneButton;
    ArrayList<Things> ToBeBorrowed=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting);
        final ThingsAdapter adapter = new ThingsAdapter(this,android.R.layout.simple_dropdown_item_1line);
        lvborrow=(ListView)findViewById(R.id.borrowList);
        final AutoCompleteTextView actv;
        //final SearchPeep searchPeep = new SearchPeep();
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        actv.setTextColor(Color.BLACK);
        actv.setAdapter(adapter);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Things things=new Things(adapter.getItem(position).getName());
                ToBeBorrowed.add(things);
                ThingsAdapter adapter2=new ThingsAdapter(getApplicationContext(),R.layout.things,ToBeBorrowed);
                lvborrow.setAdapter(adapter2);
                actv.setText("");
            }
        });
    }


}
