package com.yousef.airport.airport_java_mvp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.yousef.airport.airport_java_mvp.network.APIs;
import com.yousef.khedmatazma.safebodatest.R;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("SimpleDateFormat")
public class Utils {
    public static APIs requestApiDefault(Boolean isTokenAPI, String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request;
                    if (isTokenAPI)
                        request = chain.request().newBuilder().build();
                    else
                        request = chain.request().newBuilder().addHeader("Accept" , "application/json")
                                .addHeader("Authorization", "Bearer " + token).build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(APIs.class);
    }

    /**
     * Check internet availabilty
     *
     * @param mContext Context of activity or fragment
     * @return Returns true is internet connected and false if no internet connected
     */
    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null ? cm.getActiveNetworkInfo() : null) != null;
    }

    /**
     * Goto next Activity with animation
     *
     * @param mContext Context of the Activity.
     */
    public static void gotoNextActivityAnimation(Context mContext) {
        ((Activity) mContext).overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    /**
     * Show Log
     *
     * @param message Message that want to show into Log
     */
    public static void showLog(String message) {
        Log.e("Log Message", "" + message);
    }

    /**
     * Show message dialog
     *
     * @param mContext Context of activity o fragment
     * @param message  Message that shows on Dialog
     * @param listener Set action that you want to performon OK click
     * @return dialog
     */
    public static AlertDialog.Builder showMessageDialog(Context mContext, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setNegativeButton(mContext.getString(R.string.ok), listener);
        try {
            dialog.show();
        } catch (Exception ignored) {}
        return dialog;
    }
}
