package app.myfirstclap.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import app.myfirstclap.fragments.TopicFeedFragment;
import app.myfirstclap.model.UserTopics;

/**
 * Created by TGT on 5/30/2018.
 */

public class TopicFeedAdapter extends FragmentPagerAdapter {
    ArrayList<UserTopics> topiclist;
    private Context context;

    public TopicFeedAdapter(FragmentManager fm, Context context, ArrayList<UserTopics> topiclist) {
        super(fm);
        this.context = context;
        this.topiclist = topiclist;
    }

    @Override
    public int getCount() {
        return topiclist.size();
    }

    @Override
    public Fragment getItem(int position) {
        return TopicFeedFragment.newInstance(Integer.valueOf(topiclist.get(position).getTopicId()),
                position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return topiclist.get(position).getTopicName();
    }
}
