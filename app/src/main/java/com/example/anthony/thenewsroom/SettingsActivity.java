package com.example.anthony.thenewsroom;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        ToggleButton tog = (ToggleButton) findViewById(R.id.nightmode);
        ToggleButton shake = (ToggleButton) findViewById(R.id.shake);

        SharedPreferences sharedPref = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE);
        tog.setChecked(sharedPref.getBoolean("Nightmode", false));
        shake.setChecked(sharedPref.getBoolean("Shake", true));
        tog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE).edit();
                editor.putBoolean("Nightmode", isChecked);
                editor.apply();
            }
        });

        shake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE).edit();
                if (isChecked) {
                    editor.putBoolean("Shake", true);
                    editor.apply();
                } else {
                    editor.putBoolean("Shake", false);
                    editor.apply();

                }
            }
        });
    }
}
