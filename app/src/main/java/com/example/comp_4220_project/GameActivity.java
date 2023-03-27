package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    boolean[][] board;
    boolean[][] board2;
    int playerTurn = 1;
    int player_1_score = 0;
    int player_2_score = 0;

    public int getPlayer_1_score() { return player_1_score; }

    public void setPlayer_1_score(int player_1_score) { this.player_1_score = player_1_score; }

    public int getPlayer_2_score() { return player_2_score; }

    public void setPlayer_2_score(int player_2_score) { this.player_2_score = player_2_score; }

    public void set_zero(){
        this.player_1_score = 0;
        this.player_2_score = 0;
    }

    Button buttonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


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
}