package com.levtkachenko.lev.hourlyforecast;

/**
 * Created by LEV TKACHENKO on 22/02/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<DataModel> forecastHoursList = new ArrayList<DataModel>() ;
    MyViewHolder holder;
    int position;

    public MyRecyclerViewAdapter(ArrayList<DataModel> list, Context context){
        this.forecastHoursList = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wheater_list, parent, false);
        MyViewHolder myViewHolder= new MyViewHolder(v);

        return myViewHolder;
    }


    private void iconLoader(final String url) {

        Volley.newRequestQueue(context).add(new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                    holder.timeT.setText(forecastHoursList.get(position).getTime());
                    holder.tempCT.setText(Integer.toString(forecastHoursList.get(position).getTempC())+ "°c");
                    holder.weatherDescT.setText(forecastHoursList.get(position).getWeatherDesc());
                    holder.weatherIcon.setImageBitmap(bitmap);
            }




        }, 1024, 1024, null, null

        ));

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        this.holder = holder;
        this.position = position;

            iconLoader(forecastHoursList.get(position).getWeatherIconUrl());

    }

    @Override
    public int getItemCount() {
        return forecastHoursList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView timeT, weatherDescT, tempCT;
        ImageView weatherIcon;

        @SuppressLint("ResourceAsColor")
        public MyViewHolder(View itemView) {
            super(itemView);

            timeT =(TextView)itemView.findViewById(R.id._time_);
            tempCT =(TextView)itemView.findViewById(R.id._tempC_);
            weatherDescT =(TextView)itemView.findViewById(R.id._weatherDesc_);
            weatherIcon = (ImageView) itemView.findViewById(R.id._weatherIcon_);



        }
    }
}
