package com.richtree.richinvitations;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.richtree.richinvitations.activities.AlreadyRegisteredActivity;
import com.richtree.richinvitations.activities.LoginActivity;
import com.richtree.richinvitations.activities.MainActivity;
import com.richtree.richinvitations.activities.OTPActivity;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.controllers.UserController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.interfaces.OtpCallback;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText phoneEt;
    ImageView register;
    TextView already;
    MyApplication myapplication;
    ProgressDialog dialog;
    TinyDB tinydb;
    String android_id;
    String otp;
    String isvalidphonenumberregexp = "^[7-9][0-9]{9}$";
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinydb = new TinyDB(Register.this);
        myapplication = (MyApplication) getApplication();
        android_id = android.provider.Settings.Secure.getString
                (getApplicationContext().getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);

        /*if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/

        setContentView(R.layout.activity_register);
        dialog = new ProgressDialog(Register.this);

        phoneEt = (EditText) findViewById(R.id.email_mobile);
        register = (ImageView) findViewById(R.id.getotp);
        already = (TextView) findViewById(R.id.already);

        register.setOnClickListener(this);
        already.setOnClickListener(this);


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
                                    Intent intent = new Intent(Register.this, OTPActivity.class);
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
        final ProgressDialog dialog = new ProgressDialog(Register.this);
        dialog.show();
        JSONObject object = new JSONObject();
        try {

            if (phoneEt.getText().toString().contains("@")) {
                object.put("email", phoneEt.getText().toString());
            } else {
                object.put("email", phoneEt.getText().toString() + "@richevents.in");
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
                                } else if (!user.getApp().getDeviceId().equals(android_id)) {

                                    showAlert();

                                } else {
                                    Intent intent = new Intent(Register.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    ComponentName cn = intent.getComponent();
                                    Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            } else if (responseobject.getInt("code") == 101) {
                                otp = generateRandomNumber();

                                JSONObject requestobject = new JSONObject();


                                if (phoneEt.getText().toString().contains("@")) {
                                    requestobject.put("email", phoneEt.getText().toString());
                                    path = "notify/email/otp";
                                } else {
                                    requestobject.put("mobile", phoneEt.getText().toString());
                                    path = "notify/sms/otp";
                                }

                                requestobject.put("otp", otp);

                                sendOtP(requestobject.toString(), path);


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
                        Intent intent = new Intent(Register.this, OTPActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("otp", otp);
                        bundle.putString("phonenumber", phoneEt.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Register.this, object.getString("message"), Toast
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
                .Builder(Register.this);
        alert.setTitle("Different Device");
        alert.setMessage("Your account is already registered with other device. Please de-register current\n" +
                "device and register new device using sign-up.");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                User user = tinydb.getObject("user", User.class);
                Intent intent = new Intent(Register.this, AlreadyRegisteredActivity.class);
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
                    Toast.makeText(Register.this, "Please Enter The Email or Mobile", Toast
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
