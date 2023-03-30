package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    Button newGameButton, settings, loadGameButton;
    TextView title;
    Boolean m_mode, fx_mode, dark_mode;//holds app settings
    SharedPreferences sp;// used to store settings as preferences
    SharedPreferences.Editor editor;// used to edit preferences
    View root;//used for dark mode
    ActivityManager activityManager;

    //sound effects media players
    MediaPlayer press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //used for settings(dark mode, music, sfx)
        root = findViewById(android.R.id.content);
        getSupportActionBar().hide();//hide activity bar
        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        loadPreferences();
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        //sound effects
        press = MediaPlayer.create(this, R.raw.press);

        newGameButton = findViewById(R.id.newGameButton);
        loadGameButton = findViewById(R.id.loadGameButton);
        settings = findViewById(R.id.settingsButton);
        title = findViewById(R.id.title);

        //title drop in animation
        Animation dropInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_in);
        Animation slide_up_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_1);
        Animation slide_up_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_2);
        Animation slide_up_3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_3);
        title.setAnimation(dropInAnimation);
        newGameButton.setAnimation(slide_up_1);
        loadGameButton.setAnimation(slide_up_2);
        settings.setAnimation(slide_up_3);
        dropInAnimation.start();
        slide_up_1.start();
        slide_up_2.start();
        slide_up_3.start();
        //new game button listener
        newGameButton.setOnClickListener(e -> {
            sound(press);
            Intent i = new Intent(Home.this, SelectBoardSizeActivity.class);
            startActivity(i);
        });
        //settings button listener
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound(press);
                Intent settings_intent = new Intent(Home.this, Settings.class);
                startActivity(settings_intent);
            }
        });
        //load game button listener

        loadGameButton.setOnClickListener(e -> {
            SharedPreferences s = getSharedPreferences("Game", MODE_PRIVATE);
            Intent intent = new Intent(Home.this, GameActivity.class);
            int gameSize = s.getInt("boardSize", -1);
            int playerTurn = s.getInt("playerTurn", 1);

            if (gameSize == -1) {
                Toast.makeText(getApplicationContext(), "No saved game found", Toast.LENGTH_SHORT).show();
                return;
            }
            sound(press);
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

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    //used to save the sharedPreferences as local variables
    public void loadPreferences(){
        m_mode = sp.getBoolean("music", false);
        fx_mode = sp.getBoolean("fx", false);
        dark_mode = sp.getBoolean("dark", false);
        if(dark_mode){
            root.setBackgroundColor(getResources().getColor(R.color.black));
            root.invalidate();
        }
        else{
            root.setBackgroundColor(getResources().getColor(R.color.yellow));
            root.invalidate();
        }
    }
    //resets the music when destroying activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putBoolean("music", false);
        editor.apply();
        press.release();
    }

    //if sound effects game setting is set to on, the MediaPlayer will play its audio
    public void sound(MediaPlayer mp){
        if(fx_mode){
            mp.start();
        }
    }
}