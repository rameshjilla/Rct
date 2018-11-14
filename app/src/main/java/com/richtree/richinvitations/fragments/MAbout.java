package com.richtree.richinvitations.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.activities.Location;
import com.richtree.richinvitations.activities.MInvitationDetails;
import com.richtree.richinvitations.activities.VideoViewActivity;
import com.richtree.richinvitations.activities.YoutubePlayerActivity;
import com.richtree.richinvitations.adapters.AutoScrollPagerAdapter;
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

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.biubiubiu.justifytext.library.JustifyTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MAbout extends Fragment {
    int pos = 0;
    TinyDB tinyDB;
    WebView webview_intro;
    ProductDetailsData productdetailmodel;
    List<String> images = new ArrayList<>();
    AutoScrollViewPager viewpager;
    JustifyTextView justifytextview;
    WebView webview;
    VideoView videoView;
    VideoBean videoBeanlive;
    VideoBean videoBeanintro;
    ImageView iv_thumbnail;
    MyApplication myApplication;
    ProgressDialog dialog;
    CardView cardView;
    TextView tv_name;
    LinearLayout map;
    ImageView imageview_live, play;
    CardView card_live, card_location;
    String live_url;
    ImageView thumbnail;
    ImageView icon;
    TextView tv_title;
    List<Products> productslist = new ArrayList<>();
    private int position = 0;
    private MediaController mediaController;
    boolean islive = false;


    public MAbout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_about_event, container, false);
        tinyDB = new TinyDB(getActivity());
        dialog = new ProgressDialog(getActivity());
        videoView = (VideoView) view.findViewById(R.id.videoView);
        cardView = view.findViewById(R.id.card_intro);
        card_live = view.findViewById(R.id.card_live);
        card_location = view.findViewById(R.id.card_location);
        thumbnail = view.findViewById(R.id.thumbnail);
        icon = view.findViewById(R.id.iv_icon);
        tv_title = view.findViewById(R.id.title);
        ((MInvitationDetails) getActivity()).getSupportActionBar().setTitle("Home");

        tv_name = view.findViewById(R.id.tv_name);
        map = view.findViewById(R.id.location);
        imageview_live = view.findViewById(R.id.iv_live);
        productdetailmodel = tinyDB.getProductDetailsObject("productdetails", ProductDetailsData.class);
        justifytextview = view.findViewById(R.id.details);
        webview = view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadData(productdetailmodel.getDescription(), "text/html; charset=UTF-8", null);
        MInvitationDetails settingsActivity = (MInvitationDetails) getActivity();
        myApplication = (MyApplication) settingsActivity.getApplication();
        viewpager = view.findViewById(R.id.pager);
        AutoScrollPagerAdapter adapter = new AutoScrollPagerAdapter(getActivity(), productdetailmodel.getImages());
        viewpager.setAdapter(adapter);
        viewpager.startAutoScroll();
        tv_name.setText(productdetailmodel.getName());


        justifytextview.setText(productdetailmodel.getDescription());


        getRelatedVideo();


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), Location.class);
                bundle.putString("location", productdetailmodel.getLocation());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        card_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), YoutubePlayerActivity.class);
                bundle.putString("videourl", live_url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoBeanintro.getSource().contains("api")) {

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getActivity(), VideoViewActivity.class);
                    bundle.putString("videourl", videoBeanintro.getValue());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(getActivity(), YoutubePlayerActivity.class);
                    bundle.putString("videourl", videoBeanintro.getValue());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        return view;
    }


    public void getRelatedVideo() {
        String url;
        if (tinyDB.getBoolean("isseller")) {
            url = "/seller/0";
        } else {
            url = "";
        }

        if (!productdetailmodel.getLocation().isEmpty()) {
            card_location.setVisibility(View.VISIBLE);
        } else {
            card_location.setVisibility(View.GONE);
        }
        productslist = new ArrayList<>();
        RetrofitController.getRequestController(myApplication, dialog,
                "product/related/video/" + tinyDB
                        .getString("productid") + url, tinyDB
                        .getString("accesstoken"), new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        parseJsonresponse(success);
                    }
                });


    }

    public void parseJsonresponse(String success) {
        try {
            JSONObject object = new JSONObject(success);
            if (object.getInt("code") == 100) {
                tinyDB.putString(tinyDB.getString("productid"), success);
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    Products products = new Products();
                    products.setId(array.getJSONObject(i).getString("product_id"));
                    products.setImage(array.getJSONObject(i).getString("thumb"));
                    products.setName(array.getJSONObject(i).getString("name"));
                    products.setDescription(array.getJSONObject(i).getString("description"));
                    productslist.add(products);

                }

                for (int i = 0; i < productslist.size(); i++) {
                    getVideosDetails(productslist.get(i).getId());

                }
            } else {
                card_live.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getVideosDetails(String productid) {
        String url;

        if (tinyDB.getBoolean("isseller")) {
            url = "/seller/0";
        } else {
            url = "";
        }

        RetrofitController.getRequestController(myApplication, dialog, "product/" + productid +
                url, tinyDB
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

                            if (video.getType().equals("Live")) {
                                videoBeanlive = video;
                                card_live.setVisibility(View.VISIBLE);
                                live_url = video.getValue();
                                islive = true;
                                /*return;*/
                            } else {
                                if (!islive) {
                                    card_live.setVisibility(View.GONE);
                                }

                            }


                            if (video.getType().equals("Intro")) {
                                videoBeanintro = video;
                                cardView.setVisibility(View.VISIBLE);
                                tv_title.setText(video.getName());

                                if (productdetails.getImages().size() > 0) {
                                    if (video.getImageindex() <= productdetails.getImages().size
                                            ()) {
                                        video.setThumbnail(productdetails.getImages().get(video.getImageindex
                                                () - 1));
                                        Glide.with(getActivity()).load(video.getThumbnail())
                                                .centerCrop()/*.placeholder(R.drawable.loading)*/.crossFade().thumbnail(0.1f).
                                                diskCacheStrategy
                                                        (DiskCacheStrategy.ALL)
                                                .into(thumbnail);
                                    }
                                } else {
                                    if (video.getImageindex() <= productdetailmodel.getImages().size
                                            ()) {
                                        video.setThumbnail(productdetailmodel.getImages().get(video.getImageindex
                                                () - 1));
                                        Glide.with(getActivity()).load(video.getThumbnail())
                                                .centerCrop()/*.placeholder(R.drawable.loading)*/.crossFade().thumbnail(0.1f).
                                                diskCacheStrategy
                                                        (DiskCacheStrategy.ALL)
                                                .into(thumbnail);
                                    }
                                }


                                if (video.getSource().contains("api")) {
                                    icon.setBackgroundResource(R.drawable.video_play);

                                } else {
                                    icon.setBackgroundResource(R.drawable.youtube);
                                }
                                return;

                            } else {
                                cardView.setVisibility(View.GONE);

                            }

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public String getVideoPath(String value) {

        String path = "\n" +
                "<video width=\"350\" height=\"200\" align=\"middle\" src=\"https://api.richhub" +
                ".in/api/rest/video/" + value +
                "?apiKey=2090f06a-b47d-11e8-96f8-529269fb14592090f06a-b47d" +
                "-11e8-96f8-529269fb1459\" controls allowfullscreen></video>";


        return path;

    }

}
