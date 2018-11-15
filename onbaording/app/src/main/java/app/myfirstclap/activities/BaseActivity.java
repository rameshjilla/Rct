package app.myfirstclap.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.R;


public class BaseActivity extends AppCompatActivity {

  protected MyFirstClapApplication application;

  private String test = "This is great";
  ProgressDialog mProgressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    application = (MyFirstClapApplication) getApplication();
    mProgressDialog = new ProgressDialog(BaseActivity.this);
  }

  protected void setupToolbar() {
    setupToolbar(false);
  }

  /**
   * Configure the toolbar, with or without a back button
   *
   * @param homeEnabled - Enable the back button
   */
  protected void setupToolbar(boolean homeEnabled) {
    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
    }

    if (homeEnabled) {
      final ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
      }
    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  public void showDialog() {

    if (mProgressDialog != null && !mProgressDialog.isShowing())
      mProgressDialog.show();
  }

  public void hideDialog() {

    if (mProgressDialog != null && mProgressDialog.isShowing())
      mProgressDialog.dismiss();
  }

  public static void applyFontForToolbarTitle(Activity context) {
    Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);
    for (int i = 0; i < toolbar.getChildCount(); i++) {
      View view = toolbar.getChildAt(i);
      if (view instanceof TextView) {
        TextView tv = (TextView) view;
        Typeface titleFont = Typeface.
                createFromAsset(context.getAssets(), "fonts/Poppins-Regular.ttf");
        if (tv.getText().equals(toolbar.getTitle())) {
          tv.setTypeface(titleFont);
          break;
        }
      }
    }
  }


}
