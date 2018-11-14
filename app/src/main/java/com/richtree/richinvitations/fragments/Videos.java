package com.richtree.richinvitations.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.activities.MInvitationDetails;
import com.richtree.richinvitations.adapters.VideosAdapter;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.model.ProductDetailsData;
import com.richtree.richinvitations.model.Products;
import com.richtree.richinvitations.model.VideoBean;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Videos extends Fragment {

    ListView listView;
    List<Products> productslist = new ArrayList<>();
    GridView gridview;
    MyApplication myapplication;
    String productid;
    Bundle bundle;
    ProgressDialog dialog;
    TinyDB tinydb;
    String url;
    ArrayList<VideoBean> videoslist = new ArrayList<>();


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
        getVideos();
        return view;
    }


    public void getVideos() {
        dialog.show();
        videoslist = new ArrayList<>();
        productslist = new ArrayList<>();
        if (tinydb.getBoolean("isseller")) {
            url = "/seller/0";
        } else {
            url = "";
        }
        RetrofitController.getRequestController(myapplication, dialog,
                "product/related/video/" + tinydb
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

                                    for (int i = 0; i < productslist.size(); i++) {
                                        getVideosDetails(productslist.get(i).getId());

                                    }


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }


    public void getVideosDetails(String productid) {
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
                        try {
                            JSONObject object = new JSONObject(success);

                            if (object.getInt("code") == 100) {
                                JSONObject dataobject = object.getJSONObject("data");
                                ProductDetailsData productdetails = new Gson().fromJson(dataobject.toString(),
                                        ProductDetailsData
                                                .class);


                                JSONArray optionsarray = dataobject.getJSONArray("options");

                                for (int i = 0; i < optionsarray.length(); i++) {
                                    JSONObject optionsobject = optionsarray.getJSONObject(i);

                                    String value = Html.fromHtml(optionsobject.getString("value")).toString();

                                    VideoBean video = new Gson().fromJson(new JSONObject(value).toString(),
                                            VideoBean
                                                    .class);

                                    if (video.getImageindex() < productdetails.getImages().size()) {
                                        video.setThumbnail(productdetails.getImages().get(video.getImageindex
                                                () - 1));
                                    }


                                    if (video.getType().equals("Live") || video.getType().equals("Intro")) {

                                    } else {
                                        videoslist.add(video);
                                    }


                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        int size = videoslist.size();

                        VideosAdapter adapter = new VideosAdapter
                                (getActivity(), videoslist);
                        listView.setAdapter(adapter);
                    }
                });


    }

}
