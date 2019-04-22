package com.yousef.airport.airport_java_mvp.view;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.yousef.airport.airport_java_mvp.network.Events;
import com.yousef.khedmatazma.safebodatest.R;
import com.yousef.airport.airport_java_mvp.utils.Const;
import com.yousef.airport.airport_java_mvp.utils.TinyDB;
import com.yousef.airport.airport_java_mvp.utils.Utils;
import com.yousef.khedmatazma.safebodatest.network.*;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yousef.airport.airport_java_mvp.utils.Const.DESTINATION;
import static com.yousef.airport.airport_java_mvp.utils.Const.ORIGIN;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {


    @BindView(R.id.tvOrigin)
    TextView tvOrigin;
    @BindView(R.id.tvDestination)
    TextView tvDestination;

    private GoogleMap mMap;
    Drawable circleDrawable;
    BitmapDescriptor markerIcon;
    Double dblOrgLat,dblOrgLong,dblDstLat,dblDstLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        tvOrigin.setText(new TinyDB(this).getString(ORIGIN));
        tvDestination.setText(new TinyDB(this).getString(DESTINATION));
    }
    @Override
    public void onStart() {
        super.onStart();
        registerEventBus();
        getOriginLatLongApi();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerEventBus();
        getOriginLatLongApi();
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
    public void getOriginLatLongApi() {
        EventBus.getDefault().post(new Events.RequestOriginLatLong(this, Const.requestOriginLatLong(this)));
    }

    public void showMarkers() {
        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        circleDrawable = getResources().getDrawable(R.drawable.map_marker);
        markerIcon = getMarkerIconFromDrawable(circleDrawable);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        dblOrgLat = Double.parseDouble(new TinyDB(this).getString(Const.orgLat));
        dblOrgLong = Double.parseDouble(new TinyDB(this).getString(Const.orgLong));
        dblDstLat = Double.parseDouble(new TinyDB(this).getString(Const.dstLat));
        dblDstLong = Double.parseDouble(new TinyDB(this).getString(Const.dstLong));
        // Add a marker move the camera
        LatLng origin = new LatLng(dblOrgLat , dblOrgLong );
        LatLng destination = new LatLng(dblDstLat , dblDstLong);

        mMap.addMarker(new MarkerOptions().position(origin).icon(markerIcon)
                .draggable(false).visible(true).title("Origin: " + new TinyDB(this).getString(ORIGIN)));
        mMap.addMarker(new MarkerOptions().position(destination).icon(markerIcon)
                .draggable(false).visible(true).title("Destination: " + new TinyDB(this).getString(DESTINATION))).showInfoWindow();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        //the include method will calculate the min and max bound.
        builder.include(origin);
        builder.include(destination);
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.2);
        mMap.animateCamera((CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)));

        //Polyline options
        PolylineOptions options = new PolylineOptions();
        List<PatternItem> pattern = Arrays.<PatternItem>asList(new Dash(30), new Gap(10));
        mMap.addPolyline(options.width(10).color(R.color.colorPrimary).geodesic(false).pattern(pattern).add(origin).add(destination));
    }

    @OnClick(R.id.btBack)
    public void btBackClick() {
        startActivity(new Intent(getApplicationContext(), SchedulesActivity.class));
        Utils.gotoNextActivityAnimation(MapActivity.this);
    }

    @SuppressLint("NewApi")
    @Subscribe
    public void getOriginLatLongResult(final Events.GetOriginLatLong data) {
        new TinyDB(this).putString(Const.orgLat,data.getData().AirportResource.Airports.Airport.Position.Coordinate.Latitude);
        new TinyDB(this).putString(Const.orgLong,data.getData().AirportResource.Airports.Airport.Position.Coordinate.Longitude);
        EventBus.getDefault().post(new Events.RequestDestinationLatLong(this, Const.requestDestinationLatLong(this)));
    }

    @SuppressLint("NewApi")
    @Subscribe
    public void getDestinationLatLongResult(final Events.GetDestinationLatLong data) {
        new TinyDB(this).putString(Const.dstLat,data.getData().AirportResource.Airports.Airport.Position.Coordinate.Latitude);
        new TinyDB(this).putString(Const.dstLong,data.getData().AirportResource.Airports.Airport.Position.Coordinate.Longitude);
        showMarkers();
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        int height = 150, width = 100;
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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

