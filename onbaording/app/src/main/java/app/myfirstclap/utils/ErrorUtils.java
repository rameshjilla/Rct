package app.myfirstclap.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import app.myfirstclap.model.ApiErrorBody;
import retrofit2.Response;

/**
 * Created by TGT on 1/4/2018.
 */

public class ErrorUtils {
  public static ApiErrorBody parseError(Response<?> response, Context context) {
    ApiErrorBody error = new ApiErrorBody();
    try {
      Gson gson = new Gson();
      error = gson.fromJson(response.errorBody().charStream(), ApiErrorBody.class);
    } catch (Exception e) {
      Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
    if (TextUtils.isEmpty(error.getMessage())) {
      error.setMessage(response.raw().message());
      Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
    }
    return error;
  }
}