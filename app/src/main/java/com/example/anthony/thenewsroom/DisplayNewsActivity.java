package com.example.anthony.thenewsroom;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DisplayNewsActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private List headlines;
    private List links;

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);

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
}
