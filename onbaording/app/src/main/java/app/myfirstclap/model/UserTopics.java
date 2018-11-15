package app.myfirstclap.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 17/06/2018.
 */

public class UserTopics {

    @SerializedName("TopicId")
    private String mTopicId;
    @SerializedName("TopicName")
    private String mTopicName;


    public String getTopicId() {
        return mTopicId;
    }

    public void setTopicId(String TopicId) {
        mTopicId = TopicId;
    }

    public String getTopicName() {
        return mTopicName;
    }

    public void setTopicName(String TopicName) {
        mTopicName = TopicName;
    }


}
