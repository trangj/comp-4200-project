package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    boolean[][] board;
    boolean[][] board2;
    int playerTurn = 1;
    int player_1_score = 0;
    int player_2_score = 0;
    int player1NumRestored = 0;
    int player2NumRestored = 0;
    Boolean m_mode, fx_mode, dark_mode;//holds app settings
    SharedPreferences sp;// used to store settings as preferences
    SharedPreferences.Editor editor;// used to edit preferences
    View root;//used for dark mode

    //sound effects media players
    MediaPlayer press;

    public int getPlayer_1_score() { return player_1_score; }

    public void setPlayer_1_score(int player_1_score) { this.player_1_score = player_1_score; }

    public int getPlayer_2_score() { return player_2_score; }

    public void setPlayer_2_score(int player_2_score) { this.player_2_score = player_2_score; }

    public void set_zero(){
        this.player_1_score = 0;
        this.player_2_score = 0;
    }

    public int getPlayer1NumRestored() {
        return player1NumRestored;
    }

    public void setPlayer1NumRestored(int player1NumRestored) {
        this.player1NumRestored = player1NumRestored;
    }

    public int getPlayer2NumRestored() {
        return player2NumRestored;
    }

    public void setPlayer2NumRestored(int player2NumRestored) {
        this.player2NumRestored = player2NumRestored;
    }

    Button buttonMenu;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        press.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //used for settings(dark mode, music, sfx)
        root = findViewById(android.R.id.content);
        getSupportActionBar().hide();//hide activity bar
        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        loadPreferences();

        //sound effects
        press = MediaPlayer.create(this, R.raw.press);

        Intent intent = getIntent();
        int gameSize = intent.getIntExtra("gameSize", 0);
        boolean[] savedBoard1dPlayer1 = intent.getBooleanArrayExtra("board");
        boolean[] savedBoard1dPlayer2 = intent.getBooleanArrayExtra("board2");
        playerTurn = intent.getIntExtra("playerTurn", 1);

        if(savedBoard1dPlayer1 == null || savedBoard1dPlayer2 == null) {
            board = new boolean[gameSize][gameSize];
            board2 = new boolean[gameSize][gameSize];
        } else {
            board = to2dArray(savedBoard1dPlayer1, gameSize);
            board2 = to2dArray(savedBoard1dPlayer2, gameSize);
        }

        buttonMenu = findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(e -> {
            sound(press);
            Intent i = new Intent(GameActivity.this, InGameMenuActivity.class);
            boolean[] board1dPlayer1 = to1dArray(board);
            boolean[] board1dPlayer2 = to1dArray(board2);
            i.putExtra("board", board1dPlayer1);
            i.putExtra("board2", board1dPlayer2);
            i.putExtra("playerTurn", playerTurn);
            i.putExtra("boardSize", board.length);
            startActivity(i);
        });

        if(savedBoard1dPlayer1 == null || savedBoard1dPlayer2 == null) {
            // loading initial fragment
            player1RollDie();
        } else {
            // if we are loading a save, then skip to the game options
            gameOptions();
        }
    }

    public void player1RollDie() {
        RollDieFragment f = new RollDieFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame, f);
        ft.commit();
    }

    public void gameOptions() {
        GameOptionsFragment f = new GameOptionsFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame, f);
        ft.commit();
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public boolean[][] getBoard2() {
        return board2;
    }

    public boolean[] to1dArray(boolean[][] arr) {
        boolean[] array1d = new boolean[arr.length* arr.length];
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr.length; j++) {
                array1d[(arr.length * i) + j] = arr[i][j];
            }
        }
        return array1d;
    }

    public boolean[][] to2dArray(boolean[] arr, int size) {
        boolean[][] array2d = new boolean[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                array2d[i][j] = arr[(size * i) + j];
            }
        }
        return array2d;
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

    //if sound effects game setting is set to on, the MediaPlayer will play its audio
    public void sound(MediaPlayer mp){
        if(fx_mode){
            mp.start();
        }
    }
}