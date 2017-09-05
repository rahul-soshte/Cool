package com.example.hunter.planstart.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hunter.planstart.CoreFunctionality.Renters.ListRenters;
import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.GClasses.RentedThings;
import com.example.hunter.planstart.R;
import com.google.android.gms.vision.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by hunter on 23/7/17.
 */

public class ListRentersAdapters extends ArrayAdapter {

//public ListRentersAdapters(Context context,int resource)
//{
 //   super(context,resource);
//}
ArrayList<RentedThings> rentedThings;

    public ListRentersAdapters(Context context, int textViewResourceId, ArrayList<RentedThings> rentedThings)
    {
        super(context,textViewResourceId,rentedThings);
        this.rentedThings=rentedThings;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        RentedThings i=rentedThings.get(position);

        View v=convertView;
        if(v==null)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.listrentersview,null);
        }

        if(i!=null)
        {
            ImageView imageView=(ImageView)v.findViewById(R.id.thingdp);
            TextView tproductname=(TextView)v.findViewById(R.id.productname);
            TextView tproductseller=(TextView)v.findViewById(R.id.sellername);
           TextView trateperday=(TextView)v.findViewById(R.id.Rateperday);

            if(tproductname!=null)
            {
                tproductname.setText(i.getProdname());
            }

            if(tproductseller!=null)
            {
                tproductseller.setText(i.getusername());
            }

            if(trateperday!=null)
            {
                trateperday.setText(Double.toString(i.getRentperday())+"/Day");
            }

            if(imageView!=null)
            {
                Bitmap bitmap= null;
                try {
                    bitmap = new MyTask().execute(i.getProductimageurl()).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmap);
            }
        }
        return v;
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
     //   protected void onPostExecute(Bitmap bitmap) {
//            progressBar.setVisibility(View.GONE);
          //  return bitmap;
           // img.setImageBitmap(bitmap);

        //}

    }


    }