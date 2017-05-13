package com.example.hunter.planstart.Events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.hunter.planstart.Events.EventActivityClass.EventActivity;
import com.example.hunter.planstart.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class CreateEventActivity extends AppCompatActivity{
    String[] SPINNERLIST = {"Hangout", "Adventure", "Trip","Hobby","Concert","Party","Other..Please Specify"};
Button nextbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.material_spinner1);
        materialDesignSpinner.setAdapter(arrayAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickNext(View v)
    {
Intent intent=new Intent(CreateEventActivity.this,EventActivity.class);

startActivity(intent);

    }

}
