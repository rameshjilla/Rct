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
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import app.myfirstclap.R;
import app.myfirstclap.firebaseconfiguration.Configuration;
import app.myfirstclap.firebaseconfiguration.DeleteTokenService;
import app.myfirstclap.firebaseconfiguration.MyFirebaseInstanceIDService;
import app.myfirstclap.firebaseconfiguration.NotificationUtils;
import app.myfirstclap.firebaseconfiguration.Preferences;

/**
 * Created by admin on 31/12/2017.
 */

public class FireBaseActivity extends Activity {
  private BroadcastReceiver mRegistrationBroadcastReceiver;
  TextView firebaseIdTextView;
  ProgressDialog progressdialog;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_firebase);
    progressdialog = new ProgressDialog(FireBaseActivity.this);
    progressdialog.show();

    Preferences preferences = Preferences.getInstance(this);
    Activity activity = this;
    firebaseIdTextView = (TextView) findViewById(R.id.firebaseId);

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
            firebaseIdTextView.append(token);
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
      progressdialog.dismiss();
      firebaseIdTextView.append(FirebaseInstanceId.getInstance().getToken());
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
}
