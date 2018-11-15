package app.myfirstclap.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.IOException;

import app.myfirstclap.interfaces.SocialCallbacks;
import app.myfirstclap.model.User;
import app.myfirstclap.utils.Utils;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by TGT on 11/10/2017.
 */

public class GoogleController implements GoogleApiClient.ConnectionCallbacks {
  private GoogleApiClient mGoogleApiClient;
  private Context context;
  private static final int SIGN_IN_CODE = 0;
  final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
  private boolean is_intent_inprogress;
  private boolean is_signInBtn_clicked;
  private ConnectionResult connection_result;
  private int request_code;
  ProgressDialog progress_dialog;
  String gaccesstoken;
  private Utils utils;
  SocialCallbacks socialcallback;

  GetRuntimePermissionscallback getruntimepermissionscallback;

  public GoogleController(Context context, GoogleApiClient googleapiclient, GetRuntimePermissionscallback getruntimepermissionscallback) {
    this.context = context;
    progress_dialog = new ProgressDialog(context);
    this.getruntimepermissionscallback = getruntimepermissionscallback;
    this.mGoogleApiClient = googleapiclient;
    utils = new Utils(context);


  }

  public interface GetRuntimePermissionscallback {
    public void getRuntimePermissions(int request_code_ask_permissons);
  }


  @Override
  public void onConnected(Bundle bundle) {
    is_signInBtn_clicked = false;
    getruntimepermissionscallback.getRuntimePermissions(REQUEST_CODE_ASK_PERMISSIONS);
  }

  public void googleClientConnectionStarted() {
    mGoogleApiClient.connect();
  }


  public void onRequestPermissionsResult(int  requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    //Checking the request code of our request
    if (requestCode == 123) {

      //If permission is granted
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        getProfileInfo();

      } else {
        Toast.makeText(context, "Denied", Toast.LENGTH_LONG).show();
        progress_dialog.dismiss();
        gPlusSignOut();
      }
    }

  }


  public void getProfileInfo() {
    try {

      if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        setPersonalInfo(currentPerson);

      } else {
        progress_dialog.dismiss();
        gPlusSignOut();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void onConnectionSuspended(int i) {
    mGoogleApiClient.connect();

  }

  /*@Override
  public void onConnectionFailed(ConnectionResult result) {
    if (!result.hasResolution()) {
      isGooglePlayServicesAvailable((Activity) context);
      return;
    }


    if (!is_intent_inprogress) {
      connection_result = result;
      if (is_signInBtn_clicked) {
        resolveSignInError();
      }
    }

  }*/





  public boolean isGooglePlayServicesAvailable(Activity activity) {
    GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
    int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
    if (status != ConnectionResult.SUCCESS) {
      if (googleApiAvailability.isUserResolvableError(status)) {
        googleApiAvailability.getErrorDialog(activity, status, 1000).show();
      }
      return false;
    }
    return true;
  }

  public void onActivityResult(int reqCode, int resCode, Intent i) {
    if (reqCode == SIGN_IN_CODE) {
      request_code = reqCode;
      if (resCode != RESULT_OK) {
        is_signInBtn_clicked = false;
        progress_dialog.dismiss();
      }
      is_intent_inprogress = false;

      if (!mGoogleApiClient.isConnecting()) {
        mGoogleApiClient.connect();
      }
    }

  }

  public void gPlusSignOut() {
    mGoogleApiClient.connect();

    if (mGoogleApiClient.isConnected()) {
      Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
      mGoogleApiClient.disconnect();


    }
  }

  public void onStart() {
    mGoogleApiClient.connect();

  }

  public void onStop() {
    if (mGoogleApiClient.isConnected()) {
      mGoogleApiClient.disconnect();
    }

  }

  private void setPersonalInfo(Person currentPerson) {
    String gmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
    final String firstname = currentPerson.getName().getGivenName();
    final String lastname = currentPerson.getName().getFamilyName();
    final User user = new User();
    user.setEmail(gmail);
    user.setFirstName(firstname);
    user.setLastName(lastname);
    progress_dialog.dismiss();
    Log.d("Details", gmail + " " + firstname + " " + lastname);

    AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
      @Override
      protected String doInBackground(Void... params) {
        String token = null;
        final String SCOPES = "https://www.googleapis.com/auth/plus.login ";
        try {
          String gaccesstoken = GoogleAuthUtil.getToken(
                  getApplicationContext(),
                  Plus.AccountApi.getAccountName(mGoogleApiClient),
                  "oauth2:" + SCOPES);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (GoogleAuthException e) {
          e.printStackTrace();
        }

        return gaccesstoken;
      }

      @Override
      protected void onPostExecute(String token) {
        progress_dialog.dismiss();
        socialcallback.success(user);
        gPlusSignOut();


      }
    };
    task.execute();
  }

  public void deniedPermissonError() {
    utils.showToastMessage("We need your email as mandatory field");
    progress_dialog.dismiss();
    gPlusSignOut();
  }


}
