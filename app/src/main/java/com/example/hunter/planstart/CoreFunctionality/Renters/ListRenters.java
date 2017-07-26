package com.example.hunter.planstart.CoreFunctionality.Renters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.hunter.planstart.R;



import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListRenters extends AppCompatActivity {

    ListView BuyRent;
    ProgressBar progressBar;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_renters);
       BuyRent=(ListView)findViewById(R.id.listrenters);
        
        img = (ImageView) findViewById(R.id.img);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);

        String url="http://www.flat-e.com/flate5/wp-content/uploads/cover-960x857.jpg";
        MyTask myTask= new MyTask();
        myTask.execute(url);


       /* try {
            URL url = new URL("https://drive.google.com/drive/folders/0B1iGTxE7EvxpTU95cmpzZXo3aFk");
            //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
            HttpGet httpRequest = null;

            httpRequest = new HttpGet(url.toURI());

            HttpClient httpclient = new DefaultHttpClient();

            HttpResponse response = (HttpResponse) httpclient
                    .execute(httpRequest);

            HttpEntity entity = response.getEntity();
            BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
            InputStream input = b_entity.getContent();

            Bitmap bitmap = BitmapFactory.decodeStream(input);

            img.setImageBitmap(bitmap);

        } catch (Exception ex) {

        }
        */
        //WebView web = (WebView) findViewById(R.id.img);
        //web.loadUrl("https://drive.google.com/open?id=0B1iGTxE7EvxpTzhFZHFsUlctalE");
    }


    class MyTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... voids) {

            Bitmap bitmap=null;

            try {
                URL url =new URL(voids[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                InputStream inputStream= connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressBar.setVisibility(View.GONE);
            img.setImageBitmap(bitmap);

        }
    }
}
