package com.richtree.richinvitations.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.richtree.richinvitations.R;

/**
 * Created by TGT on 4/3/2018.
 */

public class VideoViewActivity extends AppCompatActivity {
    VideoView videoView;
    private MediaController mediaController;
    Bundle bundle;
    String videourl;
    private int position = 0;
    String fullvideourl;
    WebView webview_intro;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        webview_intro = (WebView) findViewById(R.id.videoView);
        bundle = getIntent().getExtras();
        videourl = bundle.getString("videourl");
        playVideo(videourl);


       /* fullvideourl = "https://api.richhub.in/api/rest/video/" + videourl +
                "?apiKey=2090f06a-b47d-11e8-96f8-529269fb14592090f06a-b47d-11e8-96f8-529269fb1459";
        Uri vidUri = Uri.parse(fullvideourl);
        videoView.setVideoURI(vidUri);
        videoView.start();

        mediaController = new MediaController(VideoViewActivity.this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {


                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });
*/

    }

    public void playVideo(String value) {

        String videopath = getVideoPath(value);
        webview_intro.loadDataWithBaseURL("", videopath, "text/html", "UTF-8", "");
        webview_intro.setHorizontalScrollBarEnabled(false);
        webview_intro.clearCache(true);
        webview_intro.clearHistory();
        webview_intro.getSettings().setAllowFileAccess(false);
        webview_intro.getSettings().setJavaScriptEnabled(true);
        webview_intro.getSettings().setAppCacheEnabled(true);
        webview_intro.getSettings().setDatabaseEnabled(true);
        webview_intro.getSettings().setDomStorageEnabled(true);
        webview_intro.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

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
