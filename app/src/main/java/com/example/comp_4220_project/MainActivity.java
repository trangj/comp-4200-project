package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    TextView fal, prophets, studio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        fal = findViewById(R.id.fal);
        prophets = findViewById(R.id.prophets);
        studio = findViewById(R.id.studios);
        View root = findViewById(android.R.id.content);

        //shared preferences setup
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Set<String> mySet = new HashSet<>();
        mySet.add("MUSIC_OFF");
        mySet.add("FX_OFF");
        mySet.add("DARK_OFF");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("mySetKey", mySet);
        editor.apply();

        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                root.setBackgroundColor(getResources().getColor(R.color.black));
                prophets.setTextColor(getResources().getColor(R.color.white));
                fal.setTextColor(getResources().getColor(R.color.white));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Code to be executed after 1 second
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                    }
                }, 1000); // Delay in milliseconds
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        studio.startAnimation(fadeOut);
        fal.startAnimation(fadeOut);
        prophets.startAnimation(fadeOut);
    }
}