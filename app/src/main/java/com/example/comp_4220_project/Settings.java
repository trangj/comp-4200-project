package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {
    ToggleButton music_toggle, fx_toggle, dark_toggle;
    Button back;
    View root;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Boolean m_mode, fx_mode, dark_mode;
    TextView mus_text, fx_text, dark_text;

    //sound effects media players
    MediaPlayer press;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        press.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        root = findViewById(android.R.id.content);
        sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        getSupportActionBar().hide();

        mus_text = findViewById(R.id.music_text);
        fx_text = findViewById(R.id.fx_text);
        dark_text = findViewById(R.id.dark_text);

        music_toggle = findViewById(R.id.music_toggle);
        fx_toggle = findViewById(R.id.fx_toggle);
        dark_toggle = findViewById(R.id.dark_toggle);
        back = findViewById(R.id.backButton);

        //sound effects
        press = MediaPlayer.create(this, R.raw.press);

        loadPreferences(music_toggle, fx_toggle, dark_toggle);

        music_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound(press);
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
                    sound(press);
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
                sound(press);
                if(dark_toggle.isChecked()){
                    root.setBackgroundColor(getResources().getColor(R.color.black));
                    mus_text.setTextColor(getResources().getColor(R.color.white));
                    fx_text.setTextColor(getResources().getColor(R.color.white));
                    dark_text.setTextColor(getResources().getColor(R.color.white));
                    root.invalidate();
                    editor.putBoolean("dark", true);
                    editor.apply();
                }
                else{
                    root.setBackgroundColor(getResources().getColor(R.color.yellow));
                    mus_text.setTextColor(getResources().getColor(R.color.black));
                    fx_text.setTextColor(getResources().getColor(R.color.black));
                    dark_text.setTextColor(getResources().getColor(R.color.black));
                    root.invalidate();
                    editor.putBoolean("dark", false);
                    editor.apply();
                }
            }
        });

        back.setOnClickListener(e -> {
            sound(press);
            finish();
        });

    }

    public void loadPreferences(ToggleButton m, ToggleButton f, ToggleButton d){
        m_mode = sp.getBoolean("music", false);
        fx_mode = sp.getBoolean("fx", false);
        dark_mode = sp.getBoolean("dark", false);
        if(dark_mode){
            root.setBackgroundColor(getResources().getColor(R.color.black));
            mus_text.setTextColor(getResources().getColor(R.color.white));
            fx_text.setTextColor(getResources().getColor(R.color.white));
            dark_text.setTextColor(getResources().getColor(R.color.white));
        }
        else{
            root.setBackgroundColor(getResources().getColor(R.color.yellow));
            mus_text.setTextColor(getResources().getColor(R.color.black));
            fx_text.setTextColor(getResources().getColor(R.color.black));
            dark_text.setTextColor(getResources().getColor(R.color.black));
        }
        m.setChecked(m_mode);
        f.setChecked(fx_mode);
        d.setChecked(dark_mode);
    }

    //if sound effects game setting is set to on, the MediaPlayer will play its audio
    public void sound(MediaPlayer mp){
        if(fx_mode){
            mp.start();
        }
    }
}