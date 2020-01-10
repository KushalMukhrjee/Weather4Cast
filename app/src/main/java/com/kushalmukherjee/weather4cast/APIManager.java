package com.kushalmukherjee.weather4cast;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class APIManager extends Application {


    private static String API_KEY = "aeeed94a96013f85857502616707d896";
    private static String baseUrlWeather = "https://api.openweathermap.org/data/2.5/weather";

    private static APIManager instance = null;

    private APIManager() {

    }
    public static APIManager getInstance() {

        if (instance == null) {
            instance = new APIManager();
        }

        return  instance;
    }



    public void makeRequest(Context context, HashMap<String,String> params, CompletionHandler handler ) {

        RequestQueue queue = Volley.newRequestQueue(context);

        String urlString = baseUrlWeather + "?lat="+params.get("latitude")+"&lon="+params.get("longitude")+"&appid="+API_KEY;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handler.completion(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

}


interface CompletionHandler {
    void completion(String responseString);
}