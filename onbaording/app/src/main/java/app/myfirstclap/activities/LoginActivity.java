package app.myfirstclap.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.R;
import app.myfirstclap.controllers.FacebookLoginController;
import app.myfirstclap.controllers.GoogleController;
import app.myfirstclap.controllers.RetrofitController;
import app.myfirstclap.interfaces.BaseRetrofitCallback;
import app.myfirstclap.interfaces.SocialCallbacks;
import app.myfirstclap.model.User;
import app.myfirstclap.model.UserTopics;
import app.myfirstclap.utils.CONSTANTS;
import app.myfirstclap.utils.TinyDB;
import app.myfirstclap.utils.Utils;
import au.com.dstil.atomicauth.callback.FailureCallback;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

/**
 * Created by TGT on 11/28/2017.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,
        SocialCallbacks, FailureCallback<String>, GoogleController.GetRuntimePermissionscallback,
        GoogleApiClient
                .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private ImageView signinFacebook;
    private ImageView signinGoogle;
    private ImageView signintwitter;
    private LoginButton loginbuttonFacebook;
    private TwitterLoginButton loginbuttontwitter;
    private FacebookLoginController facebookcontroller;
    private CallbackManager callbackManager;
    GoogleController googlecontroller;
    private GoogleApiClient mGoogleApiClient;
    private ConnectionResult connection_result;
    private boolean is_intent_inprogress;
    private boolean is_signInBtn_clicked;
    protected MyFirstClapApplication application;
    Utils utils;
    TinyDB tinydb;
    String TAG = "";
    SocialCallbacks socialallback;
    ProgressDialog dialog;
    User user;
    String onesignalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        buidNewGoogleApiClient();
        super.onCreate(savedInstanceState);
        socialallback = this;
        Twitter.initialize(this);
        application = (MyFirstClapApplication) getApplication();
        application.twitterInit();
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        tinydb = new TinyDB(LoginActivity.this);
        init();

    }

    private void init() {
        utils = new Utils(LoginActivity.this);
        dialog = new ProgressDialog(LoginActivity.this);
        loginbuttontwitter = (TwitterLoginButton) findViewById(R.id.loginbutton_twitter);
        signinFacebook = (ImageView) findViewById(R.id.signin_facebook);
        signinGoogle = (ImageView) findViewById(R.id.signin_google);
        signintwitter = (ImageView) findViewById(R.id.signinin_twitter);

        loginbuttonFacebook = (LoginButton) findViewById(R.id.loginbutton_facebook);
        facebookcontroller = new FacebookLoginController(LoginActivity.this, loginbuttonFacebook, callbackManager);
        googlecontroller = new GoogleController(LoginActivity.this, mGoogleApiClient, this);
        signinFacebook.setOnClickListener(this);
        signinGoogle.setOnClickListener(this);
        twitterloginCallback();
        loginbuttontwitter.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_facebook:
                TAG = "facebook";
                loginbuttonFacebook.performClick();
                facebookcontroller.facebookLogin(this);
                break;
            case R.id.signin_google:
                TAG = "google";
                gPlusSignIn();
                break;
            case R.id.loginbutton_twitter:
                TAG = "twitter";
                twitterloginCallback();

                break;
        }

    }


    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent i) {
        if (TAG.equals("facebook")) {
            callbackManager.onActivityResult(reqCode, resCode, i);
        } else if (TAG.equals("google")) {
            googlecontroller.onActivityResult(reqCode, resCode, i);
        } else {
            loginbuttontwitter.onActivityResult(reqCode, resCode, i);
        }


    }


    @Override
    public void success(User socialusermodel) {

        final User usermodel = addDeviceDetails(socialusermodel);

        if (application.isConnected()) {
            dialog.show();
            Gson gson = new Gson();

            JSONObject object = null;
            try {
                object = new JSONObject(gson.toJson(usermodel));
                object.remove("Id");
                User requestuser = new Gson().fromJson(object.toString(), User.class);
                RetrofitController.postRequestController(application, dialog, requestuser,
                        CONSTANTS.LOGIN, new BaseRetrofitCallback() {
                            @Override
                            public void Success(String success) {
                                User response = new User();
                                try {
                                    JSONObject responsebject = new JSONObject(success);
                                    response = new Gson().fromJson(responsebject.getJSONObject
                                            ("user_profile").toString(), User.class);
                                    List<UserTopics> user_topiclist = new ArrayList<>();
                                    user_topiclist = new Gson().fromJson
                                            (responsebject
                                                            .getJSONArray("user_topics").toString(),
                                                    new TypeToken<List<UserTopics>>() {
                                                    }.getType());

                                    response.setUsertopicslist(new ArrayList<UserTopics>
                                            (user_topiclist));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                tinydb.putObject("user", response);
                                if (
                                        tinydb.getUserObject("user", User.class).getUsertopicslist().size() > 0) {
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(LoginActivity.this, TopicActivity.class));
                                    finish();
                                }


                            }
                        });

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void getRuntimePermissions(int request_code_ask_permissons) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.GET_ACCOUNTS);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},
                        request_code_ask_permissons);
                return;
            } else {
                requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS},
                        request_code_ask_permissons);
                return;
            }
        } else {
            googlecontroller.getProfileInfo();
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == 123) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                googlecontroller.getProfileInfo();
            } else {
                googlecontroller.deniedPermissonError();
            }
        }
    }

    public void buidNewGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }


    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Always assign result first
        connection_result = result;

        if (!result.hasResolution()) {
            isGooglePlayServicesAvailable(LoginActivity.this);
            return;
        }
        if (!is_intent_inprogress) {
            if (is_signInBtn_clicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
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

    public void gPlusSignIn() {
        if (!mGoogleApiClient.isConnecting()) {
            Log.d("user connected", "connected");
            resolveSignInError();

        }
    }

    private void resolveSignInError() {
        if (connection_result.hasResolution()) {
            try {
                is_intent_inprogress = true;
                connection_result.startResolutionForResult(this, 0);
                Log.d("resolve error", "sign in error resolved");
            } catch (IntentSender.SendIntentException e) {
                is_intent_inprogress = false;
                googlecontroller.googleClientConnectionStarted();
            }
        }
    }

    @Override
    public void failure(String s) {
        hideDialog();

    }


    public User addDeviceDetails(User user) {
        String deviceUniqueIdentifier = null;
        deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            user.setType("Phone");
        } else {
            user.setType("Tablet");
        }

        String model = Build.MODEL;
        String versionRelease = Build.VERSION.RELEASE;
        user.setDeviceId(deviceUniqueIdentifier);
        user.setModel(model);
        user.setOs(versionRelease);
        user.setFirebaseToken(tinydb.getString("firebasetoken"));
        return user;
    }


    public void twitterloginCallback() {
        loginbuttontwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                final String token = authToken.token;
                String secret = authToken.secret;
               /* getEmailFomTwitter(session);*/
                TwitterCore.getInstance().getApiClient(session).getAccountService()
                        .verifyCredentials(true, false, true).enqueue(new Callback<com.twitter.sdk
                        .android.core.models.User>() {
                    @Override
                    public void success(Result<com.twitter.sdk.android.core.models.User> result) {
                        com.twitter.sdk.android.core.models.User user = result.data;
                        User usermodel = new User();
                        usermodel.setEmail(user.email);
                        usermodel.setFirstName(user.name);
                        usermodel.setLastName(user.name);
                        usermodel.setImageURL(user.profileImageUrl);
                        usermodel.setProviderId("2");
                        usermodel.setToken(token);
                        socialallback.success(usermodel);

                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Toast.makeText(LoginActivity.this, exception.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LoginActivity.this, exception.toString(), Toast.LENGTH_SHORT).show();
                // Do something on failure
            }
        });

    }


}

