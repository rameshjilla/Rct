package app.myfirstclap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.myfirstclap.BuildConfig;
import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.R;
import app.myfirstclap.adapters.FaceBookEndlessAdapter;
import app.myfirstclap.controllers.FeedController;
import app.myfirstclap.interfaces.FacebookFeedsCallback;
import app.myfirstclap.model.FacebookFeed;
import app.myfirstclap.utils.FacebookEndlessListView;

/**
 * Created by TGT on 12/13/2017.
 */

public class NewsFragment extends Fragment implements FacebookFeedsCallback, FacebookEndlessListView.EndLessListener {
    FacebookEndlessListView listview;
    protected MyFirstClapApplication application;
    FeedController feedcontroller;
    FaceBookEndlessAdapter adapter;
    String aftercursor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment_news, container, false);
        application = (MyFirstClapApplication) getActivity().getApplication();
        init(view);
        return view;
    }

    public void init(View v) {
        listview = (FacebookEndlessListView) v.findViewById(R.id.listview);
        listview.setLoadingView(R.layout.loading_layout);

        //	scrollview listener
        listview.setListener(this);
        FeedController.getFeeds(application, BuildConfig.Indie_Wire_PageId, this);

    }


    @Override
    public void successCallBack(FacebookFeed facebookfeed) {


        FacebookFeed.Paging paging = facebookfeed.getPaging();
        FacebookFeed.Cursors cursors = paging.getCursors();


        aftercursor = cursors.getAfter();
        if (adapter == null) {
            adapter = new FaceBookEndlessAdapter(getActivity(), facebookfeed.getData(), R
                    .layout.news_list_item);
            listview.setAdapter(adapter);
        } else {
            listview.addNewData(facebookfeed.getData());
        }


    }

    @Override
    public void failureCallback(String failureexception) {

    }

    @Override
    public void loadData() {
    /*FeedController.getFeeds(application, BuildConfig.Indie_Wire_PageId, this);*/


        FeedController.loadMoreFeeds(application, BuildConfig.Indie_Wire_PageId, aftercursor, this);
    }
}
