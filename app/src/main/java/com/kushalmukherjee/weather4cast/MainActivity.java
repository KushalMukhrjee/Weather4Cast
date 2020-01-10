package com.kushalmukherjee.weather4cast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.kushalmukherjee.weather4cast.Model.Weather;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {


    TextView cityTextView;
    LocationManager locationManager;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0) {
                getCurrentLocation();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        ImageView backgroundImage = findViewById(R.id.backgroundImage);
        Blurry.with(this).from(BitmapFactory.decodeResource(getResources(),R.drawable.weather4castimage)).into(backgroundImage);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        cityTextView = (TextView) findViewById(R.id.cityTextView);

        getCurrentLocation();







    }




    protected void getCurrentLocation() {




        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                Log.i("Location received:",location.toString());
                updateCityName(new LatLng(location.getLatitude(),location.getLongitude()));
                getWeatherDataOfLocation(location);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private void getWeatherDataOfLocation(Location location) {
        System.out.println("-----MAking api call----");

        HashMap params = new HashMap<String ,String>();
        params.put("latitude",String.valueOf(location.getLatitude()));
        params.put("longitude",String.valueOf(location.getLongitude()));


        APIManager.getInstance().makeRequest(MainActivity.this, params, new CompletionHandler() {
            @Override
            public void completion(String responseString) {
                try {
                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(responseString);

                    Weather weather = gson.fromJson(responseString,Weather.class);

                    Log.i("response JSON:", String.valueOf(weather.getCoordinates() == null));



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    protected void updateCityName(LatLng latLng) {



        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            cityTextView.setText(addressList.get(0).getLocality());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
