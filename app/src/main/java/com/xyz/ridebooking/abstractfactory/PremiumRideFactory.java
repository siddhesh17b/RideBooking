package com.xyz.ridebooking.abstractfactory;

/**
 * Concrete Factory: Creates the Premium tier family of vehicles.
 */
public class PremiumRideFactory implements RideFamilyFactory {

    @Override
    public String createBike() {
        return "Premium Bike (Sport 250cc)";
    }

    @Override
    public String createAuto() {
        return "Premium Auto (EV Silent Comfort)";
    }

    @Override
    public String createCar() {
        return "Premium Car (Sedan / Dual AC & Leather)";
    }
}
