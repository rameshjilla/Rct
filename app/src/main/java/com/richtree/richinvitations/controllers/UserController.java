package com.richtree.richinvitations.controllers;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.richtree.richinvitations.MyApplication;
import com.richtree.richinvitations.interfaces.BaseProductsDetailscallback;
import com.richtree.richinvitations.interfaces.BaseRetrofitCallback;
import com.richtree.richinvitations.interfaces.LoginCallback;
import com.richtree.richinvitations.interfaces.OtpCallback;
import com.richtree.richinvitations.interfaces.SessionCallback;
import com.richtree.richinvitations.interfaces.UserOrderCallback;
import com.richtree.richinvitations.model.OderBaseModel;
import com.richtree.richinvitations.model.ProductDetailsBaseModel;
import com.richtree.richinvitations.model.UserBaseModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 26/03/2018.
 */

public class UserController {
    public static void getSession(final MyApplication application, final SessionCallback
            sessioncallback, final ProgressDialog dialog) {
        application.userService.getSession().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        sessioncallback.success(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sessioncallback.fail(t.toString());

            }
        });
    }

    public static void getOtp(final MyApplication myapplication, String userid, String password,
                              String sender, final ProgressDialog dialog, String mobilenumber,
                              String
                                      message, final
                              OtpCallback callback) {
        myapplication.userService.getOtpfromServer(userid, password, sender, mobilenumber, message).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.Success(response.body());
                } else {
                    dialog.dismiss();
                    Toast.makeText(myapplication.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.failure(t.toString());

            }
        });
    }

    public static void getRichAlbums(final MyApplication application, String sessionid, String
            productid, final BaseRetrofitCallback
                                             basecallback, final ProgressDialog dialog) {
        application.userService.getRichAlbums(sessionid, productid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        basecallback.success(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                basecallback.fail(t.toString());

            }
        });
    }

    public static void loginUser(final MyApplication application, String request, String session,
                                 final
                                 ProgressDialog dialog, final LoginCallback logincallback) {

        application.userService.loginUser(request, session).enqueue(new Callback<UserBaseModel>() {
            @Override
            public void onResponse(Call<UserBaseModel> call, Response<UserBaseModel> response) {
                if (response.isSuccessful()) {
                    logincallback.success(response.body());
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserBaseModel> call, Throwable t) {
                logincallback.fail(t.toString());

            }
        });

    }


    public static void getOrders(final MyApplication application, String session,
                                 final
                                 ProgressDialog dialog, final UserOrderCallback userordercallback) {


        application.userService.getOrders(session).enqueue(new Callback<OderBaseModel>() {
            @Override
            public void onResponse(Call<OderBaseModel> call, Response<OderBaseModel> response) {
                if (response.isSuccessful()) {
                    userordercallback.success(response.body());
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<OderBaseModel> call, Throwable t) {
                userordercallback.fail(t.toString());

            }
        });


    }

    public static void getOrderProducts(final MyApplication application, String session, String order_id, final ProgressDialog dialog, final BaseRetrofitCallback baseresponsecallback) {
        application.userService.getProducts(session, order_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        baseresponsecallback.success(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    dialog.dismiss();
                    Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                baseresponsecallback.fail(t.toString());

            }
        });
    }


    public static void logoutUser(final MyApplication application, String session, final ProgressDialog dialog, final BaseRetrofitCallback baseresponsecallback) {
        application.userService.logoutUser(session).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        baseresponsecallback.success(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    dialog.dismiss();
                    Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                baseresponsecallback.fail(t.toString());

            }
        });
    }


    public static void getProductsDetails(final MyApplication application, String session, String product_id, final ProgressDialog dialog, final BaseProductsDetailscallback baseresponsecallback) {
        application.userService.getProductsDetails(session, product_id).enqueue(new Callback<ProductDetailsBaseModel>() {
            @Override
            public void onResponse(Call<ProductDetailsBaseModel> call, Response<ProductDetailsBaseModel> response) {
                if (response.isSuccessful()) {
                    baseresponsecallback.success(response.body());

                } else {
                    dialog.dismiss();
                    Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsBaseModel> call, Throwable t) {
                baseresponsecallback.fail(t.toString());

            }
        });
    }


    public static void getProductsRichDetails(final MyApplication application, String session,
                                              String product_id, final ProgressDialog dialog, final
                                              BaseRetrofitCallback baseresponsecallback) {
        application.userService.getProductsRichDetails(session, product_id).enqueue(new
                                                                                            Callback<ResponseBody>() {
                                                                                                @Override
                                                                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                                    if (response.isSuccessful()) {
                                                                                                        try {
                                                                                                            baseresponsecallback.success(response.body().string());
                                                                                                        } catch (IOException e) {
                                                                                                            e.printStackTrace();
                                                                                                        }

                                                                                                    } else {
                                                                                                        dialog.dismiss();
                                                                                                        Toast.makeText(application.getApplicationContext(), "Something went  wrong",
                                                                                                                Toast
                                                                                                                        .LENGTH_SHORT)
                                                                                                                .show();
                                                                                                    }
                                                                                                }

                                                                                                @Override
                                                                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                                                                    baseresponsecallback.fail(t.toString());

                                                                                                }
                                                                                            });
    }


    public static void updateAccount(final MyApplication myapplication, final ProgressDialog
            dialog, String
                                             request, String sessionid, final BaseRetrofitCallback basecallback) {
        myapplication.userService.updateAccount(request, sessionid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        basecallback.success(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    dialog.dismiss();
                    Toast.makeText(myapplication.getApplicationContext(), "Something went  wrong",
                            Toast
                                    .LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
