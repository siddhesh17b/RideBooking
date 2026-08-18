package com.xyz.ridebooking.observer;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class RideCoordinator {

    private List<RideObserver> observers = new ArrayList<>();
    private DatabaseManager database;

    public RideCoordinator(Context context) {
        database = new DatabaseManager(context);
    }

    public void registerObserver(RideObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String rideId, String status) {
        database.updateStatus(rideId, status);

        for(RideObserver observer : observers) {
            observer.update(rideId, status);
        }
    }
}