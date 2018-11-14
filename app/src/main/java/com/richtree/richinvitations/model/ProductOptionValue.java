package com.richtree.richinvitations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by TGT on 4/3/2018.
 */

public class ProductOptionValue {
  @SerializedName("image")
  @Expose
  private String image;
  @SerializedName("price")
  @Expose
  private Boolean price;
  @SerializedName("price_excluding_tax")
  @Expose
  private Boolean priceExcludingTax;
  @SerializedName("price_formated")
  @Expose
  private Boolean priceFormated;
  @SerializedName("price_prefix")
  @Expose
  private String pricePrefix;
  @SerializedName("product_option_value_id")
  @Expose
  private String productOptionValueId;
  @SerializedName("option_value_id")
  @Expose
  private String optionValueId;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("quantity")
  @Expose
  private String quantity;

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Boolean getPrice() {
    return price;
  }

  public void setPrice(Boolean price) {
    this.price = price;
  }

  public Boolean getPriceExcludingTax() {
    return priceExcludingTax;
  }

  public void setPriceExcludingTax(Boolean priceExcludingTax) {
    this.priceExcludingTax = priceExcludingTax;
  }

  public Boolean getPriceFormated() {
    return priceFormated;
  }

  public void setPriceFormated(Boolean priceFormated) {
    this.priceFormated = priceFormated;
  }

  public String getPricePrefix() {
    return pricePrefix;
  }

  public void setPricePrefix(String pricePrefix) {
    this.pricePrefix = pricePrefix;
  }

  public String getProductOptionValueId() {
    return productOptionValueId;
  }

  public void setProductOptionValueId(String productOptionValueId) {
    this.productOptionValueId = productOptionValueId;
  }

  public String getOptionValueId() {
    return optionValueId;
  }

  public void setOptionValueId(String optionValueId) {
    this.optionValueId = optionValueId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }
}
