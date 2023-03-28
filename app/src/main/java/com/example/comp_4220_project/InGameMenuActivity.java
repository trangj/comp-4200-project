package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class InGameMenuActivity extends AppCompatActivity {

    Button buttonSave, buttonExit, buttonExitToMainMenu;
    Boolean m_mode, fx_mode, dark_mode;//holds app settings
    SharedPreferences sp;// used to store settings as preferences
    SharedPreferences.Editor editor;// used to edit preferences
    View root;//used for dark mode

    //sound effects media players
    MediaPlayer press;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        press.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game_menu);
        //used for settings(dark mode, music, sfx)
        root = findViewById(android.R.id.content);
        getSupportActionBar().hide();//hide activity bar
        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        loadPreferences();

        //sound effects
        press = MediaPlayer.create(this, R.raw.press);

        buttonSave = findViewById(R.id.buttonSave);
        buttonExit = findViewById(R.id.buttonExit);
        buttonExitToMainMenu = findViewById(R.id.buttonExitToMainMenu);

        buttonSave.setOnClickListener(e -> {
            sound(press);
            Intent i = getIntent();
            boolean[] board1dPlayer1 = i.getBooleanArrayExtra("board");
            boolean[] board1dPlayer2 = i.getBooleanArrayExtra("board2");
            int playerTurn = i.getIntExtra("playerTurn", 0);
            int boardSize  = i.getIntExtra("boardSize", 0);

            save(board1dPlayer1, board1dPlayer2, boardSize, playerTurn);
            Toast.makeText(getApplicationContext(), "Game has been saved", Toast.LENGTH_SHORT).show();
        });

        buttonExit.setOnClickListener(e -> {
            sound(press);
            finish();
        });

        buttonExitToMainMenu.setOnClickListener(e -> {
            sound(press);
            navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
        });
    }

    public void save(boolean[] board, boolean[] board2, int boardSize, int playerTurn) {
        SharedPreferences sharedPref = getSharedPreferences("Game", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("playerTurn", playerTurn);
        editor.putInt("boardSize", boardSize);
        for(int i = 0; i < board.length; i++) {
            editor.putBoolean("board_"+i, board[i]);
        }
        for(int i = 0; i < board2.length; i++) {
            editor.putBoolean("board2_"+i, board2[i]);
        }
        editor.apply();
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