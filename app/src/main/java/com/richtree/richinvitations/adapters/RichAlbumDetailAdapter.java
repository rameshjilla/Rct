package com.richtree.richinvitations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.model.Products;
import com.richtree.richinvitations.utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 06/04/2018.
 */

public class RichAlbumDetailAdapter extends BaseAdapter {
    private Context c;
    ArrayList<String> imageslist;
    List<Products> productslist;
    Context context;
    TinyDB tinydb;

    @Override
    public int getCount() {
        return imageslist.size();
    }

    public RichAlbumDetailAdapter(Context context, ArrayList<String> imageslist) {
        this.context = context;
        this.imageslist = imageslist;
        tinydb = new TinyDB(context);
    }

    @Override
    public Object getItem(int position) {
        return imageslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            view = inflater.inflate(R.layout.topic_grid_item, parent,
                    false);
            holder = new ViewHolder();
            holder.profileimage = (ImageView) view.findViewById(R.id.iv_topicimage);
            holder.textview_name = (TextView) view.findViewById(R.id.tv_topicname);
            holder.textview_name.setText("");

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }


        Glide.with(context).load(imageslist.get(position))
                .centerCrop().placeholder(R.drawable.loading2).crossFade().thumbnail(0.1f).
                diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.profileimage);

   /* Glide.with(context)
            .load(fullimageurl).transform(new CircleTransform(context))
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

            .into(holder.profileimage);*/


        return view;
    }

    static class ViewHolder {
        TextView textview_name;
        ImageView profileimage;
    }
}
