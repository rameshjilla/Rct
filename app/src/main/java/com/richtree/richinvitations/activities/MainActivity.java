package com.richtree.richinvitations.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.richtree.richinvitations.AboutUS;
import com.richtree.richinvitations.Information;
import com.richtree.richinvitations.MUpcoming;
import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.Settings;
import com.richtree.richinvitations.Splash;
import com.richtree.richinvitations.TabFragmentH;
import com.richtree.richinvitations.controllers.UserController;
import com.richtree.richinvitations.interfaces.BaseRetrofitCallback;
import com.richtree.richinvitations.model.User;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    MyApplication myapplication;
    TinyDB tinydb;
    User user;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tinydb = new TinyDB(MainActivity.this);
        user = tinydb.getObject("user", User.class);
        myapplication = (MyApplication) getApplication();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        loadFragment(new MUpcoming());

        View header = navigationView.getHeaderView(0);
        TextView username = (TextView) header.findViewById(R.id.user_name);
        username.setText(user.getFirstname() + " " + user.getLastname());

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.invitation:
                    tinydb.putString("tag", "inbox");
                    fragment = new MUpcoming();
                    loadFragment(fragment);
                    return true;
                case R.id.explore:
                    tinydb.putString("tag", "explore");
                    fragment = new TabFragmentH();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        if (id == R.id.about_us) {
            Intent intent = new Intent(MainActivity.this, AboutUS.class);
            startActivity(intent);

        }
        if (id == R.id.contact_us) {
            Intent intent = new Intent(MainActivity.this, ContactUs.class);
            startActivity(intent);

        }

        if (id == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.logout) {
            tinydb.clear();
            Intent intent = new Intent(MainActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            ComponentName cn = intent.getComponent();
            Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
            startActivity(mainIntent);
            finish();
        }

        if (id == R.id.information) {
            Intent intent = new Intent(MainActivity.this, Information.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void logoutUser() {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        UserController.logoutUser(myapplication, tinydb.getString("session"), dialog, new BaseRetrofitCallback() {
            @Override
            public void success(String success) {

                try {
                    JSONObject object = new JSONObject(success);
                    boolean successb = object.getBoolean("success");
                    if (successb) {
                        tinydb.clear();
                        Intent intent = new Intent(MainActivity.this, Splash.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isloggedout", true);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void fail(String fail) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);


        } else {

            if (tinydb.getString("tag").equals("explore")) {
                tinydb.putString("tag", "inbox");
                bottomNavigationView.setSelectedItemId(R.id.invitation);
                Fragment fragment = new MUpcoming();

                loadFragment(fragment);
            } else {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are You Sure You Want To Quit ?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

