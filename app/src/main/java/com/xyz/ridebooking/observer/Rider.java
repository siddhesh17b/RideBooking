package com.xyz.ridebooking.observer;

public class Rider implements RideObserver {

    @Override
    public void update(String rideId, String status) {
        System.out.println("Rider notified: Ride " + rideId + " status is " + status);
    }
}