package com.richtree.richinvitations.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.adapters.RichAlbumDetailAdapter;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 06/04/2018.
 */

public class RichAlbumDetailView extends AppCompatActivity {
    GridView gridview;
    MyApplication myapplication;
    String productid;
    String productname;
    Bundle bundle;
    ProgressDialog dialog;
    String url;
    TinyDB tinydb;
    ArrayList<String> imageslist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wedding_album);
        gridview = (GridView) findViewById(R.id.simpleGridView);
        myapplication = (MyApplication) getApplication();
        dialog = new ProgressDialog(RichAlbumDetailView.this);
        tinydb = new TinyDB(RichAlbumDetailView.this);
        bundle = getIntent().getExtras();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (bundle != null) {
            productid = bundle.getString("product_id");
            productname = bundle.getString("product_name");
        }
        setTitle(productname);
        getProductImages();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(RichAlbumDetailView.this, FullImageActivity.class);
                bundle.putStringArrayList("imageurl", imageslist);
                bundle.putInt("pos", i);
                bundle.putString("name", productname);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }


    public void getProductImages() {
        dialog.show();
        if (tinydb.getBoolean("isseller")) {
            url = "/seller/0";
        } else {
            url = "";
        }
        RetrofitController.getRequestController(myapplication, dialog, "product/" + productid + url,
                tinydb
                        .getString("accesstoken"), new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(success);

                            if (object.getInt("code") == 100) {
                                JSONObject dataobject = object.getJSONObject("data");
                                JSONArray imagesarray = dataobject.getJSONArray("images");
                                if (imagesarray != null) {
                                    for (int i = 0; i < imagesarray.length(); i++) {
                                        imageslist.add(imagesarray.getString(i));
                                    }

                                    RichAlbumDetailAdapter adapter = new RichAlbumDetailAdapter
                                            (RichAlbumDetailView.this, imageslist);
                                    gridview.setAdapter(adapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
