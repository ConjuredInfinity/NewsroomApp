package com.example.anthony.thenewsroom.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.example.anthony.thenewsroom.R;
import com.example.anthony.thenewsroom.model.NewsItem;

/**
 * Created by cameronjackson on 5/4/17.
 */

public class NewsItemViewHolder extends RecyclerView.ViewHolder {

//    private NewsItem newsItem;

    private TextView textView;
    private TextView dateView;

    public NewsItemViewHolder(View view) {
        super(view);

        this.textView = (TextView) view.findViewById(R.id.info_text);
        this.dateView = (TextView) view.findViewById(R.id.rss_date);
    }

    public void bindItem(NewsItem newsItem) {
        textView.setText(newsItem.getTitle());

        long now = System.currentTimeMillis();
        CharSequence string = DateUtils.getRelativeTimeSpanString(newsItem.getPubDate().getTime(), now, DateUtils.SECOND_IN_MILLIS);
        dateView.setText(string+"\n\n");



    }


}
