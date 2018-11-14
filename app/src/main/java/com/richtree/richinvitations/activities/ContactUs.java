package com.richtree.richinvitations.activities;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 09/10/2018.
 */

public class ContactUs extends AppCompatActivity {
    EditText edit_name, edit_email, edit_enquiry;
    Button btn_submit;
    MyApplication myApplication;
    ProgressDialog dialog;
    TinyDB tinyDB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        edit_name = (EditText) findViewById(R.id.et_name);
        edit_email = (EditText) findViewById(R.id.et_email);
        edit_enquiry = (EditText) findViewById(R.id.et_enquiry);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        myApplication = (MyApplication) getApplication();
        dialog = new ProgressDialog(ContactUs.this);
        tinyDB = new TinyDB(ContactUs.this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_enquiry.getText().toString().isEmpty() || edit_email.getText().toString().isEmpty()
                        || edit_name.getText().toString().isEmpty()) {
                    Toast.makeText(ContactUs.this, "Please Fill All The Fields", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    submitEnquiry();
                }
            }
        });


    }


    public void submitEnquiry() {
        dialog.setTitle("Please Wait....");
        dialog.show();
        JSONObject object = new JSONObject();
        try {
            object.put("name", edit_name.getText().toString());
            object.put("email", edit_email.getText().toString());
            object.put("enquiry", edit_enquiry.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RetrofitController.postRequestController(myApplication, dialog, object.toString(),
                "contact", tinyDB.getString("accesstoken"), new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        dialog.dismiss();
                        try {
                            if (new JSONObject(success).getInt("code") == 100) {

                                Toast.makeText(ContactUs.this, "Thanks for Giving Your Feedback / Suggestions / Enquiry", Toast.LENGTH_LONG)
                                        .show();
                                Intent intent = new Intent(ContactUs.this,
                                        MainActivity.class);
                                startActivity(intent);
                                ComponentName cn = intent.getComponent();
                                Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                                startActivity(mainIntent);
                                finish();

                            } else {
                                Toast.makeText(ContactUs.this, new JSONObject(success).getString
                                        ("message"), Toast
                                        .LENGTH_SHORT)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
