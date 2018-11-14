package com.richtree.richinvitations.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.firebaseconfiguration.Configuration;
import com.richtree.richinvitations.firebaseconfiguration.DeleteTokenService;
import com.richtree.richinvitations.firebaseconfiguration.MyFirebaseInstanceIDService;
import com.richtree.richinvitations.firebaseconfiguration.NotificationUtils;
import com.richtree.richinvitations.firebaseconfiguration.Preferences;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.utils.TinyDB;

/**
 * Created by admin on 12/10/2018.
 */

public class SplashActivity extends AppCompatActivity {

    TinyDB tinydb;
    User user;
    MyApplication application;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ProgressDialog progressdialog;

/*TeTestst*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkForCrashes();
        tinydb = new TinyDB(SplashActivity.this);
        application = (MyApplication) getApplication();

     /*   if (tinydb.getString("firebasetoken") != null && !tinydb.getString("firebasetoken").isEmpty()) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
            return;
        }*/

        progressdialog = new ProgressDialog(SplashActivity.this);
        progressdialog.show();
        progressdialog.setCancelable(false);

        Preferences preferences = Preferences.getInstance(this);
        Activity activity = this;

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;

            if (preferences.getVersionCode() != versionCode) {

                System.out.println("Version code changed");

                Intent intentService = new Intent(activity, DeleteTokenService.class);
                activity.startService(intentService);

                preferences.setVersionCode(versionCode);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Configuration.REGISTRATION_COMPLETE)) {

                    // fcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    String token = intent.getStringExtra("token");
                    if (token != null && !token.isEmpty()) {
                        progressdialog.dismiss();
                        Log.d("Token", FirebaseInstanceId.getInstance().getToken());
                        tinydb.putString("firebasetoken", token);
                        navigateToLoginActivity();
                        FirebaseMessaging.getInstance().subscribeToTopic(Configuration.TOPIC_GLOBAL);
                        return;
                    } else {
                        Toast.makeText(context, "No Network connectivity", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        };

        if (FirebaseInstanceId.getInstance().getToken() != null && !FirebaseInstanceId.getInstance()
                .getToken().isEmpty()) {
            Log.d("Token", FirebaseInstanceId.getInstance().getToken());
            tinydb.putString("firebasetoken", FirebaseInstanceId.getInstance().getToken());

            navigateToLoginActivity();
            progressdialog.dismiss();
        } else {
            checkTokenForNull();
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        checkForCrashes();
    }

    public void checkTokenForNull() {
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        if (TextUtils.isEmpty(deviceToken)) {
            Intent intent = new Intent(this, MyFirebaseInstanceIDService.class);
            startService(intent);
            return;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Configuration.REGISTRATION_COMPLETE));


        // register new push postUrlFromNotification receiver
        // by doing this, the activity will be notified each time a new postUrlFromNotification arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Configuration.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    private void checkForCrashes() {
    /*CrashManager.register(SplashActivity.this);*/
    }

    public void navigateToLoginActivity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }


}
