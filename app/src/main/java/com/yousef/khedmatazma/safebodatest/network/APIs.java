package com.yousef.khedmatazma.safebodatest.network;

import com.yousef.khedmatazma.safebodatest.model.AirportLatLongPOJO;
import com.yousef.khedmatazma.safebodatest.model.AirportsPOJO;
import com.yousef.khedmatazma.safebodatest.model.LuftSchedulesPOJO;
import com.yousef.khedmatazma.safebodatest.model.TokenPOJO;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIs {

    @FormUrlEncoded
    @POST
    Call<TokenPOJO> requestAccessTokenPost(@Url String url_part, @FieldMap HashMap<String, String> map);

    @GET
    Call<LuftSchedulesPOJO> requestLuftSchedules(@Url String url_part);

    @GET
    Call<AirportsPOJO> requestAirports(@Url String url_part);

    @GET
    Call<AirportLatLongPOJO> requestOriginLatLong(@Url String url_part);

    @GET
    Call<AirportLatLongPOJO> requestDestinationLatLong(@Url String url_part);

}
