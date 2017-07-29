package com.example.hunter.planstart.CoreFunctionality;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.hunter.planstart.CoreFunctionality.Renters.ListRenters;
import com.example.hunter.planstart.CustomAdapter.ThingsAdapter;
import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.R;
import com.example.hunter.planstart.GClasses.Things;

import java.util.ArrayList;

import static android.support.constraint.R.id.parent;

public class Renting extends AppCompatActivity {
    ListView lvborrow;
    ImageButton pushDownButton;
    Button DoneButton;
    ArrayList<Things> ToBeBorrowed=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting);
        final ThingsAdapter adapter = new ThingsAdapter(this,android.R.layout.simple_dropdown_item_1line);
        lvborrow=(ListView)findViewById(R.id.borrowList);
        pushDownButton=(ImageButton)findViewById(R.id.pushdownbutton);
        final AutoCompleteTextView actv;
        //final SearchPeep searchPeep = new SearchPeep();
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        actv.setTextColor(Color.BLACK);
        actv.setAdapter(adapter);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Things things=new Things(adapter.getItem(position).getName(),false);
                ToBeBorrowed.add(things);
                ThingsAdapter adapter2=new ThingsAdapter(getApplicationContext(),R.layout.things,ToBeBorrowed);
                lvborrow.setAdapter(adapter2);
                actv.setText("");
            }
        });

        pushDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!actv.getText().toString().equals("")) {
                    Things things = new Things(actv.getText().toString(),false);
                    ToBeBorrowed.add(things);
                    ThingsAdapter adapter3 = new ThingsAdapter(getApplicationContext(), R.layout.things, ToBeBorrowed);
                    lvborrow.setAdapter(adapter3);
                    actv.setText("");
                }
            }
        });

lvborrow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
       // EventsOne event=(EventsOne)parent.getItemAtPosition(position);
        Things things=(Things) adapterView.getItemAtPosition(position);
        Intent intent=new Intent(Renting.this, ListRenters.class);
        intent.putExtra("ThingObject",things);
        startActivity(intent);
    }
});
    }


}
