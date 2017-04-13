package com.example.anthony.thenewsroom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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

        FetchPCWorldNewsAsyncTask task = new FetchPCWorldNewsAsyncTask();
        task.execute();


        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, headlines);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(links.get(position).toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });*/
    }

    public class FetchPCWorldNewsAsyncTask extends AsyncTask<Void,Void,List> {
        protected List<String> doInBackground(Void ... params) {
            List things = PCWorldParse.getPCWorldNews();
            return things;
        }

        protected void onPostExecute(List list) {
            for(int i = 0; i < list.size(); i++) {
                headlines.add(list.get(i).toString());
            }
            adapter.notifyDataSetChanged();
        }
    }
}
