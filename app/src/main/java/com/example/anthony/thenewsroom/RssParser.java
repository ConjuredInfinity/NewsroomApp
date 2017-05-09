package com.example.anthony.thenewsroom;

import android.util.Log;

import com.example.anthony.thenewsroom.model.NewsItem;
import com.example.anthony.thenewsroom.model.RssSource;
import com.example.anthony.thenewsroom.service.RssService;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anthony on 4/11/2017.
 */

class RssParser {

    static List<NewsItem> fetchRssFeeds() {
        List<String> headlines = new ArrayList<>();
        List<String> links = new ArrayList<>();
        List<Date> dates = new ArrayList<>();

        List<NewsItem> stories = new ArrayList<>();

        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);



        // the feeds to fetch
        List<RssSource> rssFeeds;

        rssFeeds = RssService.getRssItems();
        for (int i = 0; i < rssFeeds.size(); i++) {
            Log.d("Loading in: ", rssFeeds.get(i).getName());
            try {
                URL url = new URL(rssFeeds.get(i).getRssUrl());

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(url.openConnection().getInputStream(), "UTF_8");

                boolean insideItem = false;
                NewsItem item= new NewsItem();

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            item = new NewsItem();
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem)
                                item.setTitle(xpp.nextText());
                                //headlines.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem)
                                item.setLink(xpp.nextText());
                                //links.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            if(insideItem)
                                item.setPubDate(formatter.parse(xpp.nextText()));
                                //dates.add(formatter.parse(xpp.nextText()));

                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                        stories.add(item);
                    }

                    eventType = xpp.next();
                }

            } catch (XmlPullParserException | IOException | ParseException e) {
                e.printStackTrace();
            }
            //Log.i("error boi", "ran " + headlines.size());
            /*for (int j = 0; j < headlines.size(); j++) {
                NewsItem item = new NewsItem(headlines.get(j), links.get(j), dates.get(j));
                stories.add(item);
            }*/
        }
        return stories;
    }
}
