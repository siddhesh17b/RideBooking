package com.xyz.ridebooking.factory;

public class AutoRide extends Ride {

    public AutoRide(String name) {
        super(name);
    }

    @Override
    public String getRideType() {
        return "Auto Ride";
    }
}