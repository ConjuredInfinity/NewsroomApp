package com.example.anthony.thenewsroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anthony.thenewsroom.model.NewsItem;
import com.example.anthony.thenewsroom.model.RssSource;
import com.example.anthony.thenewsroom.service.RssService;
import com.example.anthony.thenewsroom.viewholder.NewsItemViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class DisplayNewsActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final int RSS_CODE = 829;

    public RecyclerView recyclerView;

    private Boolean nightModeEnabled;

    private NewsAdapter adapter;
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
        Realm.init(getApplicationContext());


        adapter = new NewsAdapter();
        recyclerView = (RecyclerView) findViewById(R.id.news_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        sharedPref = getSharedPreferences("com.example.anthony.thenewsroom", MODE_PRIVATE);

        // set and check night mode
        nightModeEnabled = sharedPref.getBoolean("Nightmode", false);
        if (nightModeEnabled) recyclerView.setBackgroundColor(Color.BLACK);

        sharedPref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("Nightmode")) {
                    // toggle nightmode
                    nightModeEnabled = sharedPref.getBoolean("Nightmode", false);
                    if (nightModeEnabled) { recyclerView.setBackgroundColor(Color.BLACK); } else { recyclerView.setBackgroundColor(Color.WHITE); }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        //async task to collect all news
        FetchNewsAsyncTask task = new FetchNewsAsyncTask();
        task.execute();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        accel = 0.00f;
        accelCurrent = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // set and check night mode
        nightModeEnabled = sharedPref.getBoolean("Nightmode", false);
        if (nightModeEnabled) recyclerView.setBackgroundColor(Color.BLACK);
        adapter.notifyDataSetChanged();
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
            case R.id.menu_add_rss:
                Intent rssIntent = AddRssActivity.newIntent(DisplayNewsActivity.this, RssType.URL);
                startActivityForResult(rssIntent, RSS_CODE);
                return true;
            case R.id.menu_remove_rss:
                startActivity(new Intent(this, RemoveRssActivity.class));
                return true;
            case R.id.menu_item_options:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == RSS_CODE) {
            // save added rss feed
            RssSource source = (RssSource) data.getSerializableExtra(RssSource.RSS_CREATED);
            RssService.AddRss(source);
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsItemViewHolder> {

        private List<NewsItem> newsItems;

        NewsAdapter() {
            newsItems = new ArrayList<>();
        }

        void setItems(List<NewsItem> items) {
            newsItems.clear();
            newsItems.addAll(items);
            notifyDataSetChanged();
        }


        @Override
        public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // handle making the night mode cell
            int resourceId = viewType == 22 ? R.layout.news_item : R.layout.news_item_night;

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(resourceId, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get the selected News Item
                    int position = recyclerView.getChildAdapterPosition(v);
                    NewsItem item = newsItems.get(position);
                    Intent viewerIntent = ViewerActivity.newIntent(DisplayNewsActivity.this, item);
                    startActivity(viewerIntent);
                }
            });
            return new NewsItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsItemViewHolder holder, int position) {
            holder.bindItem(newsItems.get(position));
        }

        @Override
        public int getItemViewType(int position) {
            return !nightModeEnabled ? 22 : 23;
        }

        @Override
        public int getItemCount() {
            return newsItems.size();
        }
    }

    private class FetchNewsAsyncTask extends AsyncTask<Void,Void,List<NewsItem>> {
        protected List<NewsItem> doInBackground(Void ... params) {
            return RssParser.fetchRssFeeds();
        }

        protected void onPostExecute(List<NewsItem> things) {
            DisplayNewsActivity.this.adapter.setItems(things);
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
