package com.yousef.khedmatazma.safebodatest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirportLatLongPOJO {

    @SerializedName("AirportResource")
    public AirportResource AirportResource;

    public static class AirportResource {
        @SerializedName("Airports")
        public Airports Airports;
    }

    public static class Airports {
        @SerializedName("Airport")
        public Airport Airport;
    }

    public static class Airport {
        @SerializedName("Position")
        public Position Position;
    }

    public static class Position {
        @SerializedName("Coordinate")
        public Coordinate Coordinate;
    }

    public static class Coordinate {
        @SerializedName("Latitude")
        public String Latitude;
        @SerializedName("Longitude")
        public String Longitude;
    }
}
