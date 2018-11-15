package app.myfirstclap.interfaces;

import retrofit2.Response;

/**
 * Created by TGT on 1/11/2018.
 */

public interface RetrofitCallbacks<T> {
  public void Sucess(Response<T> response);

}
