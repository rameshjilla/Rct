package com.richtree.richinvitations.activities;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 03/10/2018.
 */

public class UpdateProfile extends AppCompatActivity {
    EditText et_firstname, et_lastname, et_mobile;
    Button btn_update_profile;
    TinyDB tinydb;
    User user;
    MyApplication myapplication;
    ProgressDialog dialog;
    String android_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        tinydb = new TinyDB(UpdateProfile.this);
        user = tinydb.getObject("user", User.class);
        et_firstname = (EditText) findViewById(R.id.et_first_name);
        et_lastname = (EditText) findViewById(R.id.et_last_name);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        btn_update_profile = (Button) findViewById(R.id.btn_update);
        et_firstname.setText(user.getFirstname());
        et_lastname.setText(user.getLastname());
        et_mobile.setText(user.getTelephone());
        myapplication = (MyApplication) getApplication();
        dialog = new ProgressDialog(UpdateProfile.this);

        android_id = Settings.Secure.getString
                (getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setFirstname(et_firstname.getText().toString());
                user.setLastname(et_lastname.getText().toString());
                user.setTelephone(et_mobile.getText().toString());
                user.setPassword("user@123");
                user.setFax(android_id);
                Gson gson = new Gson();
                String json = gson.toJson(user);
                updateAccount(json, "account/update", user.getEmail());

            }
        });

    }


    public void updateAccount(String request, String path, final String email) {
        RetrofitController.putRequestController(myapplication, dialog, request, path, tinydb
                        .getString("accesstoken"),
                new
                        NewBaseRetrofitCallback() {
                            @Override
                            public void Success(String success) {
                                try {
                                    JSONObject jsonObject = new JSONObject(success);
                                    if (jsonObject.getInt("code") == 100) {
                                        loginUser(email);

                                    } else {
                                        Toast.makeText(UpdateProfile.this, jsonObject.getString("message"), Toast
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
        final ProgressDialog dialog = new ProgressDialog(UpdateProfile.this);
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

                                if (!user.getFax().equals(android_id)) {
                                    Toast.makeText(myapplication, "Different Device id",
                                            Toast
                                                    .LENGTH_SHORT).show();
                                } else {

                                    Intent intent = new Intent(UpdateProfile.this,
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
