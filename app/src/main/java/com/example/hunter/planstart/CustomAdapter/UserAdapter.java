package com.example.hunter.planstart.CustomAdapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.R;
import com.example.hunter.planstart.User.UserOne;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by hunter on 24/6/17.
 */

public class UserAdapter extends ArrayAdapter {
    private ArrayList<UserOne> users;
    EventsOne event;

    public UserAdapter(Context context, int resource,EventsOne event) {
        super(context, resource);
        users = new ArrayList<>();
        this.event=event;
    }

    public UserAdapter(Context context, int textViewResourceId, ArrayList<UserOne> users)
    {
        super(context,textViewResourceId,users);
        this.users=users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public UserOne getItem(int position) {
        return users.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    try {
                        //get data from the web
                        String term = constraint.toString();
                        users = new SearchPeep().execute(term).get();
                    } catch (Exception e) {
                        Log.d("HUS", "EXCEPTION " + e);
                    }
                    filterResults.values = users;
                    filterResults.count = users.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

        };
        return myFilter;
    }

    private class SearchPeep extends AsyncTask<String,Void,ArrayList>
    {
        String JSON_STRING;
        ArrayList<UserOne> value_array=new ArrayList<UserOne>();

        String addpeep_url="http://192.168.0.3/Planmap/add_peep.php";
        HttpHandler sh=new HttpHandler();

        @Override
        protected ArrayList doInBackground(String... params) {

            String argument = params[0];

            if (argument.equals("") || argument.equals("%")) {
                return value_array;

            }

            try {

                if (!(LoginActivity.isReachable("192.168.0.3", 80, 500))) {
                    return value_array;
                }
                URL url = new URL(addpeep_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                String post_data = URLEncoder.encode("argument", "UTF-8") + "=" + URLEncoder.encode(argument, "UTF-8")
                        +"&"+URLEncoder.encode("event_id", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(event.getEvent_id()), "UTF-8");
                sh.WritetoOutputStream(outputStream, post_data);
                outputStream.close();
                InputStream inputStream = conn.getInputStream();
                JSON_STRING = sh.convertStreamtoString(inputStream);
                inputStream.close();
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (JSON_STRING != null) {
                try {
                    //Getting JSON Array node
                    JSONArray results = new JSONArray(JSON_STRING);
                    for (int i = 0; i < results.length(); i++) {

                        JSONObject c = results.getJSONObject(i);

                        int user_id = c.getInt("user_id");
                        String firstname = c.getString("fname");
                        String lastname = c.getString("lname");
                        String username = c.getString("username");
                        String email_id = c.getString("email_id");
                        String password = c.getString("password");
                        UserOne user = new UserOne(user_id, firstname, lastname, email_id, username, password);
                        value_array.add(user);
                    }

                } catch (Exception e) {
                    return value_array;

                }

            }
            return value_array;
        }
    }
/*
    public boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
*/
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v=convertView;

        if(v==null)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.user_add_peep,null);
        }

        UserOne i=users.get(position);
        if(i!=null)
        {
            TextView tfname=(TextView)v.findViewById(R.id.fname);
            TextView tlname=(TextView)v.findViewById(R.id.lname);
            TextView tusername=(TextView)v.findViewById(R.id.username);


            if(tfname!=null)
            {
                tfname.setText(" "+i.getFirstName()+" ");

            }

            if(tlname!=null)
            {
                tlname.setText(i.getLastName());

            }

            if(tusername!=null)
            {
                tusername.setText("("+i.getUsername()+")");
            }

        }
        return v;
    }

}
