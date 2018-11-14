package com.richtree.richinvitations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.richtree.richinvitations.activities.LoginActivity;
import com.richtree.richinvitations.activities.MainActivity;
import com.richtree.richinvitations.controllers.UserController;
import com.richtree.richinvitations.interfaces.SessionCallback;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.utils.TinyDB;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends AwesomeSplash {
    ProgressDialog dialog;
    MyApplication application;
    TinyDB tinydb;
    Bundle bundle;
    boolean isLoggedout = false;

    @Override
    public void initSplash(ConfigSplash configSplash) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        bundle = getIntent().getExtras();

        if (bundle != null && !bundle.isEmpty()) {

            isLoggedout = true;

        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        configSplash.setBackgroundColor(R.color.splash);
        configSplash.setAnimCircularRevealDuration(3000);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);

        configSplash.setTitleSplash(getString(R.string.invitation));
        configSplash.setTitleTextColor(R.color.red);
        configSplash.setTitleTextSize(100);
        configSplash.setTitleFont("fonts/invitation.ttf");
        configSplash.setAnimTitleDuration(6000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);

        configSplash.setLogoSplash(R.drawable.rich);
        configSplash.setAnimLogoSplashDuration(3000);
        configSplash.setAnimLogoSplashTechnique(Techniques.ZoomIn);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(Splash.this);
        application = (MyApplication) getApplication();
        tinydb = new TinyDB(Splash.this);
        if (tinydb.getString("session") != null && !tinydb.getString("session").isEmpty()) {
            if (tinydb.getObject("user", User.class) != null) {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(Splash.this, Register.class);
                startActivity(intent);
                finish();
            }

        } else {
            getSession();
        }
    }

    @Override
    public void animationsFinished() {


    }

    public void getSession() {
        dialog.show();
        UserController.getSession(application, new SessionCallback() {
            @Override
            public void success(String success) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(success);
                    String message = object.getString("success");

                    if (message.equals("true")) {
                        JSONObject dataobject = object.getJSONObject("data");
                        String session = dataobject.getString("session");
                        tinydb.putString("session", session);
                        Log.d("session", session);


                        if (tinydb.getObject("user", User.class) != null) {
                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if(isLoggedout){
                                Intent intent = new Intent(Splash.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(Splash.this, Register.class);
                                startActivity(intent);
                                finish();
                            }



                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void fail(String fail) {
                dialog.dismiss();
                Toast.makeText(Splash.this, fail, Toast.LENGTH_SHORT).show();

            }
        }, dialog);
    }
}