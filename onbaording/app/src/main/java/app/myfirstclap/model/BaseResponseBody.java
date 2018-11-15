package app.myfirstclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 15/01/2018.
 */

public class BaseResponseBody<T> {
  @SerializedName("code")
  @Expose
  private Integer code;
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("data")
  @Expose
  private T result;

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


}
