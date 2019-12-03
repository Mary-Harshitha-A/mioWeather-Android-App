package com.example.weather_app.Introduction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.weather_app.FetchCurrentLocation;
import com.example.weather_app.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class AppIntro extends AppCompatActivity {
    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    Button btnGetStarted;
    int position=0;
    Animation btnAnim;
    TextView txtskip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(RestorePrefData()){

            Intent  mainActivity=new Intent(getApplicationContext(),FetchCurrentLocation.class);
            startActivity(mainActivity);
            finish();

        }

        setContentView(R.layout.activity_app_intro);

        tabIndicator=findViewById(R.id.tab_indicator);

        btnNext=findViewById(R.id.btn_next);

        btnGetStarted=findViewById(R.id.btn_getstarted);
        btnAnim= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        txtskip=findViewById(R.id.textview_skip);

        final List<ScreenItem> mList=new ArrayList<>();
        mList.add(new ScreenItem("Weather is Cloudy","Your smartphone can keep you up-to-date on severe weather conditions, including notifications of oncoming storms. Of course, you'll need to download a good weather app for those kinds of features.",R.drawable.app_intro));
        mList.add(new ScreenItem("Weather is Rainbow","Your smartphone can keep you up-to-date on severe weather conditions, including notifications of oncoming storms. Of course, you'll need to download a good weather app for those kinds of features.",R.drawable.app_intro6));
        mList.add(new ScreenItem("Weather is Dawn","Your smartphone can keep you up-to-date on severe weather conditions, including notifications of oncoming storms. Of course, you'll need to download a good weather app for those kinds of features.",R.drawable.app_intro4));


        screenPager=findViewById(R.id.screen_viewpager);
        introViewPagerAdapter=new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        txtskip.setOnClickListener(view -> {
            Intent intent=new Intent(AppIntro.this, FetchCurrentLocation.class);
            startActivity(intent);
            finish();
        });

        btnNext.setOnClickListener(view -> {
            position=screenPager.getCurrentItem();
            if(position < mList.size()){
                position++;
                screenPager.setCurrentItem(position);
            }
            if (position == mList.size()){
                loadLastScreen();
            }

        });

        btnGetStarted.setOnClickListener(view -> {
            Intent maimActivity=new Intent(getApplicationContext(), FetchCurrentLocation.class);
            startActivity(maimActivity);

            savePrefsData();
            finish();
        });


        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mList.size()-1)
                    loadLastScreen();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private boolean RestorePrefData() {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore=pref.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefsData() {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }

    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnim);
    }
}
