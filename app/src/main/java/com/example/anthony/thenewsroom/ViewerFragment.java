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
                new FetchTLDRTask().execute();

            default:
                return false;
        }

    }

    private class FetchTLDRTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (progressBar.isShowing())
                progressBar.dismiss();


            new AlertDialog.Builder(getActivity())
                    .setTitle("TLDR")
                    .setMessage("Are you sure you want to delete this entry?")
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
