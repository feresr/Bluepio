package com.feresr.bluepio;

import android.widget.AbsListView;

/**
 * Created by fernando.raviola on 1/22/15.
 */
public class EndlessScrollerListener implements AbsListView.OnScrollListener {

    private boolean scrolledOut = false;
    private TweetLoader loader;

    public EndlessScrollerListener(TweetLoader loader) {
        this.loader = loader;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && scrolledOut) {
            //load more tweets
            scrolledOut = false;
            loader.loadTweets(10);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        scrolledOut = (firstVisibleItem + visibleItemCount) == totalItemCount;
    }
}
