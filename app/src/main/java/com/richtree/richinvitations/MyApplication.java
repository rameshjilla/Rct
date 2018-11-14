package com.richtree.richinvitations;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.gson.Gson;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.model.App;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.service.RetrofitService;
import com.richtree.richinvitations.service.UserService;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by TGT on 11/9/2017.
 */

public class MyApplication extends Application {

    protected MyApplication application;
    private static MyApplication sInstance = null;
    private static final int RC_SIGN_IN_FB = 1916;
    public UserService userService;
    public RetrofitService retrofitService;
    TinyDB tinyDB;
    private static OkHttpClient.Builder httpClient;
    private static Context context;
    MyApplication myapplication;

    ProgressDialog dialog;

    // Updated your class body:
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        MyApplication.context = getApplicationContext();
        tinyDB = new TinyDB(getBaseContext());
        myapplication = returnInstance();
        dialog = new ProgressDialog(getBaseContext());
        retrofitInit();

        // Initialize the SDK before executing any other operations,


    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public MyApplication returnInstance() {
        return sInstance;
    }


    public void retrofitInit() {
        httpClient = new OkHttpClient.Builder().addInterceptor(new TokenAuthenticator());
        OkHttpClient client = httpClient.build();
        Retrofit api = new Retrofit
                .Builder()
                .baseUrl(getResources().getString(R.string.api_base_url))
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        userService = new UserService(api);
        retrofitService = new RetrofitService(api);

    }


    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


    public class TokenAuthenticator implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            if (response.code() == 403) {
                User user = tinyDB.getObject("user", User.class);
                JSONObject object = new JSONObject();
                try {
                    object.put("email", user.getEmail());
                    object.put("password", "user@123");

                    RetrofitController.postRequestController(returnInstance(), dialog, object.toString()
                            , "login", "", new NewBaseRetrofitCallback() {
                                @Override
                                public void Success(String success) {
                                    dialog.dismiss();
                                    try {
                                        JSONObject responseobject = new JSONObject(success);
                                        if (responseobject.getBoolean("success")) {
                                            tinyDB.putString("accesstoken", responseobject.getString
                                                    ("accessToken"));
                                            JSONObject dataobject = responseobject.getJSONObject("data");
                                            User user = new Gson().fromJson(dataobject.toString(), User.class);
                                            App app = new Gson().fromJson(dataobject.getJSONObject("app").toString(),
                                                    App
                                                            .class);
                                            user.setApp(app);
                                            tinyDB.putObject("user", user);

                                        } else {
                                            Toast.makeText(getApplicationContext(), responseobject.getString
                                                            ("message"),
                                                    Toast
                                                            .LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

            }
            return response;
        }
    }
}