package app.myfirstclap.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import app.myfirstclap.model.ApiErrorBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TGT on 1/16/2018.
 */

public abstract class CustomRetrofitCallbackAdapter<T> implements Callback<T> {
  Context context;
  Response<T> response;


  public CustomRetrofitCallbackAdapter(Context context) {
    this.context = context;


  }


  @Override
  public void onResponse(Call<T> call, Response<T> response) {
    this.response = response;
    if (!response.isSuccessful()) {
      ApiErrorBody errorbody = ErrorUtils.parseError(response, context);
      Toast.makeText(context, errorbody.getMessage(), Toast.LENGTH_SHORT).show();
      Log.d("errormessage", errorbody.getMessage());
    } else {
      onSuccess(response);
    }
  }

  @Override
  public void onFailure(Call<T> call, Throwable t) {
    Toast.makeText(context, toString(), Toast.LENGTH_SHORT).show();

  }

  public void onSuccess(Response<T> response) {
    this.response = response;


  }
}