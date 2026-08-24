package com.xyz.ridebooking.proxy;

import com.xyz.ridebooking.factory.Ride;

// Common interface shared by the Proxy and Real Subject.
// This allows the client to use either through the same interface.
public interface RideService {
    String bookRide(Ride ride);
}