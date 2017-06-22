package com.example.hunter.planstart.User;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class AddPeopleActivity extends AppCompatActivity {
    EditText etSearchbox;
    ListView lvFirst;
    ArrayAdapter<String> adapter1;
    String[] data = {""};

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
        ArrayList<String> value_array=new ArrayList<String>();

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

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("argument","UTF-8")+"="+URLEncoder.encode(argument,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
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
                        value_array.add(c.getString("fname"));

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, value_array);
                lvFirst.setAdapter(adapter);
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

