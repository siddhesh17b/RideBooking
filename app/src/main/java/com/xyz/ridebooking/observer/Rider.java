package com.xyz.ridebooking.observer;

/**
 * ============================================================================
 * DESIGN PATTERN: OBSERVER PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 1 (Observer Pattern Lead)
 *
 * ROLE: Concrete Observer (Rider / Passenger Subscriber)
 *
 * Demonstrates how the Rider entity receives push-like status updates.
 * When the ride is confirmed, the passenger receives immediate confirmation
 * and estimated arrival signals through this observer callback.
 * ============================================================================
 */
public class Rider implements RideObserver {

    private String lastNotification = "No notifications yet";

    @Override
    public void update(String rideId, String status) {
        lastNotification = "Rider App: Your ride " + rideId + " is now [" + status + "]. Driver is on the way.";
        System.out.println(lastNotification);
    }

    @Override
    public String getLastNotification() {
        return lastNotification;
    }
}
