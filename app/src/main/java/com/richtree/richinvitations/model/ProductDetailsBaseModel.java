package com.richtree.richinvitations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TGT on 3/29/2018.
 */

public class ProductDetailsBaseModel {
  @Expose
  private Boolean success;
  @SerializedName("data")
  @Expose
  private ProductDetailsData data;

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public ProductDetailsData getData() {
    return data;
  }

  public void setData(ProductDetailsData data) {
    this.data = data;
  }
}
