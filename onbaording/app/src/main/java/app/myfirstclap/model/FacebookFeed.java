package app.myfirstclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 01/01/2018.
 */

public class FacebookFeed {
  @SerializedName("data")
  @Expose
  private List<News> data = null;
  @SerializedName("paging")
  @Expose
  private Paging paging;

  public List<News> getData() {
    return data;
  }

  public void setData(List<News> data) {
    this.data = data;
  }

  public Paging getPaging() {
    return paging;
  }

  public void setPaging(Paging paging) {
    this.paging = paging;
  }

  public class News {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("full_picture")
    @Expose
    private String fullPicture;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("id")
    @Expose
    private String id;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getCreatedTime() {
      return createdTime;
    }

    public void setCreatedTime(String createdTime) {
      this.createdTime = createdTime;
    }

    public From getFrom() {
      return from;
    }

    public void setFrom(From from) {
      this.from = from;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }

    public String getCaption() {
      return caption;
    }

    public void setCaption(String caption) {
      this.caption = caption;
    }

    public String getFullPicture() {
      return fullPicture;
    }

    public void setFullPicture(String fullPicture) {
      this.fullPicture = fullPicture;
    }

    public String getPicture() {
      return picture;
    }

    public void setPicture(String picture) {
      this.picture = picture;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

  }

  public class From {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

  }

  public class Paging {

    @SerializedName("cursors")
    @Expose
    private Cursors cursors;
    @SerializedName("next")
    @Expose
    private String next;

    public Cursors getCursors() {
      return cursors;
    }

    public void setCursors(Cursors cursors) {
      this.cursors = cursors;
    }

    public String getNext() {
      return next;
    }

    public void setNext(String next) {
      this.next = next;
    }

  }

  public class Cursors {

    @SerializedName("before")
    @Expose
    private String before;
    @SerializedName("after")
    @Expose
    private String after;

    public String getBefore() {
      return before;
    }

    public void setBefore(String before) {
      this.before = before;
    }

    public String getAfter() {
      return after;
    }

    public void setAfter(String after) {
      this.after = after;
    }

  }
}
