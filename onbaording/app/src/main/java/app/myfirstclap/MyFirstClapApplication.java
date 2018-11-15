package app.myfirstclap;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.theartofdev.fastimageloader.FastImageLoader;
import com.theartofdev.fastimageloader.adapter.ImgIXAdapter;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import app.myfirstclap.service.FeedService;
import app.myfirstclap.service.RetrofitService;
import app.myfirstclap.service.TopicService;
import app.myfirstclap.service.UserService;
import au.com.dstil.atomicauth.IAuthFailureCallback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TGT on 11/9/2017.
 */

public class MyFirstClapApplication extends Application implements IAuthFailureCallback {

  protected MyFirstClapApplication application;
  private static final int RC_SIGN_IN_FB = 1916;
  public UserService userService;
  public FeedService feedService;
  public TopicService topicservice;
  public RetrofitService retrofitService;
  ProgressDialog dialog;

  // Updated your class body:
  @Override
  public void onCreate() {
    super.onCreate();
    // Initialize the SDK before executing any other operations,
    facebookInit();
    retrofitInit();
    Twitter.initialize(this);
    Fresco.initialize(getApplicationContext());


    FastImageLoader
            .init(this)
            .setDefaultImageServiceAdapter(new ImgIXAdapter())
            .setWriteLogsToLogcat(true)
            .setLogLevel(Log.DEBUG)
            .setDebugIndicator(true);


  }

  public MyFirstClapApplication returnInstance() {
    return application;
  }

  public void facebookInit() {
    FacebookSdk.sdkInitialize(getApplicationContext(), RC_SIGN_IN_FB);
    AppEventsLogger.activateApp(this);

  }

  public void twitterInit() {
    TwitterConfig config = new TwitterConfig.Builder(getApplicationContext())
            .logger(new DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string
                    .com_twitter_sdk_android_CONSUMER_KEY), getResources().getString(R.string
                    .com_twitter_sdk_android_CONSUMER_SECRET)))
            .debug(true)
            .build();
    Twitter.initialize(config);
  }

  @Override
  public void onAuthFailure(String s) {

  }

  public void retrofitInit() {
    Retrofit api = new Retrofit
            .Builder()
            .baseUrl(getResources().getString(R.string.api_base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    userService = new UserService(api);
    retrofitService = new RetrofitService(api);
    feedService = new FeedService(api);
    topicservice = new TopicService(api);


  }


  public boolean isConnected() {
    ConnectivityManager cm =
            (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return activeNetwork != null &&
            activeNetwork.isConnectedOrConnecting();
  }

}
