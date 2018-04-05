package com.levtkachenko.lev.hourlyforecast;

/**
 * Created by LEV TKACHENKO on 22/02/2018.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherModel {

    private String location;
    private String date;
    private int currentTempC;
    private String currentWeatherDesc;
    private String currentWeatherIconUrl;
    private int maxtempC;
    private int mintempC;

    public static final int size = 8;

    private String[] time;
    private int[] tempC;
    private String[] weatherDesc;
    private String[] weatherIconUrl;

    public WeatherModel() {

        location = "unknown";
        date = "unknown";
        currentTempC = Integer.MIN_VALUE;
        currentWeatherDesc = "unknown";
        currentWeatherIconUrl = "unknown";
        maxtempC = Integer.MIN_VALUE;
        mintempC = Integer.MIN_VALUE;

        time = new String[size];
        tempC = new int[size];
        weatherDesc = new String[size];
        weatherIconUrl = new  String[size];

        for(int i = 0; i < size; i++) {
            time[i] = "unknown";
        }
        for(int i = 0; i < size; i++) {
            tempC[i] = Integer.MIN_VALUE;
        }
        for(int i = 0; i < size; i++) {
            weatherDesc[i] = "unknown";
        }
        for(int i = 0; i < size; i++) {
            weatherIconUrl[i] = "unknown";
        }
    }

    public void parseJason(JSONObject object) throws JSONException {

        JSONObject hourlyObj, jsonObject;
        JSONArray weatherDesc_arr, weatherIconUrl_arr;

        JSONObject data = object.getJSONObject("data");

        JSONArray request = data.getJSONArray("request");
        if(request.length() > 0) {
            JSONObject request0 = request.getJSONObject(0);
            location = request0.getString("query");
        }
        JSONArray weather = data.getJSONArray("weather");
        if(weather.length() > 0) {
            JSONObject weather0 = weather.getJSONObject(0);
            date = weather0.getString("date");
            maxtempC = weather0.getInt("maxtempC");
            mintempC = weather0.getInt("mintempC");

            JSONArray hourly = weather0.getJSONArray("hourly");

            if(hourly.length() > 0) {

                for(int i = 0; i < size; i++) {
                    hourlyObj = hourly.getJSONObject(i);

                    time[i] = hourlyObj.getString("time");
                    tempC[i] = hourlyObj.getInt("tempC");

                    weatherDesc_arr = hourlyObj.getJSONArray("weatherDesc");
                    if(weatherDesc_arr.length() > 0) {
                        jsonObject = weatherDesc_arr.getJSONObject(0);
                        weatherDesc[i] = jsonObject.getString("value");
                    }
                    weatherIconUrl_arr = hourlyObj.getJSONArray("weatherIconUrl");
                    if(weatherIconUrl_arr.length() > 0) {
                        jsonObject = weatherIconUrl_arr.getJSONObject(0);
                        weatherIconUrl[i] = jsonObject.getString("value");
                    }
                }
            }
        }
        JSONArray current_condition = data.getJSONArray("current_condition");
        if(current_condition.length() > 0) {
            JSONObject condition = current_condition.getJSONObject(0);
            currentTempC = condition.getInt("temp_C");
            JSONArray weatherDesc = condition.getJSONArray("weatherDesc");
            if(weatherDesc.length() > 0) {
                JSONObject weatherDesc0 = weatherDesc.getJSONObject(0);
                currentWeatherDesc = weatherDesc0.getString("value");
            }
            JSONArray icon = condition.getJSONArray("weatherIconUrl");
            if(icon.length() > 0) {
                JSONObject iconStr = icon.getJSONObject(0);
                currentWeatherIconUrl = iconStr.getString("value");

            }
        }
    }

    public String getDate() {
        return date;
    }

    public int getCurrentTempC() {
        return currentTempC;
    }

    public int getMaxtempC() {
        return maxtempC;
    }

    public int getMintempC() {
        return mintempC;
    }

    public String getLocation() {
        return location;
    }

    public String getCurrentWeatherDesc() {
        return currentWeatherDesc;
    }

    public String getCurrentWeatherIconUrl() {
        return currentWeatherIconUrl;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public int[] getTempC() {
        return tempC;
    }

    public String[] getWeatherDesc() {
        return weatherDesc;
    }

    public String[] getWeatherIconUrl() {
        return weatherIconUrl;
    }
}
