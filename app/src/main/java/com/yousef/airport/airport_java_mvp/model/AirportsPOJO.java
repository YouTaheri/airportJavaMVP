package com.yousef.airport.airport_java_mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirportsPOJO {

    @SerializedName("AirportResource")
    public AirportResource AirportResource;

    public static class AirportResource {
        @SerializedName("Airports")
        public Airports Airports;
    }

    public static class Airports {
        @SerializedName("Airport")
        public List<Airport> Airport;
    }

    public static class Airport {
        @SerializedName("AirportCode")
        public String AirportCode;
        @SerializedName("CityCode")
        public String CityCode;
        @SerializedName("CountryCode")
        public String CountryCode;
        @SerializedName("Position")
        public Position Position;
        @SerializedName("Names")
        public Name Names;
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

    public static class Name {
        @SerializedName("Name")
        public AirportName Name;
    }

    public static class AirportName {
        @SerializedName("$")
        public String fullName;
    }
}
