package com.example.hunter.planstart.CoreFunctionality.Renters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hunter.planstart.CustomAdapter.EventAdapter;
import com.example.hunter.planstart.CustomAdapter.ListRentersAdapters;
import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.GClasses.RentedThings;
import com.example.hunter.planstart.GClasses.Things;
import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.MainActivity;
import com.example.hunter.planstart.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class ListRenters extends AppCompatActivity {

    ListView BuyRent;
    ProgressBar progressBar;
    ImageView img;
    //SessionManager instance
    SessionManager session;
    Things things;

    String user_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_renters);

        progressBar= (ProgressBar) findViewById(R.id.progressBar);

        Intent intent=getIntent();
        things=(Things)intent.getSerializableExtra("ThingObject");
       // Toast.makeText(getApplicationContext(),things.getName(),Toast.LENGTH_LONG).show();

        BuyRent=(ListView)findViewById(R.id.listrenters);
        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);

        // email
        user_email = user.get(SessionManager.KEY_EMAIL);
        BuyRent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               RentedThings rentedThings=(RentedThings)adapterView.getItemAtPosition(i);
                //intent.putExtra("ThingObject",things);
                Intent intent=new Intent(ListRenters.this,ProductDetailsActivity.class);
                intent.putExtra("RentedThingObject",rentedThings);
                startActivity(intent);
            }
        });
        new LOL().execute();
        //img = (ImageView) findViewById(R.id.img);

    }
    private class LOL extends AsyncTask<String,Void,String>
    {
        String JSON_STRING;
        private ListRentersAdapters adapter;

        ArrayList<RentedThings> value_array=new ArrayList<RentedThings>();

        //@Override
           // protected void onPreExecute() {
         //   progressBar.setVisibility(View.VISIBLE);
         //}
        @Override
        protected String doInBackground(String... strings)
        {
            String listrenters_url="http://"+MainActivity.server_ip+"/Planmap/rents_json.php";
            try {
                if (!(LoginActivity.isReachable(MainActivity.server_ip,80,500)))
                {
                    return "Not Connected or Server Down or No Signal";
                }

                HttpHandler sh = new HttpHandler();
                URL url = new URL(listrenters_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                   conn.setDoInput(true);
                OutputStream outputStream = conn.getOutputStream();
                 String post_data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8")
                         +"&"+URLEncoder.encode("argument","UTF-8")+"="+URLEncoder.encode(things.getName(),"UTF-8");
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
                    //JSONObject jsonObject=new JSONObject(JSON_STRING);
                    //Getting JSON Array node
                    JSONArray results=new JSONArray(JSON_STRING);
                    for(int i=0;i<results.length();i++)
                    {
                        JSONObject c=results.getJSONObject(i);

                        String name=c.getString("prodname");
                        String prodimageurl=c.getString("prodimageurl");
                        Double rentperday=c.getDouble("rentperday");
                        int user_id=c.getInt("user_id");
                        String username=c.getString("username");
                        String prodesc=c.getString("proddesc");
                        String contactno=c.getString("usercontact");

                        RentedThings rentedThings=new RentedThings(name,prodimageurl,rentperday,user_id,username,prodesc,contactno);
                        value_array.add(rentedThings);
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
           // progressBar.setVisibility(View.GONE);
            if(result.equals("cool"))
            {
                adapter = new ListRentersAdapters(getApplicationContext(),R.layout.listrentersview, value_array);
                BuyRent.setAdapter(adapter);
            }
            if(result.equals("Not Connected or Server Down or No Signal")) {
                Toast.makeText(getApplicationContext(),"Not Connected or Server Down or No Signal", Toast.LENGTH_LONG).show();
            }
        }
    }
}
