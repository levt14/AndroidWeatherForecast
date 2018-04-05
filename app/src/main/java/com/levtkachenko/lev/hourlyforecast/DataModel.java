package com.levtkachenko.lev.hourlyforecast;


/**
 * Created by LEV TKACHENKO on 22/02/2018.
 */

public class DataModel {

    private String time;
    private int tempC;
    private String weatherDesc;
    private String weatherIconUrl;

    public DataModel(String time, int tempC, String weatherDesc, String weatherIconUrl) {
        this.time = time;
        this.tempC = tempC;
        this.weatherDesc = weatherDesc;
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getTime() {
        return time;
    }

    public int getTempC() {
        return tempC;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }
}
