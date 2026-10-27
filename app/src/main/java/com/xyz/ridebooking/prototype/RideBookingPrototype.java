package com.xyz.ridebooking.prototype;

import androidx.annotation.NonNull;

/**
 * ============================================================================
 * DESIGN PATTERN: PROTOTYPE PATTERN (GoF - Creational)
 * PRESENTER: Presenter 4 (Prototype Pattern Lead)
 *
 * PURPOSE & PROBLEM SOLVED:
 * Passengers frequently book identical recurring commutes (e.g., Home to College,
 * or Hostel to Tech Park). Manually re-entering passenger names, pickup locations,
 * dropoff points, vehicle types, and category preferences every time is tedious.
 *
 * The Prototype Pattern solves this by storing pre-configured prototype objects.
 * Instead of creating a blank object from scratch with `new`, the system
 * CLONES an existing prototype and lets the user customize any field if needed.
 *
 * HOW IT WORKS HERE:
 * - Implements {@link Cloneable} interface.
 * - {@link #clone()} produces a duplicate instance in a distinct memory location.
 * - {@link #getMemoryId()} exposes the JVM identity hash code to prove at runtime
 *   that the clone is an independent object in memory.
 *
 * KEY TALKING POINTS FOR PRESENTER 4:
 * 1. Object creation through cloning rather than calling new and re-populating fields.
 * 2. Decouples the client from concrete initialization and configuration complexities.
 * 3. Verified at runtime: prototype.getMemoryId() != clone.getMemoryId().
 * ============================================================================
 */
public class RideBookingPrototype implements Cloneable {

    private String profileName;
    private String passengerName;
    private String pickup;
    private String destination;
    private String rideType;     // "Bike", "Auto", "Car"
    private String category;     // "Economy", "Premium"
    private String pricingMode;  // "Regular", "Surge", "Night"

    public RideBookingPrototype(String profileName, String passengerName, String pickup,
                                String destination, String rideType, String category, String pricingMode) {
        this.profileName = profileName;
        this.passengerName = passengerName;
        this.pickup = pickup;
        this.destination = destination;
        this.rideType = rideType;
        this.category = category;
        this.pricingMode = pricingMode;
    }

    /**
     * CLONING METHOD: Creates a distinct, independent copy of this ride profile.
     */
    @NonNull
    @Override
    public RideBookingPrototype clone() {
        try {
            return (RideBookingPrototype) super.clone();
        } catch (CloneNotSupportedException e) {
            // Fallback manual clone
            return new RideBookingPrototype(
                    this.profileName, this.passengerName, this.pickup,
                    this.destination, this.rideType, this.category, this.pricingMode
            );
        }
    }

    /**
     * Returns the JVM memory identity hash code (e.g., "0x7a3f8c") to prove object distinction.
     */
    public String getMemoryId() {
        return "0x" + Integer.toHexString(System.identityHashCode(this));
    }

    // Getters and Setters
    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getRideType() { return rideType; }
    public void setRideType(String rideType) { this.rideType = rideType; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPricingMode() { return pricingMode; }
    public void setPricingMode(String pricingMode) { this.pricingMode = pricingMode; }

    @NonNull
    @Override
    public String toString() {
        return "[" + profileName + "] " + passengerName + " (" + pickup + " ➔ " + destination + "), " +
                rideType + " " + category + " @ " + getMemoryId();
    }
}
