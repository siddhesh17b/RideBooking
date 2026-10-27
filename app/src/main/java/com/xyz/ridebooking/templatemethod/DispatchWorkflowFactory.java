package com.xyz.ridebooking.templatemethod;

/**
 * Helper to obtain the appropriate Template Method workflow based on the chosen vehicle.
 */
public class DispatchWorkflowFactory {

    public static RideDispatchTemplate getWorkflow(String rideType) {
        if ("Bike".equalsIgnoreCase(rideType)) {
            return new BikeDispatchWorkflow();
        } else if ("Auto".equalsIgnoreCase(rideType)) {
            return new AutoDispatchWorkflow();
        } else {
            return new CarDispatchWorkflow();
        }
    }
}
