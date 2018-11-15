package app.myfirstclap.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by TGT on 11/9/2017.
 */

public final class Utils {
  static Context context;
  public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

  public Utils(Context context) {
    this.context = context;
  }

  public void showToastMessage(String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }

  public boolean validate(EditText[] fields) {
    for (int i = 0; i < fields.length; i++) {
      EditText currentField = fields[i];
      if (currentField.getText().toString().length() <= 0) {
        return false;
      }
    }
    return true;
  }

  public String loadJSONFromAsset(String filename) {
    String json = null;
    try {
      InputStream is = context.getAssets().open(filename);
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      json = new String(buffer, "UTF-8");
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
    return json;
  }

  public boolean isValidPhoneNumber(CharSequence phoneNumber) {
    if (!TextUtils.isEmpty(phoneNumber)) {
      return Patterns.PHONE.matcher(phoneNumber).matches();
    }
    return false;
  }

  public static boolean isInternetOn() {
    ConnectivityManager connec = (ConnectivityManager) context.
            getSystemService(Context.CONNECTIVITY_SERVICE);

    // ARE WE CONNECTED TO THE NET
    if (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ||
            connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) {
      // MESSAGE TO SCREEN FOR TESTING (IF REQ)
      //Toast.makeText(this, connectionType + ” connected”, Toast.LENGTH_SHORT).show();
      return true;
    } else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
            || connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
      return false;
    }

    return false;
  }

  public static boolean checkPermission(final Context context) {

    int currentAPIVersion = Build.VERSION.SDK_INT;
    if (currentAPIVersion >= Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
          AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
          alertBuilder.setCancelable(true);
          alertBuilder.setTitle("Permission necessary");
          alertBuilder.setMessage("External storage permission is necessary");
          alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
              ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
          });
          AlertDialog alert = alertBuilder.create();
          alert.show();

        } else {
          ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  public static class CircleTransform extends BitmapTransformation {
    public CircleTransform(Context context) {
      super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
      return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
      if (source == null) return null;

      int size = Math.min(source.getWidth(), source.getHeight());
      int x = (source.getWidth() - size) / 2;
      int y = (source.getHeight() - size) / 2;

      // TODO this could be acquired from the pool too
      Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

      Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
      if (result == null) {
        result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
      }

      Canvas canvas = new Canvas(result);
      Paint paint = new Paint();
      paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
      paint.setAntiAlias(true);
      float r = size / 2f;
      canvas.drawCircle(r, r, r, paint);
      return result;
    }

    @Override
    public String getId() {
      return getClass().getName();
    }
  }


}
