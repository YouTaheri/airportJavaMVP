package com.yousef.khedmatazma.safebodatest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LuftSchedulesPOJO {

    @SerializedName("ScheduleResource")
    public ScheduleResource ScheduleResource;

    public static class ScheduleResource {
        @SerializedName("Schedule")
        public List<Schedule> Schedules;
    }

    public static class Schedule implements Comparable<Schedule> {
        @SerializedName("TotalJourney")
        public TotalJourney TotalJourney;
        @SerializedName("Flight")
        public Flight Flight;

        @Override
        public int compareTo(Schedule schedule) {
            return schedule.Flight.Departure.ScheduledTimeLocal.DateTime.compareToIgnoreCase(Flight.Departure.ScheduledTimeLocal.DateTime);
        }
    }

    public static class TotalJourney {
        @SerializedName("Duration")
        public String Duration;
    }

    public static class Flight {
        @SerializedName("Departure")
        public Dep_Arr Departure;
        @SerializedName("Arrival")
        public Dep_Arr Arrival;
        @SerializedName("Details")
        public Details Details;
        @SerializedName("MarketingCarrier")
        public MarketingCarrier MarketingCarrier;
    }

    public static class MarketingCarrier {
        @SerializedName("FlightNumber")
        public String FlightNumber;
    }

    public static class Dep_Arr {
        @SerializedName("AirportCode")
        public String AirportCode;
        @SerializedName("ScheduledTimeLocal")
        public ScheduledTimeLocal ScheduledTimeLocal;
        @SerializedName("Terminal")
        public Terminal Terminal;
    }

    public static class ScheduledTimeLocal {
        @SerializedName("DateTime")
        public String DateTime;
    }

    public static class Terminal {
        @SerializedName("Name")
        public String Name;
    }

    public static class Details {
        @SerializedName("Stops")
        public Stops Stops;
    }

    public static class Stops {
        @SerializedName("StopQuantity")
        public String StopQuantity;
    }
}
