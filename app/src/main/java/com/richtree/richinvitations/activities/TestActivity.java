package com.richtree.richinvitations.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.richtree.richinvitations.R;

/**
 * Created by admin on 04/10/2018.
 */

public class TestActivity extends Activity {
    String html = "\n" +
            "<video width=\"350\" height=\"200\" align=\"middle\" src=\"https://api.richhub" +
            ".in/api/rest/video/bbb.mp4?apiKey=2090f06a-b47d-11e8-96f8-529269fb14592090f06a-b47d" +
            "-11e8-96f8-529269fb1459\" controls allowfullscreen></video>";


    /*  String html = "\n" +
              "<video width=\"350\" height=\"200\" align=\"middle\" src=\"https://richhub.in/video/WeddingInvitation.mp4\" controls></video>";

      String html2 = "\n" +
              "<video width=\"350\" height=\"200\" align=\"middle\" src=\"https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4\" controls></video>";
  */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rich_album_list_item);

        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        String uri = "https://richhub.in/video/WeddingInvitation.mp4";

        //Uri uri = Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
        //videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.setVideoPath(uri);
        videoView.start();

        WebView webView = (WebView) findViewById(R.id.webview);
        /*webView.loadUrl("file:///android_asset/video.html");*/

        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");

        webView.setHorizontalScrollBarEnabled(false);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setAllowFileAccess(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }


}