package com.yousef.airport.airport_java_mvp.network;

import com.yousef.airport.airport_java_mvp.model.AirportLatLongPOJO;
import com.yousef.airport.airport_java_mvp.model.AirportsPOJO;
import com.yousef.airport.airport_java_mvp.model.LuftSchedulesPOJO;
import com.yousef.airport.airport_java_mvp.model.TokenPOJO;

import java.util.HashMap;

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
