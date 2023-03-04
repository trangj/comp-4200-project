package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button newGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(e -> {
            startActivity(new Intent(MainActivity.this, SelectBoardSizeActivity.class));
        });
    }
}