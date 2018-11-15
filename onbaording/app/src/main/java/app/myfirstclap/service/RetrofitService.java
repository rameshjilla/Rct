package app.myfirstclap.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by TGT on 6/15/2018.
 */

public class RetrofitService {
    private final ServiceDefinition service;

    public RetrofitService(Retrofit retrofit) {
        service = retrofit.create(ServiceDefinition.class);
    }

    public Call<ResponseBody> postRequest(Object user, final String path) {

        final Call<okhttp3.ResponseBody> call = service.postRequest(path, user);
        return call;
    }

    public Call<ResponseBody> getRequest(final String path) {

        final Call<okhttp3.ResponseBody> call = service.getRequest(path);
        return call;
    }


    interface ServiceDefinition {
        @POST("{path}")
        Call<ResponseBody> postRequest(@Path(value = "path", encoded = true) String path, @Body
                Object user);

        @GET("{path}")
        Call<ResponseBody> getRequest(@Path(value = "path", encoded = true) String path);


    }
}
