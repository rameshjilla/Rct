package com.richtree.richinvitations.activities;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import com.richtree.richinvitations.ForgotPassword;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.Register;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.model.App;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView already, forgot;
    MyApplication myapplication;
    EditText emailedit;
    TinyDB tinydb;
    String isvalidphonenumberregexp = "^[7-9][0-9]{9}$";
    String android_id;
    String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myapplication = (MyApplication) getApplication();
        already = (TextView) findViewById(R.id.link_signup);
        login = (Button) findViewById(R.id.btn_login);
        emailedit = (EditText) findViewById(R.id.email);
        forgot = (TextView) findViewById(R.id.forgot);
        tinydb = new TinyDB(LoginActivity.this);
        android_id = Settings.Secure.getString
                (getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
        token = FirebaseInstanceId.getInstance().getToken();
        Log.d("logintoken", token);


        User user = tinydb.getObject("user", User.class);


        if (user != null) {
            if (tinydb.getObject("user", User.class).getCustomerId() != 0 && tinydb.getObject
                    ("user",
                            User.class).getCustomerId() != 0) {

                if (user.getApproved() == 0) {

                } else if (user.getStatus() == 0) {

                } else if (!user.getApp().getDeviceId().equals(android_id)) {
                    showAlert();
                } else {

                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    ComponentName cn = intent.getComponent();
                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailedit.getText().toString().isEmpty()) {
                    if (emailedit.getText().toString().contains("@")) {
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailedit.getText().toString())
                                .matches() && !emailedit.getText().toString().isEmpty()) {
                            showToast("Please Enter a valid Email");
                        } else {
                            loginUser();
                        }
                    } else if (!(emailedit.getText().toString()).matches
                            (isvalidphonenumberregexp)) {
                        showToast("Please Enter a valid Phone Number");

                    } else {
                        loginUser();
                    }


                } else {
                    Toast.makeText(LoginActivity.this, "Please Enter The Email or Mobile", Toast.LENGTH_SHORT).show();
                }

            }
        });

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void loginUser() {
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.show();
        JSONObject object = new JSONObject();
        try {

            if (emailedit.getText().toString().contains("@")) {
                object.put("email", emailedit.getText().toString());
            } else {
                object.put("email", emailedit.getText().toString() + "@richevents.in");
            }

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
                                App app = new Gson().fromJson(dataobject.getJSONObject("app").toString(),
                                        App
                                                .class);
                                user.setApp(app);
                                tinydb.putObject("user", user);

                                if (user.getApproved() == 0) {
                                    Toast.makeText(myapplication, "Not Approved",
                                            Toast
                                                    .LENGTH_SHORT).show();
                                } else if (user.getStatus() == 0) {
                                    Toast.makeText(myapplication, "Status Failed",
                                            Toast
                                                    .LENGTH_SHORT).show();
                                } else if (!user.getApp().getDeviceId().equals(android_id)) {
                                    showAlert();

                                } else if (!user.getApp().getNotificationToken().equals(token)) {
                                    updateProfile(user);

                                } else {
                                    Intent intent = new Intent(LoginActivity.this,
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


    public void showAlert() {
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog
                .Builder(LoginActivity.this);
        alert.setTitle("Different Device");
        alert.setMessage("Your account is already registered with other device.\nPlease de-register current device using signup.");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                User user = tinydb.getObject("user", User.class);
                Intent intent = new Intent(LoginActivity.this, AlreadyRegisteredActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", user.getEmail());
                intent.putExtras(bundle);
                tinydb.remove("user");
                startActivity(intent);

            }
        });


        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();


            }
        });
        android.app.AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.RED);
    }

    public void updateProfile(User user) {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;
        user.getApp().setNotificationToken(token);
        user.getApp().setDevicePlatform("Android");
        user.getApp().setDeviceModel(model);
        user.setPassword("user@123");
        user.getApp().setDevicePlatformVersion(versionRelease);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.show();
        RetrofitController.putRequestController(myapplication, dialog, json, "account/update",
                tinydb.getString("accesstoken"), new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        try {
                            JSONObject object = new JSONObject(success);
                            if (object.getInt("code") == 100) {
                                loginUser();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
