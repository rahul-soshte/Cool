package com.example.hunter.planstart.TabLayout;

/**
 * Created by hunter on 7/5/17.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hunter.planstart.CustomAdapter.EventAdapter;
import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.MainActivity;
import com.example.hunter.planstart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


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
        ReadLocalJson readLocalJson=new ReadLocalJson();
        readLocalJson.execute();

        
    }

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

    private class GetContacts extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
           // Toast.makeText(getActivity().getApplicationContext(),"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            String jsonStr="";
            String listevents_url="http://192.168.42.151/Planmap/events_json.php";

try {
    HttpHandler sh = new HttpHandler();
    URL url = new URL(listevents_url);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setRequestMethod("POST");

   // httpURLConnection.setDoOutput(true);
    conn.setDoInput(true);
    OutputStream outputStream = conn.getOutputStream();

    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
    String post_data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8");

    bufferedWriter.write(post_data);
    bufferedWriter.flush();
    bufferedWriter.close();
    outputStream.close();
    InputStream in = new BufferedInputStream(conn.getInputStream());
    jsonStr = sh.convertStreamtoString(in);
}
catch(Exception e){
    e.printStackTrace();
}
            //Making a request to url and getting response
          //  String jsonStr=sh.makeServiceCall(url);

//            Log.e(TAG,"Response from url: "+jsonStr);
            if(jsonStr!=null)
            {
                try{

                    //Getting JSON Array node
                    JSONArray results=new JSONArray(jsonStr);

                    for(int i=0;i<results.length();i++)
                    {
                        JSONObject c=results.getJSONObject(i);

                        String eventname=c.getString("EventName");

                        EventsOne eventsOne=new EventsOne(eventname);

                        EList.add(eventsOne);

                    }

                }catch(final JSONException e)
                {
                    Log.e(TAG,"Json Parsing error: "+e);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getActivity().getApplicationContext(),"Json parsing error:"+e.getMessage(),Toast.LENGTH_LONG).show();


                        }
                    });
                }
            }else{
                Log.e(TAG,"Couldnt get Json from Server");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"Couldnt get json from server.Check Logcat for possible errors",Toast.LENGTH_LONG).show();

                    }
                });

            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
//        ListAdapter adapter=new SimpleAdapter(JasonActivity.this,contactList,R.layout.list_item,new String[]{"name","lat","long"},new int[]{R.id.name,R.id.lat,R.id.longi});
//        ArrayAdapter<Places> listadapter=new ArrayAdapter<Places>(JasonActivity.this,R.layout.list_item,);
            m_adapter = new EventAdapter(getActivity().getApplicationContext(), R.layout.event_listitem_layout,EList);
            EventList.setAdapter(m_adapter);
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
    */

}