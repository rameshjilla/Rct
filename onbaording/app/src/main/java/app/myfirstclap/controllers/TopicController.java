package app.myfirstclap.controllers;


import android.app.ProgressDialog;
import android.widget.Toast;

import java.io.IOException;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.interfaces.BaseRetrofitCallback;
import app.myfirstclap.interfaces.TopicsCallback;
import app.myfirstclap.model.Topic;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TGT on 1/10/2018.
 */

public final class TopicController {
    public static void getTopics(final MyFirstClapApplication application, final ProgressDialog dialog, final TopicsCallback topicscallback) {


        application.topicservice.getTopics().enqueue(new Callback<Topic.TopicResponse>() {
            @Override
            public void onResponse(Call<Topic.TopicResponse> call, Response<Topic.TopicResponse> response) {
                if (response.isSuccessful()) {
                    Topic.TopicResponse topicsresponse = response.body();
                    topicscallback.getSuccesstopicCallback(topicsresponse);
                } else {
                    dialog.dismiss();
                    Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<Topic.TopicResponse> call, Throwable t) {
                dialog.dismiss();

                if (t instanceof IOException) {
                    Toast.makeText(application.getApplicationContext(), "Connection TimeOut",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }


            }
        });


    }

    public static void newgetTopics(final MyFirstClapApplication application, final ProgressDialog
            dialog, final BaseRetrofitCallback baseRetrofitCallback) {


        application.topicservice.newGetTopics().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        baseRetrofitCallback.Success(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();

                if (t instanceof IOException) {
                    Toast.makeText(application.getApplicationContext(), "Connection TimeOut",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }


            }
        });


    }
}
