package com.example.hunter.planstart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.hunter.planstart.Events.EventActivityClass.EventActivity;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.Login.SessionManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
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
String user_email;
String eventname;
HttpHandler sh=new HttpHandler();


    AlertDialog alertDialog;
String Error="error";

    public BackgroundWorker(Context ctx)
    {
        context=ctx;

    }
    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url="http://192.168.42.151/Planmap/login.php";
        String signup_url="http://192.168.42.151/Planmap/signup.php";
        String createevent_url="http://192.168.42.151/Planmap/createevent.php";
        String listevents_url="http://192.168.42.151/Planmap/events_json.php";


        if(type.equals("login"))
        {
            try {
                user_email=params[1];
                String pass_word=params[2];

                if (!(LoginActivity.isReachable("192.168.42.151",80,500)))
                {
                    return "Not Connected or Server Down or No Signal";

                }

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
                String result=sh.convertStreamToStringWithoutNewline(inputStream);
               inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
             return Error;
            } catch (IOException e) {
                return Error;
            }
            catch (Exception e)
            {
                return Error;
            }

        }
        else if(type.equals("signup"))
        {
            try {
                String fname = params[1];

                String lname = params[2];

                user_email = params[3];

                //String mobile =
                String pass_word = params[4];
                URL url=new URL(signup_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                if (!(LoginActivity.isReachable("192.168.42.151",80,500)))
                {
                    return "Not Connected or Server Down or No Signal";

                }


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

                String result_signup=sh.convertStreamToStringWithoutNewline(inputStream);
                inputStream.close();
                httpURLConnection.disconnect();
                return result_signup;
            } catch (MalformedURLException e) {
                return Error;
            } catch (IOException e) {
                //e.printStackTrace();
             return Error;
            }
            catch(Exception e)
            {
                return Error;
            }

        }
        else if(type.equals("CreateEvent"))
        {
            try {
                eventname = params[1];
                user_email=params[2];


                if (!(LoginActivity.isReachable("192.168.42.151",80,500)))
                {
                    return "Not Connected or Server Down or No Signal";

                }

                URL url=new URL(createevent_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("eventname","UTF-8")+"="+URLEncoder.encode(eventname,"UTF-8")+"&"
                        +URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                String result=sh.convertStreamToStringWithoutNewline(inputStream);
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                return Error;
            } catch (IOException e) {
                return Error;
            }
            catch (Exception e)
            {
                return Error;
            }
        }

        return Error;
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

        if (result.equals("Welcome user!")) {
            SessionManager session = new SessionManager(context);
            session.createLoginSession("User",user_email);
//LoginActivity.wassuccessful=true;
            Intent intent=new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(intent);
            context.startActivity(intent);

//            ((LoginActivity) context).finish();

        }
        if (result.equals("Insert success")) {

            SessionManager session = new SessionManager(context);
            session.createLoginSession("User",user_email);
            Intent intent = new Intent(context, MainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
            Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_LONG).show();

        }

        if (result.equals("error")) {
            Toast.makeText(context, "Something went Wrong", Toast.LENGTH_LONG).show();
        }

        if(result.equals("Not Connected or Server Down or No Signal")) {
            Toast.makeText(context,"Not Connected or Server Down or No Signal", Toast.LENGTH_LONG).show();
        }
        if(result.equals("Event Created"))
        {
            Intent intent=new Intent(context,EventActivity.class);
            intent.putExtra("title",eventname);
            context.startActivity(intent);
            Toast.makeText(context,"Event Created", Toast.LENGTH_LONG).show();

        }

        Toast.makeText(context,result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}