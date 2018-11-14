package com.richtree.richinvitations.utils;

import android.widget.TextView;

import com.intrusoft.sectionedrecyclerview.Section;
import com.richtree.richinvitations.model.Attribute;

import java.util.List;

/**
 * Created by admin on 15/10/2018.
 */

public class SectionalHeader implements Section<Attribute> {
    public TextView sectionheadertextview;

    public TextView getSectionheadertextview() {
        return sectionheadertextview;
    }

    public void setSectionheadertextview(TextView sectionheadertextview) {
        this.sectionheadertextview = sectionheadertextview;
    }

    public List<Attribute> getChildList() {
        return childList;
    }

    public void setChildList(List<Attribute> childList) {
        this.childList = childList;
    }

    public String getActivityHeader() {
        return activityHeader;
    }

    public void setActivityHeader(String activityHeader) {
        this.activityHeader = activityHeader;
    }

    List<Attribute> childList;
    String activityHeader;

    @Override
    public List<Attribute> getChildItems() {
        return childList;
    }
}
