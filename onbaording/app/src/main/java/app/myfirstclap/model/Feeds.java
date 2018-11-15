package app.myfirstclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TGT on 2/15/2018.
 */

public class Feeds {

  @SerializedName("feed_id")
  @Expose
  private String feedId;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("image_url")
  @Expose
  private String imageUrl;
  @SerializedName("url")
  @Expose
  private String url;
  @SerializedName("follower_id")
  @Expose
  private String followerId;

  public String getFeedId() {
    return feedId;
  }

  public void setFeedId(String feedId) {
    this.feedId = feedId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getFollowerId() {
    return followerId;
  }

  public void setFollowerId(String followerId) {
    this.followerId = followerId;
  }

}
