package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    boolean[][] board;
    boolean[][] board2;
    int playerTurn = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int gameSize = intent.getIntExtra("gameSize", 0);
        board = new boolean[gameSize][gameSize];
        board2 = new boolean[gameSize][gameSize];

        // loading initial fragment
        player1RollDie();
    }

    public void player1RollDie() {
        Player1RollDieFragment f = new Player1RollDieFragment();
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
}