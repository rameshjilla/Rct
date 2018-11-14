package com.richtree.richinvitations.interfaces;

import com.richtree.richinvitations.model.ProductDetailsBaseModel;

/**
 * Created by TGT on 3/30/2018.
 */

public interface BaseProductsDetailscallback {
  void success(ProductDetailsBaseModel productdetails);

  void fail(String fail);
}
