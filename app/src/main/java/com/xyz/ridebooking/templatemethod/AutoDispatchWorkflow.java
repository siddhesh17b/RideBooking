package com.xyz.ridebooking.templatemethod;

/**
 * ============================================================================
 * DESIGN PATTERN: TEMPLATE METHOD PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 2 (Template Method Pattern Lead)
 *
 * ROLE: Concrete Class (Auto Dispatch Workflow)
 *
 * Implements the primitive hooks specifically tailored for three-wheelers:
 * - Calibrated fare meter and CNG cylinder pressure verification.
 * - Open-cabin passenger safety rail check.
 * ============================================================================
 */
public class AutoDispatchWorkflow extends RideDispatchTemplate {

    @Override
    protected String conductVehicleInspection() {
        return "Auto Check: Calibrated digital fare meter & CNG cylinder pressure certified.";
    }

    @Override
    protected String enforceSafetyProtocols() {
        return "Auto Safety: Open-cabin passenger safety side rails & weather curtains checked.";
    }

    @Override
    public String getVehicleType() {
        return "Auto";
    }
}
