package com.richtree.richinvitations;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImageViewActivity extends AppCompatActivity {

    ArrayList<Integer> list_images;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_image_view);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {

            list_images=getIntent().getIntegerArrayListExtra("images");
            position=getIntent().getIntExtra("position",0);

            List<ImageView> images=new ArrayList<>();

            for(int i=0;i<list_images.size();i++){
                ImageView imageView=new ImageView(this);
                imageView.setImageResource(list_images.get(i));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                images.add(imageView);
            }

           ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(images);

            ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setCurrentItem(position);

        }catch (Exception e){
            e.printStackTrace();
        }


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

}
