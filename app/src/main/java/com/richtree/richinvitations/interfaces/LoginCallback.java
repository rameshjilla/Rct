package com.richtree.richinvitations.interfaces;

import com.richtree.richinvitations.model.UserBaseModel;

/**
 * Created by admin on 27/03/2018.
 */

public interface LoginCallback {
    public void success(UserBaseModel user);

    public void fail(String fail);
}
