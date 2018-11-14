package com.richtree.richinvitations.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.richtree.richinvitations.R;
import com.richtree.richinvitations.activities.MInvitationDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class Selfie extends Fragment {
    private Context context;


    public Selfie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MInvitationDetails) getActivity()).getSupportActionBar().setTitle("Selfie");
        return inflater.inflate(R.layout.fragment_selfie, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {//Name of your activity
            this.context = (AppCompatActivity) context;
        }
    }

}
