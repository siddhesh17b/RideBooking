package com.xyz.ridebooking.templatemethod;

/**
 * ============================================================================
 * DESIGN PATTERN: TEMPLATE METHOD PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 2 (Template Method Pattern Lead)
 *
 * ROLE: Concrete Class (Car Dispatch Workflow)
 *
 * Implements the primitive hooks specifically tailored for four-wheelers:
 * - Dual-zone AC airflow and OBD-II electronic diagnostics.
 * - 3-point seatbelt interlocks and child-lock checks.
 * ============================================================================
 */
public class CarDispatchWorkflow extends RideDispatchTemplate {

    @Override
    protected String conductVehicleInspection() {
        return "Car Check: Dual-zone AC cooling active, OBD-II diagnostic cleared, boot space ready.";
    }

    @Override
    protected String enforceSafetyProtocols() {
        return "Car Safety: All 3-point seatbelts locked & rear child safety locks engaged.";
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }
}
