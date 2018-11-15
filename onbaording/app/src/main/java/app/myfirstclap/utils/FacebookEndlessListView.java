package app.myfirstclap.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import java.util.List;

import app.myfirstclap.adapters.FaceBookEndlessAdapter;
import app.myfirstclap.model.FacebookFeed;

public class FacebookEndlessListView extends ListView implements OnScrollListener {

    private Context context;
    private View footer;
    private boolean isLoading;
    private EndLessListener listener;
    private FaceBookEndlessAdapter adapter;

    public FacebookEndlessListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);
        this.context = context;
    }

    public FacebookEndlessListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
        this.context = context;
    }

    public FacebookEndlessListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
        this.context = context;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    //	4
    public void addNewData(List<FacebookFeed.News> data) {
        this.removeFooterView(footer);
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (getAdapter() == null)
            return;

        if (getAdapter().getCount() == 0)
            return;

        int l = visibleItemCount + firstVisibleItem;

        if (l >= totalItemCount && !isLoading) {

            //	add footer layout
            this.addFooterView(footer);

            //	set progress boolean
            isLoading = true;

            //	call interface method to load new data
            listener.loadData();
        }
    }

    //	Calling order from MainActivity
    //	1
    public void setLoadingView(int resId) {
        footer = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resId, null);        //		footer = (View)inflater.inflate(resId, null);
        this.addFooterView(footer);
    }

    //	2
    public void setListener(EndLessListener listener) {
        this.listener = listener;
    }

    //	3
    public void setAdapter(FaceBookEndlessAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
        this.removeFooterView(footer);
    }

    //	interface
    public interface EndLessListener {
        public void loadData();
    }
}