package app.myfirstclap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by TGT on 11/8/2017.
 */

public class User<T> {
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("ImageURL")
    @Expose
    private Object imageURL;
    @SerializedName("ProviderId")
    @Expose
    private String providerId;
    @SerializedName("FirebaseToken")
    @Expose
    private String firebaseToken;
    @SerializedName("DeviceId")
    @Expose
    private String deviceId;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Os")
    @Expose
    private String os;
    @SerializedName("Model")
    @Expose
    private String model;

    public ArrayList<UserTopics> getUsertopicslist() {
        return usertopicslist;
    }

    public void setUsertopicslist(ArrayList<UserTopics> usertopicslist) {
        this.usertopicslist = usertopicslist;
    }

    private ArrayList<UserTopics> usertopicslist;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getImageURL() {
        return imageURL;
    }

    public void setImageURL(Object imageURL) {
        this.imageURL = imageURL;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


}
