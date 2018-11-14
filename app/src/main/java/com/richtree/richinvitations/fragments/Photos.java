package com.richtree.richinvitations.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.activities.MInvitationDetails;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.model.Products;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Photos extends Fragment {


    TinyDB tinydb;
    ListView listView;
    ProgressDialog dialog;
    String url;
    MyApplication myapplication;
    List<Products> productslist = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.list_view_2, container, false);
        tinydb = new TinyDB(getActivity());
        dialog = new ProgressDialog(getActivity());
        MInvitationDetails settingsActivity = (MInvitationDetails) getActivity();
        myapplication = (MyApplication) settingsActivity.getApplication();
        listView = (ListView) view.findViewById(R.id.invite);
        getRichAlbums();
        return view;
    }


    public void getRichAlbums() {
        dialog.show();

        productslist = new ArrayList<>();
        if (tinydb.getBoolean("isseller")) {
            url = "/seller/0";
        } else {
            url = "";
        }
        RetrofitController.getRequestController(myapplication, dialog,
                "product/related/album/" + tinydb
                        .getString("productid") + url, tinydb
                        .getString("accesstoken"), new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        try {
                            JSONObject object = new JSONObject(success);

                            if (object.getInt("code") == 100) {
                                JSONArray array = object.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    Products products = new Products();
                                    products.setId(array.getJSONObject(i).getString("product_id"));
                                    products.setImage(array.getJSONObject(i).getString("thumb"));
                                    products.setName(array.getJSONObject(i).getString("name"));
                                    products.setDescription(array.getJSONObject(i).getString("description"));
                                    productslist.add(products);

                                }

                                if (productslist.size() > 0) {
                                    com.richtree.richinvitations.adapters.RichAlbumAdapter adapter = new com
                                            .richtree.richinvitations.adapters.RichAlbumAdapter(getActivity(),
                                            productslist);
                                    listView.setAdapter(adapter);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

}
