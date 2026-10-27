package com.xyz.ridebooking.templatemethod;

/**
 * ============================================================================
 * DESIGN PATTERN: TEMPLATE METHOD PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 2 (Template Method Pattern Lead)
 *
 * ROLE: Concrete Class (Bike Dispatch Workflow)
 *
 * Implements the primitive hooks specifically tailored for two-wheelers:
 * - Tyre pressure and chain tension inspection.
 * - Mandatory dual-helmet safety checklist.
 * ============================================================================
 */
public class BikeDispatchWorkflow extends RideDispatchTemplate {

    @Override
    protected String conductVehicleInspection() {
        return "Bike Check: Tyre pressure at 32 PSI, brake cables & headlight operational.";
    }

    @Override
    protected String enforceSafetyProtocols() {
        return "Bike Safety: Dual ISI-certified helmets verified for captain and passenger.";
    }

    @Override
    public String getVehicleType() {
        return "Bike";
    }
}
