package com.example.hunter.planstart.Login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunter.planstart.BackgroundWorker;
import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final String LOGIN_URL = "http://192.168.1.4/test/login.php";
    @Bind(R.id.input_firstname)
    EditText _firstnameText;
    @Bind(R.id.input_lastname) EditText _lastnameText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_username) EditText _userNameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;
    @Bind(R.id.result_text) TextView Result_text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
//        TextView tv=(TextView)findViewById(R.id.result_text);
       // GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
       // gifImageView.setGifImageResource(R.drawable.ripple);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _userNameText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                if(isOnline()) {
//String type="checkusername";
//CheckUsername checkUsername=new CheckUsername();
  //                  checkUsername.execute(arg0.toString());
new CheckUsername().execute(arg0.toString());
//new BackgroundWorker(getApplicationContext()).execute(type,arg0.toString());
                }
                else {
                    Result_text.setText("Loading..");
                    Toast.makeText(getApplicationContext(),"Not Connected to Internet", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                if(isOnline()) {
                    if ((Result_text.getText()).equals("Loading..")) {
                        new CheckUsername().execute(arg0.toString());

                    }

                } else {
                        Result_text.setText("Loading..");
                        Toast.makeText(getApplicationContext(),"Not Connected to Internet", Toast.LENGTH_LONG).show();
                    }

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    private class CheckUsername extends AsyncTask<String,Void,String>
    {
HttpHandler sh=new HttpHandler();

        @Override
        protected String doInBackground(String... params) {

            String checkusername_url="http://192.168.0.3/Planmap/checkusername.php";
                String username=params[0];

                try {

                    if (!(LoginActivity.isReachable("192.168.0.3",80,500)))
                    {
                        return "Not Connected or Server Down or No Signal";

                    }

                    URL url=new URL(checkusername_url);
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    String post_data= URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(username,"UTF-8");
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
                return "noone";


            }
        protected void onPostExecute(String result)
        {
            if(result.equals("Not Connected or Server Down or No Signal"))
            {
                Result_text.setText("Loading..");

            }

    if(result.equals("Username already taken"))
    {
        //   TextView tv=(TextView)findViewById(R.id.result_text);
Result_text.setText("Username Already Taken");
    }
    if(result.equals("Cool"))
    {
        Result_text.setText("Cool");
    }
        }

        }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        String firstname = _firstnameText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String email = _emailText.getText().toString();
        String username = _userNameText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
String type = "signup";

        // TODO: Implement your own signup logic here.
if(isOnline()) {
    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
    backgroundWorker.execute(type, firstname, lastname, email,password,username);
}
else{
    Toast.makeText(getBaseContext(), "No Signal or Server Down or Not Connected", Toast.LENGTH_LONG).show();
}

    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(SignupActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstname = _firstnameText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String email = _emailText.getText().toString();
        String username = _userNameText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String result_textval= Result_text.getText().toString();

         if (firstname.isEmpty() || firstname.length() < 1) {
            _firstnameText.setError("at least 1 characters");
            valid = false;
        } else {
            _firstnameText.setError(null);
        }
        if (lastname.isEmpty() || lastname.length() < 1) {
            _lastnameText.setError("at least 1 characters");
            valid = false;
        } else {
            _lastnameText.setError(null);
        }


        if(result_textval.equals("Username Already Taken"))
        {
            _userNameText.setError("Username Already Taken");
            valid=false;

        }else if (username.length()<2 || username.isEmpty())
        {
            _userNameText.setError("Atleast 2 character");
        }
        else {
            _userNameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            _emailText.setError("enter a valid email address");
            valid = false;

        } else {
            _emailText.setError(null);
        }

      /*  if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }
*/
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}