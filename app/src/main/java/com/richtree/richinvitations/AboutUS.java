package com.richtree.richinvitations;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutUS extends AppCompatActivity {
    WebView webview;
    MyApplication myApplication;
    ProgressDialog dialog;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        webview = (WebView) findViewById(R.id.webview);
        myApplication = (MyApplication) getApplication();
        dialog = new ProgressDialog(AboutUS.this);
        tinyDB = new TinyDB(AboutUS.this);


        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getAboutus();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getAboutus() {
        RetrofitController.getRequestController(myApplication, dialog, "information/4", tinyDB
                .getString("accesstoken"), new NewBaseRetrofitCallback() {
            @Override
            public void Success(String success) {
                try {
                    JSONObject object = new JSONObject(success);

                    if (object.getInt("code") == 100) {
                        JSONObject dataarray = object.getJSONObject("data");
                        String description = dataarray.getString("description");
                        webview.getSettings().setJavaScriptEnabled(true);
                        webview.loadData(Html.fromHtml(description).toString(), "text/html; " +
                                "charset=UTF-8", null);
                    } else {
                        Toast.makeText(AboutUS.this, object.getString("message"), Toast
                                .LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    
                }
            }
        });

    }
}
