package com.example.anthony.thenewsroom.model;

import java.io.Serializable;

/**
 * Created by cameronjackson on 5/4/17.
 */

public class NewsItem implements Serializable {

    private String title;
    private String link;

    public NewsItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

}
