package com.example.weather_app.WeatherScreens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.weather_app.ForecastData.City;
import com.example.weather_app.ForecastData.ForecastService;
import com.example.weather_app.ForecastData.HourlyForecast;
import com.example.weather_app.ForecastData.List;
import com.example.weather_app.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private java.util.List<List> list = new ArrayList<>();
    ForecastDetailAdapter adapter;
    String units;
    String latitude;
    String longitude;
    String key;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item_list_forecast);

        toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        this.latitude = getIntent().getStringExtra("lat");
        this.longitude = getIntent().getStringExtra("long");
        recyclerView = findViewById(R.id.list);
        adapter = new ForecastDetailAdapter(list);
        recyclerView.setAdapter(adapter);

        fetchWeatherForecast();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchWeatherForecast() {
        units = "metric";
        key = "d9a48945433789ebd7f363e7fe891470";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ForecastService service = retrofit.create(ForecastService.class);

        Call<HourlyForecast> repos = service.getForecastService(this.latitude,
                this.longitude, units, key);

        repos.enqueue(new Callback<HourlyForecast>() {
            @Override
            public void onResponse(Call<HourlyForecast> call, Response<HourlyForecast> response) {
                HourlyForecast forecast = response.body();
                City city = forecast.getCity();
                if (forecast != null && forecast.getList() != null) {
                    list = forecast.getList();
                    adapter.setData(list);
                } else {
                    Toast.makeText(ForecastDetailActivity.this, "Network failure. Please try again", Toast.LENGTH_SHORT).show();

                }
                toolbar.setTitle(city.getName());
            }

            @Override
            public void onFailure(Call<HourlyForecast> call, Throwable t) {
                Toast.makeText(ForecastDetailActivity.this, "Service temporarily unavailable", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
