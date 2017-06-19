package com.example.hunter.planstart.TabLayout;

/**
 * Created by hunter on 7/5/17.
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hunter.planstart.CustomAdapter.EventAdapter;
import com.example.hunter.planstart.Events.EventActivityClass.EventActivity;
import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.MainActivity;
import com.example.hunter.planstart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


//Our class extending fragment
public class Tab1 extends Fragment {

    ArrayList<EventsOne> EList;

    private EventAdapter m_adapter;
 String user_email;
 ListView EventList;
    private String TAG = MainActivity.class.getSimpleName();
SessionManager session;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.tab1, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventList=(ListView)getActivity().findViewById(R.id.list);
        //Resources res=getResources();
        //String [] events=res.getStringArray(R.array.event_list);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,events);
        //EventList.setAdapter(adapter);
        session = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        user_email = user.get(SessionManager.KEY_EMAIL);

        EventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity().getApplicationContext(),EventActivity.class);
              //  myIntent.putExtra("test", "hello");

                startActivity(myIntent);
            }
        });


        if (isOnline()) {
            //          BackgroundWorker backgroundWorker = new BackgroundWorker(getActivity().getApplicationContext());
            //new GetContacts().execute();

            ReadJson readJson=new ReadJson();
            readJson.execute();
        }//Else SQLITE OR CHANGED=0----------POSSIBLE FUTURE CODE

        else {
            Toast.makeText(getActivity(), "Not Connected to Internet", Toast.LENGTH_LONG).show();

        }

    }

/*
    public class ReadLocalJson extends AsyncTask<String,String,String>
    {
        ArrayList<String> value_array=new ArrayList<String>();

        @Override
        protected String doInBackground(String... strings) {
            String json=null;

            try {
                InputStream is=getResources().openRawResource(R.raw.events);
                int size=is.available();
                byte[] buffer=new byte[size];
                is.read(buffer);
                is.close();
                json=new String(buffer,"UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONArray obj_array=null;

            try {
                obj_array=new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for(int i=0;i<obj_array.length();i++)
            {
                JSONObject json_data=null;

                try {
                    json_data=obj_array.getJSONObject(i);
                    value_array.add(json_data.getString("EventName"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;


        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,value_array);
            EventList.setAdapter(adapter);
        }
    }
*/
/*
@Override
    public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);


        EList=new ArrayList<>();
        EventList=(ListView)getActivity().findViewById(R.id.list);

        session = new SessionManager(getActivity().getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        user_email = user.get(SessionManager.KEY_EMAIL);

        String type = "ListEvents";

        if (isOnline()) {
  //          BackgroundWorker backgroundWorker = new BackgroundWorker(getActivity().getApplicationContext());
         new GetContacts().execute();

        }//Else SQLITE OR CHANGED=0----------POSSIBLE FUTURE CODE
        else {
            Toast.makeText(getActivity().getApplicationContext(), "Not Connected to Internet", Toast.LENGTH_LONG).show();

        }
    }
*/

    private class ReadJson extends AsyncTask<String,Void,String>
    {

        String JSON_STRING;

        ArrayList<String> value_array=new ArrayList<String>();

        @Override
        protected String doInBackground(String... strings)
        {
            String result="";
            String jsonStr="";
            String listevents_url="http://192.168.42.151/Planmap/events_json.php";

try {

    if (!(LoginActivity.isReachable("192.168.42.151",80,500)))
    {
        return "Not Connected or Server Down or No Signal";

    }

    HttpHandler sh = new HttpHandler();

    URL url = new URL(listevents_url);

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setRequestMethod("POST");


    conn.setDoInput(true);

    OutputStream outputStream = conn.getOutputStream();

    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

    String post_data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8");

    bufferedWriter.write(post_data);

    bufferedWriter.flush();

    bufferedWriter.close();

    outputStream.close();

   InputStream inputStream = conn.getInputStream();

    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

    StringBuilder stringBuilder=new StringBuilder();

    while((JSON_STRING=bufferedReader.readLine())!=null)
    {

        stringBuilder.append(JSON_STRING+"\n");

    }

    bufferedReader.close();
    inputStream.close();
    conn.disconnect();
    JSON_STRING=stringBuilder.toString().trim();
}
catch(Exception e){
    e.printStackTrace();
}


//            Log.e(TAG,"Response from url: "+result);
            if(JSON_STRING!=null)
            {
                try{

                    JSONObject jsonObject=new JSONObject(JSON_STRING);

                    //Getting JSON Array node
                    JSONArray results=jsonObject.getJSONArray("server_response");

                    for(int i=0;i<results.length();i++)
                    {

                        JSONObject c=results.getJSONObject(i);

                        value_array.add(c.getString("EventName"));

                    }

                }catch(final JSONException e)
                {
    //                Log.e(TAG,"Json Parsing error: "+e);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),"Json parsing error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else{
      //          Log.e(TAG,"Couldnt get Json from Server");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"Couldnt get json from server.Check Logcat for possible errors",Toast.LENGTH_LONG).show();
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, value_array);
                EventList.setAdapter(adapter);
            }
            if(result.equals("Not Connected or Server Down or No Signal")) {
                Toast.makeText(getActivity(),"Not Connected or Server Down or No Signal", Toast.LENGTH_LONG).show();
            }

        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


}