package com.kushalmukherjee.weather4cast;

import android.util.Log;

import com.kushalmukherjee.weather4cast.Model.Forecast;
import com.kushalmukherjee.weather4cast.Model.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {


    public Weather parseWeatherJson(JSONObject weatherJson) {

        Weather weatherObj = new Weather();


        try {

            Weather.Coordinates coordinates = weatherObj.new Coordinates();

            coordinates.setLatitude(Double.parseDouble(weatherJson.getJSONObject("coord").get("lat").toString()));
            coordinates.setLongitude(Double.parseDouble(weatherJson.getJSONObject("coord").get("lon").toString()));


            Weather.WeatherMain weatherMain = weatherObj.new WeatherMain();

            weatherMain.setTemp(Double.parseDouble(weatherJson.getJSONObject("main").get("temp").toString()));
            weatherMain.setTempMax(Double.parseDouble(weatherJson.getJSONObject("main").get("temp_max").toString()));
            weatherMain.setTempMin(Double.parseDouble(weatherJson.getJSONObject("main").get("temp_min").toString()));
            weatherMain.setFeelsLike(Double.parseDouble(weatherJson.getJSONObject("main").get("feels_like").toString()));
            weatherMain.setHumidity(Double.parseDouble(weatherJson.getJSONObject("main").get("humidity").toString()));


            Weather.WeatherDescription weatherDescription = weatherObj.new WeatherDescription();

            JSONArray jsonArray = weatherJson.getJSONArray("weather");
            weatherDescription.setMain(jsonArray.getJSONObject(0).getString("main"));
            weatherDescription.setDescription(jsonArray.getJSONObject(0).getString("description"));
            weatherDescription.setIcon(jsonArray.getJSONObject(0).getString("icon"));


            weatherObj.setCoordinates(coordinates);
            weatherObj.setWeatherMain(weatherMain);
            weatherObj.setWeatherDescription(weatherDescription);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return weatherObj;


    }


    public Forecast parseForecastJson(JSONObject forecastJson) {

        Forecast forecastObj = new Forecast();

        ArrayList<Weather> forecasts = new ArrayList<Weather>();


        try {
            JSONArray jsonWeatherLists = forecastJson.getJSONArray("list");

           for(int i = 0; i < jsonWeatherLists.length();i++) {


               JSONObject weatherJson = jsonWeatherLists.getJSONObject(i);
               Weather weatherObj = new Weather();
               Weather.WeatherMain weatherMain = weatherObj.new WeatherMain();
               Weather.WeatherDescription weatherDescription = weatherObj.new WeatherDescription();

               weatherMain.setTemp(Double.parseDouble(weatherJson.getJSONObject("main").get("temp").toString()));
               weatherMain.setTempMax(Double.parseDouble(weatherJson.getJSONObject("main").get("temp_max").toString()));
               weatherMain.setTempMin(Double.parseDouble(weatherJson.getJSONObject("main").get("temp_min").toString()));
               weatherMain.setFeelsLike(Double.parseDouble(weatherJson.getJSONObject("main").get("feels_like").toString()));
               weatherMain.setHumidity(Double.parseDouble(weatherJson.getJSONObject("main").get("humidity").toString()));

               JSONArray jsonArray = weatherJson.getJSONArray("weather");
               weatherDescription.setMain(jsonArray.getJSONObject(0).getString("main"));
               weatherDescription.setDescription(jsonArray.getJSONObject(0).getString("description"));
               weatherDescription.setIcon(jsonArray.getJSONObject(0).getString("icon"));

               weatherObj.setDate(weatherJson.getString("dt_txt"));

               weatherObj.setWeatherMain(weatherMain);
               weatherObj.setWeatherDescription(weatherDescription);

               forecasts.add(weatherObj);

           }


        } catch(Exception e) {
            e.printStackTrace();
        }


        forecastObj.setForecastWeatherList(forecasts);

        return forecastObj;
    }


}
