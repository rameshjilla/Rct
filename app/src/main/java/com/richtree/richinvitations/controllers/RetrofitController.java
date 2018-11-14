package com.richtree.richinvitations.controllers;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TGT on 6/15/2018.
 */

public final class RetrofitController {


    public static void postRequestController(final MyApplication myapplication, final
    ProgressDialog dialog, String request, String path, String accesstoken, final
                                             NewBaseRetrofitCallback
                                                     baseRetrofitCallback) {
        myapplication.retrofitService.postRequest(request, path, accesstoken).enqueue(new ResponseCallback
                (dialog, myapplication, baseRetrofitCallback));
    }

    public static void putRequestController(final MyApplication myapplication, final
    ProgressDialog dialog, String request, String path, String accesstoken, final
                                            NewBaseRetrofitCallback
                                                    baseRetrofitCallback) {
        myapplication.retrofitService.putRequest(request, path, accesstoken).enqueue(new ResponseCallback
                (dialog, myapplication, baseRetrofitCallback));
    }


    public static void getRequestController(final MyApplication myapplication, final
    ProgressDialog dialog, String path, String accesstoken, final NewBaseRetrofitCallback
                                                    baseRetrofitCallback) {
        myapplication.retrofitService.getRequest(path, accesstoken).enqueue(new ResponseCallback(dialog,
                myapplication, baseRetrofitCallback));
    }


    public static class ResponseCallback implements Callback<ResponseBody> {
        ProgressDialog dialog;
        MyApplication myapplication;
        NewBaseRetrofitCallback baseRetrofitCallback;

        ResponseCallback(ProgressDialog dialog, MyApplication myapplication, NewBaseRetrofitCallback
                baseRetrofitCallback) {
            this.dialog = dialog;
            this.myapplication = myapplication;
            this.baseRetrofitCallback = baseRetrofitCallback;

        }


        @Override
        public void onFailure(Call call, Throwable t) {
            dialog.dismiss();
            if (t instanceof IOException) {
                Toast.makeText(myapplication.getApplicationContext(), "Connection TimeOut",
                        Toast
                                .LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(myapplication.getApplicationContext(), "Something went wrong",
                        Toast
                                .LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
                dialog.dismiss();
                try {
                    baseRetrofitCallback.Success(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                dialog.dismiss();
                Toast.makeText(myapplication.getApplicationContext(), "Something went wrong",
                        Toast
                                .LENGTH_SHORT)
                        .show();
            }
        }
    }
}
