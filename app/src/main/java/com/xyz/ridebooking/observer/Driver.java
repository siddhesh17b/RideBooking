package com.xyz.ridebooking.observer;

public class Driver implements RideObserver {

    @Override
    public void update(String rideId, String status) {
        System.out.println("Driver notified: Ride " + rideId + " status is " + status);
    }
}