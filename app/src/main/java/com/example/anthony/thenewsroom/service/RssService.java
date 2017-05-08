package com.example.anthony.thenewsroom.service;

import com.example.anthony.thenewsroom.model.RssSource;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by cameronjackson on 5/1/17.
 */

public class RssService {


    /**
     * Adds a new rss item to the db
     * @param rss
     */
    public static void AddRss(RssSource rss) {
        Realm realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);

        // begin the write block
        realm.beginTransaction();
        realm.copyToRealm(rss);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Removes the rss url from the db
     * @param rssUrl
     */
    public static void RemoveRss(String rssUrl) {
        Realm realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);

        RssSource rssItem = realm.where(RssSource.class).equalTo("rssUrl", rssUrl).findFirst();

        // this rss feed does not exists in the db
        if (rssItem == null)
            return;

        realm.beginTransaction();
        rssItem.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    /**
     * Gets all of the rss items from the db
     * @return
     */
    public static List<RssSource> getRssItems() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<RssSource> results = realm.where(RssSource.class).findAll();
        List<RssSource> sources = realm.copyFromRealm(results);
        realm.close();
        return sources;
    }

}
