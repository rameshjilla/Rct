package com.richtree.richinvitations.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.adapters.CustomPagerAdapter;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by admin on 06/04/2018.
 */

public class FullImageActivity extends AppCompatActivity {
    ImageView imageview;
    Bundle bundle;
    String imageurl;
    AutoScrollViewPager viewpager;
    ArrayList<String> imageslist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        imageview = (ImageView) findViewById(R.id.fullimage);
        bundle = getIntent().getExtras();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(bundle.getString("name"));
        imageslist = bundle.getStringArrayList("imageurl");

        Glide.with(FullImageActivity.this).load(imageurl)
                .centerCrop()/*.placeholder(R.drawable.loading)*/.crossFade()
                .into(imageview);

        viewpager = (AutoScrollViewPager) findViewById(R.id.pager);

        CustomPagerAdapter adapter = new CustomPagerAdapter(FullImageActivity.this,
                imageslist);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(bundle.getInt("pos"));

    }
}
