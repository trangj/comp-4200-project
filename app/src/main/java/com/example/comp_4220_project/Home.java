package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Set;

public class Home extends AppCompatActivity {
    Button newGameButton, settings;
    TextView title;
    Boolean m_mode, fx_mode, dark_mode;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    View root;
    ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        root = findViewById(android.R.id.content);

        getSupportActionBar().hide();
        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        loadPreferences();
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        newGameButton = findViewById(R.id.newGameButton);
        settings = findViewById(R.id.settingsButton);
        title = findViewById(R.id.title);

        Animation dropInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_in);
        title.setAnimation(dropInAnimation);
        dropInAnimation.start();
        newGameButton.setOnClickListener(e -> {
            Intent i = new Intent(Home.this, SelectBoardSizeActivity.class);
            startActivity(i);
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings_intent = new Intent(Home.this, Settings.class);
                startActivity(settings_intent);
            }
        });
    }

    public void loadPreferences(){
        m_mode = sp.getBoolean("music", false);
        fx_mode = sp.getBoolean("fx", false);
        dark_mode = sp.getBoolean("dark", false);
        if(dark_mode){
            root.setBackgroundColor(getResources().getColor(R.color.black));
            root.invalidate();
        }
        else{
            root.setBackgroundColor(getResources().getColor(R.color.white));
            root.invalidate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putBoolean("music", false);
        editor.apply();
    }
}