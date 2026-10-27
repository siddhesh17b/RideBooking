package com.xyz.ridebooking.prototype;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ============================================================================
 * DESIGN PATTERN: PROTOTYPE PATTERN (GoF - Creational)
 * PRESENTER: Presenter 4 (Prototype Pattern Lead)
 *
 * ROLE: Prototype Registry / Manager
 *
 * Stores pre-configured prototype templates that users commonly use. When a user
 * requests a commute preset (e.g., "College Commute"), the registry fetches
 * the registered prototype and CLONES it before returning it to the caller.
 * ============================================================================
 */
public class RidePrototypeRegistry {

    private final Map<String, RideBookingPrototype> prototypes = new HashMap<>();

    public RidePrototypeRegistry() {
        loadDefaultPrototypes();
    }

    private void loadDefaultPrototypes() {
        // Preset 1: Student daily commute
        prototypes.put("College Commute", new RideBookingPrototype(
                "College Commute",
                "Siddhesh",
                "Hostel Block C",
                "Campus Gate 2",
                "Bike",
                "Economy",
                "Standard Rate (1.0x)"
        ));

        // Preset 2: Tech Park Office Express
        prototypes.put("Office Express", new RideBookingPrototype(
                "Office Express",
                "Priya",
                "Green Glen Layout",
                "Cyber Tech Park",
                "Car",
                "Premium",
                "Peak Rush Surge (1.8x)"
        ));

        // Preset 3: Weekend City Mall Trip
        prototypes.put("City Mall Trip", new RideBookingPrototype(
                "City Mall Trip",
                "Aman",
                "Metro Station Exit 3",
                "Phoenix Marketcity",
                "Auto",
                "Economy",
                "Standard Rate (1.0x)"
        ));
    }

    public void addPrototype(String key, RideBookingPrototype prototype) {
        prototypes.put(key, prototype);
    }

    public Set<String> getAvailablePresets() {
        return prototypes.keySet();
    }

    /**
     * Retrieves an independent CLONE of the requested prototype.
     */
    public RideBookingPrototype getClonedRide(String key) {
        RideBookingPrototype prototype = prototypes.get(key);
        if (prototype != null) {
            return prototype.clone();
        }
        return null;
    }

    /**
     * Gets direct reference to original prototype (for memory ID comparison proof).
     */
    public RideBookingPrototype getOriginalPrototype(String key) {
        return prototypes.get(key);
    }
}
