package app.myfirstclap.interfaces;

import app.myfirstclap.model.FacebookFeed;

/**
 * Created by TGT on 12/13/2017.
 */

public interface FacebookFeedsCallback {
    public void successCallBack(FacebookFeed newsfeedresponse);

    public void failureCallback(String failureexception);
}
