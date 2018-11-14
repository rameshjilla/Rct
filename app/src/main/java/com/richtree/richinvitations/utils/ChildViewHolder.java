package com.richtree.richinvitations.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.richtree.richinvitations.R;

/**
 * Created by admin on 15/10/2018.
 */

public class ChildViewHolder extends RecyclerView.ViewHolder {

    public TextView textview_event_organiser;
    public TextView textview_eventlocation;


    public ChildViewHolder(View itemView) {
        super(itemView);
        textview_event_organiser = (TextView) itemView.findViewById(R.id.event_organizer);
        textview_eventlocation = (TextView) itemView.findViewById(R.id.event_address);


    }
}