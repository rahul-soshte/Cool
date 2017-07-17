package com.example.hunter.planstart.Events.EventActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hunter.planstart.CoreFunctionality.GetCenter;
import com.example.hunter.planstart.CoreFunctionality.Renting;
import com.example.hunter.planstart.Events.EventParticipantDetails;
import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.MainActivity;
import com.example.hunter.planstart.R;

public class EventActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,PlanRoom.PlanToolListListener {
    //This is our tablayout
    private TabLayout tabLayout;
    EventsOne event;


    //This is our viewPager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intent=getIntent();
      //  String name = intent.getStringExtra("title");
        event=(EventsOne)intent.getSerializableExtra("EventObject");
        setTitle(event.getEvent_name());
        //    int event_id=intent.getIntExtra("event_id",);



        //Adding toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventActivity.this, EventParticipantDetails.class);

                intent.putExtra("EventObject",event);
                startActivity(intent);
//                Toast.makeText(EventActivity.this,"Toolbar title clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Chat Room"));
        tabLayout.addTab(tabLayout.newTab().setText("Plan Room"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        com.example.hunter.planstart.Events.EventActivityClass.Pager adapter = new com.example.hunter.planstart.Events.EventActivityClass.Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
    @Override
    public void itemClicked(int id)
    {
        if(id==0)
        {
            Intent myIntent = new Intent(getApplicationContext(),Renting.class);
             myIntent.putExtra("EventObject",event);
                startActivity(myIntent);
        }
    }


   // public static EventsOne getEvent()
    //{
     //   return event;
    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    public void onBackPressed() {
        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
