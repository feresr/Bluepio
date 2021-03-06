package com.feresr.bluepio.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feresr.bluepio.Constants;
import com.feresr.bluepio.EndlessScrollListener;
import com.feresr.bluepio.TweetLoader;
import com.feresr.bluepio.adapters.TweetsAdapter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import java.util.List;

public class ProfileFragment extends ListFragment implements TweetLoader {

    private TweetsAdapter adapter;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TweetsAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setListAdapter(adapter);
        if (adapter.getCount() == 0) {
            loadTweets(Constants.TWEET_AMOUNT);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DashboardActivity) getActivity()).getSupportActionBar().setTitle("Tweets");
        getListView().setOnScrollListener(new EndlessScrollListener(this));
    }

    @Override
    public void loadTweets(int amount) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();

        /**
         * See method documentation and accepted parameters here:
         * https://dev.twitter.com/rest/reference/get/statuses/user_timeline
         */
        Tweet tweet = adapter.getLastTweet();
        statusesService.userTimeline(null, //User id
                null, //User screen name
                amount, //Number of tweets to retrieve, null = 20;
                null, //Since id
                tweet != null ? tweet.id - 1 : null, //Max id
                null, //Trim user (boolean)
                null, //Exclude reply (boolean)
                null, //Contributor details (boolean)
                null, //include_rts (boolean)
                new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> listResult) {
                        adapter.setTweets(listResult.data);
                    }

                    @Override
                    public void failure(TwitterException e) {

                    }
                });
    }
}
