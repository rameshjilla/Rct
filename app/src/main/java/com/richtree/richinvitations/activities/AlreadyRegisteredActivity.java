package com.richtree.richinvitations.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.controllers.UserController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.interfaces.OtpCallback;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;

/**
 * Created by admin on 07/10/2018.
 */

public class AlreadyRegisteredActivity extends AppCompatActivity implements View.OnClickListener {
    EditText phoneEt;
    ImageView register;
    TextView already;
    MyApplication myapplication;
    ProgressDialog dialog;
    TinyDB tinydb;
    String android_id;
    String otp;
    String username;
    String isvalidphonenumberregexp = "^[7-9][0-9]{9}$";

    String path;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinydb = new TinyDB(AlreadyRegisteredActivity.this);
        myapplication = (MyApplication) getApplication();
        android_id = android.provider.Settings.Secure.getString
                (getApplicationContext().getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
        bundle = getIntent().getExtras();

        /*if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/

        setContentView(R.layout.activity_already_registered);
        dialog = new ProgressDialog(AlreadyRegisteredActivity.this);

        phoneEt = (EditText) findViewById(R.id.email_mobile);
        register = (ImageView) findViewById(R.id.getotp);


        username = bundle.getString("username");

        if (username.contains("@richevents.in")) {
            String usernamemod = username.replace("@richevents.in", "");
            Log.d("username", usernamemod);
            phoneEt.setText(usernamemod);
        } else {
            phoneEt.setText(username);
        }

        register.setOnClickListener(this);


    }

    public String generateRandomNumber() {
        String formatted = null;
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        formatted = String.format("%05d", num);
        return formatted;

    }


    public void requestotpFromServer() {
        dialog.show();

        otp = generateRandomNumber();
        Log.d("registerotp", otp);
        UserController.getOtp(myapplication, "richtree", "123456", "RICHTR", dialog, phoneEt
                        .getText
                                ().toString(),
                otp, new
                        OtpCallback() {
                            @Override
                            public void Success(String message) {
                                dialog.dismiss();
                                if (message.contains("Success")) {
                                    Intent intent = new Intent(AlreadyRegisteredActivity.this, OTPActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("otp", otp);
                                    bundle.putString("phonenumber", phoneEt.getText().toString());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(myapplication, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }


                            }

                            @Override
                            public void failure(String failre) {
                                dialog.dismiss();
                                Toast.makeText(myapplication, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        });
    }


    public void loginUser() {
        final ProgressDialog dialog = new ProgressDialog(AlreadyRegisteredActivity.this);
        dialog.show();
        JSONObject object = new JSONObject();
        try {


            object.put("email", username);
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
                            if (responseobject.getInt("code") == 100) {
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
                                } else {
                                    JSONObject requestobject = new JSONObject();
                                    otp = generateRandomNumber();

                                    if (username.contains("@richevents.in")) {
                                        requestobject.put("mobile", phoneEt.getText().toString());
                                        path = "notify/sms/otp";
                                    } else {
                                        requestobject.put("email", phoneEt.getText().toString());
                                        path = "notify/email/otp";
                                    }


                                    requestobject.put("otp", otp);

                                    sendOtP(requestobject.toString(), path);

                                }
                            } else if (responseobject.getInt("code") == 101) {


                                Toast.makeText(AlreadyRegisteredActivity.this, responseobject
                                        .getString("message"), Toast
                                        .LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                });

    }


    public void sendOtP(String request, String path) {

        RetrofitController.postRequestController(myapplication, dialog, request, path, "", new NewBaseRetrofitCallback() {
            @Override
            public void Success(String success) {

                try {
                    JSONObject object = new JSONObject(success);

                    if (object.getInt("code") == 100) {
                        Intent intent = new Intent(AlreadyRegisteredActivity.this, RegisteredOtpActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("otp", otp);
                        bundle.putString("phonenumber", username);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AlreadyRegisteredActivity.this, object.getString("message"), Toast
                                .LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getotp:

                if (!phoneEt.getText().toString().isEmpty()) {
                    if (phoneEt.getText().toString().contains("@")) {
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(phoneEt.getText().toString())
                                .matches() && !phoneEt.getText().toString().isEmpty()) {
                            showToast("Please Enter a valid Email");
                        } else {
                            loginUser();
                        }
                    } else if (!(phoneEt.getText().toString()).matches
                            (isvalidphonenumberregexp)) {
                        showToast("Please Enter a valid Phone Number");

                    } else {
                        loginUser();
                    }


                } else {
                    Toast.makeText(AlreadyRegisteredActivity.this, "Please Enter The Email or Mobile", Toast
                            .LENGTH_SHORT).show();
                }


                break;

            case R.id.already:
                Intent already = new Intent(this, LoginActivity.class);
                startActivity(already);
                finish();
                break;
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
