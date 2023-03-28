package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SelectBoardSizeActivity extends AppCompatActivity {

    Button startGameButton;
    RadioGroup boardSizeRadioGroup;
    Boolean m_mode, fx_mode, dark_mode;//holds app settings
    SharedPreferences sp;// used to store settings as preferences
    SharedPreferences.Editor editor;// used to edit preferences
    View root;//used for dark mode
    MediaPlayer press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_board_size);
        //used for settings(dark mode, music, sfx)
        root = findViewById(android.R.id.content);
        getSupportActionBar().hide();//hide activity bar
        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        loadPreferences();

        //sound effects
        press = MediaPlayer.create(this, R.raw.press);

        startGameButton = findViewById(R.id.startGameButton);
        boardSizeRadioGroup = findViewById(R.id.boardSizeRadioGroup);
        startGameButton.setOnClickListener(e -> {
            sound(press);
            Intent i = new Intent(SelectBoardSizeActivity.this, GameActivity.class);
            int gameSize;
            switch (boardSizeRadioGroup.getCheckedRadioButtonId()) {
                case R.id.radioButton2:
                    gameSize = 4;
                    break;
                case R.id.radioButton3:
                    gameSize = 5;
                    break;
                default:
                    gameSize = 3;
            }
            i.putExtra("gameSize", gameSize);
            startActivity(i);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        press.release();
    }

    //used to save the sharedPreferences as local variables
    public void loadPreferences(){
        m_mode = sp.getBoolean("music", false);
        fx_mode = sp.getBoolean("fx", false);
        dark_mode = sp.getBoolean("dark", false);
        if(dark_mode){
            root.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else{
            root.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
    }

    //if sound effects game setting is set to on, the MediaPlayer will play its audio
    public void sound(MediaPlayer mp){
        if(fx_mode){
            mp.start();
        }
    }
}