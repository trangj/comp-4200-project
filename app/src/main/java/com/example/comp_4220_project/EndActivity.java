package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class EndActivity extends AppCompatActivity {

    Button mainMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        mainMenuButton = findViewById(R.id.mainMenuButton);

        mainMenuButton.setOnClickListener(e -> {
            startActivity(new Intent(EndActivity.this, MainActivity.class));
        });
    }
}