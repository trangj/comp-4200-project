package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SelectBoardSizeActivity extends AppCompatActivity {

    Button startGameButton;
    RadioGroup boardSizeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_board_size);

        startGameButton = findViewById(R.id.startGameButton);
        boardSizeRadioGroup = findViewById(R.id.boardSizeRadioGroup);
        startGameButton.setOnClickListener(e -> {
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
}