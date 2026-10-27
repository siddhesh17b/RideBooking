package com.xyz.ridebooking.memento;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ============================================================================
 * DESIGN PATTERN: MEMENTO PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 5 (Memento Pattern Lead)
 *
 * PURPOSE & PROBLEM SOLVED:
 * Passengers frequently modify input fields (changing pickup, destination,
 * vehicle type, or pricing mode) while exploring route options. If they make a
 * mistake or want to revert back to their earlier inputs, standard UI widgets
 * lose the previous state.
 *
 * Storing private form fields in a global public variable would violate OOP
 * encapsulation and expose internal state to outside classes.
 *
 * HOW IT WORKS HERE:
 * - Memento: {@link RideBookingMemento} is an immutable snapshot holding state.
 *   Its fields cannot be modified after instantiation.
 * - Originator: {@link BookingOriginator} creates snapshots of its current state
 *   and restores its internal fields from a provided memento.
 * - Caretaker: {@link BookingCaretaker} stores snapshots in an undo stack without
 *   ever inspecting, reading, or modifying the contents of the memento.
 *
 * KEY TALKING POINTS FOR PRESENTER 5:
 * 1. Preserves Encapsulation: Internal state is externalized safely without making fields public.
 * 2. Reliable Undo / Rollback: Provides instant rollback to any previous form draft.
 * 3. Clear separation of responsibilities between Originator, Memento, and Caretaker.
 * ============================================================================
 */
public class RideBookingMemento {

    private final String passengerName;
    private final String pickupLocation;
    private final String destinationLocation;
    private final String rideType;
    private final String category;
    private final String pricingMode;
    private final String timestamp;

    public RideBookingMemento(String passengerName, String pickupLocation, String destinationLocation,
                              String rideType, String category, String pricingMode) {
        this.passengerName = passengerName;
        this.pickupLocation = pickupLocation;
        this.destinationLocation = destinationLocation;
        this.rideType = rideType;
        this.category = category;
        this.pricingMode = pricingMode;
        this.timestamp = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    // Getters only - Memento is strictly immutable
    public String getPassengerName() { return passengerName; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDestinationLocation() { return destinationLocation; }
    public String getRideType() { return rideType; }
    public String getCategory() { return category; }
    public String getPricingMode() { return pricingMode; }
    public String getTimestamp() { return timestamp; }

    public String getSummary() {
        return passengerName + " (" + pickupLocation + " ➔ " + destinationLocation + ") [" +
                rideType + ", " + category + "] at " + timestamp;
    }
}
