package com.kushalmukherjee.weather4cast.Model;

import java.util.ArrayList;

public class Weather {


    public class Coordinates {


        private Double longitude;
        private Double latitude;

        public Coordinates() {

        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
    }

    public class WeatherMain {


        private Double temp;
        private Double feelsLike;
        private Double tempMin;
        private Double tempMax;
        private Double humidity;

        public WeatherMain() {

        }

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(Double feelsLike) {
            this.feelsLike = feelsLike;
        }

        public Double getTempMin() {
            return tempMin;
        }

        public void setTempMin(Double tempMin) {
            this.tempMin = tempMin;
        }

        public Double getTempMax() {
            return tempMax;
        }

        public void setTempMax(Double tempMax) {
            this.tempMax = tempMax;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }
    }

    public class WeatherDescription {

        private String main;
        private String description;
        private String icon;

        public WeatherDescription() {

        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }


    private Coordinates coordinates;
    private WeatherMain weatherMain;
    private WeatherDescription weatherDescription;
    private String Date;

    public Weather() {

    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public WeatherMain getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(WeatherMain weatherMain) {
        this.weatherMain = weatherMain;
    }

    public WeatherDescription getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(WeatherDescription weatherDescriptions) {
        this.weatherDescription = weatherDescriptions;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
