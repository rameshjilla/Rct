package com.richtree.richinvitations.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.richtree.richinvitations.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MLocation extends Fragment {
    int pos = 0;


    public MLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_location, container, false);



      /*  final InvitationAdapter adapter = new InvitationAdapter(getActivity());
        final ImageView img = (ImageView) view.findViewById(R.id.image);
        final TextView locationTv = (TextView) view.findViewById(R.id.location);
        final TextView location2Tv = (TextView) view.findViewById(R.id.location2);

        img.setImageResource(adapter.images[pos]);
        locationTv.setText(adapter.location[pos]);
        location2Tv.setText(adapter.location2[pos]);
*/
        return view;
    }

}
