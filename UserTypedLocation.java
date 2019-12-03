package com.example.weather_app.WeatherScreens;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather_app.AppManagement.AddLocationActivity;
import com.example.weather_app.Data.Clouds;
import com.example.weather_app.Data.Main;
import com.example.weather_app.Data.Sys;
import com.example.weather_app.Data.UserEnteredLoc;
import com.example.weather_app.Data.WeatherInfo;
import com.example.weather_app.Data.Wind;
import com.example.weather_app.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserTypedLocation extends Fragment {
    private TextView temp;
    private TextView place;
    private TextView humidityValue;
    private TextView pressureValue;
    private TextView speedValue;
    private TextView sunriseValue;
    private TextView sunsetValue;
    private TextView cloudCover;
    private TextView date_view;
    private TextView time_view;
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
    static String location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readBundle(getArguments());
        fetchWeatherInfo();
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            location = bundle.getString("locality");
        }
    }

    private void fetchWeatherInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserEnteredLoc service = retrofit.create(UserEnteredLoc.class);

        units = "metric";
        key = "d9a48945433789ebd7f363e7fe891470";
        Call<WeatherInfo> repos = service.getService(location, units, key);

        repos.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                if(response.isSuccessful()){
                    WeatherInfo data = response.body();
                    assert data != null;
                    Main main = data.getMain();
                    Wind wind = data.getWind();
                    Sys sys = data.getSys();
                    Clouds cloud = data.getClouds();

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

                    date_view.setText(date3 + " " + day);
                    time_view.setText(time + " " + tf);
                    place.setText(data.getName());
                    temp.setText(main.getTemp() + " \u2103");
                    humidityValue.setText(main.getHumidity() + " %");
                    pressureValue.setText(main.getPressure() + " mb");
                    speedValue.setText(wind.getSpeed() + " km/h");
                    sunriseValue.setText(riseTime + " " + rtf);
                    sunsetValue.setText(setTime + " " + stf);
                    cloudCover.setText(cloud.getAll() + "%");

                    ((AddLocationActivity) getActivity()).setActionBarTitle(data.getName());

                }
                else
                {
                    Toast.makeText(getActivity(), "Enter valid location", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Toast.makeText(getActivity(), "Service failed", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_typed_location, container, false);

        date_view = view.findViewById(R.id.valueDate);
        time_view = view.findViewById(R.id.valueTime);
        temp = view.findViewById(R.id.forecastTemperature);
        place = view.findViewById(R.id.forecastPlace);
        humidityValue = view.findViewById(R.id.valueHumidity);
        pressureValue = view.findViewById(R.id.valuePressure);
        speedValue = view.findViewById(R.id.valueWind);
        sunriseValue = view.findViewById(R.id.valueSunrise);
        sunsetValue = view.findViewById(R.id.valueSunset);
        cloudCover = view.findViewById(R.id.valueCloud);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
