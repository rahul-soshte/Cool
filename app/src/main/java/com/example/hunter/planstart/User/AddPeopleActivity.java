package com.example.hunter.planstart.User;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hunter.planstart.CustomAdapter.UserAdapter;
import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
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

public class AddPeopleActivity extends AppCompatActivity {
    EditText etSearchbox;
    ListView lvFirst;
    //ArrayAdapter<String> adapter1;
    String[] data = {""};
private UserAdapter m_adapter;

    ArrayList<String> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        final SearchPeep searchPeep = new SearchPeep();
        etSearchbox = (EditText) findViewById(R.id.etSearchbox);
        lvFirst = (ListView) findViewById(R.id.lvFirst);
        lvFirst.setTextFilterEnabled(true);
        etSearchbox.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
if(isOnline()) {
    new SearchPeep().execute(arg0.toString());
}
else {
    Toast.makeText(getApplicationContext(),"Not Connected to Internet", Toast.LENGTH_LONG).show();
}
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private class SearchPeep extends AsyncTask<String,Void,String>
    {
        String JSON_STRING;
        ArrayList<UserOne> value_array=new ArrayList<UserOne>();

String addpeep_url="http://192.168.42.151/Planmap/add_peep.php";
HttpHandler sh=new HttpHandler();

        @Override
        protected String doInBackground(String... params) {

         String argument=params[0];

            if(argument.equals("") || argument.equals("%") )
            {
                return "cool";
            }
            try {

                if (!(LoginActivity.isReachable("192.168.42.151",80,500)))
                {
                    return "Not Connected or Server Down or No Signal";
                }
                URL url=new URL(addpeep_url);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                OutputStream outputStream=conn.getOutputStream();
                String post_data= URLEncoder.encode("argument","UTF-8")+"="+URLEncoder.encode(argument,"UTF-8");
                sh.WritetoOutputStream(outputStream,post_data);
                outputStream.close();
                InputStream inputStream = conn.getInputStream();

                JSON_STRING = sh.convertStreamtoString(inputStream);
                inputStream.close();
                conn.disconnect();


            } catch (IOException e) {
                e.printStackTrace();
            }

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
        protected void onPostExecute(String result)
        {
            if(result.equals("cool"))
            {
                m_adapter = new UserAdapter(getApplicationContext(),R.layout.user_add_peep,value_array);
                lvFirst.setAdapter(m_adapter);
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

