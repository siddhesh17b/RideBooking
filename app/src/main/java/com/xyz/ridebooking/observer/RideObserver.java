package com.xyz.ridebooking.observer;

public interface RideObserver {
    void update(String rideId, String status);
}