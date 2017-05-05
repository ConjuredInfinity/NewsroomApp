package com.example.anthony.thenewsroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by cameronjackson on 5/5/17.
 */

public class ViewerFragment extends Fragment {


    private WebView webview;
    private ProgressDialog progressBar;

    private String urlString;


    public static ViewerFragment newInstance(String url) {

        Bundle args = new Bundle();

        ViewerFragment fragment = new ViewerFragment();
        fragment.setArguments(args);
        fragment.urlString = url;
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
        webview.loadUrl(urlString);


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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                startActivity(browserIntent);
                return true;
            default:
                return false;
        }

    }
}
