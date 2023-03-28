package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {
    ToggleButton music_toggle, fx_toggle, dark_toggle;
    Button back;
    View root;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Boolean m_mode, fx_mode, dark_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        root = getWindow().getDecorView().getRootView();
        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        getSupportActionBar().hide();

        music_toggle = findViewById(R.id.music_toggle);
        fx_toggle = findViewById(R.id.fx_toggle);
        dark_toggle = findViewById(R.id.dark_toggle);
        back = findViewById(R.id.backButton);

        loadPreferences(music_toggle, fx_toggle, dark_toggle);

        music_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music_toggle.isChecked()){
                    editor.putBoolean("music", true);
                    editor.apply();
                    startService(new Intent(Settings.this, Music.class));
                }
                else{
                    editor.putBoolean("music", false);
                    editor.apply();
                    stopService(new Intent(Settings.this, Music.class));
                }
            }
        });
        fx_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fx_toggle.isChecked()){
                    editor.putBoolean("fx", true);
                    editor.apply();
                }
                else{
                    editor.putBoolean("fx", false);
                    editor.apply();
                }
            }
        });
        dark_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dark_toggle.isChecked()){
                    editor.putBoolean("dark", true);
                    editor.apply();
                }
                else{
                    editor.putBoolean("dark", false);
                    editor.apply();
                }
            }
        });

        back.setOnClickListener(e -> {
            finish();
        });

    }

    public void loadPreferences(ToggleButton m, ToggleButton f, ToggleButton d){
        m_mode = sp.getBoolean("music", false);
        fx_mode = sp.getBoolean("fx", false);
        dark_mode = sp.getBoolean("dark", false);

        m.setChecked(m_mode);
        f.setChecked(fx_mode);
        d.setChecked(dark_mode);
    }
}