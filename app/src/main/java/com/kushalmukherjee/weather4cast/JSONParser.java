package com.kushalmukherjee.weather4cast;

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




}
