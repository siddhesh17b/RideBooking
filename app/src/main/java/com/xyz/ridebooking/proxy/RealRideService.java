package com.xyz.ridebooking.proxy;

import com.xyz.ridebooking.factory.Ride;

// Real Subject: performs the actual ride-booking operation.
// The Proxy controls access to this class.
public class RealRideService implements RideService {

    @Override
    public String bookRide(Ride ride) {

        // Actual booking logic is performed here.
        return "Ride booked successfully: " + ride.getDetails();
    }
}