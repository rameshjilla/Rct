package app.myfirstclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TGT on 6/13/2018.
 */

public class NewUser {

  @SerializedName("user_id")
  @Expose
  private String userId;
  @SerializedName("FirstName")
  @Expose
  private String firstName;
  @SerializedName("LastName")
  @Expose
  private String lastName;
  @SerializedName("Email")
  @Expose
  private String email;
  @SerializedName("ImageURL")
  @Expose
  private String imageURL;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

}