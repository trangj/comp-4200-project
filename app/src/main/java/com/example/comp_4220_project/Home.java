package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    String m_mode, fx_mode, dark_mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        newGameButton = findViewById(R.id.newGameButton);
        settings = findViewById(R.id.settingsButton);
        title = findViewById(R.id.title);

        Animation dropInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_in);
        title.setAnimation(dropInAnimation);
        dropInAnimation.start();
        newGameButton.setOnClickListener(e -> {
            startActivity(new Intent(Home.this, SelectBoardSizeActivity.class));
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settings_intent = new Intent(Home.this, Settings.class);
                startActivity(settings_intent);
            }
        });
    }

    public void loadPreferences(Set<String> mySet){
        Iterator<String> iterator = mySet.iterator();
        m_mode = iterator.next();
        fx_mode = iterator.next();
        dark_mode = iterator.next();

        if(dark_mode == "DARK_ON"){

        }
    }
}