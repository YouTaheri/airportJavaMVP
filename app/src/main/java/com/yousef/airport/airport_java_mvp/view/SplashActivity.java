package com.yousef.airport.airport_java_mvp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yousef.khedmatazma.safebodatest.R;
import com.yousef.airport.airport_java_mvp.network.Events;
import com.yousef.airport.airport_java_mvp.utils.Const;
import com.yousef.airport.airport_java_mvp.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getTokenApi();
    }
    @Override
    public void onResume() {
        super.onResume();
        registerEventBus();
        getTokenApi();
    }
    @Override
    public void onStop() {
        unRegisterEventBus();
        super.onStop();
    }
    public void getTokenApi() {
        EventBus.getDefault().post(new Events.RequestAccessToken(this, Const.requestAccessToken(this)));
    }
    @SuppressLint("NewApi")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTokenResult(final Events.GetAccessToken data) {
        Const.setAccessToken( this , data.getData().access_token);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        Utils.gotoNextActivityAnimation(SplashActivity.this);
    }
    public void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }
    public void unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}
