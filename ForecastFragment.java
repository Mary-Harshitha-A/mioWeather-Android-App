package com.example.weather_app.WeatherScreens;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.weather_app.ForecastData.ForecastService;
import com.example.weather_app.ForecastData.HourlyForecast;
import com.example.weather_app.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastFragment extends Fragment {

    RecyclerView recyclerView;
    static Location currentLocation;
    private List<com.example.weather_app.ForecastData.List> forecastList = new ArrayList<>();
    private String key;
    private String units;
    ForecastAdapter forecastAdapter;
    static String lt;
    static String lg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchWeatherForecast();
    }

    public ForecastFragment(String lat,String lon){
        lt = lat;
        lg = lon;
    }

    private void fetchWeatherForecast() {
        units = "metric";
        key = "d9a48945433789ebd7f363e7fe891470";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ForecastService service = retrofit.create(ForecastService.class);

        Call<HourlyForecast> repos = service.getForecastService(lt, lg,units, key);

        repos.enqueue(new Callback<HourlyForecast>() {
            @Override
            public void onResponse(Call<HourlyForecast> call, Response<HourlyForecast> response) {
                HourlyForecast forecast = response.body();
                forecastList = forecast.getList();
                forecastAdapter.setData(forecastList);
            }

            @Override
            public void onFailure(Call<HourlyForecast> call, Throwable t) {
                Toast.makeText(getActivity(), "Service temporarily unavailable",Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forecast_list, container, false);

        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            forecastAdapter = new ForecastAdapter(forecastList);
            recyclerView.setAdapter(forecastAdapter);
        }
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
