package com.yousef.airport.airport_java_mvp.network;

import android.annotation.SuppressLint;
import android.content.Context;

import com.yousef.airport.airport_java_mvp.model.AirportLatLongPOJO;
import com.yousef.airport.airport_java_mvp.model.AirportsPOJO;
import com.yousef.airport.airport_java_mvp.model.LuftSchedulesPOJO;
import com.yousef.airport.airport_java_mvp.model.TokenPOJO;

import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Response;

@SuppressLint("NewApi")
public class Events {
    public static class RequestAccessToken {
        public RequestAccessToken(final Context mContext, Call<TokenPOJO> call) {
            new ServerRequest<TokenPOJO>(mContext, call) {
                @Override
                public void onCompletion(Response<TokenPOJO> response) {
                    EventBus.getDefault().post(new GetAccessToken(response.body()));
                }
            };
        }
    }
    public static class GetAccessToken {
        TokenPOJO data;
        GetAccessToken(TokenPOJO data) {
            this.data = data;
        }
        public TokenPOJO getData() {
            return data;
        }
    }

    public static class RequestLuftSchedules {
        public RequestLuftSchedules(final Context mContext, Call<LuftSchedulesPOJO> call) {
            new ServerRequest<LuftSchedulesPOJO>(mContext, call) {
                @Override
                public void onCompletion(Response<LuftSchedulesPOJO> response) {
                    EventBus.getDefault().post(new GetLuftSchedules(response.body()));
                }
            };
        }
    }
    public static class GetLuftSchedules {
        LuftSchedulesPOJO data;
        GetLuftSchedules(LuftSchedulesPOJO data) {
            this.data = data;
        }
        public LuftSchedulesPOJO getData() {
            return data;
        }
    }

    public static class RequestAirports {
        public RequestAirports(final Context mContext, Call<AirportsPOJO> call) {
            new ServerRequest<AirportsPOJO>(mContext, call) {
                @Override
                public void onCompletion(Response<AirportsPOJO> response) {
                    EventBus.getDefault().post(new GetAirports(response.body()));
                }
            };
        }
    }
    public static class GetAirports {
        AirportsPOJO data;
        GetAirports(AirportsPOJO data) {
            this.data = data;
        }
        public AirportsPOJO getData() {
            return data;
        }
    }

    public static class RequestOriginLatLong {
        public RequestOriginLatLong(final Context mContext, Call<AirportLatLongPOJO> call) {
            new ServerRequest<AirportLatLongPOJO>(mContext, call) {
                @Override
                public void onCompletion(Response<AirportLatLongPOJO> response) {
                    EventBus.getDefault().post(new GetOriginLatLong(response.body()));
                }
            };
        }
    }
    public static class GetOriginLatLong {
        AirportLatLongPOJO data;
        GetOriginLatLong(AirportLatLongPOJO data) {
            this.data = data;
        }
        public AirportLatLongPOJO getData() {
            return data;
        }
    }

    public static class RequestDestinationLatLong {
        public RequestDestinationLatLong(final Context mContext, Call<AirportLatLongPOJO> call) {
            new ServerRequest<AirportLatLongPOJO>(mContext, call) {
                @Override
                public void onCompletion(Response<AirportLatLongPOJO> response) {
                    EventBus.getDefault().post(new GetDestinationLatLong(response.body()));
                }
            };
        }
    }
    public static class GetDestinationLatLong {
        AirportLatLongPOJO data;
        GetDestinationLatLong(AirportLatLongPOJO data) {
            this.data = data;
        }
        public AirportLatLongPOJO getData() {
            return data;
        }
    }
}
