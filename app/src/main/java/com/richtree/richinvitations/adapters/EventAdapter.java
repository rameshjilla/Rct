package com.richtree.richinvitations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.richtree.richinvitations.R;
import com.richtree.richinvitations.model.Attribute;
import com.richtree.richinvitations.model.AttributeGroup;
import com.richtree.richinvitations.utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TGT on 3/30/2018.
 */

public class EventAdapter extends BaseAdapter {
    private Context c;
    List<AttributeGroup> attributelist;
    Context context;
    TinyDB tinydb;


    @Override
    public int getCount() {
        return attributelist.size();
    }

    public EventAdapter(Context context, List<AttributeGroup> attributelist
    ) {
        this.context = context;
        this.attributelist = attributelist;
        tinydb = new TinyDB(context);


    }

    @Override
    public Object getItem(int position) {
        return attributelist.get(position);
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

            view = inflater.inflate(R.layout.event_list_item, parent,
                    false);
            holder = new ViewHolder();
            holder.textview_eventname = (TextView) view.findViewById(R.id.event_name);
            holder.textview_event_organiser = (TextView) view.findViewById(R.id.event_organizer);
            holder.textview_eventlocation = (TextView) view.findViewById(R.id.event_address);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textview_eventname.setText(attributelist.get(position).getName());
        AttributeGroup attributegroup = attributelist.get(position);
        List<Attribute> attributelist = new ArrayList<>();
        attributelist = attributegroup.getAttribute();
        holder.textview_eventlocation.setText(attributelist.get(0).getText());
        holder.textview_event_organiser.setText(attributelist.get(0).getName());


   /* Glide.with(context)
            .load(fullimageurl).transform(new CircleTransform(context))
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

            .into(holder.profileimage);*/


        return view;
    }


    static class ViewHolder {
        TextView textview_eventname, textview_eventlocation, textview_event_organiser;

        // TextView counter;

    }


}
