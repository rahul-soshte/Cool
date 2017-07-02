package com.example.hunter.planstart.CoreFunctionality;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.R;
import com.example.hunter.planstart.User.UserOne;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class GetCenter extends FragmentActivity implements OnMapReadyCallback {

    ArrayList<UserOne> user_coordinates=new ArrayList<UserOne>();
ArrayList<Marker> markers=new ArrayList<>();
    private GoogleMap mMap;
     EventsOne event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_center);

        Intent intent=getIntent();
        event=(EventsOne)intent.getSerializableExtra("EventObject");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        new GetCenterofUsers().execute();


    }
    private class GetCenterofUsers extends AsyncTask<String,Void,String>
    {
        String result="";
HttpHandler sh=new HttpHandler();
      String coordinates_url="http://192.168.42.151/Planmap/getcoordinates.php";

        String event_id=Integer.toString(event.getEvent_id());

        @Override
        protected String doInBackground(String... strings) {
            if (!(LoginActivity.isReachable("192.168.42.151",80,500)))
            {
                return "Not Connected or Server Down or No Signal";
            }

            try {
                URL url=new URL(coordinates_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                String post_data = URLEncoder.encode("event_id","UTF-8")+"="+URLEncoder.encode(event_id,"UTF-8");
                sh.WritetoOutputStream(outputStream,post_data);
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                result=sh.convertStreamtoString(inputStream);
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(result!=null) {
try{
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    int user_id = 0;
                    double userlat = 0;
                    double userLong = 0;

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    user_id = jsonObject.getInt("user_id_fk");
                    userlat = jsonObject.getDouble("UserLat");
                    userLong = jsonObject.getDouble("UserLong");

                    UserOne user = new UserOne(user_id);
                    user.setGpsLatLong(userlat, userLong);
                    user_coordinates.add(user);
                }
            }catch(final JSONException e)
            {
                //                Log.e(TAG,"Json Parsing error: "+e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Json parsing error:" + e.getMessage(), Toast.LENGTH_LONG).show();
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


        @Override
protected void onPostExecute(String result)
{
    if(result.equals("cool"))
    {
        PlotPoints();
    }
}
    }

/*
    public LatLng getCenterofPeep()
    {
        LatLng center;
        if(!user_coordinates.isEmpty()){
            LatLng latLng=new LatLng();
            LatLngBounds bound = new LatLngBounds.Builder()
                    .include(user_coordinates).build();
        }
        return center;

    }
    */

    public void PlotPoints()
    {
        if(!user_coordinates.isEmpty() && user_coordinates!=null )
        {
            for(int i=0;i<user_coordinates.size();i++)
            {
                user_coordinates.get(i).getGpsLat();
                LatLng latLng = new LatLng(user_coordinates.get(i).getGpsLat(),user_coordinates.get(i).getGpsLong());

                Marker marker=mMap.addMarker(new MarkerOptions().position(latLng).title(Integer.toString(user_coordinates.get(i).getUser_id())));
               markers.add(marker);

                // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            }

        }
        if(markers.size()==1)
        {
            CameraPosition cameraPosition=new CameraPosition.Builder().target(markers.get(0).getPosition()).zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }else {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }

            LatLngBounds bounds = builder.build();
            int padding = 50; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
            LatLng latLng;
            latLng = bounds.getCenter();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Center"));

        }
    }

}
