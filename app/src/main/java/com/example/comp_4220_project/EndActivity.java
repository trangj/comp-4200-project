package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    Button mainMenuButton;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        textView = findViewById(R.id.tv_winner);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int winner = extras.getInt("winner");
            String display_winner = (winner == 1) ? "Player 1 Wins!!" : "Player 2 Wins!!";
            textView.setText(display_winner);
        }

        mainMenuButton.setOnClickListener(e -> {
            navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
        });
    }
}