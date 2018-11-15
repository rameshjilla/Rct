
package app.myfirstclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiErrorBody {
  @Expose
  private Integer code;
  @SerializedName("Message")
  @Expose
  private String message;
  @SerializedName("Response")
  @Expose
  private ErrorMessage response;

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

  public ErrorMessage getResponse() {
    return response;
  }

  public void setResponse(ErrorMessage response) {
    this.response = response;
  }

  public class ErrorMessage {

    @SerializedName("Error")
    @Expose
    private String error;

    public String getError() {
      return error;
    }

    public void setError(String error) {
      this.error = error;
    }

  }


}
