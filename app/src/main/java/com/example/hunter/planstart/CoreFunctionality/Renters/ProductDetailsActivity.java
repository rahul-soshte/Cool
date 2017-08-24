package com.example.hunter.planstart.CoreFunctionality.Renters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hunter.planstart.CustomAdapter.ListRentersAdapters;
import com.example.hunter.planstart.GClasses.RentedThings;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.MainActivity;
import com.example.hunter.planstart.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView ProductName;
    TextView Description;
    TextView ContactNo;
    TextView rentPerDay;
    ImageView imageView;
    TextView Seller;
    RentedThings rentedThings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ProductName=(TextView)findViewById(R.id.textView5);
        Description=(TextView)findViewById(R.id.textView6);
        ContactNo=(TextView)findViewById(R.id.textView8);
        rentPerDay=(TextView)findViewById(R.id.textView7);
        imageView=(ImageView)findViewById(R.id.imageView);
        Seller=(TextView)findViewById(R.id.textView13);
        Intent intent=getIntent();
        rentedThings=(RentedThings)intent.getSerializableExtra("RentedThingObject");
        Description.setText(rentedThings.getProdesc());
        ProductName.setText(rentedThings.getProdname());
        ContactNo.setText(rentedThings.getContactno());
        rentPerDay.setText(Double.toString(rentedThings.getRentperday()));
        Seller.setText(rentedThings.getusername());
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {

        super.onStart();
        /*
        if(LoginActivity.isReachable(MainActivity.server_ip,80,500))
{       Picasso.with(ProductDetailsActivity.this)
                .load(rentedThings.getProductimageurl())
                .into(imageView);
}      else{
            Toast.makeText(getApplicationContext(),"Not Connected or Server Down or No Signal", Toast.LENGTH_LONG).show();
        }
        */

        Bitmap bitmap = null;
        if(isOnline()) {
            try {
                new MyTask().execute(rentedThings.getProductimageurl()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Not Connected or Server Down or No Signal", Toast.LENGTH_LONG).show();
        }
        }
 //               imageView.setImageBitmap(bitmap);



    public class MyTask extends AsyncTask<String, Void, String> {
        //  @Override
        //    protected void onPreExecute() {
        //    progressBar.setVisibility(View.VISIBLE);
        // }
        String url;
        @Override
        protected String doInBackground(String... voids) {

          //  Bitmap bitmap=null;

                 url = voids[0];
                //HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                //InputStream inputStream = connection.getInputStream();
                //bitmap = BitmapFactory.decodeStream(inputStream);
            return url;
        }

        // @Override
        //protected void onPostExecute(Bitmap bitmap) {
//            progressBar.setVisibility(View.GONE);
        //  return bitmap;
        // img.setImageBitmap(bitmap);
        //}


        @Override
        protected void onPostExecute(String loadurl)
        {
     //       imageView.setImageBitmap(bitmap);
            Picasso.with(ProductDetailsActivity.this)
                    .load(loadurl)
                    .into(imageView);

        }
    }


}
