package com.example.weather_app.WeatherScreens;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weather_app.ForecastData.List;
import com.example.weather_app.ForecastData.Main;
import com.example.weather_app.ForecastData.Weather;
import com.example.weather_app.R;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ForecastDetailAdapter extends RecyclerView.Adapter<ForecastDetailAdapter.ViewHolder>
{
    private static String description;
    java.util.List<Weather> weatherList = new ArrayList<>();
    private java.util.List<List> mValues;

    public ForecastDetailAdapter(java.util.List<List> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        com.example.weather_app.ForecastData.List list = mValues.get(position);

        String[] date_time = list.getDtTxt().split(" ");
        String date = date_time[0];
        String time = date_time[1];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1= null;
        try {
            dt1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EEEE");
        String finalDay = format2.format(dt1);

        Main main = list.getMain();
        weatherList = list.getWeather();
        description = weatherList.get(0).getDescription();

        holder.time.setText(time);
        holder.date.setText(finalDay);
        holder.temp.setText(main.getTemp()+" \u2103");
        holder.weather.setText(description);
        holder.humidity.setText(main.getHumidity()+" %");
        holder.pressure.setText(main.getPressure()+" md");
        holder.minTemp.setText(main.getTempMin()+" \u2103");
        holder.maxTemp.setText(main.getTempMax()+" \u2103");

        if(description.equals("clear sky") )
            holder.imageView.setImageResource(R.drawable.sunny);
        else if(description.equals("scattered clouds"))
            holder.imageView.setImageResource(R.drawable.scattered_clouds);
        else if(description.equals("few clouds"))
            holder.imageView.setImageResource(R.drawable.scattered_clouds);
        else if(description.equals("broken clouds"))
            holder.imageView.setImageResource(R.drawable.scattered_clouds);
        else if(description.equals("light rain"))
            holder.imageView.setImageResource(R.drawable.storm);

    }

    public void setData(java.util.List<List> list){
        this.mValues = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView date;
        public final TextView time;
        public final TextView temp;
        public final TextView weather;
        public final TextView humidity;
        public final TextView pressure;
        public final TextView minTemp;
        public final TextView maxTemp;
        public final ImageView imageView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            date =  view.findViewById(R.id.dateDay);
            time =  view.findViewById(R.id.time_view);
            temp = view.findViewById(R.id.temperature);
            weather = view.findViewById(R.id.desc);
            humidity = view.findViewById(R.id.vHumidity);
            pressure = view.findViewById(R.id.vPressure);
            minTemp = view.findViewById(R.id.vMin);
            maxTemp = view.findViewById(R.id.vMax);
            imageView = view.findViewById(R.id.weatherNow);
        }
    }
}
