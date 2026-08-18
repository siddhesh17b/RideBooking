package com.xyz.ridebooking.factory;

public class CarRide extends Ride {

    public CarRide(String name) {
        super(name);
    }

    @Override
    public String getRideType() {
        return "Car Ride";
    }
}