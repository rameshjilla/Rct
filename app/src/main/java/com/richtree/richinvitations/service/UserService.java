package com.richtree.richinvitations.service;

import com.richtree.richinvitations.model.OderBaseModel;
import com.richtree.richinvitations.model.ProductDetailsBaseModel;
import com.richtree.richinvitations.model.UserBaseModel;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 26/03/2018.
 */

public class UserService {
    private final ServiceDefinition service;

    public UserService(Retrofit retrofit) {
        service = retrofit.create(ServiceDefinition.class);
    }


    public Call<UserBaseModel> loginUser(String request, String session) {

        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                request);
        final Call<UserBaseModel> call = service.login(requestBody, session);
        return call;
    }

    public Call<String> getOtpfromServer(String userid, String password, String sender, String mobilenumber, String message) {

        final Call<String> call = service.OtpVerification(userid, password, sender, mobilenumber, message);
        return call;
    }

    public Call<ResponseBody> getSession() {
        final Call<ResponseBody> call = service.getSession();
        return call;
    }

    public Call<OderBaseModel> getOrders(String sessionid) {
        final Call<OderBaseModel> call = service.getOrders(sessionid);
        return call;
    }

    public Call<ResponseBody> getProducts(String sessionid, String order_id) {
        final Call<ResponseBody> call = service.getProducts(sessionid, order_id);
        return call;
    }

    public Call<ResponseBody> logoutUser(String sessionid) {
        final Call<ResponseBody> call = service.logoutUser(sessionid);
        return call;
    }


    public Call<ResponseBody> getRichAlbums(String sessionid, String productid) {
        final Call<ResponseBody> call = service.getRichItems(sessionid, productid);
        return call;
    }

    public Call<ProductDetailsBaseModel> getProductsDetails(String sessionid, String product_id) {
        final Call<ProductDetailsBaseModel> call = service.getProductsDetails(sessionid, product_id);
        return call;
    }

    public Call<ResponseBody> getProductsRichDetails(String sessionid, String product_id) {
        final Call<ResponseBody> call = service.getProductsRichDetails(sessionid, product_id);
        return call;
    }


    public Call<ResponseBody> updateAccount(String request, String sessionid) {
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                request);
        final Call<ResponseBody> call = service.updateAccount(requestBody, sessionid);
        return call;
    }





 /*   public Call<User> setupProfile(final User user) {
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), user.toSetupProfileJsonString());
        final Call<User> call = service.setupProfile(requestBody);
        return call;
    }*/

    interface ServiceDefinition {
        @Headers("X-Oc-Merchant-Id: 994988")
        @POST("login")
        Call<UserBaseModel> login(@Body RequestBody user, @Header("X-Oc-Session") String sessionId);


        @Headers("X-Oc-Merchant-Id: 994988")
        @POST("account")
        Call<ResponseBody> updateAccount(@Body RequestBody user, @Header("X-Oc-Session") String
                sessionId);

        @Headers("X-Oc-Merchant-Id: 994988")
        @GET("session")
        Call<ResponseBody> getSession();

        @FormUrlEncoded
        @POST("http://103.233.76.48/websms/sendsms.aspx?")
        Call<String> OtpVerification(@Field("userid") String userid, @Field("password") String password, @Field("sender") String sender,
                                     @Field("mobileno") String mobilenumber, @Field("msg") String message);


        @Headers("X-Oc-Merchant-Id: 994988")
        @GET("customerorders/limit/1111/page/1")
        Call<OderBaseModel> getOrders(@Header("X-Oc-Session") String
                                              sessionId);


        @Headers("X-Oc-Merchant-Id: 994988")
        @GET("customerorders/{order_id}")
        Call<ResponseBody> getProducts(@Header("X-Oc-Session") String
                                               sessionId, @Path(value = "order_id", encoded = true) String order_id);

        @Headers("X-Oc-Merchant-Id: 994988")
        @GET("related/{product_id}")
        Call<ResponseBody> getRichItems(@Header("X-Oc-Session") String
                                                sessionId, @Path(value = "product_id", encoded = true)
                                                String product_id);

        @Headers("X-Oc-Merchant-Id: 994988")
        @GET("products/{product_id}")
        Call<ProductDetailsBaseModel> getProductsDetails(@Header("X-Oc-Session") String
                                                                 sessionId, @Path(value = "product_id", encoded = true) String product_id);

        @Headers("X-Oc-Merchant-Id: 994988")
        @POST("logout")
        Call<ResponseBody> logoutUser(@Header("X-Oc-Session") String
                                              sessionId);


        @Headers("X-Oc-Merchant-Id: 994988")
        @GET("products/{product_id}")
        Call<ResponseBody> getProductsRichDetails(@Header("X-Oc-Session") String
                                                          sessionId, @Path(value = "product_id", encoded = true) String product_id);

    }
}
