package com.example.weather_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class FetchCurrentLocation extends AppCompatActivity {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_current_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    public void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }


        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if(location != null)
            {
                currentLocation = location;
                Intent i = new Intent(FetchCurrentLocation.this, SplashScreen.class);
                i.putExtra("lat",currentLocation.getLatitude());
                i.putExtra("long",currentLocation.getLongitude());
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                else
                {
                    new AlertDialog.Builder(this)
                            .setTitle("Location permission required")
                            .setMessage("Required location permission for accessing this App.\n"+
                                    "Granting location permission results in accurate weather data")
                            .setPositiveButton("OK", (dialog, which) ->
                                    ActivityCompat.requestPermissions(FetchCurrentLocation.this, new String[]{
                                            Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE))
                            .setNegativeButton("Cancel", (dialog, which) -> {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(this, new String[]
                                            {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                            })
                            .create()
                            .show();
                }

                break;
        }
    }
}
