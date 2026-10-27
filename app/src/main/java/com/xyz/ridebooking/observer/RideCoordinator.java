package com.xyz.ridebooking.observer;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * DESIGN PATTERN: OBSERVER PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 1 (Observer Pattern Lead)
 *
 * ROLE: Subject / Observable (Ride Coordinator)
 *
 * Maintains the registry of all subscribed {@link RideObserver} instances.
 * Whenever a ride event occurs, it iterates through its registered observers
 * and invokes update(), ensuring that all components (Driver, Rider, Database)
 * stay synchronized with zero tight coupling.
 * ============================================================================
 */
public class RideCoordinator {

    private final List<RideObserver> observers = new ArrayList<>();
    private final DatabaseManager database;

    public RideCoordinator(Context context) {
        database = new DatabaseManager(context);
    }

    /**
     * Subscribes an observer to receive future ride notifications.
     */
    public void registerObserver(RideObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Unsubscribes an observer (demonstrates dynamic deregistration at runtime).
     */
    public void unregisterObserver(RideObserver observer) {
        observers.remove(observer);
    }

    /**
     * Returns the list of currently registered observers.
     */
    public List<RideObserver> getObservers() {
        return observers;
    }

    /**
     * Broadcasts the updated ride status to all registered observers and logs to DB.
     */
    public void notifyObservers(String rideId, String status) {
        // Update SQLite database status
        database.updateStatus(rideId, status);

        // Notify each concrete observer
        for (RideObserver observer : observers) {
            observer.update(rideId, status);
        }
    }

    /**
     * Broadcasts status and returns a formatted trace of all observer reactions.
     */
    public List<String> notifyObserversWithTrace(String rideId, String status) {
        notifyObservers(rideId, status);

        List<String> trace = new ArrayList<>();
        trace.add("RideCoordinator: Broadcasted status '" + status + "' to " + observers.size() + " active observers:");
        for (RideObserver obs : observers) {
            trace.add("  • " + obs.getLastNotification());
        }
        return trace;
    }
}
