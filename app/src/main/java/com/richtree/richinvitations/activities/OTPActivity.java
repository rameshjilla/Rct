package com.richtree.richinvitations.activities;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.controllers.UserController;
import com.richtree.richinvitations.interfaces.BaseRetrofitCallback;
import com.richtree.richinvitations.interfaces.LoginCallback;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.model.App;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.model.UserBaseModel;
import com.richtree.richinvitations.utils.TinyDB;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {
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
        tinydb = new TinyDB(OTPActivity.this);
        user = tinydb.getObject("user", User.class);
        Log.d("bundleotp", otp);
        dialog = new ProgressDialog(OTPActivity.this);
        myapplication = (MyApplication) getApplication();
        android_id = Settings.Secure.getString
                (getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

       //test commit

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

       /* if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/

        setContentView(R.layout.activity_otp);

        country_code = (TextView) findViewById(R.id.country_code);
        mobilenumber = (TextView) findViewById(R.id.mobilenumber);

        resend = (TextView) findViewById(R.id.resend);
        enter = (Button) findViewById(R.id.enter);
        et_otp = (EditText) findViewById(R.id.otp);

        if (phonenumber.contains("@")) {
            country_code.setVisibility(View.GONE);

            String maskemail = phonenumber.replaceAll("(?<=.{3}).(?=.*@)", "*");
            mobilenumber.setText(maskemail);
        } else {
            String phoennumber = phonenumber.replaceAll("\\d(?=\\d{4})", "*");
            mobilenumber.setText(phoennumber);
        }

        enter.setOnClickListener(this);
        resend.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.enter:

                if (otp.equals(et_otp.getText().toString())) {
                    showPopup();
                } else {
                    Toast.makeText(OTPActivity.this, "Otp doesnt match", Toast.LENGTH_SHORT)
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
        final ProgressDialog dialog = new ProgressDialog(OTPActivity.this);
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
                            Intent intent = new Intent(OTPActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            ComponentName cn = intent.getComponent();
                            Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                            startActivity(mainIntent);
                            finish();

                        } else {
                            Toast.makeText(OTPActivity.this, "User Doest Exist", Toast
                                    .LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void fail(String fail) {
                        dialog.dismiss();
                        Toast.makeText(OTPActivity.this, fail, Toast.LENGTH_SHORT).show();

                    }
                });
    }


    public void updateAccount(String firstname, String lastname) {
        String unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("uniqueid", unique_id);
        final ProgressDialog dialog = new ProgressDialog(OTPActivity.this);
        dialog.show();
        JSONObject object = new JSONObject();
        dialog.show();
        try {
            object.put("email", phonenumber + "@richevents.in");
            object.put("lastname", lastname);
            object.put("firstname", firstname);
            object.put("telephone", phonenumber);
            object.put("fax", unique_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        UserController.updateAccount(myapplication, dialog, object.toString(), tinydb.getString
                ("session"), new BaseRetrofitCallback() {
            @Override
            public void success(String success) {
                dialog.dismiss();
                try {
                    JSONObject object1 = new JSONObject(success);
                    if (object1.getBoolean("success")) {
                        tinydb.putObject("user", userbasemodel);
                        Intent intent = new Intent(OTPActivity.this, MainActivity.class);
                        startActivity(intent);
                        ComponentName cn = intent.getComponent();
                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                        startActivity(mainIntent);
                        finish();

                    } else {
                        Toast.makeText(OTPActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void fail(String fail) {
                dialog.dismiss();
                Toast.makeText(OTPActivity.this, fail, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showPopup() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_signup, null);
        dialogBuilder.setView(dialogView);

        final EditText et_firstname = (EditText) dialogView.findViewById(R.id.first_name);
        final EditText et_phonenumber = (EditText) dialogView.findViewById(R.id.mobilenumber);
        final EditText et_lastname = (EditText) dialogView.findViewById(R.id.last_name);

        final Button createaccount = dialogView.findViewById(R.id.popup_login);


        final AlertDialog alertDialog = dialogBuilder.create();

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                String manufacturer = Build.MANUFACTURER;
                String model = Build.MODEL;
                int version = Build.VERSION.SDK_INT;
                String versionRelease = Build.VERSION.RELEASE;
                JSONObject requestobject = new JSONObject();
                JSONObject appobject = new JSONObject();
                try {
                    if (phonenumber.contains("@")) {
                        requestobject.put("email", phonenumber);
                        requestobject.put("telephone", et_phonenumber.getText().toString());
                        email = phonenumber;
                    } else {
                        requestobject.put("email", phonenumber + "@richevents.in");
                        requestobject.put("telephone", phonenumber);
                        email = phonenumber + "@richevents.in";
                    }
                    requestobject.put("customer_id", 0);
                    requestobject.put("customer_group_id", 1);
                    requestobject.put("fax", "");
                    requestobject.put("status", 1);
                    requestobject.put("approved", 1);
                    requestobject.put("safe", 1);
                    requestobject.put("firstname", et_firstname.getText().toString());
                    requestobject.put("lastname", et_lastname.getText().toString());
                    requestobject.put("password", "user@123");
                    appobject.put("device_id", android_id);
                    appobject.put("device_model", Build.MODEL);
                    appobject.put("device_platform_version", Build.VERSION.SDK_INT);
                    appobject.put("device_platform", "Android");
                    appobject.put("notification_status", 0);
                    appobject.put("notification_token", FirebaseInstanceId.getInstance().getToken());
                    appobject.put("store_id", 1);
                    appobject.put("terms_conditions_agreed", 0);

                    requestobject.put("app", appobject);


                    createAccount(requestobject.toString(), "account/create", email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




       /* dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();

            }
        });*/
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                alertDialog.dismiss();


            }
        });
        alertDialog.show();
    }


    private void createAndDisplayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        TextView tvMessage = new TextView(this);
        final EditText etInput_firstname = new EditText(this);
        final EditText etInput_lastname = new EditText(this);
        etInput_firstname.setHint("First Name");
        etInput_lastname.setHint("Last Name");
        builder.setTitle("Update Account");
        builder.setCancelable(false);

        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        etInput_firstname.setSingleLine();
        etInput_lastname.setSingleLine();
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage);
        layout.addView(etInput_firstname);

        layout.addView(etInput_lastname);
        layout.setPadding(50, 40, 50, 10);

        builder.setView(layout);


        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateAccount(etInput_firstname.getText().toString(), etInput_lastname.getText()
                        .toString());
                dialogInterface.cancel();

            }
        });


        builder.create().show();
    }


    public void createAccount(String request, String path, final String email) {
        RetrofitController.postRequestController(myapplication, dialog, request, path, "", new NewBaseRetrofitCallback() {
            @Override
            public void Success(String success) {
                try {
                    JSONObject jsonObject = new JSONObject(success);

                    if (jsonObject.getInt("code") == 100) {
                        loginUser(email);

                    } else {
                        Toast.makeText(OTPActivity.this, jsonObject.getString("message"), Toast
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
        final ProgressDialog dialog = new ProgressDialog(OTPActivity.this);
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
                                App app = new Gson().fromJson(dataobject.getJSONObject("app").toString(),
                                        App
                                                .class);
                                user.setApp(app);
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

                                    Intent intent = new Intent(OTPActivity.this,
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
