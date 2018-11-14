package com.richtree.richinvitations.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.richtree.richinvitations.R;

/**
 * Created by admin on 06/10/2018.
 */

public class YoutubePlayerActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerFragment playerFragment;
    private YouTubePlayer mPlayer;
    private String YouTubeKey = "AIzaSyBWI2Gxwp_FFcvYD6RDhkE16iSvc5U3mqE";
    Bundle bundle;
    ListView listview;
    String getsavedjsonresponse;
    int savedposition;
    String videoid;
    YouTubePlayer.OnInitializedListener listener;
    YouTubePlayer.Provider Mprovider;
    boolean isrestored;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        bundle = getIntent().getExtras();
        listview = (ListView) findViewById(R.id.listview);
        listener = this;

        playerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_player_fragment);

        playerFragment.initialize(YouTubeKey, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        mPlayer = player;
        Mprovider = provider;
        isrestored = wasRestored;

        //Enables automatic control of orientation
        mPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        //Show full screen in landscape mode always
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        //System controls will appear automatically
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        if (!wasRestored) {
            //player.cueVideo("9rLZYyMbJic");
            if (!bundle.getString("videourl").isEmpty()) {
                mPlayer.loadVideo(bundle.getString("videourl"));

            } else {

            }


        } else {
            mPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        mPlayer = null;
    }


}