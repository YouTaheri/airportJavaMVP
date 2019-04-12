package com.yousef.khedmatazma.safebodatest.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import com.yousef.khedmatazma.safebodatest.R;
import com.yousef.khedmatazma.safebodatest.model.AirportsPOJO;
import com.yousef.khedmatazma.safebodatest.utils.Const;
import com.yousef.khedmatazma.safebodatest.utils.Utils;
import com.yousef.khedmatazma.safebodatest.network.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etOrigin)
    EditText etOrigin;
    @BindView(R.id.etDestination)
    EditText etDestination;
    @BindView(R.id.root)
    CoordinatorLayout coordinatorLayout;

    List<String> airportNameStr;
    List<AirportsPOJO.Airport> airports;
    int airportIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getAirportsApi();
    }

    @OnClick(R.id.btShowSchedules)
    public void btShowSchedulesClick() {

        if (etOrigin.getText().toString().isEmpty())
            showError(getString(R.string.enter_origin));
        else
        if (etDestination.getText().toString().isEmpty())
            showError(getString(R.string.enter_destination));
        else{
            airportIndex = find(airportNameStr, etOrigin.getText().toString());
            if (airportIndex > -1)
                Const.setOrigin(this, airports.get(airportIndex).AirportCode);
            else
                Const.setOrigin(this, etOrigin.getText().toString());

            airportIndex = find(airportNameStr, etDestination.getText().toString());
            if (airportIndex > -1)
                Const.setDestination(this, airports.get(airportIndex).AirportCode);
            else
                Const.setDestination(this, etDestination.getText().toString());

            startActivity(new Intent(getApplicationContext(), SchedulesActivity.class));
            Utils.gotoNextActivityAnimation(MainActivity.this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus();
        getAirportsApi();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerEventBus();
        getAirportsApi();
    }

    @Override
    protected void onDestroy() {
        unRegisterEventBus();
        super.onDestroy();
    }
    @Override
    public void onStop() {
        unRegisterEventBus();
        super.onStop();
    }
    public void getAirportsApi() {
        EventBus.getDefault().post(new Events.RequestAirports(this, Const.requestAirports(this)));
    }

    @OnClick(R.id.ivClearOrigin)
    public void ivClearOriginClick() {
        etOrigin.setText("");
    }

    @OnClick(R.id.ivClearDestination)
    public void ivClearDestinationClick() {
        etDestination.setText("");
    }

    @SuppressLint("NewApi")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAirportsResult(final Events.GetAirports data) {
        airports = data.getData().AirportResource.Airports.Airport;
        setAutoStrings();
    }

    public void setAutoStrings() {
        airportNameStr = new ArrayList<>();
        for (AirportsPOJO.Airport airport:airports) {
            airportNameStr.add(airport.Names.Name.fullName + " - " + airport.CityCode + " - " + airport.CountryCode);
        }
        final AutoCompleteTextView tvOrigin = findViewById(R.id.etOrigin);
        ArrayAdapter<String> originAdp=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,airportNameStr);
        tvOrigin.setThreshold(1);
        tvOrigin.setAdapter(originAdp);
        final AutoCompleteTextView tvDestination = findViewById(R.id.etDestination);
        ArrayAdapter<String> destinationAdp=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,airportNameStr);
        tvDestination.setThreshold(1);
        tvDestination.setAdapter(destinationAdp);
    }
    public int find(List<String> a, String target)
    {
        for (int i = 0; i < a.size(); i++)
            if (target.equals(a.get(i)))
                return i;
        return -1;
    }
    /**
     * Snackbar shows error
     */
    private void showError(String msg) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
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



