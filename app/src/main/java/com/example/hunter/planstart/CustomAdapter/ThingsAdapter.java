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

import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.R;
import com.example.hunter.planstart.Things;
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
 * Created by hunter on 18/7/17.
 */

public class ThingsAdapter extends ArrayAdapter{
    private ArrayList<Things> things;

    public ThingsAdapter(Context context, int resource)
{
    super(context,resource);

}

public ThingsAdapter(Context context,int textViewResourceId,ArrayList<Things> things)
{
    super(context,textViewResourceId);
this.things=things;

}
    @Override
    public int getCount() {
        return things.size();
    }

    @Override
    public Things getItem(int position) {
        return things.get(position);
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
                        things = new SearchThings().execute(term).get();
                    } catch (Exception e) {
                        Log.d("HUS", "EXCEPTION " + e);
                    }
                    filterResults.values = things;
                    filterResults.count = things.size();
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

    private class SearchThings extends AsyncTask<String,Void,ArrayList>
    {
        String JSON_STRING;
        ArrayList<Things> value_array=new ArrayList<Things>();

        String findthings_url="http://192.168.0.3/Planmap/find_things.php";
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
                URL url = new URL(findthings_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                String post_data = URLEncoder.encode("argument", "UTF-8") + "=" + URLEncoder.encode(argument, "UTF-8");
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
                        String thing_name=c.getString("thing_name");
                        Things thing=new Things(thing_name);
                        value_array.add(thing);
                    }

                } catch (Exception e) {
                    return value_array;

                }

            }
            return value_array;
        }
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v=convertView;

        if(v==null)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.things,null);
        }

        Things i=things.get(position);
        if(i!=null)
        {
            TextView thingname=(TextView)v.findViewById(R.id.thingname);
            if(thingname!=null)
            {
                thingname.setText(" "+i.getName()+" ");
            }

        }
        return v;
    }

}
