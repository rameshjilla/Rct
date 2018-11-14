package com.richtree.richinvitations.service;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * Created by admin on 26/09/2018.
 */

public class RetrofitService {
    private final ServiceDefinition service;

    public RetrofitService(Retrofit retrofit) {
        service = retrofit.create(ServiceDefinition.class);
    }

    public Call<ResponseBody> postRequest(String request, final String path, String accesstoken) {
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                request);

        final Call<ResponseBody> call = service.postRequest(path, requestBody, accesstoken);
        return call;
    }


    public Call<ResponseBody> putRequest(String request, final String path, String accesstoken) {
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                request);

        final Call<ResponseBody> call = service.PutRequest(path, requestBody, accesstoken);
        return call;
    }

    public Call<ResponseBody> getRequest(final String path, String accesstoken) {

        final Call<ResponseBody> call = service.getRequest(path, accesstoken);
        return call;
    }


    interface ServiceDefinition {
        @Headers({
                "x-api-key: 2090f06a-b47d-11e8-96f8-529269fb14592090f06a-b47d-11e8-96f8-529269fb1459",
                "x-app-id: 1"
        })

        @POST()
        Call<ResponseBody> postRequest(@Url String url, @Body
                RequestBody user, @Header("x-access-token") String accessToken);

        @Headers({
                "x-api-key: 2090f06a-b47d-11e8-96f8-529269fb14592090f06a-b47d-11e8-96f8-529269fb1459",
                "x-app-id: 1"
        })
        @GET()
        Call<ResponseBody> getRequest(@Url String url, @Header("x-access-token") String accessToken);

        @Headers({
                "x-api-key: 2090f06a-b47d-11e8-96f8-529269fb14592090f06a-b47d-11e8-96f8-529269fb1459",
                "x-app-id: 1"
        })

        @PUT()
        Call<ResponseBody> PutRequest(@Url String url, @Body
                RequestBody user, @Header("x-access-token") String accessToken);
    }
}
