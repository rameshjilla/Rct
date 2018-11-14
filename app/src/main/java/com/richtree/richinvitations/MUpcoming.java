package com.richtree.richinvitations;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.richtree.richinvitations.activities.MInvitationDetails;
import com.richtree.richinvitations.activities.MainActivity;
import com.richtree.richinvitations.adapters.InvitationAdapter;
import com.richtree.richinvitations.controllers.RetrofitController;
import com.richtree.richinvitations.controllers.UserController;
import com.richtree.richinvitations.interfaces.BaseRetrofitCallback;
import com.richtree.richinvitations.interfaces.NewBaseRetrofitCallback;
import com.richtree.richinvitations.model.OrderBean;
import com.richtree.richinvitations.model.ProductBean;
import com.richtree.richinvitations.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MUpcoming extends Fragment {
    ListView listView;
    MyApplication myapplication;
    Button past, upcoming;
    TinyDB tinydb;
    InvitationAdapter adapter;
    ArrayList<ProductBean> productslist = new ArrayList<>();
    boolean ispastordersPresent = false;
    String tag;


    public MUpcoming() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.list_view, container, false);
        MainActivity settingsActivity = (MainActivity) getActivity();
        myapplication = (MyApplication) settingsActivity.getApplication();
        tinydb = new TinyDB(getActivity());


        listView = (ListView) view.findViewById(R.id.invite);
        past = (Button) view.findViewById(R.id.past_invitations);
        upcoming = (Button) view.findViewById(R.id.upcoming_invitations);

        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = "past";

                productslist = new ArrayList<>();
                past.setBackground(getResources().getDrawable(R.drawable.radius_button_2));
                past.setTextColor(getResources().getColor(R.color.white));
                upcoming.setBackground(getResources().getDrawable(R.drawable.border_button_2));
                upcoming.setTextColor(getResources().getColor(R.color.colorPrimary));
                getOrders("customerorders/past", "past");
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (tag.equals("future")) {
                    if (!ispastordersPresent) {
                        ProductBean bean = (ProductBean) adapterView.getItemAtPosition(i);
                        Intent intent = new Intent(getActivity(), MInvitationDetails.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isseller", true);
                        bundle.putString("product_id", String.valueOf(bean.getProductId()));
                        tinydb.putString("productid", String.valueOf(bean.getProductId()));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return;

                    }
                }

                ProductBean bean = (ProductBean) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), MInvitationDetails.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isseller", false);
                bundle.putString("product_id", String.valueOf(bean.getProductId()));
                tinydb.putString("productid", String.valueOf(bean.getProductId()));
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = "future";
                productslist = new ArrayList<>();
                upcoming.setBackground(getResources().getDrawable(R.drawable.radius_button));
                upcoming.setTextColor(getResources().getColor(R.color.white));
                past.setBackground(getResources().getDrawable(R.drawable.border_button));
                past.setTextColor(getResources().getColor(R.color.colorPrimary));
                getOrders("customerorders/future", "future");


            }
        });

        getOrders("customerorders/future", "future");

        tag = "future";
        return view;
    }

    public void getOrders(String path, final String TAG) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.show();

        RetrofitController.getRequestController(myapplication, dialog, path,
                tinydb.getString("accesstoken"), new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        Log.d("response", success);
                        Gson gson = new Gson();
                        productslist = new ArrayList<>();

                        try {
                            JSONObject responseobject = new JSONObject(success);
                            if (responseobject.getInt("code") == 100) {
                                Type order_type = new TypeToken<List<OrderBean>>
                                        () {
                                }.getType();


                                List<OrderBean> orderlist = gson.fromJson(responseobject
                                                .getJSONArray("data")
                                                .toString(),
                                        order_type);

                                for (int i = 0; i < orderlist.size(); i++) {
                                    productslist.add(orderlist.get(i).getProduct());

                                    tinydb.putBoolean("isseller", false);

                                }
                                ispastordersPresent = true;
                                adapter = new InvitationAdapter(getActivity(), productslist);
                                listView.setAdapter(adapter);

                            } else {
                                listView.setAdapter(null);
                                if (TAG.equals("future")) {
                                    getSellersOrders("customerorders/future/seller/0");
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }


    public void getSellersOrders(String path) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.show();

        RetrofitController.getRequestController(myapplication, dialog, path,
                tinydb.getString("accesstoken"), new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        Log.d("response", success);
                        Gson gson = new Gson();
                        productslist = new ArrayList<>();

                        try {
                            JSONObject responseobject = new JSONObject(success);
                            if (responseobject.getInt("code") == 100) {
                                Type order_type = new TypeToken<List<OrderBean>>
                                        () {
                                }.getType();


                                List<OrderBean> orderlist = gson.fromJson(responseobject
                                                .getJSONArray("data")
                                                .toString(),
                                        order_type);

                                for (int i = 0; i < orderlist.size(); i++) {
                                    productslist.add(orderlist.get(i).getProduct());
                                    tinydb.putBoolean("isseller", true);

                                }

                                adapter = new InvitationAdapter(getActivity(), productslist);
                                listView.setAdapter(adapter);

                            } else {

                                listView.setAdapter(null);

                                Toast.makeText(getActivity(), responseobject.getString("message"),
                                        Toast
                                                .LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }


    public void getPastOrdersCount(String path) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.show();

        RetrofitController.getRequestController(myapplication, dialog, path,
                tinydb.getString("accesstoken"), new NewBaseRetrofitCallback() {
                    @Override
                    public void Success(String success) {
                        Log.d("response", success);
                        Gson gson = new Gson();
                        productslist = new ArrayList<>();

                        try {
                            JSONObject responseobject = new JSONObject(success);
                            if (responseobject.getInt("code") == 100) {
                                ispastordersPresent = true;
                            } else {
                                ispastordersPresent = false;
                                getSellersOrders("customerorders/future/seller/0");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }


    public void logoutUser() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.show();
        UserController.logoutUser(myapplication, tinydb.getString("session"), dialog, new BaseRetrofitCallback() {
            @Override
            public void success(String success) {

                try {
                    JSONObject object = new JSONObject(success);
                    boolean successb = object.getBoolean("success");
                    if (successb) {
                        tinydb.clear();
                        Intent intent = new Intent(getActivity(), Splash.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void fail(String fail) {

            }
        });
    }

}
