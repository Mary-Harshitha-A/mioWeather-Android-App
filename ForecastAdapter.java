package com.example.weather_app.WeatherScreens;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weather_app.ForecastData.List;
import com.example.weather_app.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private java.util.List<List> mValues;
    String description;
    ImageView imageView;

    public ForecastAdapter(java.util.List<List> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        com.example.weather_app.ForecastData.List listForecast =mValues.get(position);
        holder.humidityPercent.setText(listForecast.getMain().getHumidity()+" %");
        holder.minTemperature.setText(listForecast.getMain().getTempMin()+" \u2103");
        holder.maxTemperature.setText(listForecast.getMain().getTempMax()+" \u2103");
        description = listForecast.getWeather().get(0).getDescription();

        String[] date_time = listForecast.getDtTxt().split(" ");
        String date = date_time[0];
        String time = date_time[1];

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1= null;
        try {
            dt1 = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EEE");
        String finalDay = format2.format(dt1);

        holder.date.setText(finalDay);

        SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        try {
            date1 = parseFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.time.setText(displayFormat.format(date1));

        if(description.equals("clear sky") )
            holder.imageView.setImageResource(R.drawable.sunny);
        else if(description.equals("scattered clouds"))
            holder.imageView.setImageResource(R.drawable.scattered_clouds);
        else if(description.equals("few clouds"))
            holder.imageView.setImageResource(R.drawable.scattered_clouds);
        else if(description.equals("broken clouds"))
            holder.imageView.setImageResource(R.drawable.scattered_clouds);
        else if(description.equals("light rain"))
            holder.imageView.setImageResource(R.drawable.rainy);
    }

    public void setData(java.util.List<List> forecastList){
        this.mValues = forecastList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView humidityPercent;
        public final TextView minTemperature;
        public final TextView maxTemperature;
        public final ImageView imageView;
        public final TextView date;
        public final TextView time;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            humidityPercent = view.findViewById(R.id.rainPercentValue);
            date =view.findViewById(R.id.day);
            time =view.findViewById(R.id.dateDay);
            minTemperature = view.findViewById(R.id.minTemp);
            maxTemperature = view.findViewById(R.id.maxTemp);
            imageView = view.findViewById(R.id.clouds);
        }
    }
}
