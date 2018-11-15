package app.myfirstclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TGT on 1/10/2018.
 */

public class Topic {

  @SerializedName("Id")
  @Expose
  private Integer id;
  @SerializedName("TopicName")
  @Expose
  private String topicName;
  @SerializedName("TopicImage")
  @Expose
  private String topicImage;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTopicName() {
    return topicName;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  public String getTopicImage() {
    return topicImage;
  }

  public void setTopicImage(String topicImage) {
    this.topicImage = topicImage;
  }


  public class TopicResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("data")
    @Expose
    private List<Topic> data = null;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public Integer getCode() {
      return code;
    }

    public void setCode(Integer code) {
      this.code = code;
    }

    public List<Topic> getData() {
      return data;
    }

    public void setData(List<Topic> data) {
      this.data = data;
    }


  }

  @Override
  public boolean equals(Object o) {
    // TODO Auto-generated method stub
    if (o instanceof Topic) {
      return this.id == (((Topic) o).getId());
    }
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    // TODO Auto-generated method stub
    return id.hashCode();
  }

}