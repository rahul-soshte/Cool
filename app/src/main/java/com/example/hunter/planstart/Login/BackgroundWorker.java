package com.example.hunter.planstart.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.hunter.planstart.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Rahul on 21-05-2017.
 */

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;

    BackgroundWorker(Context ctx)
    {
        context=ctx;

    }
    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url="http://192.168.42.151/Planmap/login.php";
        String signup_url="http://192.168.42.151/Planmap/signup.php";

        if(type.equals("login"))
        {
            try {String user_email=params[1];
                String pass_word=params[2];

                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8")+"&"
                        +URLEncoder.encode("pass_word","UTF-8")+"="+URLEncoder.encode(pass_word,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line=bufferedReader.readLine())!=null)
                {
                    result+=line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else if(type.equals("signup"))
        {
            try {
                String fname = params[1];

                String lname = params[2];

                String user_email = params[3];

                //String mobile =
                String pass_word = params[4];
                URL url=new URL(signup_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(fname,"UTF-8")+"&"
                 + URLEncoder.encode("lname","UTF-8")+"="+URLEncoder.encode(lname,"UTF-8")+"&"
                 + URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8")+"&"
                        +URLEncoder.encode("pass_word","UTF-8")+"="+URLEncoder.encode(pass_word,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result_signup="";
                String line="";
                while((line=bufferedReader.readLine())!=null)
                {
                    result_signup+=line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result_signup;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
/*        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
*/
     super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {

        //alertDialog.setMessage(result);
        //alertDialog.show();

if(result.equals("Welcome user!"))
{
    SessionManager session=new SessionManager(context);
    session.createLoginSession("Bro","Bro");
//LoginActivity.wassuccessful=true;
    Intent intent=new Intent(context, MainActivity.class);
    context.startActivity(intent);
    ((LoginActivity)context).finish();

}
if(result.equals("Insert success")) {

        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
        Toast.makeText(context,"Account Created Successfully",Toast.LENGTH_LONG).show();

}
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}