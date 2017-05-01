package com.example.anthony.thenewsroom;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static android.app.Activity.RESULT_CANCELED;

public class TwitterFragment extends Fragment {

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
                // TODO: Validate and add the rss feed to the database
            }
        });


        return view;

    }
}
