package com.example.anthony.thenewsroom.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by cameronjackson on 5/1/17.
 */

public class RssItem extends RealmObject implements Serializable {

    private String name;
    private String rssUrl;

    public RssItem(String name, String rss) {
        super();

        this.name = name;
        this.rssUrl = rss;
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
}
