package app.myfirstclap.interfaces;

import android.content.Context;
import android.content.Intent;

public interface GoogleSignInPresenter {
  void createGoogleClient(Context context);

  void onStart();

  void signIn(Context context);

  void onActivityResult(Context context, int requestCode, int resultCode, Intent data);

  void onStop();

  void onDestroy();
}