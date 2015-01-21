package com.feresr.bluepio;

/**
 * Created by fernando.raviola on 1/21/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class LogInFragment extends Fragment {
    private TwitterLoginButton loginButton;

    private LogInCallbacks callbacks;

    public interface LogInCallbacks {
        public void onSuccessfulLogin(TwitterSession session);
    }

    public LogInFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbacks = (LogInCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getClass().getSimpleName() + "must implement the" +
                    " LogInCallbacks interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = (TwitterLoginButton) rootView.findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Toast.makeText(getActivity(), "Authorization succeeded", Toast.LENGTH_SHORT).show();
                callbacks.onSuccessfulLogin(result.data);

            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getActivity(), "Authorization failed :(", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}