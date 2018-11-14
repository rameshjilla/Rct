package com.richtree.richinvitations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TGT on 3/29/2018.
 */

public class ProductDetailsData {


  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("seo_h1")
  @Expose
  private String seoH1;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("manufacturer")
  @Expose
  private String manufacturer;
  @SerializedName("sku")
  @Expose
  private String sku;
  @SerializedName("model")
  @Expose
  private String model;
  @SerializedName("image")
  @Expose
  private String image;
  @SerializedName("images")
  @Expose
  private List<String> images = null;
  @SerializedName("original_image")
  @Expose
  private String originalImage;
  @SerializedName("original_images")
  @Expose
  private List<String> originalImages = null;
  @SerializedName("rating")
  @Expose
  private Integer rating;
  @SerializedName("description")
  @Expose
  private String description;


  @SerializedName("attribute_groups")
  @Expose
  private List<AttributeGroup> attributeGroups = null;
  @SerializedName("special")
  @Expose
  private String special;
  @SerializedName("special_excluding_tax")
  @Expose
  private String specialExcludingTax;
  @SerializedName("special_formated")
  @Expose
  private String specialFormated;
  @SerializedName("special_start_date")
  @Expose
  private String specialStartDate;
  @SerializedName("special_end_date")
  @Expose
  private String specialEndDate;
  @SerializedName("discounts")
  @Expose
  private List<Object> discounts = null;
  @SerializedName("options")
  @Expose
  private List<ProductOption> options = null;
  @SerializedName("minimum")
  @Expose
  private String minimum;
  @SerializedName("meta_title")
  @Expose
  private String metaTitle;
  @SerializedName("meta_description")
  @Expose
  private String metaDescription;
  @SerializedName("meta_keyword")
  @Expose
  private String metaKeyword;
  @SerializedName("tag")
  @Expose
  private String tag;
  @SerializedName("upc")
  @Expose
  private String upc;
  @SerializedName("ean")
  @Expose
  private String ean;
  @SerializedName("jan")
  @Expose
  private String jan;
  @SerializedName("isbn")
  @Expose
  private String isbn;
  @SerializedName("mpn")
  @Expose
  private String mpn;
  @SerializedName("location")
  @Expose
  private String location;
  @SerializedName("stock_status")
  @Expose
  private String stockStatus;
  @SerializedName("manufacturer_id")
  @Expose
  private String manufacturerId;
  @SerializedName("tax_class_id")
  @Expose
  private String taxClassId;
  @SerializedName("date_available")
  @Expose
  private String dateAvailable;
  @SerializedName("weight")
  @Expose
  private String weight;
  @SerializedName("weight_class_id")
  @Expose
  private String weightClassId;
  @SerializedName("length")
  @Expose
  private String length;
  @SerializedName("width")
  @Expose
  private String width;
  @SerializedName("height")
  @Expose
  private String height;
  @SerializedName("length_class_id")
  @Expose
  private String lengthClassId;
  @SerializedName("subtract")
  @Expose
  private String subtract;
  @SerializedName("sort_order")
  @Expose
  private String sortOrder;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("date_added")
  @Expose
  private String dateAdded;
  @SerializedName("date_modified")
  @Expose
  private String dateModified;
  @SerializedName("viewed")
  @Expose
  private String viewed;
  @SerializedName("weight_class")
  @Expose
  private String weightClass;
  @SerializedName("length_class")
  @Expose
  private String lengthClass;
  @SerializedName("reward")
  @Expose
  private String reward;
  @SerializedName("points")
  @Expose
  private String points;

  @SerializedName("quantity")
  @Expose
  private String quantity;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSeoH1() {
    return seoH1;
  }

  public void setSeoH1(String seoH1) {
    this.seoH1 = seoH1;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public String getOriginalImage() {
    return originalImage;
  }

  public void setOriginalImage(String originalImage) {
    this.originalImage = originalImage;
  }

  public List<String> getOriginalImages() {
    return originalImages;
  }

  public void setOriginalImages(List<String> originalImages) {
    this.originalImages = originalImages;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<AttributeGroup> getAttributeGroups() {
    return attributeGroups;
  }

  public void setAttributeGroups(List<AttributeGroup> attributeGroups) {
    this.attributeGroups = attributeGroups;
  }

  public String getSpecial() {
    return special;
  }

  public void setSpecial(String special) {
    this.special = special;
  }

  public String getSpecialExcludingTax() {
    return specialExcludingTax;
  }

  public void setSpecialExcludingTax(String specialExcludingTax) {
    this.specialExcludingTax = specialExcludingTax;
  }

  public String getSpecialFormated() {
    return specialFormated;
  }

  public void setSpecialFormated(String specialFormated) {
    this.specialFormated = specialFormated;
  }

  public String getSpecialStartDate() {
    return specialStartDate;
  }

  public void setSpecialStartDate(String specialStartDate) {
    this.specialStartDate = specialStartDate;
  }

  public String getSpecialEndDate() {
    return specialEndDate;
  }

  public void setSpecialEndDate(String specialEndDate) {
    this.specialEndDate = specialEndDate;
  }

  public List<Object> getDiscounts() {
    return discounts;
  }

  public void setDiscounts(List<Object> discounts) {
    this.discounts = discounts;
  }

  public List<ProductOption> getOptions() {
    return options;
  }

  public void setOptions(List<ProductOption> options) {
    this.options = options;
  }

  public String getMinimum() {
    return minimum;
  }

  public void setMinimum(String minimum) {
    this.minimum = minimum;
  }

  public String getMetaTitle() {
    return metaTitle;
  }

  public void setMetaTitle(String metaTitle) {
    this.metaTitle = metaTitle;
  }

  public String getMetaDescription() {
    return metaDescription;
  }

  public void setMetaDescription(String metaDescription) {
    this.metaDescription = metaDescription;
  }

  public String getMetaKeyword() {
    return metaKeyword;
  }

  public void setMetaKeyword(String metaKeyword) {
    this.metaKeyword = metaKeyword;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getUpc() {
    return upc;
  }

  public void setUpc(String upc) {
    this.upc = upc;
  }

  public String getEan() {
    return ean;
  }

  public void setEan(String ean) {
    this.ean = ean;
  }

  public String getJan() {
    return jan;
  }

  public void setJan(String jan) {
    this.jan = jan;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getMpn() {
    return mpn;
  }

  public void setMpn(String mpn) {
    this.mpn = mpn;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getStockStatus() {
    return stockStatus;
  }

  public void setStockStatus(String stockStatus) {
    this.stockStatus = stockStatus;
  }

  public String getManufacturerId() {
    return manufacturerId;
  }

  public void setManufacturerId(String manufacturerId) {
    this.manufacturerId = manufacturerId;
  }

  public String getTaxClassId() {
    return taxClassId;
  }

  public void setTaxClassId(String taxClassId) {
    this.taxClassId = taxClassId;
  }

  public String getDateAvailable() {
    return dateAvailable;
  }

  public void setDateAvailable(String dateAvailable) {
    this.dateAvailable = dateAvailable;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getWeightClassId() {
    return weightClassId;
  }

  public void setWeightClassId(String weightClassId) {
    this.weightClassId = weightClassId;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getLengthClassId() {
    return lengthClassId;
  }

  public void setLengthClassId(String lengthClassId) {
    this.lengthClassId = lengthClassId;
  }

  public String getSubtract() {
    return subtract;
  }

  public void setSubtract(String subtract) {
    this.subtract = subtract;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDateAdded() {
    return dateAdded;
  }

  public void setDateAdded(String dateAdded) {
    this.dateAdded = dateAdded;
  }

  public String getDateModified() {
    return dateModified;
  }

  public void setDateModified(String dateModified) {
    this.dateModified = dateModified;
  }

  public String getViewed() {
    return viewed;
  }

  public void setViewed(String viewed) {
    this.viewed = viewed;
  }

  public String getWeightClass() {
    return weightClass;
  }

  public void setWeightClass(String weightClass) {
    this.weightClass = weightClass;
  }

  public String getLengthClass() {
    return lengthClass;
  }

  public void setLengthClass(String lengthClass) {
    this.lengthClass = lengthClass;
  }

  public String getReward() {
    return reward;
  }

  public void setReward(String reward) {
    this.reward = reward;
  }

  public String getPoints() {
    return points;
  }

  public void setPoints(String points) {
    this.points = points;
  }


  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }


}

