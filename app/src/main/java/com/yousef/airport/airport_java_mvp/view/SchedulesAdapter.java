package com.yousef.airport.airport_java_mvp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yousef.khedmatazma.safebodatest.R;
import com.yousef.airport.airport_java_mvp.model.LuftSchedulesPOJO;
import com.yousef.airport.airport_java_mvp.utils.Utils;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.Holder> {

    private Context mContext;
    private List<LuftSchedulesPOJO.Schedule> list;

    public SchedulesAdapter(Context mContext, List<LuftSchedulesPOJO.Schedule> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false);
        return new Holder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final LuftSchedulesPOJO.Schedule item = list.get(position);
        holder.tvDepartureDate.setText(item.Flight.Departure.ScheduledTimeLocal.DateTime.replace("T","  "));
        holder.tvArrivalDate.setText(item.Flight.Arrival.ScheduledTimeLocal.DateTime.replace("T","  "));
        holder.tvFlightNumber.setText("FlightNumber: " + item.Flight.MarketingCarrier.FlightNumber);
        holder.tvViewMap.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, MapActivity.class));
            Utils.gotoNextActivityAnimation(mContext);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFlightNumber)
        TextView tvFlightNumber;
        @BindView(R.id.tvDepartureDate)
        TextView tvDepartureDate;
        @BindView(R.id.tvArrivalDate)
        TextView tvArrivalDate;
        @BindView(R.id.tvViewMap)
        TextView tvViewMap;
        @BindView(R.id.requestCard)
        CardView requestCard;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
