package com.feresr.bluepio;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando.raviola on 1/21/15.
 */
public class TweetsAdapter extends BaseAdapter {

    private List<Tweet> tweets;
    private Context context;
    public TweetsAdapter(Context context) {
        super();
        this.context = context;
        tweets = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return tweets.size();
    }

    @Override
    public Object getItem(int position) {
        return tweets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new CompactTweetView(context, (Tweet) getItem(position), R.style.tw__TweetDarkStyle);
        } else {
            ((CompactTweetView) convertView).setTweet((Tweet) getItem(position));
        }
        return convertView;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
        this.notifyDataSetChanged();
    }
}
