package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import java.util.Arrays;



public class MainActivity extends AppCompatActivity {
    TextView fal, prophets, studio;
    Button newGameButton, loadGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        fal = findViewById(R.id.fal);
        prophets = findViewById(R.id.prophets);
        studio = findViewById(R.id.studios);
        View root = findViewById(android.R.id.content);

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

        loadGameButton = findViewById(R.id.loadGameButton);
        loadGameButton.setOnClickListener(e -> {
            SharedPreferences s = getSharedPreferences("Game", MODE_PRIVATE);
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            int gameSize = s.getInt("boardSize", -1);
            int playerTurn = s.getInt("playerTurn", 1);

            if (gameSize == -1) {
                Toast.makeText(getApplicationContext(), "No saved game found", Toast.LENGTH_SHORT).show();
                return;
            }

            intent.putExtra("gameSize", gameSize);
            intent.putExtra("playerTurn", playerTurn);
            boolean [] board = new boolean[gameSize*gameSize];
            boolean [] board2 = new boolean[gameSize*gameSize];
            for (int i = 0; i < board.length; i++) {
                board[i] = s.getBoolean("board_"+i, false);
            }
            for (int i = 0; i < board2.length; i++) {
                board2[i] = s.getBoolean("board2_"+i, false);
            }
            intent.putExtra("board", board);
            intent.putExtra("board2", board2);
            Toast.makeText(getApplicationContext(), "Loaded previous saved game", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }
}