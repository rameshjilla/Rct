package app.myfirstclap.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.controllers.RetrofitController;
import app.myfirstclap.firebaseconfiguration.Configuration;
import app.myfirstclap.firebaseconfiguration.DeleteTokenService;
import app.myfirstclap.firebaseconfiguration.MyFirebaseInstanceIDService;
import app.myfirstclap.firebaseconfiguration.NotificationUtils;
import app.myfirstclap.firebaseconfiguration.Preferences;
import app.myfirstclap.interfaces.BaseRetrofitCallback;
import app.myfirstclap.model.Topic;
import app.myfirstclap.model.User;
import app.myfirstclap.utils.CONSTANTS;
import app.myfirstclap.utils.TinyDB;

/**
 * Created by admin on 14/01/2018.
 */

public class SplashActivity extends AppCompatActivity {
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ProgressDialog progressdialog;
    TinyDB tinydb;
    MyFirstClapApplication myapplication;
    List<Topic> topiclist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinydb = new TinyDB(SplashActivity.this);
        myapplication = (MyFirstClapApplication) getApplication();


        if (tinydb.getString("firebasetoken") != null && !tinydb.getString("firebasetoken").isEmpty()) {
            if (tinydb.getUserObject("user", User.class) != null) {
                if (!tinydb.getUserObject("user", User.class).getUsertopicslist().isEmpty()) {
                    navigateToActivity(SplashActivity.this, HomeActivity.class);
                    return;
                } else {
                    navigateToActivity(SplashActivity.this, TopicActivity.class);
                    return;
                }
            } else {
                navigateToActivity(SplashActivity.this, LoginActivity.class);
                return;
            }


        }


        progressdialog = new ProgressDialog(SplashActivity.this);
        progressdialog.show();

        Preferences preferences = Preferences.getInstance(this);
        Activity activity = this;

        if (tinydb.getString("topicobject") == null && tinydb.getString("topicobject").isEmpty
                ()) {
            getTopics();
        }


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
                        tinydb.putString("firebasetoken", token);
                        navigateToLoginActivity();
                        FirebaseMessaging.getInstance().subscribeToTopic(Configuration.TOPIC_GLOBAL);
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

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    public void navigateToLoginActivity() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }


    public static void navigateToActivity(Activity from, Class<?> to) {
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
        from.finish();

    }

    public void getTopics() {
        progressdialog.show();
        RetrofitController.getRequestController(myapplication, progressdialog, CONSTANTS.GET_TOPICS, new BaseRetrofitCallback() {
            @Override
            public void Success(String success) {
                tinydb.putString("topicobject", success);

            }
        });
    }

}
