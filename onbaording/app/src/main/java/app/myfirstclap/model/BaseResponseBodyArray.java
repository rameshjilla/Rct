package app.myfirstclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TGT on 1/16/2018.
 */

public class BaseResponseBodyArray<T> {
  @SerializedName("code")
  @Expose
  private Integer code;
  @SerializedName("message")
  @Expose
  private String message;

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  @SerializedName("data")
  @Expose
  private List<T> data = null;

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
