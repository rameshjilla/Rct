package com.richtree.richinvitations.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.adapters.BottomNavigationViewHelper;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.fragments.MAbout;
import com.richtree.richinvitations.fragments.MDateAndTime;
import com.richtree.richinvitations.fragments.MGallery;
import com.richtree.richinvitations.fragments.Selfie;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.model.ProductDetailsData;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

public class MInvitationDetails extends AppCompatActivity {
    TinyDB tinydb;
    MyApplication myapplication;
    String product_id;
    ProgressDialog dialog;
    Bundle bundle;
    boolean isseller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_details);
        myapplication = (MyApplication) getApplication();
        dialog = new ProgressDialog(MInvitationDetails.this);
        tinydb = new TinyDB(MInvitationDetails.this);
        bundle = getIntent().getExtras();

        if (bundle != null) {
            product_id = bundle.getString("product_id");
            isseller = bundle.getBoolean("isseller");
        }

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Initializing the bottomNavigationView
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation2);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        if (isseller) {
            getProductDetails("product/" + product_id + "/seller/0");
        } else {
            getProductDetails("product/" + product_id);
        }

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.host:
                    fragment = new MAbout();
                    loadFragment(fragment);
                    setTitle("Home");
                    return true;
                case R.id.programs:
                    fragment = new MDateAndTime();
                    loadFragment(fragment);
                    setTitle("Information");
                    return true;
                case R.id.gallery:
                    fragment = new MGallery();
                    loadFragment(fragment);
                    setTitle("Gallery");
                    return true;
                case R.id.selfie:
                    fragment = new Selfie();
                    loadFragment(fragment);
                    setTitle("Selfie");
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getProductDetails(String path) {

        RetrofitController.getRequestController(myapplication, dialog, path,
                tinydb.getString("accesstoken"), new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        dialog.dismiss();
                        Log.d("response", success);
                        try {
                            JSONObject responseobject = new JSONObject(success);
                            if (responseobject.getBoolean("success")) {
                                JSONObject dataobject = responseobject.getJSONObject("data");
                                ProductDetailsData productDetailsData = new Gson().fromJson(dataobject.toString(),
                                        ProductDetailsData.class);
                                tinydb.putObject("productdetails", productDetailsData);
                                loadFragment(new MAbout());
                               /* if (productDetailsData.getAttributeGroups().size() == 0) {
                                    bottomNavigationView.getMenu().removeItem(R.id.programs);

                                }*/
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

     /*   dialog.show();
        UserController.getProductsDetails(myapplication, tinydb.getString("session"), product_id, dialog, new BaseProductsDetailscallback() {
            @Override
            public void success(ProductDetailsBaseModel productdetails) {
                dialog.dismiss();
                tinydb.putObject("productdetails", productdetails);
                setupViewPager(viewPager);
            }

            @Override
            public void fail(String fail) {
                dialog.dismiss();
                Toast.makeText(myapplication, fail, Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
