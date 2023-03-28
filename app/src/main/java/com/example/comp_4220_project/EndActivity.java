package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    Button mainMenuButton;
    Boolean m_mode, fx_mode, dark_mode;//holds app settings
    SharedPreferences sp;// used to store settings as preferences
    SharedPreferences.Editor editor;// used to edit preferences
    View root;//used for dark mode
    TextView textView;

    //sound effects media players
    MediaPlayer press;
    MediaPlayer victory;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        press.release();
        victory.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        //used for settings(dark mode, music, sfx)
        root = findViewById(android.R.id.content);
        getSupportActionBar().hide();//hide activity bar
        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        loadPreferences();
        textView = findViewById(R.id.tv_winner);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        Bundle extras = getIntent().getExtras();

        //sound effects
        press = MediaPlayer.create(this, R.raw.press);
        victory = MediaPlayer.create(this, R.raw.victory);

        if(extras != null){
            sound(victory);
            int winner = extras.getInt("winner");
            String display_winner = (winner == 1) ? "Player 1 Wins!!" : "Player 2 Wins!!";
            textView.setText(display_winner);
        }

        mainMenuButton.setOnClickListener(e -> {
            sound(press);
            navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
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
            root.setBackgroundColor(getResources().getColor(R.color.yellow));
            root.invalidate();
        }
    }

    //if sound effects game setting is set to on, the MediaPlayer will play its audio
    public void sound(MediaPlayer mp){
        if(fx_mode){
            mp.start();
        }
    }
}