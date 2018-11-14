package com.richtree.richinvitations.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;

import com.richtree.richinvitations.R;
import com.richtree.richinvitations.activities.MInvitationDetails;
import com.richtree.richinvitations.adapters.EventAdapter;
import com.richtree.richinvitations.adapters.SectionalViewAdapter;
import com.richtree.richinvitations.model.Attribute;
import com.richtree.richinvitations.model.ProductDetailsData;
import com.richtree.richinvitations.utils.SectionalHeader;
import com.richtree.richinvitations.utils.TinyDB;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MDateAndTime extends Fragment {
    int pos = 0;
    ListView listview;
    TinyDB tinyDB;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    EventAdapter adapter;
    ArrayList<SectionalHeader> sectionallist = new ArrayList<>();
    List<Attribute> attributelist = new ArrayList<>();

    ProductDetailsData productdetailmodel;
    private WebView webView;

    public MDateAndTime() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_date_and_time, container, false);
        listview = view.findViewById(R.id.listview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        tinyDB = new TinyDB(getActivity());
        ((MInvitationDetails) getActivity()).getSupportActionBar().setTitle("Information");

        productdetailmodel = tinyDB.getProductDetailsObject("productdetails", ProductDetailsData.class);
        sectionallist = new ArrayList<>();

        for (int i = 0; i < productdetailmodel.getAttributeGroups().size(); i++) {

            SectionalHeader header = new SectionalHeader();
            header.setActivityHeader(productdetailmodel.getAttributeGroups().get(i).getName());
            header.setChildList(productdetailmodel.getAttributeGroups().get(i).getAttribute());
            sectionallist.add(header);
        }

        SectionalViewAdapter adapter = new SectionalViewAdapter(getActivity(), sectionallist);
        recyclerView.setAdapter(adapter);


      /*  EventAdapter adapter = new EventAdapter(getActivity(), productdetailmodel.getAttributeGroups());
        listview.setAdapter(adapter);*/

    /*String url="https://goo.gl/maps/yPGs74ogBTK2";
    webView=(WebView)view.findViewById(R.id.webView);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.loadUrl(url);
    webView.setWebViewClient(new MyWebViewClient());*/


       /* pos = getActivity().getIntent().getExtras().getInt("position");*/

      /*  final InvitationAdapter adapter = new InvitationAdapter(getActivity());*/
       /* final ImageView img = (ImageView) view.findViewById(R.id.image);
        final TextView dateTv = (TextView) view.findViewById(R.id.date);
        final TextView date2Tv = (TextView) view.findViewById(R.id.date2);

            img.setImageResource(adapter.images[pos]);
            dateTv.setText(adapter.date[pos]);
            date2Tv.setText(adapter.date2[pos]);*/

        return view;
    }

}
