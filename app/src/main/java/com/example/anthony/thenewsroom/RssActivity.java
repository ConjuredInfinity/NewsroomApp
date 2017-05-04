package com.example.anthony.thenewsroom;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by cameronjackson on 5/1/17.
 */

public class RssActivity extends SingleFragmentActivity {

    private static String RSS_CREATE_TYPE = "rssCreateType";

    private Spinner spinner;

    private ArrayAdapter<String> adapter;


    @Override
    protected Fragment createFragment() {
        Intent rssIntent = getIntent();
        RssType rssType = (RssType) rssIntent.getSerializableExtra(RSS_CREATE_TYPE);
        return RssFragment.newInstance(rssType);
    }


    public static Intent newIntent(Context ctx, RssType rssType) {
        Intent storyIntent = new Intent(ctx, RssActivity.class);
        storyIntent.putExtra(RSS_CREATE_TYPE, rssType);
        return storyIntent;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.add_menu, menu);
//
//        MenuItem item = menu.findItem(R.id.spinner);
//        spinner = (Spinner) MenuItemCompat.getActionView(item);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.add_entries));
//        spinner.setAdapter(adapter);
//
//
//        return true;
//    }
}
