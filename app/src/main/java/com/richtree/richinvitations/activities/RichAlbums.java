package com.richtree.richinvitations.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.controllers.UserController;
import com.richtree.richinvitations.interfaces.BaseRetrofitCallback;
import com.richtree.richinvitations.model.Products;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RichAlbums extends AppCompatActivity {
    ListView listView;
    TinyDB tinydb;
    ProgressDialog dialog;
    MyApplication myapplication;
    List<Products> productslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_2);
        tinydb = new TinyDB(RichAlbums.this);
        dialog = new ProgressDialog(RichAlbums.this);
        myapplication = (MyApplication) getApplication();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.invite);
        getRichAlbums();

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

    public void getRichAlbums() {
        dialog.show();
        productslist = new ArrayList<>();
        UserController.getRichAlbums(myapplication, tinydb.getString("sessionid"), tinydb
                .getString("productid"), new BaseRetrofitCallback() {
            @Override
            public void success(String success) {
                dialog.dismiss();

                try {
                    JSONObject object = new JSONObject(success);
                    if (object.getBoolean("success")) {
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            Products products = new Products();
                            products.setId(array.getJSONObject(i).getString("product_id"));
                            products.setImage(array.getJSONObject(i).getString("thumb"));
                            products.setName(array.getJSONObject(i).getString("name"));
                            products.setDescription(array.getJSONObject(i).getString("description"));
                            productslist.add(products);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (productslist.size() > 0) {
                    com.richtree.richinvitations.adapters.RichAlbumAdapter adapter = new com
                            .richtree.richinvitations.adapters.RichAlbumAdapter(RichAlbums.this,
                            productslist);
                    listView.setAdapter(adapter);
                }

            }


            @Override
            public void fail(String fail) {
                dialog.dismiss();
                Toast.makeText(RichAlbums.this, fail, Toast.LENGTH_SHORT).show();

            }
        }, dialog);

    }
}
