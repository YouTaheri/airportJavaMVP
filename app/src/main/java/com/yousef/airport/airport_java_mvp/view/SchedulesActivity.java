package com.yousef.airport.airport_java_mvp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yousef.airport.airport_java_mvp.model.LuftSchedulesPOJO;
import com.yousef.airport.airport_java_mvp.network.Events;
import com.yousef.khedmatazma.safebodatest.R;
import com.yousef.airport.airport_java_mvp.utils.Const;
import com.yousef.airport.airport_java_mvp.utils.TinyDB;
import com.yousef.airport.airport_java_mvp.utils.Utils;
import com.yousef.khedmatazma.safebodatest.network.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.yousef.airport.airport_java_mvp.utils.Const.DESTINATION;
import static com.yousef.airport.airport_java_mvp.utils.Const.ORIGIN;

public class SchedulesActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.tvOrigin)
    TextView tvOrigin;
    @BindView(R.id.tvDestination)
    TextView tvDestination;

    List<LuftSchedulesPOJO.Schedule> schedulesList = new ArrayList<>();
    SchedulesAdapter schedulesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedules);
        ButterKnife.bind(this);
        iniUI();
        setData();
    }

    public void iniUI() {
        tvOrigin.setText(new TinyDB(this).getString(ORIGIN));
        tvDestination.setText(new TinyDB(this).getString(DESTINATION));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        schedulesAdapter = new SchedulesAdapter(this, schedulesList);
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(false);
            getSchedulesApi();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        registerEventBus();
        getSchedulesApi();
    }

    @Override
    public void onStop() {
        super.onStop();
        unRegisterEventBus();
    }
    public void getSchedulesApi() {
        EventBus.getDefault().post(new Events.RequestLuftSchedules(this, Const.requestSchedules(this)));
    }

    public void setData() {
        recyclerView.setAdapter(schedulesAdapter);
    }

    @SuppressLint("NewApi")
    @Subscribe
    public void getSchedulesResult(Events.GetLuftSchedules data) {
        schedulesList.clear();
        List<LuftSchedulesPOJO.Schedule> schedules=data.getData().ScheduleResource.Schedules;
        Collections.sort(schedules);
        schedulesList.addAll(schedules);
        if (schedulesList.size()==0) {
            Utils.showMessageDialog(this, getString(R.string.empty_schedule), null);
            btBackClick();
        }else
            schedulesAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btBack)
    public void btBackClick() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        Utils.gotoNextActivityAnimation(SchedulesActivity.this);
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
