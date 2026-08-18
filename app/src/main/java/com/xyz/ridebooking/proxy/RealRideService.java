package com.xyz.ridebooking.proxy;

import com.xyz.ridebooking.factory.Ride;

public class RealRideService implements RideService {

    @Override
    public String bookRide(Ride ride) {
        return "Ride booked successfully: " + ride.getDetails();
    }
}