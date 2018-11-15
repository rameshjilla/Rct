package app.myfirstclap.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Set;

import app.myfirstclap.R;
import app.myfirstclap.interfaces.SocialCallbacks;
import app.myfirstclap.model.User;
import app.myfirstclap.utils.Utils;

/**
 * Created by TGT on 11/8/2017.
 */

public class FacebookLoginController {
  private Context context;
  private LoginButton loginbutton;
  Utils utils;
  private CallbackManager callbackManager;
  private static final int RC_SIGN_IN_FB = 1916;


  public FacebookLoginController(Context context, LoginButton loginbutton, CallbackManager callbackmanager) {
    this.loginbutton = loginbutton;
    this.context = context;
    utils = new Utils(context);
    this.callbackManager = callbackmanager;


  }

  public void facebookLogin(final SocialCallbacks socialcallback) {
    loginbutton.setReadPermissions(Arrays.asList(
            "public_profile", "user_birthday", "user_friends"));


    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(final LoginResult loginResult) {


        if (loginResult.getAccessToken() != null) {
          Set<String> deniedPermissions = loginResult.getRecentlyDeniedPermissions();
          if (deniedPermissions.contains("email")) {
            LoginManager.getInstance().logInWithReadPermissions((Activity) context,
                    Arrays.asList("email"));
            Toast.makeText(context, "Email Persmission is required", Toast.LENGTH_SHORT).show();
          } else {
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                      @Override
                      public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                          String email = response.getJSONObject().getString("email");
                          String firstname = response.getJSONObject().getString("first_name");
                          String lastname = response.getJSONObject().getString("last_name");
                          String profileImg = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=large&width=1080";
                          Log.d("imageurl", profileImg);
                          User user = new User();
                          user.setEmail(email);
                          user.setFirstName(firstname);
                          user.setLastName(lastname);
                          user.setImageURL(profileImg);
                          user.setToken(AccessToken.getCurrentAccessToken().getToken());
                          user.setProviderId("1");
                          LoginManager.getInstance().logOut();
                          socialcallback.success(user);
                        } catch (JSONException e) {
                          e.printStackTrace();
                          String message = context.getString(R.string.error_fail);
                          utils.showToastMessage(message);
                        }
                      }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,email,first_name,last_name,gender, " +
                    "birthday,name,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();

          }

        }

      }

      @Override
      public void onCancel() {

      }

      @Override
      public void onError(FacebookException error) {
        utils.showToastMessage(error.toString());

      }
    });


  }


}

