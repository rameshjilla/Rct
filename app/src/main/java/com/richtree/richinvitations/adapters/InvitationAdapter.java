package com.richtree.richinvitations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.model.ProductBean;
import com.richtree.richinvitations.utils.TinyDB;

import java.util.List;


/**
 * Created by richtree on 11/24/2017.
 */

public class InvitationAdapter extends BaseAdapter {
    private Context c;
    List<ProductBean> ProductBeanlist;
    Context context;
    TinyDB tinydb;


    @Override
    public int getCount() {
        return ProductBeanlist.size();
    }

    public InvitationAdapter(Context context, List<ProductBean> ProductBeanlist
    ) {
        this.context = context;
        this.ProductBeanlist = ProductBeanlist;
        tinydb = new TinyDB(context);


    }

    @Override
    public Object getItem(int position) {
        return ProductBeanlist.get(position);
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

            view = inflater.inflate(R.layout.invitation_list_item, parent,
                    false);
            holder = new ViewHolder();
            holder.textview_name = (TextView) view.findViewById(R.id.name);
            holder.profileimage = (ImageView) view.findViewById(R.id.image);
            holder.textview_date = (TextView) view.findViewById(R.id.date);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textview_name.setText(ProductBeanlist.get(position).getName());
        holder.textview_date.setText(ProductBeanlist.get(position).getStartDate());

        Glide.with(context).load(ProductBeanlist.get(position).getImage())
                .centerCrop().placeholder(R.drawable.loading).crossFade()
                .into(holder.profileimage);

   /* Glide.with(context)
            .load(fullimageurl).transform(new CircleTransform(context))
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

            .into(holder.profileimage);*/


        return view;
    }


    static class ViewHolder {
        TextView textview_name, textview_date, textview_phonenumber;
        TextView changestatus;
        ImageView profileimage;
        ImageView iv_call;
        // TextView counter;

    }


}
