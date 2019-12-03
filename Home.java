package com.example.weather_app.WeatherScreens;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.weather_app.Data.Clouds;
import com.example.weather_app.Data.Main;
import com.example.weather_app.Data.Sys;
import com.example.weather_app.Data.Weather;
import com.example.weather_app.Data.WeatherInfo;
import com.example.weather_app.Data.WeatherService;
import com.example.weather_app.Data.Wind;
import com.example.weather_app.MainActivity;
import com.example.weather_app.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends Fragment {
    private TextView temp;
    private TextView description;
    private TextView minMax;
    private TextView humidityValue;
    private TextView pressureValue;
    private TextView speedValue;
    private TextView sunriseValue;
    private TextView sunsetValue;
    private TextView cloudCover;
    private TextView date_view;
    private TextView time_view;
    private ImageView imageView;
    private Button detail;
    private static Integer epochTime;
    private static String epoch;
    private static Long epochSeconds;
    private static Integer epochRise;
    private static String epoch1;
    private static Long epochRiseSeconds;
    private static Integer epochSet;
    private static String epoch2;
    private static Long epochSetSeconds;
    private static String day;
    private static String dt;
    private static String rise;
    private static String set;
    private static String key;
    private static String units;
    private static String date3;
    private static String time;
    private static String tf;
    private static String riseTime;
    private static String rtf;
    private static String setTime;
    private static String stf;
    private static String[] dt1;
    private static String[] rise1;
    private static String[] set1;
    private onButtonClick buttonClick;
    static String latitude;
    static String longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initForcastFm(latitude, longitude);
        fetchWeatherInfo();
    }

    public Home(Double lat,Double lon){
        latitude = lat.toString();
        longitude = lon.toString();
    }

    private void fetchWeatherInfo() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);


        units = "metric";
        key = "d9a48945433789ebd7f363e7fe891470";
        Call<WeatherInfo> repos = service.getService(latitude, longitude, units, key);

        repos.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                WeatherInfo data = response.body();
                assert data != null;
                Main main = data.getMain();
                Wind wind = data.getWind();
                List<Weather> weatherList = data.getWeather();
                Sys sys = data.getSys();
                Clouds cloud = data.getClouds();

                String description1 = weatherList.get(0).getDescription();

                epochTime = data.getDt();
                epoch = epochTime.toString();
                epochSeconds = Long.parseLong(epoch);
                Date date = new Date(epochSeconds * 1000L);

                epochRise = sys.getSunrise();
                epoch1 = epochRise.toString();
                epochRiseSeconds = Long.parseLong(epoch1);
                Date date1 = new Date(epochRiseSeconds * 1000L);

                epochSet = sys.getSunset();
                epoch2 = epochSet.toString();
                epochSetSeconds = Long.parseLong(epoch2);
                Date date2 = new Date(epochSetSeconds * 1000L);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss a");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE");
                day = simpleDateFormat1.format(date);

                dt = simpleDateFormat.format(date);
                dt1 = dt.split(" ");
                date3 = dt1[0];
                time = dt1[1];
                tf = dt1[2];

                rise = simpleDateFormat.format(date1);
                rise1 = rise.split(" ");
                riseTime = rise1[1];
                rtf = rise1[2];

                set = simpleDateFormat.format(date2);
                set1 = set.split(" ");
                setTime = set1[1];
                stf = set1[2];

                if(description1.equals("clear sky") )
                    imageView.setImageResource(R.drawable.sunny);
                else if(description1.equals("scattered clouds"))
                    imageView.setImageResource(R.drawable.scattered_clouds);
                else if(description1.equals("mist"))
                    imageView.setImageResource(R.drawable.ic_fog);
                else if(description1.equals("few clouds"))
                    imageView.setImageResource(R.drawable.scattered_clouds);
                else if(description1.equals("broken clouds"))
                    imageView.setImageResource(R.drawable.scattered_clouds);
                else if(description1.equals("light rain"))
                    imageView.setImageResource(R.drawable.storm);

                date_view.setText(date3 + " " + day);
                time_view.setText(time + " " + tf);
                temp.setText(main.getTemp() + " \u2103");
                description.setText(description1);
                minMax.setText(main.getTemp_min() + " \u2103 / " + main.getTemp_max() + " \u2103");
                humidityValue.setText(main.getHumidity() + " %");
                pressureValue.setText(main.getPressure() + " mb");
                speedValue.setText(wind.getSpeed() + " km/h");
                sunriseValue.setText(riseTime + " " + rtf);
                sunsetValue.setText(setTime + " " + stf);
                cloudCover.setText(cloud.getAll() + "%");



                ((MainActivity) getActivity()).setActionBarTitle(data.getName());
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Toast.makeText(getActivity(), "Service failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        detail = view.findViewById(R.id.detail_button);
        imageView = view.findViewById(R.id.weather_img);
        date_view = view.findViewById(R.id.text_view_date);
        time_view = view.findViewById(R.id.text_view_time);
        temp = view.findViewById(R.id.temperature);
        description = view.findViewById(R.id.description);
        minMax = view.findViewById(R.id.min_maxTemp);
        humidityValue = view.findViewById(R.id.valueHumidity);
        pressureValue = view.findViewById(R.id.valuePressure);
        speedValue = view.findViewById(R.id.valueWind);
        sunriseValue = view.findViewById(R.id.valueSunrise);
        sunsetValue = view.findViewById(R.id.valueSunset);
        cloudCover = view.findViewById(R.id.valueCloud);

        detail.setOnClickListener(v -> buttonClick.click(latitude, longitude));



        return view;
    }

    private void initForcastFm( String latitude,String longitude) {
        ForecastFragment forecastList = new ForecastFragment(latitude, longitude);
        getChildFragmentManager().beginTransaction().replace(R.id.container_forecast,
                forecastList).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onButtonClick) {
            buttonClick = (onButtonClick) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public interface onButtonClick {
        void click(String lat,String lon);
    }
}
