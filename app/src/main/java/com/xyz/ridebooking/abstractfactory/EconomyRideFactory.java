package com.xyz.ridebooking.abstractfactory;

/**
 * Concrete Factory: Creates the Economy tier family of vehicles.
 */
public class EconomyRideFactory implements RideFamilyFactory {

    @Override
    public String createBike() {
        return "Economy Bike (Commuter 110cc)";
    }

    @Override
    public String createAuto() {
        return "Economy Auto (Standard 3-Seater)";
    }

    @Override
    public String createCar() {
        return "Economy Car (Hatchback / Non-AC)";
    }
}
