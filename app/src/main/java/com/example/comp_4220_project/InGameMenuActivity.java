package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class InGameMenuActivity extends AppCompatActivity {

    Button buttonSave, buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game_menu);

        buttonSave = findViewById(R.id.buttonSave);
        buttonExit = findViewById(R.id.buttonExit);

        buttonSave.setOnClickListener(e -> {
            Intent i = getIntent();
            boolean[] board1dPlayer1 = i.getBooleanArrayExtra("board");
            boolean[] board1dPlayer2 = i.getBooleanArrayExtra("board2");
            int playerTurn = i.getIntExtra("playerTurn", 0);
            int boardSize  = i.getIntExtra("boardSize", 0);

            save(board1dPlayer1, board1dPlayer2, boardSize, playerTurn);
        });

        buttonExit.setOnClickListener(e -> {
            finish();
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
}