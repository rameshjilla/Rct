package app.myfirstclap.service;

import app.myfirstclap.model.Topic;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by TGT on 1/10/2018.
 */

public class TopicService {
  private final ServiceDefinition service;

  public TopicService(Retrofit retrofit) {
    service = retrofit.create(ServiceDefinition.class);
  }


  public Call<Topic.TopicResponse> getTopics() {

    final Call<Topic.TopicResponse> call = service.getTopics();
    return call;
  }

  public Call<ResponseBody> newGetTopics() {

    final Call<ResponseBody> call = service.newGetTopics();
    return call;
  }


  interface ServiceDefinition {

    @POST("gettopics.php")
    Call<Topic.TopicResponse> getTopics();

    @GET("topics")
    Call<ResponseBody> newGetTopics();


  }
}