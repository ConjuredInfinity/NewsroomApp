package com.example.anthony.thenewsroom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.anthony.thenewsroom.model.NewsItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cameronjackson on 5/5/17.
 */

public class ViewerFragment extends Fragment {


    private WebView webview;
    private ProgressDialog progressBar;

    private NewsItem newsItem;


    public static ViewerFragment newInstance(NewsItem item) {

        Bundle args = new Bundle();

        ViewerFragment fragment = new ViewerFragment();
        fragment.setArguments(args);
        fragment.newsItem = item;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_viewer, container, false);


        webview = (WebView) view.findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing())
                    progressBar.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (progressBar != null && progressBar.isShowing())
                    progressBar.dismiss();
                progressBar = ProgressDialog.show(getActivity(), null, "Loading...");
            }
        });
        webview.loadUrl(newsItem.getLink());


        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.viewer_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.open_item:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.getLink()));
                startActivity(browserIntent);
                return true;
            case R.id.tldr_item:
                progressBar = ProgressDialog.show(getActivity(), null, "Loading...");
                new FetchTLDRTask().execute(newsItem.getLink());

            default:
                return false;
        }

    }

    private class FetchTLDRTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String jsonString = "";
            HttpURLConnection connection = null;



            try {
                String urlString = Uri.parse("https://rss-cmsc436-backend.herokuapp.com/tldr")
                        .buildUpon().appendQueryParameter("url", params[0])
                        .build().toString();

                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connection.getInputStream();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[1024];

                while ((bytesRead = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();

                jsonString = new String(outputStream.toByteArray());


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) { connection.disconnect(); };
            }

            JSONObject jObject = null;
            try {
                jObject = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String tldr = null;
            try {
                if (jObject != null) {
                    tldr = jObject.getString("sm_api_content");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return tldr;
        }


        @Override
        protected void onPostExecute(String s) {
            if (progressBar.isShowing())
                progressBar.dismiss();

            if (s == null) {
                return;
            }

            new AlertDialog.Builder(getActivity())
                    .setTitle("TLDR")
                    .setMessage(s)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
