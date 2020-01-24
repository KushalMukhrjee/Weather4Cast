package com.kushalmukherjee.weather4cast.Model;

import android.content.Intent;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Forecast {

    private ArrayList<Weather> forecastWeatherList;


    public Forecast() {

    }

    public ArrayList<Weather> getForecastWeatherList() {
        return forecastWeatherList;
    }

    public void setForecastWeatherList(ArrayList<Weather> forecastWeatherList) {
        this.forecastWeatherList = forecastWeatherList;
    }


    public ArrayList<String> getFollowingFiveDays() {


        ArrayList<String> followingFiveDays = new ArrayList<String>();


        Date todayDate = new Date();
        ArrayList<String> days = new ArrayList<String>();

        for(int i = 0; i < forecastWeatherList.size(); i++) {

            Weather weatherObj = forecastWeatherList.get(i);


            SimpleDateFormat dtFormatter = new SimpleDateFormat("yyyy-MM-dd");



            if (dtFormatter.format(todayDate).compareTo(weatherObj.getDate().split(" ")[0]) < 0) {

                try {

                    Date date = dtFormatter.parse(weatherObj.getDate().split(" ")[0]);

                    days.add(new SimpleDateFormat("E").format(date));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }


        for (String day: days) {

            if (!followingFiveDays.contains(day)) {
                followingFiveDays.add(day);
            }


        }

       return followingFiveDays;
    }



    public ArrayList<String> getMaxMinTempForFollowingFiveDays() {

        ArrayList<String> maxMinTempForFollowingFiveDays = new ArrayList<String>();


        for (String day: getFollowingFiveDays()) {

            Double maxTemp = 0.0;
            Double minTemp = 373.0;

            for (Weather forecast: forecastWeatherList) {

                try {
                    String forecastDateString = forecast.getDate();
                    Date forecastDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(forecastDateString);

                    String forecastDay = (new SimpleDateFormat("E")).format(forecastDate);

                    if (forecastDay.equals(day)) {

                        if (forecast.getWeatherMain().getTempMax() > maxTemp) {
                            maxTemp = forecast.getWeatherMain().getTempMax();
                        }

                        if (forecast.getWeatherMain().getTempMin() < minTemp) {
                            minTemp = forecast.getWeatherMain().getTempMin();
                        }


                    } else {
                        continue;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            DecimalFormat df = new DecimalFormat("#");

            maxMinTempForFollowingFiveDays.add(String.valueOf(Double.valueOf(df.format(maxTemp - 273)))+"/"+String.valueOf(Double.valueOf(df.format(minTemp - 273))));

        }



        return maxMinTempForFollowingFiveDays;
    }

    public ArrayList<String> getAvgWeatherConditionImageStringsForFiveDays() {


        ArrayList<String> weatherImages = new ArrayList<String>();




        for (String currentDay: getFollowingFiveDays()) {

            HashMap<String, Integer> weatherCondDict = new HashMap<String, Integer>();

            for (Weather forecast: forecastWeatherList) {

                try {
                    String forecastDayString = forecast.getDate();
                    Date forecastDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(forecastDayString));
                    String forecastDay = (new SimpleDateFormat("E").format(forecastDate));

                    if (forecastDay.equals(currentDay)) {

                        String condition = forecast.getWeatherDescription().getIcon().substring(0,forecast.getWeatherDescription().getIcon().length()-1);

                        Integer count = 0;

                        if (weatherCondDict.containsKey(condition)) {

                            count = weatherCondDict.get(condition);

                        }

                        count += 1;

                        weatherCondDict.put(condition,count);

                    } else {
                         continue;
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            System.out.println("For day:"+currentDay+":"+weatherCondDict);

            String maxKey = "";
            Integer maxValue = 0;
            for (Map.Entry<String,Integer> entry: weatherCondDict.entrySet()) {

                if (entry.getValue() > maxValue) {
                    maxKey = entry.getKey();
                    maxValue = entry.getValue();
                }

            }

            weatherImages.add(maxKey);
        }

        System.out.println(weatherImages);

        return weatherImages;

    }





}
