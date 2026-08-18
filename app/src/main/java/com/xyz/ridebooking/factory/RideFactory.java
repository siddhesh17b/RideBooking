package com.xyz.ridebooking.factory;

public class RideFactory {

    public Ride createRide(String type, String name) {

        if(type.equalsIgnoreCase("Bike"))
            return new BikeRide(name);

        if(type.equalsIgnoreCase("Auto"))
            return new AutoRide(name);

        if(type.equalsIgnoreCase("Car"))
            return new CarRide(name);

        return null;
    }
}