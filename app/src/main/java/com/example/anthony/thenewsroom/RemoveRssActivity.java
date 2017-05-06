package com.example.anthony.thenewsroom;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.thenewsroom.model.RssSource;
import com.example.anthony.thenewsroom.service.RssService;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class RemoveRssActivity extends AppCompatActivity {
    MyCustomAdapter dataAdapter = null;
    final Hashtable checked = new Hashtable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.remove);

         //Generate list View from ArrayList
         displayListView();

         checkButtonClick();
    }

    private void displayListView() {
        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.rss_info, RssService.getRssItems());
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                RssSource rss = (RssSource) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + rss.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }


    private class MyCustomAdapter extends ArrayAdapter<RssSource> {

        private ArrayList<RssSource> rssList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               List<RssSource> rssList) {
            super(context, textViewResourceId, rssList);
            this.rssList = new ArrayList<RssSource>();
            this.rssList.addAll(rssList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));


            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.rss_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);
                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        checked.put(cb.getText(), cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            RssSource rss = rssList.get(position);
            holder.code.setText(" (" +  rss.getRssUrl() + ")");
            holder.name.setText(rss.getName());
            if (checked.containsKey(rss.getName()))
                holder.name.setChecked((Boolean) checked.get(rss.getName()));
            else
                holder.name.setChecked(false);


            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<RssSource> rssList = dataAdapter.rssList;
                for(int i=0;i<rssList.size();i++) {
                    if (checked.containsKey(rssList.get(i).getName())){
                        RssSource rss = rssList.get(i);
                        RssService.RemoveRss(rss.getRssUrl());
                    }
                }
            finish();

            }
        });

    }

}

