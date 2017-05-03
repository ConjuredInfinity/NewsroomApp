package com.example.anthony.thenewsroom;

import com.example.anthony.thenewsroom.model.RssSource;
import com.example.anthony.thenewsroom.service.RssService;

/**
 * This class is where we will put all the urls for the RSS feeds we are using
 */

public class RssFeedUrls {
    public static final RssSource pcworld = new RssSource("PCWorld", "http://feeds.pcworld.com/pcworld/latestnews");
    public static final RssSource aljazeera = new RssSource("Aljazeera", "http://america.aljazeera.com/content/ajam/articles.rss");
    public static final RssSource et = new RssSource("ET", "http://entertainmentweekly.tumblr.com/rss");
    public static final RssSource cnn = new RssSource("CNN", "http://rss.cnn.com/rss/cnn_topstories.rss");
    public static final RssSource thr = new RssSource("THR", "http://feeds.feedburner.com/thr/news");
    public static final RssSource bloomberg = new RssSource("Bloomberg", "https://www.bloomberg.com/feeds/podcasts/etf_report.xml");
    public static final RssSource espn = new RssSource("ESPN", "http://www.espn.com/espn/rss/news");
    public static final RssSource nfl = new RssSource("NFL", "http://www.nfl.com/rss/rsslanding?searchString=home");
    public static final RssSource yahooS = new RssSource ("YahooS", "https://www.yahoo.com/news/rss/sports");
    public static final RssSource gizmodo = new RssSource("Gizmodo", "http://feeds.gawker.com/gizmodo/full");
    public static final RssSource nyt = new RssSource("NYT", "http://rss.nytimes.com/services/xml/rss/nyt/Books.xml");
    public static final RssSource entrepreneur = new RssSource("Entrepreneur", "http://feeds.feedburner.com/entrepreneur/latest");
    public static final RssSource variety = new RssSource("Variety", "http://variety.com/static-pages/rss-2/");
}
