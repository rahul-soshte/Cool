package com.example.hunter.planstart.CoreFunctionality;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.hunter.planstart.Events.EventsOne;
import com.example.hunter.planstart.GPS.GPSTracker;
import com.example.hunter.planstart.HttpHandler;
import com.example.hunter.planstart.Login.LoginActivity;
import com.example.hunter.planstart.Login.SessionManager;
import com.example.hunter.planstart.Places.PlacesOne;
import com.example.hunter.planstart.R;
import com.example.hunter.planstart.User.UserOne;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import java.util.HashMap;

//Google Places API Web Service
// AIzaSyDiJ02luwrL_VxUo3E4al2eJqo45mSEzns


//Google Places Android API Key
// AIzaSyCvEmzifwbu6O3GuCMy8ONEtDJ0EfvapiE
public class GetCenter extends AppCompatActivity implements OnMapReadyCallback {
Button EditLocationButton;
SessionManager session;
    String user_email;

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    GPSTracker gps;
    ArrayList<UserOne> user_coordinates=new ArrayList<UserOne>();
ArrayList<Marker> markers=new ArrayList<>();
    ArrayList<PlacesOne> places=new ArrayList<>();

    private GoogleMap mMap;
     EventsOne event;
String API_KEY="AIzaSyDiJ02luwrL_VxUo3E4al2eJqo45mSEzns";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_center);
        Button btn = (Button) findViewById(R.id.PlaceFilter);

        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {
Intent intent=new Intent(getApplicationContext(),Filters.class);
                startActivity(intent);
            }
        };

        btn.setOnClickListener(listener);
        EditLocationButton=(Button)findViewById(R.id.editloc);

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        // name
        //name = user.get(SessionManager.KEY_NAME);

        // email
        user_email = user.get(SessionManager.KEY_EMAIL);

        Intent intent=getIntent();
        event=(EventsOne)intent.getSerializableExtra("EventObject");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


       registerForContextMenu(EditLocationButton);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng latLng=place.getLatLng();
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                String type="ChangeLocation";
                new GetCenterofUsers().execute(type,Double.toString(latitude),Double.toString(longitude));

                // Log.i(TAG, "Place Selected: " + place.getName());
               // mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(), place.getId(), place.getAddress(), place.getPhoneNumber(), place.getWebsiteUri()));
       /*         CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    mPlaceAttriibution.setText(Html.fromHtml(attributions.toString()));


                } else {
                    mPlaceAttriibution.setText("");
                }
                */
            }
            else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                //Log.e(TAG, "Error:Status=" + status.toString());

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
/*
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id, CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
    //    Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber, websiteUri));
      //  return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber, websiteUri));

    }
*/
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
       /*Reference
        https://dzone.com/articles/context-menu-android-tutorial
         */

        switch (item.getItemId()) {
            case R.id.GPS:
                TrackandMark();
                return true;
            case R.id.Autocomplete:
                OverlayAutoComplete();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    /*Reference
http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
 */
    public void OverlayAutoComplete()
    {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);


        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(), 0).show();

        } catch (GooglePlayServicesNotAvailableException e) {
            String message = "Google Play Services is not available:" + GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
         //   Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void TrackandMark(){
        gps=new GPSTracker(GetCenter.this);
        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            String type="ChangeLocation";
            new GetCenterofUsers().execute(type,Double.toString(latitude),Double.toString(longitude));


            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    public void onHelp(View v) {
        openContextMenu(v);
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
        mMap.getUiSettings().setMapToolbarEnabled(false);
        //Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
     String type="Plotting";
        new GetCenterofUsers().execute(type);

    }



    private class GetCenterofUsers extends AsyncTask<String,Void,String>
    {
        String radius="1000";
        String locationlat;
        String locationlong;

        String result="";
HttpHandler sh=new HttpHandler();

      String coordinates_url="http://192.168.0.3/Planmap/getcoordinates.php";
String suggestion_url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        String changelocation="http://192.168.0.3/Planmap/changecoordinates.php";
        String event_id=Integer.toString(event.getEvent_id());

        @Override
        protected String doInBackground(String... params) {
            String type=params[0];

if(type.equals("Plotting")) {
    if (!(LoginActivity.isReachable("192.168.0.3", 80, 500))) {
        return "Not Connected or Server Down or No Signal";
    }

    try {
        URL url = new URL(coordinates_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        String post_data = URLEncoder.encode("event_id", "UTF-8") + "=" + URLEncoder.encode(event_id, "UTF-8");
        sh.WritetoOutputStream(outputStream, post_data);
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        result = sh.convertStreamtoString(inputStream);
        inputStream.close();
        httpURLConnection.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
    }
    if (result != null) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                int user_id = 0;
                double userlat = 0;
                double userLong = 0;

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                user_id = jsonObject.getInt("user_id_fk");
                userlat = jsonObject.getDouble("UserLat");
                userLong = jsonObject.getDouble("UserLong");
                String useremail=jsonObject.getString("email_id");
                String username=jsonObject.getString("username");
                UserOne user = new UserOne(user_id);
                user.setGpsLatLong(userlat, userLong);
                user.setUsername(username);
                user.setEmail_id(useremail);
                user_coordinates.add(user);
            }
        } catch (final JSONException e) {
            //                Log.e(TAG,"Json Parsing error: "+e);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Json parsing error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    } else {
        //          Log.e(TAG,"Couldnt get Json from Server");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Couldnt get json from server.Check Logcat for possible errors", Toast.LENGTH_LONG).show();
            }
        });
    }

    return "cool";
}
if(type.equals("Suggestion"))
{
    locationlat=params[1];
    locationlong=params[2];
    String jsonStr=sh.makeServiceCall(suggestion_url+locationlat+","+locationlong+"&"+"radius="+radius+"&"+"key="+API_KEY);

    if(jsonStr!=null)
    {
        try{
            JSONObject jsonObj=new JSONObject(jsonStr);
            //Getting JSON Array node
            JSONArray results=jsonObj.getJSONArray("results");

            for(int i=0;i<results.length();i++)
            {
                JSONObject c=results.getJSONObject(i);
                String name=c.getString("name");
                String place_id=c.getString("place_id");
                JSONObject geometry=c.getJSONObject("geometry");
                JSONObject location=geometry.getJSONObject("location");
                double lat=location.getDouble("lat");
                double longi=location.getDouble("lng");
                PlacesOne place=new PlacesOne(name,lat,longi);
                places.add(place);
            }
        }catch(final JSONException e)
        {
           // Log.e(TAG,"Json Parsing error: "+e);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Json parsing error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }else{
        //Log.e(TAG,"Couldnt get Json from Server");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"Couldnt get json from server.Check Logcat for possible errors",Toast.LENGTH_LONG).show();
            }
        });

    }


    return "Suggested";

}

if(type.equals("ChangeLocation"))
{
    String latitude=params[1];
    String longitude=params[2];

    if (!(LoginActivity.isReachable("192.168.0.3", 80, 500))) {
        return "Not Connected or Server Down or No Signal";
    }
    try {
        URL url = new URL(changelocation);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        String post_data = URLEncoder.encode("event_id", "UTF-8") + "=" + URLEncoder.encode(event_id, "UTF-8")+"&"+
                URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8")+"&"+
                URLEncoder.encode("latitude","UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")+"&"+
                URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8");

        sh.WritetoOutputStream(outputStream, post_data);
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        result = sh.convertStreamToStringWithoutNewline(inputStream);
        inputStream.close();
        httpURLConnection.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;

}

return "nothing";

        }



        @Override
protected void onPostExecute(String result)
{
    if(result.equals("cool"))
    {
        PlotUserPoints();
    }
    if(result.equals("Suggested"))
    {
     PlotSuggestionPoints();
    }
    if(result.equals("Changed"))
    {
        mMap.clear();
        user_coordinates.clear();
        places.clear();
        markers.clear();
        String type="Plotting";
        new GetCenterofUsers().execute(type);
    }

}
    }

    public void PlotUserPoints()
    {
        if(!user_coordinates.isEmpty() && user_coordinates!=null )
        {
            for(int i=0;i<user_coordinates.size();i++)
            {
                user_coordinates.get(i).getGpsLat();
                LatLng latLng = new LatLng(user_coordinates.get(i).getGpsLat(),user_coordinates.get(i).getGpsLong());

                Marker marker=mMap.addMarker(new MarkerOptions().position(latLng).title(user_coordinates.get(i).getUsername()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
               markers.add(marker);

                // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            }

        }

        if(markers.size()==1)
        {

            CameraPosition cameraPosition=new CameraPosition.Builder().target(markers.get(0).getPosition()).zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            String type="Suggestion";
            new GetCenterofUsers().execute(type,Double.toString(markers.get(0).getPosition().latitude),Double.toString(markers.get(0).getPosition().longitude));

        }
        else {
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

            mMap.addMarker(new MarkerOptions().position(latLng).title("Center").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            String type="Suggestion";

            new GetCenterofUsers().execute(type,Double.toString(latLng.latitude),Double.toString(latLng.longitude));

        }
    }

    public void PlotSuggestionPoints()
    {
        int i;
        for(i=0;i<places.size();i++) {

            LatLng latLng = new LatLng(places.get(i).getLatitude(), places.get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(places.get(i).getPlaceName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        }

    }
}
