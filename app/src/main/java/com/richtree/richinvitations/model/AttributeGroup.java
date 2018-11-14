package com.richtree.richinvitations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TGT on 3/29/2018.
 */

public class AttributeGroup {

  @SerializedName("attribute_group_id")
  @Expose
  private String attributeGroupId;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("attribute")
  @Expose
  private List<Attribute> attribute = null;

  public String getAttributeGroupId() {
    return attributeGroupId;
  }

  public void setAttributeGroupId(String attributeGroupId) {
    this.attributeGroupId = attributeGroupId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Attribute> getAttribute() {
    return attribute;
  }

  public void setAttribute(List<Attribute> attribute) {
    this.attribute = attribute;
  }

}