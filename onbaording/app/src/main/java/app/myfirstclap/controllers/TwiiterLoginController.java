package app.myfirstclap.controllers;

import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.R;
import app.myfirstclap.interfaces.SocialCallbacks;

/**
 * Created by TGT on 11/8/2017.
 */

public final class TwiiterLoginController {

  public static void login(final MyFirstClapApplication application, TwitterLoginButton loginbutton,
                           final SocialCallbacks
                                   twittercallback) {
    loginbutton.setCallback(new Callback<TwitterSession>() {
      @Override
      public void success(Result<TwitterSession> result) {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterCore.getInstance().getApiClient(session).getAccountService().verifyCredentials(false,
                false, true).enqueue(new Callback<User>() {
          @Override
          public void success(Result<User> result) {


          }

          @Override
          public void failure(TwitterException exception) {
            Toast.makeText(application.getApplicationContext(), application
                            .getApplicationContext().getResources().getString(R.string.error_fail),
                    Toast
                            .LENGTH_SHORT).show();

          }
        });

      }

      @Override
      public void failure(TwitterException exception) {
        Toast.makeText(application.getApplicationContext(), application
                        .getApplicationContext().getResources().getString(R.string.error_fail),
                Toast
                        .LENGTH_SHORT).show();

      }
    });
  }


}
