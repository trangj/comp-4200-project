package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TextView dropout, studio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        dropout = findViewById(R.id.dropout);
        studio = findViewById(R.id.studios);
        View root = findViewById(android.R.id.content);

        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                root.setBackgroundColor(getResources().getColor(R.color.black));
                dropout.setTextColor(getResources().getColor(R.color.white));
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
        dropout.startAnimation(fadeOut);
    }
}