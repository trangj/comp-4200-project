package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button newGameButton, loadGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(e -> {
            startActivity(new Intent(MainActivity.this, SelectBoardSizeActivity.class));
        });

        loadGameButton = findViewById(R.id.loadGameButton);
        loadGameButton.setOnClickListener(e -> {
            SharedPreferences s = getSharedPreferences("Game", MODE_PRIVATE);
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            int gameSize = s.getInt("boardSize", 3);
            int playerTurn = s.getInt("playerTurn", 1);
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
            startActivity(intent);
        });
    }
}