package com.example.anthony.thenewsroom.service;

import com.example.anthony.thenewsroom.model.RssItem;

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
    public static void AddRss(RssItem rss) {
        Realm realm = Realm.getDefaultInstance();

        // begin the write block
        realm.beginTransaction();
        realm.copyToRealm(rss);
        realm.commitTransaction();
    }

    /**
     * Removes the rss url from the db
     * @param rssUrl
     */
    public static void RemoveRss(String rssUrl) {
        Realm realm = Realm.getDefaultInstance();
        RssItem rssItem = realm.where(RssItem.class).equalTo("rssUrl", rssUrl).findFirst();

        // this rss feed does not exists in the db
        if (rssItem == null)
            return;

        realm.beginTransaction();
        rssItem.deleteFromRealm();
        realm.commitTransaction();
    }

    /**
     * Gets all of the rss items from the db
     * @return
     */
    public static List<RssItem> getRssItems() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RssItem> results = realm.where(RssItem.class).findAll();
        return realm.copyFromRealm(results);
    }

}
