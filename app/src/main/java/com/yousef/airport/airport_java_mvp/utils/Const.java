package com.yousef.airport.airport_java_mvp.utils;

import android.content.Context;

import com.yousef.airport.airport_java_mvp.model.AirportsPOJO;
import com.yousef.airport.airport_java_mvp.model.LuftSchedulesPOJO;
import com.yousef.airport.airport_java_mvp.model.TokenPOJO;
import com.yousef.airport.airport_java_mvp.model.AirportLatLongPOJO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import retrofit2.Call;

public class Const {
    //<-------------------------------------------------------------------------------->\\
    //Basic constants
    //<-------------------------------------------------------------------------------->\\
    private static String MAIN_BASE = "https://api.lufthansa.com/";
    public static String BASE_URL = MAIN_BASE + "v1/";
    public static String dstLat = "dstLat";
    public static String dstLong = "dstLong";
    public static String orgLat = "orgLat";
    public static String orgLong = "orgLong";
    public static String ORIGIN = "ORIGIN";
    public static String DESTINATION = "DESTINATION";
    private static String AccessToken = "AccessToken";
    public static String AM = " AM";
    public static String PM = " PM";
    //<-------------------------------------------------------------------------------->\\
    //TinyDB read and write
    //<-------------------------------------------------------------------------------->\\
    public static void setAccessToken(Context mContext, String data) {
        new TinyDB(mContext).putString(AccessToken, data);
    }
    public static void setOrigin(Context mContext, String data) {
        new TinyDB(mContext).putString(ORIGIN, data);
    }
    public static void setDestination(Context mContext, String data) {
        new TinyDB(mContext).putString(DESTINATION, data);
    }
    private static String getAccessToken(Context mContext) {
        return new TinyDB(mContext).getString(AccessToken);
    }
    private static String getOrigin(Context mContext) {
        return new TinyDB(mContext).getString(ORIGIN);
    }
    private static String getDestination(Context mContext) {
        return new TinyDB(mContext).getString(DESTINATION);
    }
    //<-------------------------------------------------------------------------------->\\
    //All api's methods
    //<-------------------------------------------------------------------------------->\\
    public static Call<TokenPOJO> requestAccessToken(Context mContext) {
        HashMap<String, String> map = new HashMap<>();
        map.put("client_id", "ssqkem7uzmwagnnc3kmy6hwe");
        map.put("client_secret", "eq69KQan4K");
        map.put("grant_type", "client_credentials");
        return Utils.requestApiDefault(true, getAccessToken(mContext)).requestAccessTokenPost("/v1/oauth/token", map);
    }
    public static Call<LuftSchedulesPOJO> requestSchedules(Context mContext) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return Utils.requestApiDefault(false, getAccessToken(mContext)).requestLuftSchedules(
                "/v1/operations/schedules/"+getOrigin(mContext)+"/"+getDestination(mContext)+"/"+date+
                        "?directFlights=1&limit=100");
    }
    public static Call<AirportsPOJO> requestAirports(Context mContext) {
        return Utils.requestApiDefault(false, getAccessToken(mContext)).requestAirports(
                "/v1/references/airports/?lang=en&limit=100&offset=0&LHoperated=0");
    }
    public static Call<AirportLatLongPOJO> requestOriginLatLong(Context mContext) {
        return Utils.requestApiDefault(false, getAccessToken(mContext)).requestOriginLatLong(
                "/v1/references/airports/"+getOrigin(mContext)+"?lang=en&limit=100&offset=0&LHoperated=0");
    }
    public static Call<AirportLatLongPOJO> requestDestinationLatLong(Context mContext) {
        return Utils.requestApiDefault(false, getAccessToken(mContext)).requestDestinationLatLong(
                "/v1/references/airports/"+getDestination(mContext)+"?lang=en&limit=100&offset=0&LHoperated=0");
    }
}