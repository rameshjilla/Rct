package app.myfirstclap.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.IOException;

import app.myfirstclap.R;

/**
 * Created by admin on 09/11/2017.
 */

public class GoogleActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
  private GoogleApiClient mGoogleApiClient;


  private static final int SIGN_IN_CODE = 0;
  final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
  private boolean is_intent_inprogress;
  private boolean is_signInBtn_clicked;
  private ConnectionResult connection_result;
  private int request_code;
  ProgressDialog progress_dialog;
  String gaccesstoken;
  Button button;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    buidNewGoogleApiClient();
    super.onCreate(savedInstanceState);
    progress_dialog = new ProgressDialog(GoogleActivity.this);
    setContentView(R.layout.activity_google);
    button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        gPlusSignIn();
      }
    });

   /* button.performClick();*/

  }


  private void buidNewGoogleApiClient() {
    mGoogleApiClient = new GoogleApiClient.Builder(GoogleActivity.this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(Plus.API, Plus.PlusOptions.builder().build())
            .addScope(Plus.SCOPE_PLUS_PROFILE)
            .addScope(Plus.SCOPE_PLUS_LOGIN)
            .build();


  }


  @Override
  public void onConnected(@Nullable Bundle bundle) {
    is_signInBtn_clicked = false;
    // Get user's information and set it into the layout


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

      int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.GET_ACCOUNTS);
      if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},
                REQUEST_CODE_ASK_PERMISSIONS);
        return;
      } else {
        requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},
                REQUEST_CODE_ASK_PERMISSIONS);
        return;
      }
    } else {
      getProfileInfo();
    }

  }

  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    //Checking the request code of our request
    if (requestCode == 123) {

      //If permission is granted
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        getProfileInfo();

      } else {
        Toast.makeText(GoogleActivity.this, "Denied", Toast.LENGTH_LONG).show();
        progress_dialog.dismiss();
        gPlusSignOut();
      }
    }

  }


  private void getProfileInfo() {
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

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult result) {
    if (!result.hasResolution()) {
      isGooglePlayServicesAvailable(GoogleActivity.this);
      return;
    }


    if (!is_intent_inprogress) {
      connection_result = result;
      if (is_signInBtn_clicked) {
        resolveSignInError();
      }
    }

  }


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


  private void resolveSignInError() {
    if (connection_result.hasResolution()) {
      try {
        is_intent_inprogress = true;
        connection_result.startResolutionForResult(this, SIGN_IN_CODE);
        Log.d("resolve error", "sign in error resolved");
      } catch (IntentSender.SendIntentException e) {
        is_intent_inprogress = false;
        mGoogleApiClient.connect();
      }
    }
  }

  private void gPlusSignIn() {
    if (!mGoogleApiClient.isConnecting()) {
      Log.d("user connected", "connected");
      is_signInBtn_clicked = true;
      progress_dialog.show();
      resolveSignInError();
    }
  }

  private void setPersonalInfo(Person currentPerson) {
    String gmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
    final String firstname = currentPerson.getName().getGivenName();
    final String lastname = currentPerson.getName().getFamilyName();
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


        gPlusSignOut();


      }
    };
    task.execute();
  }

  public void gPlusSignOut() {
    mGoogleApiClient.connect();

    if (mGoogleApiClient.isConnected()) {
      Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
      mGoogleApiClient.disconnect();
      //google_api_client.connect();

    }
  }

  protected void onStart() {
    super.onStart();
    mGoogleApiClient.connect();
  }

  protected void onStop() {
    super.onStop();
    if (mGoogleApiClient.isConnected()) {
      mGoogleApiClient.disconnect();
    }
  }

  protected void onResume() {
    super.onResume();

    if (mGoogleApiClient.isConnected()) {
      mGoogleApiClient.connect();
    }
  }

  @Override
  protected void onActivityResult(int reqCode, int resCode, Intent i) {
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


}
