package com.example.anthony.thenewsroom;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.anthony.thenewsroom.model.NewsItem;

/**
 * Created by cameronjackson on 5/5/17.
 */

public class ViewerActivity extends SingleFragmentActivity {

    private static String VIEWER_ITEM = "viewerStringURL";

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        NewsItem item = (NewsItem) intent.getSerializableExtra(VIEWER_ITEM);
        return ViewerFragment.newInstance(item);
    }

    public static Intent newIntent(Context ctx, NewsItem item) {
        Intent storyIntent = new Intent(ctx, ViewerActivity.class);
        storyIntent.putExtra(VIEWER_ITEM, item);
        return storyIntent;
    }
}
