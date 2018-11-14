package com.richtree.richinvitations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TGT on 4/3/2018.
 */

public class ProductOption {

  @SerializedName("product_option_id")
  @Expose
  private String productOptionId;
  @SerializedName("option_value")
  @Expose
  private List<ProductOptionValue> ProductOptionValue = null;
  @SerializedName("option_id")
  @Expose
  private String optionId;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("type")
  @Expose
  private String type;
  @SerializedName("value")
  @Expose
  private String value;
  @SerializedName("required")
  @Expose
  private String required;

  public String getProductOptionId() {
    return productOptionId;
  }

  public void setProductOptionId(String productOptionId) {
    this.productOptionId = productOptionId;
  }

  public List<ProductOptionValue> getOptionValue() {
    return ProductOptionValue;
  }

  public void setOptionValue(List<ProductOptionValue> ProductOptionValue) {
    this.ProductOptionValue = ProductOptionValue;
  }

  public String getOptionId() {
    return optionId;
  }

  public void setOptionId(String optionId) {
    this.optionId = optionId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getRequired() {
    return required;
  }

  public void setRequired(String required) {
    this.required = required;
  }

}