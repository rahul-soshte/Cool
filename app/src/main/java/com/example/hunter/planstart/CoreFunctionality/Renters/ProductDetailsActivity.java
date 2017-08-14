package com.example.hunter.planstart.CoreFunctionality.Renters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hunter.planstart.CustomAdapter.ListRentersAdapters;
import com.example.hunter.planstart.GClasses.RentedThings;
import com.example.hunter.planstart.R;

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

        Intent intent=getIntent();
        rentedThings=(RentedThings)intent.getSerializableExtra("RentedThingObject");
        Description.setText(rentedThings.getProdesc());
        ProductName.setText(rentedThings.getProdname());
        ContactNo.setText(rentedThings.getContactno());
        rentPerDay.setText(Double.toString(rentedThings.getRentperday()));
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //Bitmap bitmap= null;
        try {
             new MyTask().execute(rentedThings.getProductimageurl()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    //    imageView.setImageBitmap(bitmap);
    }

    public class MyTask extends AsyncTask<String, Void, Bitmap> {
        //  @Override
        //    protected void onPreExecute() {
        //    progressBar.setVisibility(View.VISIBLE);
        // }
        @Override
        protected Bitmap doInBackground(String... voids) {

            Bitmap bitmap=null;

            try {
                URL url =new URL(voids[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        // @Override
        //protected void onPostExecute(Bitmap bitmap) {
//            progressBar.setVisibility(View.GONE);
        //  return bitmap;
        // img.setImageBitmap(bitmap);
        //}
        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            imageView.setImageBitmap(bitmap);
        }
    }
}
