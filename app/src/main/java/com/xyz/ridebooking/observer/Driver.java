package com.xyz.ridebooking.observer;

/**
 * ============================================================================
 * DESIGN PATTERN: OBSERVER PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 1 (Observer Pattern Lead)
 *
 * ROLE: Concrete Observer (Driver Subscriber)
 *
 * Demonstrates how the Driver entity listens to system state changes. When
 * a ride is booked or updated by the RideCoordinator, the Driver instance
 * receives a real-time dispatch notification without knowing the caller's details.
 * ============================================================================
 */
public class Driver implements RideObserver {

    private String lastNotification = "No notifications yet";

    @Override
    public void update(String rideId, String status) {
        lastNotification = "Driver App: Trip " + rideId + " marked as [" + status + "]. Route navigation activated.";
        System.out.println(lastNotification);
    }

    @Override
    public String getLastNotification() {
        return lastNotification;
    }
}
