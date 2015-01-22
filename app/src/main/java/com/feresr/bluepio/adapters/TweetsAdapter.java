package com.feresr.bluepio.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.feresr.bluepio.R;
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
    }

    @Override
    public int getCount() {
        return tweets == null? 0 : tweets.size();
    }

    @Override
    public Object getItem(int position) {
        return tweets == null? null : tweets.get(position);
    }

    public Tweet getLastTweet() {
        if (tweets != null && !tweets.isEmpty()) {
            return tweets.get(tweets.size()-1);
        }
        return null;
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
        if (this.tweets == null) {
            this.tweets = tweets;
        } else {
            this.tweets = union(this.tweets, tweets);
        }
        this.notifyDataSetChanged();
    }

    private List<Tweet> union(final List<Tweet> list1, final List<Tweet> list2) {
        final ArrayList<Tweet> result = new ArrayList<>(list1);
        result.addAll(list2);
        return result;
    }
}
