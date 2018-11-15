package app.myfirstclap.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.myfirstclap.MyFirstClapApplication;
import app.myfirstclap.R;
import app.myfirstclap.adapters.NewsEndLessAdapter;
import app.myfirstclap.controllers.FeedController;
import app.myfirstclap.interfaces.BaseRetrofitCallback;
import app.myfirstclap.model.Feeds;
import app.myfirstclap.utils.EndlessListView;
import app.myfirstclap.utils.TinyDB;

/**
 * Created by TGT on 5/30/2018.
 */

public class TopicFeedFragment extends Fragment implements EndlessListView.EndLessListener {
  public static final String ARG_PAGE = "ARG_PAGE";
  private int mPage;
  TinyDB tinydb;
  String topicid;
  EndlessListView listview;
  MyFirstClapApplication myapplication;
  ProgressDialog dialog;
  NewsEndLessAdapter adapter;


  public static TopicFeedFragment newInstance(int topicid, int page) {
    Bundle args = new Bundle();
    args.putInt(ARG_PAGE, page);
    args.putInt("id", topicid);

    TopicFeedFragment fragment = new TopicFeedFragment();
    fragment.setArguments(args);
    return fragment;

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPage = getArguments().getInt("position");
    tinydb = new TinyDB(getActivity());
    dialog = new ProgressDialog(getActivity());
    myapplication = (MyFirstClapApplication) getActivity().getApplication();

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_news, container, false);
    topicid = String.valueOf(getArguments().getInt("id"));
    listview = (EndlessListView) view.findViewById(R.id.listview);
    listview.setListener(this);
    listview.setLoadingView(R.layout.loading_layout);
    getTopicFeeds();
    return view;
  }

  public void getTopicFeeds() {
    JSONObject object = new JSONObject();
    try {
      object.put("topic_id", topicid);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    final List<Feeds> feedslist = new ArrayList<>();

    FeedController.getTopicFeeds(myapplication, dialog, object.toString(), new BaseRetrofitCallback() {
      @Override
      public void Success(String success) {
        try {
          JSONObject successobject = new JSONObject(success);
          if (successobject.getInt("code") == 100) {
            JSONArray Feedsarray = successobject.getJSONArray("message");
            for (int i = 0; i < Feedsarray.length(); i++) {
              Feeds feed = new Feeds();
              feed.setTitle(Feedsarray.getJSONObject(i).getString("Title"));
              feed.setImageUrl(Feedsarray.getJSONObject(i).getString("Image_url"));
              feed.setFeedId(Feedsarray.getJSONObject(i).getString("Feed Id"));
              feed.setUrl(Feedsarray.getJSONObject(i).getString("Url"));
              feedslist.add(feed);

            }
          }
          adapter = new NewsEndLessAdapter(getActivity(), feedslist, R
                  .layout.news_list_item);
          listview.setAdapter(adapter);
        } catch (JSONException e) {
          e.printStackTrace();
        }

      }
    });
  }

  @Override
  public void loadData() {

  }
}
