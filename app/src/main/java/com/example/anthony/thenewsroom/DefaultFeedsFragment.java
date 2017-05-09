package com.example.anthony.thenewsroom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

/**
 * Created by cameronjackson on 5/9/17.
 */

public class DefaultFeedsFragment extends Fragment {

    private Button button;
    private MyCustomAdapter dataAdapter;
    final Hashtable checked = new Hashtable();


    public static DefaultFeedsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DefaultFeedsFragment fragment = new DefaultFeedsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remove, container, false);

        button = (Button) view.findViewById(R.id.findSelected);
        button.setText("Add");

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<RssSource> rssList = dataAdapter.rssList;
                for(int i=0;i<rssList.size();i++) {
                    if (checked.containsKey(rssList.get(i).getName())){
                        RssSource rss = rssList.get(i);
                        RssService.AddRss(rss);
//                        RssService.RemoveRss(rss.getRssUrl());
                    }
                }
                getActivity().finish();

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayListView();
    }

    private void displayListView() {

        List<RssSource> defaults = RssFeedUrls.getDefaultFeeds();
        List<RssSource> current = RssService.getRssItems();
        List<RssSource> filtered = new ArrayList<>();

        for (RssSource source: defaults) {
            if (!current.contains(source))
                filtered.add(source);
        }


        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(getContext(), R.layout.rss_info, filtered);
        ListView listView = (ListView) getView().findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                RssSource rss = (RssSource) parent.getItemAtPosition(position);
                Toast.makeText(DefaultFeedsFragment.this.getActivity(),
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

            MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));


            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) DefaultFeedsFragment.this.getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.rss_info, null);

                holder = new MyCustomAdapter.ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);
                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        checked.put(cb.getText(), cb.isChecked());
                    }
                });
            } else {
                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();
            }

            RssSource rss = rssList.get(position);
            holder.code.setText(" (" + rss.getRssUrl() + ")");
            holder.name.setText(rss.getName());
            if (checked.containsKey(rss.getName()))
                holder.name.setChecked((Boolean) checked.get(rss.getName()));
            else
                holder.name.setChecked(false);


            return convertView;

        }

    }

}
