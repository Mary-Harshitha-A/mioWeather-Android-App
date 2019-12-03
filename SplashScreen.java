package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.latitude = getIntent().getDoubleExtra("lat", 0);
        this.longitude = getIntent().getDoubleExtra("long", 0);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            i.putExtra("lat",latitude);
            i.putExtra("long",longitude);
            startActivity(i);
            finish();
        }, 2000);
    }
}
