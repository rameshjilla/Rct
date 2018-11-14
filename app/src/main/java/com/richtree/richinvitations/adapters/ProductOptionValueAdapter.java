package com.richtree.richinvitations.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.activities.FullImageActivity;
import com.richtree.richinvitations.model.ProductOptionValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TGT on 4/3/2018.
 */

public class ProductOptionValueAdapter extends BaseAdapter {
    List<ProductOptionValue> productoptionvalueslist;
    Context context;
    public static int sCorner = 15;
    public static int sMargin = 2;
    public static int sBorder = 10;
    public static String sColor = "#7D9067";
    ProductOptionAdapter.selectTopicsCallback selecttopicscallback;


    @Override
    public int getCount() {
        return productoptionvalueslist.size();
    }

    @Override
    public Object getItem(int i) {
        return productoptionvalueslist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public ProductOptionValueAdapter(Context context, List<ProductOptionValue> productoptionvalueslist
    ) {
        this.context = context;
        this.productoptionvalueslist = productoptionvalueslist;


    }


    public interface selectTopicsCallback {
        void selectedtopic(int id);
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        final ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            view = inflater.inflate(R.layout.topic_grid_item, viewGroup,
                    false);
            holder = new ViewHolder();
            holder.heading = (TextView) view.findViewById(R.id.tv_topicname);
            holder.icon = (ImageView) view.findViewById(R.id.iv_topicimage);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.heading.setText(productoptionvalueslist.get(i).getName());
        Glide.with(context).load(productoptionvalueslist.get(i).getImage())
                .thumbnail(0.1f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.icon);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("pos", i);
                Intent intent = new Intent(context, FullImageActivity.class);
                ArrayList<String> imageslist = new ArrayList<>();
                for (int i = 0; i < productoptionvalueslist.size(); i++) {
                    imageslist.add(productoptionvalueslist.get(i).getImage());

                }
                bundle.putStringArrayList("imageurl", imageslist);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });


        return view;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public class ViewHolder {
        public TextView heading;
        public ImageView icon;
        public AppCompatCheckBox checkbox;

    }
}
