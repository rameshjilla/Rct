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

import com.richtree.richinvitations.Card;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.activities.WeddingAlbum;
import com.richtree.richinvitations.model.ProductOption;

import java.util.List;


/**
 * Created by TGT on 1/10/2018.
 */

public class ProductOptionAdapter extends BaseAdapter {
    List<ProductOption> productoptionlist;
    Context context;
    public static int sCorner = 15;
    public static int sMargin = 2;
    public static int sBorder = 10;
    public static String sColor = "#7D9067";
    selectTopicsCallback selecttopicscallback;


    @Override
    public int getCount() {
        return productoptionlist.size();
    }

    @Override
    public Object getItem(int i) {
        return productoptionlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public ProductOptionAdapter(Context context, List<ProductOption> productoptionlist
    ) {
        this.context = context;
        this.productoptionlist = productoptionlist;


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
        holder.heading.setText(productoptionlist.get(i).getName());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productoptionlist.get(i).getType().equals("text")) {
                    String value = productoptionlist.get(i).getValue();
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, Card.class);
                    bundle.putString("url", value);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, WeddingAlbum.class);
                    bundle.putInt("pos", i);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }


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
