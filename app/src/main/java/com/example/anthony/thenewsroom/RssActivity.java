package com.example.anthony.thenewsroom;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by cameronjackson on 5/1/17.
 */

public class RssActivity extends SingleFragmentActivity {

    private static String RSS_CREATE_TYPE = "rssCreateType";

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

}
