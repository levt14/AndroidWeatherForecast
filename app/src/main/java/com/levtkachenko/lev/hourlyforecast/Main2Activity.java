package com.levtkachenko.lev.hourlyforecast;

/**
 * Created by LEV TKACHENKO on 22/02/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {


    TextView forecastLocation;
    TextView date;
    TextView currentTempC;
    TextView currentWeatherDesc;
    ImageView currentWeatherIcon;
    TextView maxtempC;
    TextView mintempC;

    RequestQueue volleyQueue;
    WeatherModel weatherModel;

    String[] timeArr;
    int[] tempCArr;
    String[] weatherDescArr;
    String[] weatherIconUrl;

    RecyclerView recyclerView;
    ArrayList<DataModel> forecastHoursList = new ArrayList<DataModel>() ;
    MyRecyclerViewAdapter adapter;
    Context context;

    public  void setDataSource() {
        for (int i = 0; i < WeatherModel.size; i++) {
            DataModel dataModel = new DataModel(timeArr[i], tempCArr[i], weatherDescArr[i], weatherIconUrl[i]);
            forecastHoursList.add(dataModel);
        }
    }

    public String changeDateFormat(String string) {
        String stringArr[] = string.split("-");
        return (stringArr[2] + "/" + stringArr[1] + "/" + stringArr[0]);
    }

    public void changeTimeFormat() {
        timeArr[0] = timeArr[0] + ":" + "00";
        for(int i = 1; i < WeatherModel.size; i++) {
            timeArr[i] = timeArr[i].replace("0", ":0");
        }
        for(int i = 1; i < WeatherModel.size; i++) {
            timeArr[i] = timeArr[i].substring(0, Math.min(timeArr[i].length(), 3));
        }
        for (int i = 1; i < WeatherModel.size; i++) {
            if(i < WeatherModel.size - 4) {
                timeArr[i] =  timeArr[i] + "0";
            }
            else {
                timeArr[i] =  timeArr[i] + "00";
            }
        }
    }
    public void fetchWeatherData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Log.i("Json", response.toString());

                    weatherModel = new WeatherModel();
                    weatherModel.parseJason(response);

                    forecastLocation.setText(weatherModel.getLocation());
                    date.setText(changeDateFormat(weatherModel.getDate()));

                    maxtempC.setText("Max: " + Integer.toString(weatherModel.getMaxtempC()) + "°c");
                    mintempC.setText("Min: " + Integer.toString(weatherModel.getMintempC())+ "°c");

                    currentTempC.setText(Integer.toString(weatherModel.getCurrentTempC())+ "°c");
                    currentWeatherDesc.setText(weatherModel.getCurrentWeatherDesc());
                    iconLoader(weatherModel.getCurrentWeatherIconUrl());

                    timeArr = weatherModel.getTime();

                    changeTimeFormat();

                    tempCArr = weatherModel.getTempC();
                    weatherDescArr = weatherModel.getWeatherDesc();
                    weatherIconUrl = weatherModel.getWeatherIconUrl();

                    setDataSource();

                    adapter = new MyRecyclerViewAdapter(forecastHoursList, context);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        volleyQueue.add(jsonObjectRequest);
    }

    private void iconLoader(String url) {

        Volley.newRequestQueue(this).add(new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                currentWeatherIcon.setImageBitmap(bitmap);
            }
        }, 1024, 1024, null, null));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        context = this;

        recyclerView =  findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        forecastLocation = findViewById(R.id._forecast_location);
        date = findViewById(R.id._date);
        currentTempC = findViewById(R.id._current_tempC);
        currentWeatherDesc = findViewById(R.id._current_weatherDesc);
        currentWeatherIcon = findViewById(R.id._current_weatherIcon);
        maxtempC = findViewById(R.id._maxtempC);
        mintempC = findViewById(R.id._mintempC);

        Intent intent = getIntent();
        String cityName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        volleyQueue = Volley.newRequestQueue(this);

        fetchWeatherData("https://authentication-debd1.firebaseapp.com/weather.json");
    }
}
