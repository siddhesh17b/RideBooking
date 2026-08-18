package com.xyz.ridebooking.singleton;

public class RideBookingSystem {

    private static RideBookingSystem instance;

    private RideBookingSystem() {
    }

    public static RideBookingSystem getInstance() {
        if(instance == null) {
            instance = new RideBookingSystem();
        }

        return instance;
    }

    public String getSystemName() {
        return "Ride Booking System";
    }
}