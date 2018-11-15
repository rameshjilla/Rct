package app.myfirstclap.interfaces;

import app.myfirstclap.model.Topic;

/**
 * Created by TGT on 1/10/2018.
 */

public interface TopicsCallback {
  public void getSuccesstopicCallback(Topic.TopicResponse topicresponse);

  public void getTopicFailureCallback(String exception);
}
