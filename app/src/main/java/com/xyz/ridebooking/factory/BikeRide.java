package com.xyz.ridebooking.factory;

public class BikeRide extends Ride {

    public BikeRide(String name) {
        super(name);
    }

    @Override
    public String getRideType() {
        return "Bike Ride";
    }
}