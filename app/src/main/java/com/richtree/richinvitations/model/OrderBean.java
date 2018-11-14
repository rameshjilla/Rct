package com.richtree.richinvitations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 27/09/2018.
 */

public class OrderBean {
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("product")
    @Expose
    private ProductBean product;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }


}
