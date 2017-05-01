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

/**
 * Created by rusty on 5/1/2017.
 */

public class Options extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        final ListView text = DisplayNewsActivity.listView;
        ToggleButton tog = (ToggleButton) findViewById(R.id.nightmode);
        ToggleButton shake = (ToggleButton) findViewById(R.id.shake);
        SharedPreferences sharedPref = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE);
        tog.setChecked(sharedPref.getBoolean("Nightmode", false));
        shake.setChecked(sharedPref.getBoolean("Shake", true));
        tog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE).edit();
                    editor.putBoolean("Nightmode", true);
                    editor.commit();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Options.this, R.layout.custom_textview, DisplayNewsActivity.headlines);
                    text.setAdapter(adapter);
                    text.setBackgroundColor(Color.BLACK);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE).edit();
                    editor.putBoolean("Nightmode", false);
                    editor.commit();
                    text.setBackgroundColor(Color.WHITE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Options.this, android.R.layout.simple_list_item_1, DisplayNewsActivity.headlines);
                    text.setAdapter(adapter);
                }
            }
        });

        shake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE).edit();
                    editor.putBoolean("Shake", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE).edit();
                    editor.putBoolean("Shake", false);
                    editor.commit();

                }
            }
        });
    }
}
