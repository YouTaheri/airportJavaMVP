package com.yousef.khedmatazma.safebodatest.network;

import com.yousef.khedmatazma.safebodatest.model.AirportLatLongPOJO;
import com.yousef.khedmatazma.safebodatest.model.AirportsPOJO;
import com.yousef.khedmatazma.safebodatest.model.LuftSchedulesPOJO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIs {
    @GET
    Call<LuftSchedulesPOJO> requestLuftSchedules(@Url String url_part);

    @GET
    Call<AirportsPOJO> requestAirports(@Url String url_part);

    @GET
    Call<AirportLatLongPOJO> requestOriginLatLong(@Url String url_part);

    @GET
    Call<AirportLatLongPOJO> requestDestinationLatLong(@Url String url_part);

}
