package com.example.comp_4220_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Settings extends AppCompatActivity {
    ToggleButton music_toggle, fx_toggle, dark_toggle;
    View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        root = getWindow().getDecorView().getRootView();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        getSupportActionBar().hide();

        music_toggle = findViewById(R.id.music_toggle);
        fx_toggle = findViewById(R.id.fx_toggle);
        dark_toggle = findViewById(R.id.dark_toggle);

        music_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music_toggle.isChecked()){
                    startService(new Intent(Settings.this, Music.class));
                }
                else{
                    stopService(new Intent(Settings.this, Music.class));
                }
            }
        });
        /*fx_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> mySet = sharedPreferences.getStringSet("mySetKey", null);
                Set<String> updatedSet = new HashSet<>(mySet);
                if(mySet.contains("FX_OFF") && fx_toggle.isChecked()){
                    updatedSet.remove("FX_OFF");
                    updatedSet.add("FX_ON");
                }
                else if(mySet.contains("FX_ON") && !fx_toggle.isChecked()){
                    updatedSet.remove("FX_ON");
                    updatedSet.add("FX_OFF");
                }
                editor.putStringSet("mySetKey", updatedSet);
                editor.apply();
            }
        });
        dark_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> mySet = sharedPreferences.getStringSet("mySetKey", null);
                Set<String> updatedSet = new HashSet<>(mySet);
                if(mySet.contains("DARK_OFF") && fx_toggle.isChecked()){
                    updatedSet.remove("DARK_OFF");
                    updatedSet.add("DARK_ON");
                    root.setBackgroundColor(getResources().getColor(R.color.black));
                }
                else if(mySet.contains("DARK_ON") && !fx_toggle.isChecked()){
                    updatedSet.remove("DARK_ON");
                    updatedSet.add("DARK_OFF");
                    root.setBackgroundColor(getResources().getColor(R.color.white));
                }
                editor.putStringSet("mySetKey", updatedSet);
                editor.apply();
            }
        });*/

    }

    public void back(){
        Intent intent = new Intent(Settings.this, Home.class);
        startActivity(intent);
    }
}