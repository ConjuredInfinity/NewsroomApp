package com.example.anthony.thenewsroom.viewholder;

import android.support.v7.widget.RecyclerView;
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

    public NewsItemViewHolder(View view) {
        super(view);

        this.textView = (TextView) view.findViewById(R.id.info_text);
    }

    public void bindItem(NewsItem newsItem) {
        textView.setText(newsItem.getTitle());
    }


}
