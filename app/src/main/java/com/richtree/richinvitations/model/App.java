package com.richtree.richinvitations.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 05/10/2018.
 */

public class App {

    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_platform")
    @Expose
    private String devicePlatform;
    @SerializedName("device_platform_version")
    @Expose
    private String devicePlatformVersion;
    @SerializedName("device_model")
    @Expose
    private String deviceModel;
    @SerializedName("notification_token")
    @Expose
    private String notificationToken;
    @SerializedName("notification_status")
    @Expose
    private Integer notificationStatus;
    @SerializedName("terms_conditions_agreed")
    @Expose
    private Integer termsConditionsAgreed;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Object getDevicePlatform() {
        return devicePlatform;
    }

    public void setDevicePlatform(String devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public Object getDevicePlatformVersion() {
        return devicePlatformVersion;
    }

    public void setDevicePlatformVersion(String devicePlatformVersion) {
        this.devicePlatformVersion = devicePlatformVersion;
    }

    public Object getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public Integer getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Integer notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Integer getTermsConditionsAgreed() {
        return termsConditionsAgreed;
    }

    public void setTermsConditionsAgreed(Integer termsConditionsAgreed) {
        this.termsConditionsAgreed = termsConditionsAgreed;
    }

}
