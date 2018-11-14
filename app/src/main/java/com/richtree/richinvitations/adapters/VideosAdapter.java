package com.richtree.richinvitations.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.activities.VideoViewActivity;
import com.richtree.richinvitations.activities.YoutubePlayerActivity;
import com.richtree.richinvitations.model.VideoBean;
import com.richtree.richinvitations.utils.TinyDB;

import java.util.ArrayList;

/**
 * Created by richtree on 10/5/2018.
 */

public class VideosAdapter extends BaseAdapter {

    private Context c;
    ArrayList<VideoBean> videoslist;
    Context context;
    TinyDB tinydb;

    @Override
    public int getCount() {
        return videoslist.size();
    }

    public VideosAdapter(Context context, ArrayList<VideoBean> videolist) {
        this.context = context;
        this.videoslist = videolist;
        tinydb = new TinyDB(context);
    }

    @Override
    public Object getItem(int position) {
        return videoslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final ViewHolder holder;
        int type = getItemViewType(position);
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (videoslist.get(position).getSource().equals("API")) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.videos_source_item_view, parent, false);
            } else {
                view = inflater.inflate(R.layout.videos_youtube_item_view, parent, false);

                holder = new ViewHolder();
            }

            holder.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            holder.title = (TextView) view.findViewById(R.id.title);


            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(videoslist.get(position).getName());

        Glide.with(context).load(videoslist.get(position).getThumbnail())
                .centerCrop().placeholder(R.drawable.loading).crossFade()
                .into(holder.thumbnail);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (videoslist.get(position).getSource().contains("API")) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, VideoViewActivity.class);
                    bundle.putString("videourl", videoslist.get(position).getValue());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, YoutubePlayerActivity.class);
                    bundle.putString("videourl", videoslist.get(position).getValue());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }


            }
        });

        return view;
    }

    static class ViewHolder {
        TextView title;
        ImageView thumbnail;
    }
}
