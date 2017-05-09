package com.example.anthony.thenewsroom;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class AddRssActivity extends SingleFragmentActivity {

    private static String RSS_CREATE_TYPE = "rssCreateType";

    private Spinner spinner;

    private ArrayAdapter<String> adapter;


    @Override
    protected Fragment createFragment() {
        Intent rssIntent = getIntent();
        RssType rssType = (RssType) rssIntent.getSerializableExtra(RSS_CREATE_TYPE);
        return AddRssFragment.newInstance(rssType);
    }


    public static Intent newIntent(Context ctx, RssType rssType) {
        Intent storyIntent = new Intent(ctx, AddRssActivity.class);
        storyIntent.putExtra(RSS_CREATE_TYPE, rssType);
        return storyIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.add_entries));
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment = null;
                RssType rssType;

                // handle the manual adding fragments
                if (position < 3) {

                    switch (position){
                        case 0:
                            rssType = RssType.URL;
                            break;
                        case 1:
                            rssType = RssType.TWITTER;
                            break;
                        case 2:
                            rssType = RssType.REDDIT;
                            break;
                        default:rssType = RssType.URL;
                    }

                    fragment = AddRssFragment.newInstance(rssType);
                } else {
                    // defaults fragment
                    fragment = DefaultFeedsFragment.newInstance();
                }


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return true;
    }
}
