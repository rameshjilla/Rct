package com.richtree.richinvitations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 26/03/2018.
 */

public class User {
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer_group_id")
    @Expose
    private Integer customerGroupId;

    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("cart")
    @Expose
    private Object cart;
    @SerializedName("wishlist")
    @Expose
    private Object wishlist;
    @SerializedName("newsletter")
    @Expose
    private Integer newsletter;
    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("approved")
    @Expose
    private Integer approved;
    @SerializedName("safe")
    @Expose
    private Integer safe;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("account_custom_field")
    @Expose
    private Object accountCustomField;


    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @SerializedName("app")
    @Expose
    private App app;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(Integer customerGroupId) {
        this.customerGroupId = customerGroupId;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Object getCart() {
        return cart;
    }

    public void setCart(Object cart) {
        this.cart = cart;
    }

    public Object getWishlist() {
        return wishlist;
    }

    public void setWishlist(Object wishlist) {
        this.wishlist = wishlist;
    }

    public Integer getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Integer newsletter) {
        this.newsletter = newsletter;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public Integer getSafe() {
        return safe;
    }

    public void setSafe(Integer safe) {
        this.safe = safe;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Object getAccountCustomField() {
        return accountCustomField;
    }

    public void setAccountCustomField(Object accountCustomField) {
        this.accountCustomField = accountCustomField;
    }
}
