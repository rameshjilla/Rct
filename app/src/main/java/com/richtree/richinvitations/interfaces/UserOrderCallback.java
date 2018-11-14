package com.richtree.richinvitations.interfaces;

import com.richtree.richinvitations.model.OderBaseModel;

/**
 * Created by admin on 27/03/2018.
 */

public interface UserOrderCallback {
    void success(OderBaseModel orderbasemodel);

    void fail(String fail);
}
