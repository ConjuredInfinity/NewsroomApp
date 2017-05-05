package com.example.anthony.thenewsroom;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by cameronjackson on 5/5/17.
 */

public class ViewerActivity extends SingleFragmentActivity {

    private static String VIEWER_URL = "viewerStringURL";

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(VIEWER_URL);
        return ViewerFragment.newInstance(url);
    }

    public static Intent newIntent(Context ctx, String url) {
        Intent storyIntent = new Intent(ctx, ViewerActivity.class);
        storyIntent.putExtra(VIEWER_URL, url);
        return storyIntent;
    }
}
