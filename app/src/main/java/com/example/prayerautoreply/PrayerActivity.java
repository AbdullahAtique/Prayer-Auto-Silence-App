package com.example.prayerautoreply;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PrayerActivity extends AppCompatActivity {


    private FusedLocationProviderClient client;
    Context context;
    LocationManager locationManager;
    boolean GpsStatus;
    Location loc;
    double longitude;
    double latitude;

    private RequestQueue mqueue;
    String hiturl;

    TextView fajr_time;
    TextView zuhur_time;
    TextView asr_time;
    TextView maghrib_time;
    TextView isha_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainui);
        context = getApplicationContext();

        if(CheckGpsStatus()) {

                getLocation();
            Bundle b = getIntent().getExtras();
            longitude = b.getDouble("Longitude");
            latitude= b.getDouble("Latitude");

            hiturl= "http://api.aladhan.com/v1/timings/07-12-2019?latitude="+latitude+"&longitude="+longitude+"&method=1";

            mqueue = Volley.newRequestQueue(this);
            getPrayerData(hiturl);
//           final AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//


        }
        else
        {
            enableLocation();
            getLocation();
            longitude= loc.getLongitude();
            latitude= loc.getLatitude();
            hiturl= "http://api.aladhan.com/v1/timings/07-12-2019?latitude="+latitude+"&longitude="+longitude+"&method=1";

            mqueue = Volley.newRequestQueue(this);
            getPrayerData(hiturl);

        }




    }

    public void getPrayerData( String url)
    {

        Toast.makeText(getApplicationContext(),"Initiating Connection",Toast.LENGTH_LONG).show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),"Connection Successful",Toast.LENGTH_LONG).show();

                        try {
                            JSONObject rq= response.getJSONObject("data");
                            JSONObject prayertimings =rq.getJSONObject("timings");
                            String fajr= prayertimings.getString("Fajr");
                            String Zuhur= prayertimings.getString("Dhuhr");
                            String Asr= prayertimings.getString("Asr");
                            String maghrib= prayertimings.getString("Maghrib");
                            String isha= prayertimings.getString("Isha");



                            Toast.makeText(getApplicationContext(),"Setting PrayerTimings",Toast.LENGTH_LONG).show();
                            fajr_time=findViewById(R.id.fajr_start_time);
                            fajr_time.setText(fajr);

                            zuhur_time= findViewById(R.id.zuhr_start_time);
                            zuhur_time.setText(Zuhur);

                            asr_time= findViewById(R.id.asr_start_time);
                            asr_time.setText(Asr);

                            maghrib_time= findViewById(R.id.maghrib_start_time);
                            maghrib_time.setText(maghrib);

                            isha_time= findViewById(R.id.isha_start_time);
                            isha_time.setText(isha);





                        } catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"NetworkConnection Error",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mqueue.add(request);
    }
    public boolean CheckGpsStatus(){
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus == true) {
            return true;
        } else {

            return false;
        }
    }
    public void enableLocation()
    {

        if(CheckGpsStatus()==false)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(PrayerActivity.this);
            builder.setTitle("Enable Location");
            builder.setMessage("Your Location seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }

    }

    public  void getLocation()
    {

        client = LocationServices.getFusedLocationProviderClient(this);

        client.getLastLocation().addOnSuccessListener(PrayerActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!=null)
                {
//                    longitude= location.getLongitude();
//                    latitude= location.getLatitude();
                    loc= location;
                }
            }
        });

    }
}
