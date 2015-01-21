package com.feresr.bluepio;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import java.util.List;

public class MyTweetsFragment extends ListFragment {

    private TweetsAdapter adapter;

    public MyTweetsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new TweetsAdapter(getActivity());
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DashboardActivity) getActivity()).getSupportActionBar().setTitle("Tweets");
        loadTweets(10);
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {

            private boolean scrolledOut = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && scrolledOut) {
                    //load more tweets
                    scrolledOut = false;
                    loadTweets(10);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                scrolledOut = (firstVisibleItem + visibleItemCount) == totalItemCount;
            }
        });
    }
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
                tweet != null? tweet.id : null, //Max id
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
