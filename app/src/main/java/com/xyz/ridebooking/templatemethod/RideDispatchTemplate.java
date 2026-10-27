package com.xyz.ridebooking.templatemethod;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * DESIGN PATTERN: TEMPLATE METHOD PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 2 (Template Method Pattern Lead)
 *
 * PURPOSE & PROBLEM SOLVED:
 * Every ride dispatch follows a strict, multi-step pipeline:
 * 1. Verify driver availability & GPS lock.
 * 2. Perform vehicle-specific mechanical inspection.
 * 3. Enforce vehicle-specific passenger safety protocol.
 * 4. Issue official dispatch ticket.
 *
 * Without the Template Method pattern, different vehicle classes would either:
 * - Duplicate the boilerplate steps (violating DRY), or
 * - Allow steps to run in arbitrary, out-of-order sequences (causing bugs).
 *
 * HOW IT WORKS HERE:
 * - {@link #executeDispatchWorkflow(String, String, String)} is marked `final` so
 *   subclasses CANNOT alter the execution sequence.
 * - Common steps are implemented in this base class.
 * - Primitive/Hook steps ({@link #conductVehicleInspection()}, {@link #enforceSafetyProtocols()})
 *   are abstract and implemented differently by Bike, Auto, and Car subclasses.
 *
 * KEY TALKING POINTS FOR PRESENTER 2:
 * 1. Inversion of Control ("Hollywood Principle": "Don't call us, we'll call you").
 * 2. The base class controls the overall algorithm skeleton.
 * 3. Subclasses only supply customized behavior for specific hook steps.
 * ============================================================================
 */
public abstract class RideDispatchTemplate {

    /**
     * THE TEMPLATE METHOD.
     * Marked 'final' so subclasses cannot change the algorithm sequence.
     *
     * @param passenger Passenger name
     * @param pickup    Pickup location
     * @param dest      Destination location
     * @return Execution trace showing each step executed in order.
     */
    public final List<String> executeDispatchWorkflow(String passenger, String pickup, String dest) {
        List<String> pipelineLogs = new ArrayList<>();

        // Step 1: Invariant Common Step
        pipelineLogs.add("Step 1 [Common]: " + verifyDriverAndGPS());

        // Step 2: Primitive Step (Overridden by Subclasses)
        pipelineLogs.add("Step 2 [" + getVehicleType() + " Hook]: " + conductVehicleInspection());

        // Step 3: Primitive Step (Overridden by Subclasses)
        pipelineLogs.add("Step 3 [" + getVehicleType() + " Hook]: " + enforceSafetyProtocols());

        // Step 4: Invariant Common Step
        pipelineLogs.add("Step 4 [Common]: " + issueDispatchTicket(passenger, pickup, dest));

        return pipelineLogs;
    }

    // Common invariant step 1
    private String verifyDriverAndGPS() {
        return "Nearest driver matched. GPS lock acquired & ETA calculated.";
    }

    // Abstract primitive step 2: Vehicle inspection hook
    protected abstract String conductVehicleInspection();

    // Abstract primitive step 3: Safety protocols hook
    protected abstract String enforceSafetyProtocols();

    // Vehicle name identifier
    public abstract String getVehicleType();

    // Common invariant step 4
    private String issueDispatchTicket(String passenger, String pickup, String dest) {
        return "Trip ticket issued for " + passenger + " (" + pickup + " ➔ " + dest + ") via " + getVehicleType() + ".";
    }
}
