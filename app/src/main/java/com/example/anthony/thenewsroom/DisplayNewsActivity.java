package com.example.anthony.thenewsroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DisplayNewsActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final int TWITTER_CODE = 829;

    public static ListView listView;
    private List headlines;
    private List links;

    private ArrayAdapter adapter;
    private FetchNewsAsyncTask task;
    private SensorManager sensorManager;
    private SharedPreferences sharedPref;
    private float accel; // acceleration apart from gravity
    private float accelCurrent; // current acceleration including gravity
    private float accelLast; // last acceleration including gravity



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);
        
        sharedPref = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE);
        headlines = new ArrayList();
        links = new ArrayList();

        //async task to collect all news
        FetchNewsAsyncTask task = new FetchNewsAsyncTask();
        task.execute();


        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, headlines);

        ListView listView = (ListView) findViewById(R.id.list);

        //adapter for the links, could be used to create an in app browser
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(links.get(position).toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        accel = 0.00f;
        accelCurrent = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.feed_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add_twitter:
                Intent twitterIntent = new Intent(this, TwitterActivity.class);
                startActivityForResult(twitterIntent, TWITTER_CODE);
                return true;
            case R.id.menu_item_options:
                startActivity(new Intent(this, Options.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class FetchNewsAsyncTask extends AsyncTask<Void,Void,HashMap<String, String>> {
        protected HashMap<String, String> doInBackground(Void ... params) {
            HashMap<String, String> things = PCWorldParse.getPCWorldNews();
            return things;
        }

        protected void onPostExecute(HashMap<String,String> things) {
            Set<String> keys = things.keySet();
            for(String s : keys) {
                headlines.add(s);
                links.add(things.get(s));
            }
            adapter.notifyDataSetChanged();
        }
    }
     private final SensorEventListener sensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (sharedPref.getBoolean("Shake", true)){
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                accelLast = accelCurrent;
                accelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
                float delta = accelCurrent - accelLast;
                accel = accel * 0.9f + delta; // perform low-cut filter
                if (accel > 12) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_SHORT);
                    toast.show();
                    task = new FetchNewsAsyncTask();
                    task.execute();
                    toast = Toast.makeText(getApplicationContext(), "Task has been executed", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
