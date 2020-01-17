package com.kushalmukherjee.weather4cast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.kushalmukherjee.weather4cast.Model.Weather;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {


    TextView cityTextView;
    TextView currentTemperatureTextView;
    TextView maxTemperatureTextView;
    TextView minTemperatureTextView;
    ImageView weatherImageView;
    ImageButton changeCityButton;


    LocationManager locationManager;

    Weather currentWeather;
    String currentCityName;


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
        currentTemperatureTextView = (TextView) findViewById(R.id.tempTextView);
        maxTemperatureTextView = (TextView) findViewById(R.id.maxTempTextView);
        minTemperatureTextView = (TextView) findViewById(R.id.minTempTextView);
        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);
        changeCityButton = (ImageButton) findViewById(R.id.changeCityImageButton);

        changeCityButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SelectCityActivity.class);
                startActivityForResult(intent,1);



            }
        });

        resetUIState();
        getCurrentLocation();

    }




    protected void getCurrentLocation() {

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                Log.i("Location received:",location.toString());
                currentCityName = getCityName(new LatLng(location.getLatitude(),location.getLongitude()));

                getWeatherDataOfLocation(location, new GetWeatherCompletionHandler() {
                    @Override
                    public void completion(Weather weather) {
                        currentWeather = weather;

                        refreshUIState();
                    }
                });

                locationManager.removeUpdates(this);


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

    private void getWeatherDataOfLocation(Location location, GetWeatherCompletionHandler completionHandler) {
        System.out.println("-----MAking api call----");

        HashMap params = new HashMap<String ,String>();
        params.put("latitude",String.valueOf(location.getLatitude()));
        params.put("longitude",String.valueOf(location.getLongitude()));

        APIManager.getInstance().makeRequest(MainActivity.this, params, new RequestCompletionHandler() {

            @Override
            public void completion(String responseString) {
                try {

                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONParser parser = new JSONParser();
                    completionHandler.completion(parser.parseWeatherJson(jsonObject));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    protected String getCityName(LatLng latLng) {

        String cityName = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            cityName = addressList.get(0).getLocality();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cityName;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if(resultCode == Activity.RESULT_OK) {
                String location = data.getStringExtra("city");

                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> list = geocoder.getFromLocationName(location, 1);
                    Log.i("RecdLAtlng", String.valueOf(list.get(0).getLatitude()));

                    Location receivedLocation = new Location("select_city_activity");
                    receivedLocation.setLatitude(list.get(0).getLatitude());
                    receivedLocation.setLongitude(list.get(0).getLongitude());

                    getWeatherDataOfLocation(receivedLocation, new GetWeatherCompletionHandler() {
                        @Override
                        public void completion(Weather weather) {
                            currentWeather = weather;
                            currentCityName = list.get(0).getLocality();

                            refreshUIState();
                        }
                    });




                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }



    }

    public void refreshUIState() {

        cityTextView.setText(currentCityName);
        DecimalFormat df = new DecimalFormat("#.#");


        Double currentTempInCenti = Double.valueOf(df.format(currentWeather.getWeatherMain().getTemp() - 273));
        Double maxTempInCenti = Double.valueOf(df.format(currentWeather.getWeatherMain().getTempMax() - 273));
        Double minTempInCenti =  Double.valueOf(df.format(currentWeather.getWeatherMain().getTempMin() - 273));
        currentTemperatureTextView.setText(currentTempInCenti.toString()+ " °c");
        maxTemperatureTextView.setText(maxTempInCenti.toString()+ " °c");
        minTemperatureTextView.setText(minTempInCenti.toString()+ " °c");
        changeCityButton.setVisibility(View.VISIBLE);
        try {
            weatherImageView.setImageBitmap(new ImageDownloader().execute("https://openweathermap.org/img/wn/" + (currentWeather.getWeatherDescription().getIcon()) + "@2x.png").get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetUIState() {
        cityTextView.setText("");
        currentTemperatureTextView.setText("");
        maxTemperatureTextView.setText("");
        minTemperatureTextView.setText("");
        weatherImageView.setImageBitmap(null);
        changeCityButton.setVisibility(View.INVISIBLE);
    }
}



interface GetWeatherCompletionHandler {
    void completion(Weather weather);
        }