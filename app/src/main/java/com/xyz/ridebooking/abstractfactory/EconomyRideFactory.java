package com.xyz.ridebooking.abstractfactory;

public class EconomyRideFactory implements RideFamilyFactory {

    @Override
    public String createBike() {
        return "Economy Bike";
    }

    @Override
    public String createCar() {
        return "Economy Car";
    }
}   