package com.richtree.richinvitations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TGT on 3/29/2018.
 */

public class Attribute {

  @SerializedName("attribute_id")
  @Expose
  private String attributeId;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("text")
  @Expose
  private String text;

  public String getAttributeId() {
    return attributeId;
  }

  public void setAttributeId(String attributeId) {
    this.attributeId = attributeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}