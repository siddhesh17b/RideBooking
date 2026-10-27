package com.xyz.ridebooking.memento;

/**
 * ============================================================================
 * DESIGN PATTERN: MEMENTO PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 5 (Memento Pattern Lead)
 *
 * ROLE: Originator (Booking Form Originator)
 *
 * Owns the actual current state of the trip booking form.
 * Creates a {@link RideBookingMemento} snapshot of itself when requested,
 * and restores its internal state when given a previous memento.
 * ============================================================================
 */
public class BookingOriginator {

    private String passengerName;
    private String pickupLocation;
    private String destinationLocation;
    private String rideType;
    private String category;
    private String pricingMode;

    public void setFormState(String name, String pickup, String dest, String rideType, String category, String pricingMode) {
        this.passengerName = name;
        this.pickupLocation = pickup;
        this.destinationLocation = dest;
        this.rideType = rideType;
        this.category = category;
        this.pricingMode = pricingMode;
    }

    /**
     * Creates and returns an immutable Memento snapshot of the current state.
     */
    public RideBookingMemento saveStateToMemento() {
        return new RideBookingMemento(passengerName, pickupLocation, destinationLocation,
                rideType, category, pricingMode);
    }

    /**
     * Restores internal state from a previously saved Memento snapshot.
     */
    public void restoreFromMemento(RideBookingMemento memento) {
        if (memento != null) {
            this.passengerName = memento.getPassengerName();
            this.pickupLocation = memento.getPickupLocation();
            this.destinationLocation = memento.getDestinationLocation();
            this.rideType = memento.getRideType();
            this.category = memento.getCategory();
            this.pricingMode = memento.getPricingMode();
        }
    }

    // Getters for restored values
    public String getPassengerName() { return passengerName; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDestinationLocation() { return destinationLocation; }
    public String getRideType() { return rideType; }
    public String getCategory() { return category; }
    public String getPricingMode() { return pricingMode; }
}
