package app.myfirstclap.controllers;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.R;
import app.myfirstclap.interfaces.BaseRetrofitCallback;
import app.myfirstclap.interfaces.RetrofitCallbacks;
import app.myfirstclap.model.BaseResponseBody;
import app.myfirstclap.model.BaseResponseBodyArray;
import app.myfirstclap.model.User;
import app.myfirstclap.utils.CustomRetrofitCallbackAdapter;
import au.com.dstil.atomicauth.callback.FailureCallback;
import au.com.dstil.atomicauth.callback.SuccessCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TGT on 11/28/2017.
 */

public final class UserLoginController {

  public static void loginUser(final MyFirstClapApplication application, final User user, final SuccessCallback<User> successcallback, final FailureCallback<String> failureCallback) {

    application.userService.loginUser(user).enqueue(new Callback<BaseResponseBody>() {
      @Override
      public void onResponse(Call<BaseResponseBody> call, Response<BaseResponseBody> response) {
        if (response.isSuccessful()) {

          BaseResponseBody body = response.body();

          if (body.getCode() == 100) {
            Gson gson = new Gson();
            LinkedTreeMap treemap = (LinkedTreeMap) body.getResult();
            JsonObject jsonObject = gson.toJsonTree(treemap).getAsJsonObject();
            User user = gson.fromJson(jsonObject, User.class);
            successcallback.success(user);


          } else {
            Toast.makeText(application.getApplicationContext(), body.getMessage(), Toast
                    .LENGTH_SHORT)
                    .show();
          }

        } else {
          Toast.makeText(application.getApplicationContext(), application
                  .getApplicationContext().getResources().getString(R.string.error_fail), Toast
                  .LENGTH_SHORT)
                  .show();
        }
      }

      @Override
      public void onFailure(Call<BaseResponseBody> call, Throwable t) {
        Toast.makeText(application.getApplicationContext(), application
                .getApplicationContext().getResources().getString(R.string.error_fail), Toast
                .LENGTH_SHORT)
                .show();

      }
    });


  }


  public static void addUserTopics(final MyFirstClapApplication application, final String usertopicselection, final RetrofitCallbacks retrofitresponsecallback) {

    application.userService.addUserTopics(usertopicselection).enqueue(new CustomRetrofitCallbackAdapter<BaseResponseBodyArray>(application.getApplicationContext()) {
      @Override
      public void onSuccess(Response<BaseResponseBodyArray> response) {
        retrofitresponsecallback.Sucess(response);
      }
    });

  }


  public static void newAddUserTopics(final MyFirstClapApplication application, final ProgressDialog dialog, final String usertopicselection, final BaseRetrofitCallback baseRetrofitCallback) {

    application.userService.newaddUserTopics(usertopicselection).enqueue(new Callback<okhttp3.ResponseBody>() {
      @Override
      public void onResponse(Call<okhttp3.ResponseBody> call, Response<okhttp3.ResponseBody> response) {
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
      public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {
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


  public static void updateOneSignalId(final MyFirstClapApplication application, final ProgressDialog dialog, final String json, final BaseRetrofitCallback baseRetrofitCallback) {

    application.userService.updateOnesignal(json).enqueue(new Callback<okhttp3.ResponseBody>() {
      @Override
      public void onResponse(Call<okhttp3.ResponseBody> call, Response<okhttp3.ResponseBody> response) {
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
      public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {
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

  public static void newLoginUser(final MyFirstClapApplication myapplication, final ProgressDialog dialog, final User user, final BaseRetrofitCallback baseretrofitcallback) {
    myapplication.userService.newLoginUser(user).enqueue(new Callback<okhttp3.ResponseBody>() {
      @Override
      public void onResponse(Call<okhttp3.ResponseBody> call, Response<okhttp3.ResponseBody> response) {
        if (response.isSuccessful()) {
          try {
            baseretrofitcallback.Success(response.body().string());
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

      @Override
      public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {
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
    });
  }







 /* public static void setUpProfile(MyFirstClapApplication application, final User user, final SuccessCallback<User> successCallback, final FailureCallback<String> failureCallback) {
    if (application.isConnected()) {
      application.userService.setupProfile(user).enqueue(new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {

        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

        }
      });

    }
  }*/
}
