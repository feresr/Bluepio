package com.feresr.bluepio.dashboard;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.feresr.bluepio.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class ComposeFragment extends Fragment {

    private EditText statusEditText;
    private StatusesService statusesService;
    private final int IMAGE_PICK = 1;
    private Uri image_uri;
    public ComposeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compose, container, false);
        statusEditText = (EditText) view.findViewById(R.id.tweet_status);
        view.findViewById(R.id.tweet_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                statusesService.update(statusEditText.getText().toString(), null, null, null, null, null, null, null, new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> tweetResult) {
                        Toast.makeText(getActivity(), "TWEET POSTED!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(TwitterException e) {

                    }
                });
            }
        });
        view.findViewById(R.id.add_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == IMAGE_PICK && data != null && data.getData() != null) {
            image_uri = data.getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

        statusesService = twitterApiClient.getStatusesService();
    }
}
