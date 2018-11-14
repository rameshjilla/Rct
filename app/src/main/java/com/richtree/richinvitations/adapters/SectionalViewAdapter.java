package com.richtree.richinvitations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;
import com.richtree.richinvitations.R;
import com.richtree.richinvitations.model.Attribute;
import com.richtree.richinvitations.utils.ChildViewHolder;
import com.richtree.richinvitations.utils.SectionViewHolder;
import com.richtree.richinvitations.utils.SectionalHeader;

import java.util.List;

/**
 * Created by admin on 15/10/2018.
 */

public class SectionalViewAdapter extends SectionRecyclerViewAdapter<SectionalHeader, Attribute, SectionViewHolder, ChildViewHolder> {
    Context context;


    public SectionalViewAdapter(Context context, List<SectionalHeader> SectionalHeaderItemList) {
        super(context, SectionalHeaderItemList);
        this.context = context;

    }


    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_list_item, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_list_item_attributes, childViewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(final SectionViewHolder sectionViewHolder, int i, final SectionalHeader SectionalHeader) {
        sectionViewHolder.name.setText(SectionalHeader.getActivityHeader());


    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int i, int i1, Attribute Attribute) {
        childViewHolder.textview_event_organiser.setText(Attribute.getName());
        childViewHolder.textview_eventlocation.setText(Attribute.getText());

    }
}