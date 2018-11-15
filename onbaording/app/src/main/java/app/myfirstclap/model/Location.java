package app.myfirstclap.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TGT on 11/29/2017.
 */

public class Location {
  @SerializedName("id")
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCityname() {
    return cityname;
  }

  public void setCityname(String cityname) {
    this.cityname = cityname;
  }

  @SerializedName("name")
  private String cityname;
  @SerializedName("state")
  private String state;
}
