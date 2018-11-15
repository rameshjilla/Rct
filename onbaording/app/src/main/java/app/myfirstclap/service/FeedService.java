package app.myfirstclap.service;

import org.json.JSONException;
import org.json.JSONObject;

import app.myfirstclap.model.FacebookFeed;
import au.com.dstil.atomicauth.annotation.Authentication;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static au.com.dstil.atomicauth.annotation.Authentication.Type.OAUTH;

/**
 * Created by admin on 22/12/2017.
 */

public class FeedService {
  private final FeedService.ServiceDefinition feedservice;

  public FeedService(Retrofit retrofit) {
    feedservice = retrofit.create(FeedService.ServiceDefinition.class);
  }

  public Call<FacebookFeed> getFeeds(String pageid) {
    JSONObject object = new JSONObject();
    try {
      object.put("Page_Id", pageid);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), object
            .toString());
    final Call<FacebookFeed> FacebookFeed = feedservice.getFeeds(requestBody);
    return FacebookFeed;
  }

  public Call<FacebookFeed> loadMoreFeeds(String pageid, String aftercursor) {
    JSONObject object = new JSONObject();
    try {
      object.put("Page_Id", pageid);
      object.put("after", aftercursor);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), object
            .toString());
    final Call<FacebookFeed> FacebookFeed = feedservice.loadMoreFeeds(requestBody);
    return FacebookFeed;
  }

  public Call<ResponseBody> topicFeeds(String request) {

    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), request
    );
    final Call<ResponseBody> TopicFeeds = feedservice.topicFeeds(requestBody);
    return TopicFeeds;
  }

  interface ServiceDefinition {
    @Authentication(OAUTH)
    @POST("getfacebookfeeds.php")
    Call<FacebookFeed> getFeeds(@Body RequestBody body);

    @Authentication(OAUTH)
    @POST("loadmorefeeds.php")
    Call<FacebookFeed> loadMoreFeeds(@Body RequestBody body);


    @POST("topic_feed")
    Call<ResponseBody> topicFeeds(@Body RequestBody body);

  }
}
