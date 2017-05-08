package com.example.anthony.thenewsroom.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cameronjackson on 5/4/17.
 */

public class NewsItem implements Serializable {

    private String title;
    private String link;
    private Date pubDate;

    public NewsItem(String title, String link, Date pubDate) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Date getPubDate() {
        return pubDate;
    }
}
