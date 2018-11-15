package app.myfirstclap.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

/**
 * Created by TGT on 6/7/2018.
 */

public class TestActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getAdId();
  }

  public void getAdId() {
    Thread thread = new Thread() {
      @Override
      public void run() {
        try {
          AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
          String advertisingId = adInfo != null ? adInfo.getId() : null;
          Log.d("advertisingid", advertisingId);
        } catch (IOException | GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException exception) {
          exception.printStackTrace();
        }
      }
    };

    // call thread start for background process
    thread.start();
  }



}
