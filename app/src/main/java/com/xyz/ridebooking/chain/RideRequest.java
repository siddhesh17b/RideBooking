package com.xyz.ridebooking.chain;

public class RideRequest {

    private String name;
    private String pickup;
    private String destination;
    private String rideType;
    private boolean loggedIn;

    public RideRequest(String name, String pickup, String destination, String rideType, boolean loggedIn) {
        this.name = name;
        this.pickup = pickup;
        this.destination = destination;
        this.rideType = rideType;
        this.loggedIn = loggedIn;
    }

    public String getName() {
        return name;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDestination() {
        return destination;
    }

    public String getRideType() {
        return rideType;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}