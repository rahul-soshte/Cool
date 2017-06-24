package com.example.hunter.planstart.Events;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunter.planstart.CustomAdapter.UserAdapter;
import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.R;
import com.example.hunter.planstart.User.AddPeopleActivity;
import com.example.hunter.planstart.User.UserOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class EventParticipantDetails extends AppCompatActivity {

    SessionManager session;

    ListView listView;
EventsOne event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_participant_details);
        Intent intent=getIntent();
         event=(EventsOne)intent.getSerializableExtra("EventObject");
       // int event_id=event.getEvent_id();
    listView=(ListView)findViewById(R.id.list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session=new SessionManager(this);

TextView tv=(TextView)findViewById(R.id.addpeep);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventParticipantDetails.this, AddPeopleActivity.class);
                startActivity(intent);
            }
        });

        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        ListPeople listPeople=new ListPeople();
   if(isOnline()) {
       listPeople.execute();
   }
   else {
       Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_LONG).show();

   }
    }


    private class ListPeople extends AsyncTask<String,Void,String>
    {

        int event_id=event.getEvent_id();
        String listpeopleinevent_url="http://192.168.42.151/Planmap/listpeep.php";
        String JSON_STRING;

        private UserAdapter adapter;

        ArrayList<UserOne> value_array=new ArrayList<UserOne>();

        @Override
        protected String doInBackground(String... strings)
        {

            try {

                if (!(LoginActivity.isReachable("192.168.42.151",80,500)))
                {
                    return "Not Connected or Server Down or No Signal";

                }
                HttpHandler sh = new HttpHandler();
                URL url = new URL(listpeopleinevent_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                String post_data = URLEncoder.encode("event_id", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(event_id),"UTF-8");
                sh.WritetoOutputStream(outputStream,post_data);
                outputStream.close();
                InputStream inputStream = conn.getInputStream();
                JSON_STRING=sh.convertStreamtoString(inputStream);
                inputStream.close();
                conn.disconnect();
            }
            catch(Exception e){
                e.printStackTrace();
            }
//            Log.e(TAG,"Response from url: "+result);
            if(JSON_STRING!=null)
            {
                try{

                    //Getting JSON Array node
                     JSONArray results=new JSONArray(JSON_STRING);

                    for(int i=0;i<results.length();i++)
                    {
                        JSONObject c=results.getJSONObject(i);
                        int user_id=c.getInt("user_id");
                        String firstname=c.getString("fname");
                        String lastname=c.getString("lname");
                        String username=c.getString("username");
                        String email_id=c.getString("email_id");
                        String password=c.getString("password");

                        UserOne user=new UserOne(user_id,firstname,lastname,email_id,username,password);
                        value_array.add(user);
                    }

                }catch(final JSONException e)
                {
                    //                Log.e(TAG,"Json Parsing error: "+e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Json parsing error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else{
                //          Log.e(TAG,"Couldnt get Json from Server");
               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Couldnt get json from server.Check Logcat for possible errors",Toast.LENGTH_LONG).show();
                    }
                });
            }

            return "cool";
        }
        @Override
        protected void onPostExecute(String result)
        {
            if(result.equals("cool"))
            {
                adapter = new UserAdapter(getApplicationContext(),R.layout.user_add_peep, value_array);
                listView.setAdapter(adapter);

            }
            if(result.equals("Not Connected or Server Down or No Signal")) {
                Toast.makeText(getApplicationContext(),"Not Connected or Server Down or No Signal", Toast.LENGTH_LONG).show();
            }

        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
