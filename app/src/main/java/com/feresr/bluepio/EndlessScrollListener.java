package com.feresr.bluepio;

import android.widget.AbsListView;

public class EndlessScrollListener implements AbsListView.OnScrollListener {

    private boolean scrolledOut = false;
    private TweetLoader loader;

    public EndlessScrollListener(TweetLoader loader) {
        this.loader = loader;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && scrolledOut) {
            //load more tweets
            scrolledOut = false;
            loader.loadTweets(Constants.TWEET_AMOUNT);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        scrolledOut = (firstVisibleItem + visibleItemCount) == totalItemCount;
    }
}
