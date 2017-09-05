package com.example.hunter.planstart.Events;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hunter.planstart.CustomAdapter.RecyclerViewAdapter;
import com.example.hunter.planstart.CustomAdapter.UserAdapter;
import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.MainActivity;
import com.example.hunter.planstart.R;
import com.example.hunter.planstart.User.AddPeopleActivity;
import com.example.hunter.planstart.User.UserOne;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventParticipantDetails2 extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    EventsOne event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_participant_details2);
        Intent intent = getIntent();
        event = (EventsOne) intent.getSerializableExtra("EventObject");
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        layoutManager = new LinearLayoutManager(EventParticipantDetails2.this);
        recyclerView.setLayoutManager(layoutManager);
        requestJsonObject();
    }
    private void requestJsonObject(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.7/Planmap/listpeep.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                List<UserOne> posts = new ArrayList<UserOne>();
                posts = Arrays.asList(mGson.fromJson(response, UserOne[].class));
                adapter = new RecyclerViewAdapter(EventParticipantDetails2.this, posts);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error " + error.getMessage());
            }
        }){

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_id",Integer.toString(event.getEvent_id()));
                return params;
            }
        };
        queue.add(stringRequest);
    }
}

