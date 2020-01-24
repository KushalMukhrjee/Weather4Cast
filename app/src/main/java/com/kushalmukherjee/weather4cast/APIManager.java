package com.kushalmukherjee.weather4cast;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class APIManager extends Application {


    private static String API_KEY = "aeeed94a96013f85857502616707d896";
    private static String baseUrlWeather = "https://api.openweathermap.org/data/2.5/weather";
    private static String baseUrlForecast = "https://api.openweathermap.org/data/2.5/forecast";

    private static APIManager instance = null;

    private APIManager() {

    }
    public static APIManager getInstance() {

        if (instance == null) {
            instance = new APIManager();
        }

        return  instance;
    }



    public void makeWeatherRequest(Context context, HashMap<String,String> params, RequestCompletionHandler handler ) {

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

    public void makeForecastRequest(Context context, HashMap<String,String> params, RequestCompletionHandler handler) {

        RequestQueue queue = Volley.newRequestQueue(context);



        String urlString = baseUrlForecast + "?lat="+params.get("latitude")+"&lon="+params.get("longitude")+"&appid="+API_KEY;

        System.out.println(urlString);

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


interface RequestCompletionHandler {
    void completion(String responseString);
}