package com.richtree.richinvitations.activities;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.controllers.UserController;
import com.richtree.richinvitations.interfaces.LoginCallback;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.model.UserBaseModel;
import com.richtree.richinvitations.utils.TinyDB;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 06/10/2018.
 */

public class RegisteredOtpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView country_code, mobilenumber, resend;
    Button enter;
    EditText et_otp;
    String otp;
    Bundle bundle;
    MyApplication myapplication;
    String phonenumber;
    TinyDB tinydb;
    UserBaseModel userbasemodel;
    ProgressDialog dialog;
    String android_id;
    String email;
    User user;
    SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        otp = bundle.getString("otp");
        phonenumber = bundle.getString("phonenumber");
        tinydb = new TinyDB(RegisteredOtpActivity.this);
        user = tinydb.getObject("user", User.class);
        Log.d("bundleotp", otp);
        dialog = new ProgressDialog(RegisteredOtpActivity.this);
        myapplication = (MyApplication) getApplication();
        android_id = Settings.Secure.getString
                (getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

       /* if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/

        setContentView(R.layout.activity_otp);

        country_code = (TextView) findViewById(R.id.country_code);
        mobilenumber = (TextView) findViewById(R.id.mobilenumber);

        if (phonenumber.contains("@richevents.in")) {
            String usernamemod = phonenumber.replace("@richevents.in", "");
            String phoennumber = usernamemod.replaceAll("\\d(?=\\d{4})", "*");
            mobilenumber.setText(phoennumber);

        } else {
            String maskemail = phonenumber.replaceAll("(?<=.{3}).(?=.*@)", "*");
            mobilenumber.setText(maskemail);

        }

        resend = (TextView) findViewById(R.id.resend);
        enter = (Button) findViewById(R.id.enter);
        et_otp = (EditText) findViewById(R.id.otp);
        smsVerifyCatcher = new SmsVerifyCatcher(this, new
                OnSmsCatchListener<String>() {
                    @Override
                    public void onSmsCatch(String message) {
                        //Parse verification code
                        et_otp.setText(message);//set code in edit text
                        //then you can send verification code to server
                    }
                });

        smsVerifyCatcher.setPhoneNumberFilter("MD-RICHTR");
     /*   if (phonenumber.contains("@")) {
            country_code.setVisibility(View.GONE);
            String maskemail = phonenumber.replaceAll("(?<=.{3}).(?=.*@)", "*");
            mobilenumber.setText(maskemail);
        }*/
        enter.setOnClickListener(this);
        resend.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.enter:
                if (otp.equals(et_otp.getText().toString())) {
                    user.setPassword("user@123");
                    user.getApp().setDeviceId(android_id);
                    user.getApp().setDevicePlatform("Android");
                    user.getApp().setDevicePlatformVersion(Build.VERSION.RELEASE);
                    user.getApp().setDeviceModel(Build.MODEL);
                    user.getApp().setNotificationToken(FirebaseInstanceId.getInstance().getToken());
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    updateAccount(json, "account/update", user.getEmail());

                } else {
                    Toast.makeText(RegisteredOtpActivity.this, "Otp doesnt match", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case R.id.resend:
                Intent skip = new Intent(this, OTPActivity.class);
                startActivity(skip);
                finish();
                break;

        }
    }


    public void login() {
        final ProgressDialog dialog = new ProgressDialog(RegisteredOtpActivity.this);
        JSONObject object = new JSONObject();
        dialog.show();
        try {
            object.put("email", phonenumber + "@richevents.in");
            object.put("password", "user@123");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        UserController.loginUser(myapplication, object.toString(), tinydb.getString("session"),
                dialog, new LoginCallback() {
                    @Override
                    public void success(UserBaseModel user) {
                        dialog.dismiss();
                        userbasemodel = user;
                        if (user.getSuccess()) {
                            tinydb.putObject("user", userbasemodel);
                            Intent intent = new Intent(RegisteredOtpActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            ComponentName cn = intent.getComponent();
                            Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                            startActivity(mainIntent);
                            finish();

                        } else {
                            Toast.makeText(RegisteredOtpActivity.this, "User Doest Exist", Toast
                                    .LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void fail(String fail) {
                        dialog.dismiss();
                        Toast.makeText(RegisteredOtpActivity.this, fail, Toast.LENGTH_SHORT).show();

                    }
                });
    }


    public void updateAccount(String request, String path, final String email) {
        RetrofitController.putRequestController(myapplication, dialog, request, path, tinydb
                .getString("accesstoken"), new
                NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        try {
                            JSONObject jsonObject = new JSONObject(success);

                            if (jsonObject.getInt("code") == 100) {
                                loginUser(email);

                            } else {
                                Toast.makeText(RegisteredOtpActivity.this, jsonObject.getString("message"), Toast
                                        .LENGTH_SHORT)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public void loginUser(String email) {
        final ProgressDialog dialog = new ProgressDialog(RegisteredOtpActivity.this);
        dialog.show();
        JSONObject object = new JSONObject();
        try {
            object.put("email", email);
            object.put("password", "user@123");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RetrofitController.postRequestController(myapplication, dialog, object.toString(),
                "login", "",
                new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {

                        try {
                            JSONObject responseobject = new JSONObject(success);
                            if (responseobject.getBoolean("success")) {
                                tinydb.putString("accesstoken", responseobject.getString("accessToken"));
                                JSONObject dataobject = responseobject.getJSONObject("data");
                                User user = new Gson().fromJson(dataobject.toString(), User.class);
                                tinydb.putObject("user", user);

                                if (user.getApproved().equals("0")) {
                                    Toast.makeText(myapplication, "Not Approved",
                                            Toast
                                                    .LENGTH_SHORT).show();
                                } else if (user.getStatus().equals("0")) {
                                    Toast.makeText(myapplication, "Status Failed",
                                            Toast
                                                    .LENGTH_SHORT).show();
                                } else if (!user.getApp().getDeviceId().equals(android_id)) {
                                    Toast.makeText(myapplication, "Different Device id",
                                            Toast
                                                    .LENGTH_SHORT).show();
                                } else {

                                    Intent intent = new Intent(RegisteredOtpActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    ComponentName cn = intent.getComponent();
                                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                    startActivity(mainIntent);
                                    finish();
                                }


                            } else {
                                Toast.makeText(myapplication, responseobject.getString("message"),
                                        Toast
                                                .LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                });
    }
}
