package app.myfirstclap.controllers;

import android.app.ProgressDialog;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.interfaces.BaseRetrofitCallback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TGT on 6/15/2018.
 */

public final class RetrofitController {


    public static void postRequestController(final MyFirstClapApplication myapplication, final
    ProgressDialog dialog, Object request, String path, final BaseRetrofitCallback
                                                     baseRetrofitCallback) {
        myapplication.retrofitService.postRequest(request, path).enqueue(new ResponseCallback(dialog, myapplication, baseRetrofitCallback));
    }

    public static void getRequestController(final MyFirstClapApplication myapplication, final ProgressDialog dialog, String path, final BaseRetrofitCallback baseRetrofitCallback) {
        myapplication.retrofitService.getRequest(path).enqueue(new ResponseCallback(dialog, myapplication, baseRetrofitCallback));
    }


    public static class ResponseCallback implements Callback<ResponseBody> {
        ProgressDialog dialog;
        MyFirstClapApplication myapplication;
        BaseRetrofitCallback baseRetrofitCallback;

        ResponseCallback(ProgressDialog dialog, MyFirstClapApplication myapplication, BaseRetrofitCallback baseRetrofitCallback) {
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
                Toast.makeText(myapplication.getApplicationContext(), "Something went  wrong",
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
                    JSONObject object = new JSONObject(response.body().string());
                    if (object.getInt("code") == 100) {
                        baseRetrofitCallback.Success(object.getJSONObject("result").toString());
                    } else {
                        Toast.makeText(myapplication.getApplicationContext(), object.getString("message"),
                                Toast
                                        .LENGTH_SHORT)
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
    }
}
