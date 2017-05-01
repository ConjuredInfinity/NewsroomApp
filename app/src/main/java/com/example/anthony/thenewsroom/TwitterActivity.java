package com.example.anthony.thenewsroom;

import android.support.v4.app.Fragment;

/**
 * Created by cameronjackson on 5/1/17.
 */

public class TwitterActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return TwitterFragment.newInstance();
    }
}
