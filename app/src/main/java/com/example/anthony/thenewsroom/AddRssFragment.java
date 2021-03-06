package com.example.anthony.thenewsroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.thenewsroom.model.RssSource;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddRssFragment extends Fragment {


    private Button mSaveButton;
    private Button mCancelButton;

    private EditText mNameTextView;
    private EditText mTermTextView;
    private TextView mUrlTextView;

    private RssType rssType;


    public AddRssFragment() {
        // do nothing
    }

    public static AddRssFragment newInstance(RssType type) {
        AddRssFragment fragment = new AddRssFragment();
        fragment.rssType = type;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rss, container, false);

        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mCancelButton = (Button) view.findViewById(R.id.cancel_button);

        mNameTextView = (EditText) view.findViewById(R.id.rss_name);
        mTermTextView = (EditText) view.findViewById(R.id.twitter_term);
        mUrlTextView = (TextView) view.findViewById(R.id.rss_url_textview);

        // configure the hint text
        if (rssType == RssType.TWITTER) {
            mTermTextView.setHint(getActivity().getResources().getString(R.string.twitter_term_placeholder));
        } else if (rssType == RssType.REDDIT) {
            mTermTextView.setHint("SubReddit");
            mUrlTextView.setText("SubReddit");
        } else {
            mTermTextView.setHint(getActivity().getResources().getString(R.string.rss_term_placeholder));
            mUrlTextView.setText("URL:");
        }

        // add click listeners
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setResult(RESULT_CANCELED);
                getActivity().finish();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mNameTextView.getText().toString();
                if (name.trim().isEmpty()) {
                    // name cannot be empty
                    Toast.makeText(AddRssFragment.this.getContext(), "Name cannot be empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String term = mTermTextView.getText().toString();
                if (term.trim().isEmpty()) {
                    // term cannot be empty
                    Toast.makeText(AddRssFragment.this.getContext(), "Term cannot be empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                // generate the twitter rss url and create the rss item
                String url;
                if (rssType == RssType.TWITTER) {
                    url = "https://queryfeed.net/twitter?q=" + term + "&title-type=tweet-text-full&geocode=&omit-direct=on&omit-retweets=on&attach=on";
                } else if(rssType == RssType.REDDIT) {
                    url = "https://reddit.com/r/" + term + "/.rss";
                } else {
                    url = term;
                }
                RssSource rss = new RssSource(name, url);

                // create the intent with the new rss feed
                Intent intent = new Intent();
                intent.putExtra(RssSource.RSS_CREATED, rss);

                // end this activity with the OK result
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();


            }
        });


        return view;

    }
}
