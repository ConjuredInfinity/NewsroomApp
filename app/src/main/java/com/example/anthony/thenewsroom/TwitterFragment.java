package com.example.anthony.thenewsroom;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anthony.thenewsroom.model.RssSource;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class TwitterFragment extends Fragment {

    public static final String TWITTER_RSS_CREATED = "twitterRssCreated";

    private Button mSaveButton;
    private Button mCancelButton;

    private EditText mNameTextView;
    private EditText mTermTextView;


    public TwitterFragment() {
        // do nothing
    }

    public static TwitterFragment newInstance() {
        TwitterFragment fragment = new TwitterFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_twitter, container, false);

        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mCancelButton = (Button) view.findViewById(R.id.cancel_button);

        mNameTextView = (EditText) view.findViewById(R.id.rss_name);
        mTermTextView = (EditText) view.findViewById(R.id.twitter_term);

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
                    Toast.makeText(TwitterFragment.this.getContext(), "Name cannot be empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String term = mTermTextView.getText().toString();
                if (term.trim().isEmpty()) {
                    // term cannot be empty
                    Toast.makeText(TwitterFragment.this.getContext(), "Name cannot be empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                // generate the twitter rss url and create the rss item
                String twitterUrl = "https://twitrss.me/twitter_search_to_rss/?term=" + term;
                RssSource rss = new RssSource(name, twitterUrl);

                // create the intent with the new rss feed
                Intent intent = new Intent();
                intent.putExtra(TWITTER_RSS_CREATED, rss);

                // end this activity with the OK result
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();


            }
        });


        return view;

    }
}
