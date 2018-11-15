package app.myfirstclap.service;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.myfirstclap.model.BaseResponseBody;
import app.myfirstclap.model.BaseResponseBodyArray;
import app.myfirstclap.model.User;
import au.com.dstil.atomicauth.annotation.Authentication;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static au.com.dstil.atomicauth.annotation.Authentication.Type.OAUTH;

/**
 * Created by TGT on 11/28/2017.
 */

public class UserService {
  private final ServiceDefinition service;

  public UserService(Retrofit retrofit) {
    service = retrofit.create(ServiceDefinition.class);
  }


  public Call<BaseResponseBody> loginUser(final User user) {
    Gson gson = new Gson();
    JSONObject object = null;
    try {
      object = new JSONObject(gson.toJson(user));
      object.remove("Id");

    } catch (JSONException e) {
      e.printStackTrace();
    }
    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
            object.toString());
    final Call<BaseResponseBody> call = service.login(requestBody);
    return call;
  }


  public Call<okhttp3.ResponseBody> newLoginUser(final User user) {
    Gson gson = new Gson();
    JSONObject object = null;
    try {
      object = new JSONObject(gson.toJson(user));
      object.remove("Id");

    } catch (JSONException e) {
      e.printStackTrace();
    }
    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
            object.toString());
    final Call<okhttp3.ResponseBody> call = service.newlogin(requestBody);
    return call;
  }

  public Call<BaseResponseBodyArray> addUserTopics(final String usertopicselection) {
    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
            usertopicselection);
    final Call<BaseResponseBodyArray> call = service.addUserTopics(requestBody);
    return call;
  }

  public Call<okhttp3.ResponseBody> newaddUserTopics(final String usertopicselection) {
    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
            usertopicselection);
    final Call<okhttp3.ResponseBody> call = service.newaddUserTopics(requestBody);
    return call;
  }

  public Call<okhttp3.ResponseBody> updateOnesignal(final String usertopicselection) {
    final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
            usertopicselection);
    final Call<okhttp3.ResponseBody> call = service.updateOnesignalId(requestBody);
    return call;
  }


 /*   public Call<User> setupProfile(final User user) {
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), user.toSetupProfileJsonString());
        final Call<User> call = service.setupProfile(requestBody);
        return call;
    }*/

  interface ServiceDefinition {
    @POST("login.php")
    Call<BaseResponseBody> login(@Body RequestBody user);

    @POST("login.php")
    Call<okhttp3.ResponseBody> newlogin(@Body RequestBody user);

    @POST("updateOnesignalId.php")
    Call<okhttp3.ResponseBody> updateOnesignalId(@Body RequestBody user);


    @POST("addusertopics.php")
    Call<BaseResponseBodyArray> addUserTopics(@Body RequestBody user);

    @POST("inserttopics")
    Call<okhttp3.ResponseBody> newaddUserTopics(@Body RequestBody user);


    @Authentication(OAUTH)
    @POST("setupprofile")
    Call<User> setupProfile(@Body RequestBody user);

    @Authentication(OAUTH)
    @GET("users/{id}/userdetails")
    Call<List<UserService>> get(@Path("id") int id);
  }
}
