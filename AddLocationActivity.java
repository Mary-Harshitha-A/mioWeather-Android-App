package com.example.weather_app.AppManagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.weather_app.R;
import com.example.weather_app.WeatherScreens.UserTypedLocation;

public class AddLocationActivity extends AppCompatActivity {
    Toolbar toolbar;
    private String[] locations = {"Bangalore","Chennai","Delhi","Singapore","Chicago",
    "Hyderabad","Australia","Japan","Calcutta","Kerala","Koramangala"};
    AutoCompleteTextView autoCompleteTextView;
    Switch aSwitch;
    static String enteredLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_managelocation);

        autoCompleteTextView = findViewById(R.id.customLoc);
        aSwitch = findViewById(R.id.switchLocation);

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.select_dialog_item, locations);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);

        toolbar = findViewById(R.id.locationToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            enteredLocation = autoCompleteTextView.getText().toString();
            if(!(TextUtils.isEmpty(enteredLocation))){
                boolean switchState = aSwitch.isChecked();
                if(switchState) {
                    Bundle bundle = new Bundle();
                    bundle.putString("locality", enteredLocation);

                    UserTypedLocation typedLocation = new UserTypedLocation();
                    typedLocation.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction().add(R.id.locationContainer,
                            typedLocation).commit();

                }
                else{
                    onRestart();
                }
            }
            else
                Toast.makeText(getApplicationContext(), "Please enter a location",Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(AddLocationActivity.this, AddLocationActivity.class);
        startActivity(i);
        finish();
    }

    public void setActionBarTitle(String name) {
        toolbar.setTitle(name);
    }
}
