package com.xyz.ridebooking.proxy;

import com.xyz.ridebooking.factory.Ride;

// Proxy: controls access to the RealRideService
// before allowing the ride-booking operation.
public class RideServiceProxy implements RideService {

    // Reference to the actual service that performs the booking.
    private RealRideService realRideService;

    // Stores whether the user is authenticated.
    private boolean loggedIn;

    public RideServiceProxy(boolean loggedIn) {
        this.loggedIn = loggedIn;

        // Proxy creates/holds the Real Subject.
        realRideService = new RealRideService();
    }

    @Override
    public String bookRide(Ride ride) {

        // Proxy checks access before calling the real service.
        // If the user is not logged in, the request is blocked.
        if(!loggedIn) {
            return "Please login before booking a ride.";
        }

        // Access is allowed, so the Proxy forwards the request
        // to the RealRideService.
        return realRideService.bookRide(ride);
    }
}