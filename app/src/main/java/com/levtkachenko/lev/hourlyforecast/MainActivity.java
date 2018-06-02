package com.levtkachenko.lev.hourlyforecast;

/**
 * Created by LEV TKACHENKO on 22/02/2018.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
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
            "Afula Israel",
            "Akko Israel",
            "Arad Israel",
            "Ashdod Israel",
            "Ashqelon Israel",
            "Bat Yam Israel",
            "Beersheba Israel",
            "Beith Shean Israel",
            "Bney-Brit Israel",
            "Caesarea Israel",
            "Dimona Israel",
            "Dor Israel",
            "Elat Israel",
            "En Gedi Israel",
            "Hadera Israel",
            "Haifa Israel",
            "Herzliyya Israel",
            "Holon Israel",
            "Jerusalem Israel",
            "Karmiel Israel",
            "Kefar Sava Israel",
            "Lod Israel Israel",
            "Meron Israel Israel",
            "Nahariyya Israel",
            "Nazareth Israel",
            "Netanya Israel",
            "Petah Tiqwa Israel",
            "Qastina Israel",
            "Qiryat Shemona Israel",
            "Ramat Gan Israel",
            "Ramla Israel",
            "Rehovot Israel",
            "Rishon Leziyyon Israel",
            "Sedom Israel Israel",
            "Tel Aviv–Yafo Israel",
            "Tiberias Israel",
            "Zefat Israel"
    };


    AutoCompleteTextView cityName;
    public static final String EXTRA_MESSAGE = "com.levtkachenko.lev.weatherforecast.MESSAGE";
    RequestQueue volleyQueue;




    private void popUpAlertDialogConnectionError() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Error");
        builder1.setMessage("There might be problems with the server or network connection.");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "TRY AGAIN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(isNetworkConnected() == false) {
                            popUpAlertDialogConnectionError();
                        }
                        else  {
                            fetchWeatherData("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=(api key here)&q="  + cityName.getText().toString() + "&format=json&num_of_days=5");
                        }
                    }
                });

        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }



    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

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


        if(isNetworkConnected() == false) {
            popUpAlertDialogConnectionError();
        }
        else {
            fetchWeatherData("http://api.worldweatheronline.com/premium/v1/weather.ashx?key=38964d3f06374511bb6132939180106&q="  + cityName.getText().toString() + "&format=json&num_of_days=5");
        }
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
                makeToast("Some error occurred, please try again.");
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

        cityName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cityName.showDropDown();
                return false;
            }
        });

        volleyQueue = Volley.newRequestQueue(this);
    }
}
