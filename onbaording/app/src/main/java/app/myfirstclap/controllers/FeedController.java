package app.myfirstclap.controllers;

import android.app.ProgressDialog;
import android.widget.Toast;

import java.io.IOException;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.interfaces.BaseRetrofitCallback;
import app.myfirstclap.interfaces.FacebookFeedsCallback;
import app.myfirstclap.model.FacebookFeed;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TGT on 12/13/2017.
 */

public final class FeedController {


  public static void getFeeds(MyFirstClapApplication application, String pageid, final
  FacebookFeedsCallback facebookfeedsCallback) {
    application.feedService.getFeeds(pageid).enqueue(new Callback<FacebookFeed>() {
      @Override
      public void onResponse(Call<FacebookFeed> call, Response<FacebookFeed> response) {
        FacebookFeed facebookfeed = response.body();


        facebookfeedsCallback.successCallBack(facebookfeed);
      }

      @Override
      public void onFailure(Call<FacebookFeed> call, Throwable t) {

      }
    });
  }

  public static void loadMoreFeeds(MyFirstClapApplication application, String pageid, String
          aftercursor, final
                                   FacebookFeedsCallback facebookfeedsCallback) {
    application.feedService.loadMoreFeeds(pageid, aftercursor).enqueue(new Callback<FacebookFeed>() {
      @Override
      public void onResponse(Call<FacebookFeed> call, Response<FacebookFeed> response) {
        FacebookFeed facebookfeed = response.body();
        facebookfeedsCallback.successCallBack(facebookfeed);
      }

      @Override
      public void onFailure(Call<FacebookFeed> call, Throwable t) {

      }
    });
  }


  public static void getTopicFeeds(final MyFirstClapApplication myapplication, final ProgressDialog dialog, String request, final BaseRetrofitCallback baseretrofitcallback) {
    myapplication.feedService.topicFeeds(request).enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
          try {
            baseretrofitcallback.Success(response.body().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        } else {
          dialog.dismiss();
          Toast.makeText(myapplication.getApplicationContext(), "Something went  wrong",
                  Toast
                          .LENGTH_SHORT)
                  .show();
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        dialog.dismiss();
        if (t instanceof IOException) {
          Toast.makeText(myapplication.getApplicationContext(), "Connection TimeOut",
                  Toast
                          .LENGTH_SHORT)
                  .show();
        } else {
          Toast.makeText(myapplication.getApplicationContext(), "Something went  wrong",
                  Toast
                          .LENGTH_SHORT)
                  .show();
        }
      }
    });
  }
}
