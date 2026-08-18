package com.xyz.ridebooking.abstractfactory;

public class PremiumRideFactory implements RideFamilyFactory {

    @Override
    public String createBike() {
        return "Premium Bike";
    }

    @Override
    public String createCar() {
        return "Premium Car";
    }
}