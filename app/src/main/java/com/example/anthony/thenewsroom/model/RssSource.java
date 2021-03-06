package com.example.anthony.thenewsroom.model;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by cameronjackson on 5/1/17.
 */

public class RssSource extends RealmObject implements Serializable {

    public static final String RSS_CREATED = "twitterRssCreated";

    private String name;
    private String rssUrl;
    private Date created;


    /// Default construct that does nothing
    public RssSource() {}

    public RssSource(String name, String rss) {
        super();

        this.name = name;
        this.rssUrl = rss;

        created = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass()))
            return false;
        RssSource source = (RssSource) obj;
        return rssUrl.equals(source.rssUrl);
    }
}
