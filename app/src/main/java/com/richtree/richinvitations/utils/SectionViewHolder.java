package com.richtree.richinvitations.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.richtree.richinvitations.R;

/**
 * Created by admin on 15/10/2018.
 */

public class SectionViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public View view;

    public SectionViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.event_name);
        view = itemView.findViewById(R.id.view);
    }
}