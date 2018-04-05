package com.levtkachenko.lev.hourlyforecast;

/**
 * Created by LEV TKACHENKO on 22/02/2018.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {



    private static final String[] COUNTRIES = new String[] {
            "ʿAfula, Israel",
            "ʿAkko, Israel",
            "ʿArad, Israel",
            "Ashdod, Israel",
            "Ashqelon, Israel",
            "Bat Yam, Israel",
            "Beersheba, Israel",
            "Bet Sheʾan, Israel",
            "Bet Sheʿarim, Israel",
            "Bnei Brak, Israel",
            "Caesarea, Israel",
            "Dimona, Israel",
            "Dor, Israel",
            "Elat, Israel",
            "ʿEn Gedi, Israel",
            "Givʿatayim, Israel",
            "H̱adera, Israel",
            "Haifa, Israel",
            "Herzliyya, Israel",
            "H̱olon, Israel",
            "Jerusalem, Israel",
            "Karmiʾel, Israel",
            "Kefar Sava, Israel",
            "Lod, Israel",
            "Meron, Israel",
            "Nahariyya, Israel",
            "Nazareth, Israel",
            "Netanya, Israel",
            "Petaẖ Tiqwa, Israel",
            "Qiryat Shemona, Israel",
            "Ramat Gan, Israel",
            "Ramla, Israel",
            "Reẖovot, Israel",
            "Rishon LeẔiyyon, Israel",
            "Sedom, Israel",
            "Tel Aviv–Yafo, Israel",
            "Tiberias, Israel",
            "Ẕefat, Israel"
    };


    AutoCompleteTextView cityName;
    public static final String EXTRA_MESSAGE = "com.levtkachenko.lev.weatherforecast.MESSAGE";
    RequestQueue volleyQueue;

    public void makeToast(String message) {

        Toast toast= Toast.makeText(getApplicationContext(),
                message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public void openNextActivity() {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra(EXTRA_MESSAGE, cityName.getText().toString());
        startActivity(intent);
    }

    public void checkCity(View view) {

        if(cityName.getText().toString().length() == 0) {
            makeToast("The field can't be empty.");
            return;
        }

        fetchWeatherData("https://authentication-debd1.firebaseapp.com/weather.json");
    }

    public void fetchWeatherData(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response.toString().contains("error")) {
                    makeToast("Unable to find any matching.");
                }
                else {
                    openNextActivity();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                makeToast("There is no internet connection.");
            }
        });
        volleyQueue.add(jsonObjectRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        cityName =  findViewById(R.id.city_name);


        cityName.setAdapter(adapter);

        volleyQueue = Volley.newRequestQueue(this);
    }
}
