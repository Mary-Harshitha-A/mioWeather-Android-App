package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.weather_app.AppManagement.AboutActivity;
import com.example.weather_app.AppManagement.AddLocationActivity;
import com.example.weather_app.WeatherScreens.ForecastDetailActivity;
import com.example.weather_app.WeatherScreens.Home;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        Home.onButtonClick {
    private DrawerLayout drawer;
    FetchCurrentLocation currentLocation = new FetchCurrentLocation();
    static Toolbar toolbar;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.latitude = getIntent().getDoubleExtra("lat", 0);
        this.longitude = getIntent().getDoubleExtra("long", 0);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Home(this.latitude,this.longitude)).commit();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void setActionBarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Home(this.latitude,this.longitude)).commit();
                break;
            case R.id.nav_location:
                Intent loc = new Intent(MainActivity.this, AddLocationActivity.class);
                startActivity(loc);
                break;

            case R.id.nav_about:
                Intent intent=new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void click(String lat, String lon) {
        Intent intent = new Intent(this, ForecastDetailActivity.class);
        intent.putExtra("lat",lat);
        intent.putExtra("long",lon);
        startActivity(intent);
    }
}


