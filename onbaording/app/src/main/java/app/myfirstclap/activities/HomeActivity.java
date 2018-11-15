package app.myfirstclap.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.myfirstclap.R;
import app.myfirstclap.adapters.TopicFeedAdapter;
import app.myfirstclap.fragments.NewsFragment;
import app.myfirstclap.model.User;
import app.myfirstclap.model.UserTopics;
import app.myfirstclap.utils.TinyDB;

/*import app.myfirstclap.fragments.NewsFragment;*/

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewpager;
    TinyDB tinydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewpager = findViewById(R.id.viewpager);
        tinydb = new TinyDB(HomeActivity.this);
        String form = removeUrl("#Annihilation. #BladeRunner2049. #ReadyPlayerOne. Studios are risking it all on blockbusters, and ushering in a new golden https://t.co/Bv5YaMvi5w https://t.co/vfflKZkDMz");

        Log.d("formatted", form);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Toast.makeText(application, "Bundle is there", Toast.LENGTH_SHORT).show();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        applyFontForToolbarTitle(HomeActivity.this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

    /*FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction();
    Fragment fragment = new NewsFragment();
    fragmenttransaction.replace(R.id.frame, fragment);
    fragmenttransaction.commit();*/
        navigateToTopicsFeeds();
    }


    private String removeUrl(String commentstr) {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i), "").trim();
            i++;
        }

        return commentstr;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
     /* fragment = new TopicFeedFragment();
      fragmenttransaction.replace(R.id.frame, fragment);
      fragmenttransaction.commit();*/
            fragmenttransaction = getSupportFragmentManager().beginTransaction();
            fragment = new NewsFragment();
            fragmenttransaction.replace(R.id.frame, fragment);
            fragmenttransaction.commit();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navigateToTopicsFeeds() {
        ArrayList<UserTopics> usertopicslist = new ArrayList<>();
        usertopicslist = tinydb.getUserObject("user", User.class).getUsertopicslist();

        viewpager.setAdapter(new TopicFeedAdapter(getSupportFragmentManager(),
                HomeActivity.this, usertopicslist));
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


}
