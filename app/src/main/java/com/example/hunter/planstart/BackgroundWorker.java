package com.example.hunter.planstart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.hunter.planstart.Events.EventActivityClass.EventActivity;
import com.example.hunter.planstart.Events.EventParticipantDetails;
import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.User.UserOne;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Rahul on 21-05-2017.
 */

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
String user_email;
String eventname;
    EventsOne event;

    private ArrayList<UserOne> users;

HttpHandler sh=new HttpHandler();

//WeakReference<Activity> mWeakActivity;
    AlertDialog alertDialog;
String Error="error";

    public BackgroundWorker(Context ctx)
    {
        context=ctx;

    }
    public BackgroundWorker(Context ctx, ArrayList<UserOne> users)
    {
        context=ctx;
        this.users=users;
    }
    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url="http://192.168.42.151/Planmap/login.php";
        String signup_url="http://192.168.42.151/Planmap/signup.php";
        String createevent_url="http://192.168.42.151/Planmap/createevent.php";
        String listevents_url="http://192.168.42.151/Planmap/events_json.php";
        String checkusername_url="http://192.168.42.151/Planmap/checkusername.php";
        String addpeepurl="http://192.168.42.151/Planmap/addtheselectedpeep.php";

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
                String post_data= URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8")+"&"
                        +URLEncoder.encode("pass_word","UTF-8")+"="+URLEncoder.encode(pass_word,"UTF-8");
                sh.WritetoOutputStream(outputStream,post_data);
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

                String username=params[5];


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

                String post_data= URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(fname,"UTF-8")+"&"
                 + URLEncoder.encode("lname","UTF-8")+"="+URLEncoder.encode(lname,"UTF-8")+"&"
                 + URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8")+"&"
                        +URLEncoder.encode("pass_word","UTF-8")+"="+URLEncoder.encode(pass_word,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");

                sh.WritetoOutputStream(outputStream,post_data);
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
                OutputStream outputStream=httpURLConnection.getOutputStream();

                String post_data= URLEncoder.encode("eventname","UTF-8")+"="+URLEncoder.encode(eventname,"UTF-8")+"&"
                        +URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8");
                sh.WritetoOutputStream(outputStream,post_data);
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                String result=sh.convertStreamToStringWithoutNewline(inputStream);
                int event_id=Integer.valueOf(result);
                event=new EventsOne(event_id,eventname);
        //        eventcreatedboolean=true;
                inputStream.close();
                httpURLConnection.disconnect();
                return "Event Created";
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
        if(type.equals("addpeep"))
        {
            String event_id=params[1];
            eventname=params[2];


            if (!(LoginActivity.isReachable("192.168.42.151",80,500)))
            {
                return "Not Connected or Server Down or No Signal";

            }
            try {

                if (!(LoginActivity.isReachable("192.168.42.151",80,500)))
                {
                    return "Not Connected or Server Down or No Signal";

                }
                URL url=new URL(addpeepurl);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();

                //Convert arraylist to JSON String
                String json=sh.convertarraylisttojson(users);

                String post_data= URLEncoder.encode("event_id","UTF-8")+"="+URLEncoder.encode(event_id,"UTF-8")+"&"
                        +URLEncoder.encode("json","UTF-8")+"="+URLEncoder.encode(json,"UTF-8");

                sh.WritetoOutputStream(outputStream,post_data);
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                String result=sh.convertStreamToStringWithoutNewline(inputStream);
                event=new EventsOne(Integer.valueOf(event_id),eventname);
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
        /*
        else if (type.equals("checkusername"))
        {
String username=params[1];

            try {

                URL url=new URL(checkusername_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                String post_data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                sh.WritetoOutputStream(outputStream,post_data);
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                String result=sh.convertStreamToStringWithoutNewline(inputStream);
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        */

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
        /*
        if(result.equals("Username already taken"))
        {
            TextView tv=(TextView)((SignupActivity)context).findViewById(R.id.result_text);
            tv.setText("hello");
        }
        if(result.equals("Cool"))
        {
            TextView tv=(TextView)((SignupActivity)context).findViewById(R.id.result_text);
            tv.setText("Move Bitch!");
        }
        */

        if (result.equals("error")) {
            Toast.makeText(context, "Something went Wrong", Toast.LENGTH_LONG).show();
        }

        if(result.equals("Not Connected or Server Down or No Signal")) {
            Toast.makeText(context,"Not Connected or Server Down or No Signal", Toast.LENGTH_LONG).show();
        }
        if(result.equals("Event Created"))
        {
            Intent intent=new Intent(context,EventActivity.class);
            intent.putExtra("EventObject",event);
            context.startActivity(intent);
            Toast.makeText(context,"Event Created", Toast.LENGTH_LONG).show();

        }
        if(result.equals("Users successfully added"))
        {
            Intent intent=new Intent(context,EventParticipantDetails.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("EventObject",event);
            context.startActivity(intent);
            Toast.makeText(context,"Users added", Toast.LENGTH_LONG).show();

        }


        Toast.makeText(context,result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}