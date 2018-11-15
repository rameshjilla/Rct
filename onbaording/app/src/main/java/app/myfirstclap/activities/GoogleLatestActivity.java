package app.myfirstclap.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import app.myfirstclap.R;

/**
 * Created by TGT on 11/10/2017.
 */

public class GoogleLatestActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

  private Button signInButton;
  private GoogleSignInOptions gso;
  private GoogleApiClient mGoogleApiClient;
  private int SIGN_IN = 30;
  private TextView tv;
  private ImageView iv;
  final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
  private Button btn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_google);

    gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    signInButton = (Button) findViewById(R.id.button);
    mGoogleApiClient = new GoogleApiClient.Builder(this)
            .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .addApi(Plus.API)
            .build();

    signInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN);
      }
    });


  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //If signin
    if (requestCode == SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      //Calling a new function to handle signin


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
        handleSignInResult(result);
      }


    }
  }



  private void handleSignInResult(GoogleSignInResult result) {
    //If the login succeed
    if (result.isSuccess()) {
      //Getting google account
      final GoogleSignInAccount acct = result.getSignInAccount();

      //Displaying name and email
      String name = acct.getDisplayName();
      final String mail = acct.getEmail();
      // String photourl = acct.getPhotoUrl().toString();

      final String givenname = "", familyname = "", displayname = "", birthday = "";

      Plus.PeopleApi.load(mGoogleApiClient, acct.getId()).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
        @Override
        public void onResult(@NonNull People.LoadPeopleResult loadPeopleResult) {
          Person person = loadPeopleResult.getPersonBuffer().get(0);

          Log.d("GivenName ", person.getName().getGivenName());
          Log.d("FamilyName ", person.getName().getFamilyName());
          Log.d("DisplayName ", person.getDisplayName());
          Log.d("gender ", String.valueOf(person.getGender())); //0 = male 1 = female


          Log.d("Uriddd", acct.getPhotoUrl().toString());
                  /*   Log.d(TAG,"CurrentLocation "+person.getCurrentLocation());
                    Log.d(TAG,"AboutMe "+person.getAboutMe());*/
          // Log.d("Birthday ",person.getBirthday());
          // Log.d(TAG,"Image "+person.getImage());
        }
      });
    } else {
      //If login fails
      Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {

  }
}
