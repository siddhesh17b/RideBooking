package com.xyz.ridebooking.proxy;

import com.xyz.ridebooking.factory.Ride;

public class RideServiceProxy implements RideService {

    private RealRideService realRideService;
    private boolean loggedIn;

    public RideServiceProxy(boolean loggedIn) {
        this.loggedIn = loggedIn;
        realRideService = new RealRideService();
    }

    @Override
    public String bookRide(Ride ride) {

        if(!loggedIn) {
            return "Please login before booking a ride.";
        }

        return realRideService.bookRide(ride);
    }
}