package com.xyz.ridebooking.memento;

import java.util.Stack;

/**
 * ============================================================================
 * DESIGN PATTERN: MEMENTO PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 5 (Memento Pattern Lead)
 *
 * ROLE: Caretaker (Booking History Caretaker)
 *
 * Responsible for keeping the saved {@link RideBookingMemento} snapshots.
 * It NEVER modifies or examines the contents of a memento; it simply maintains
 * a LIFO undo stack to provide multi-level rollback capabilities.
 * ============================================================================
 */
public class BookingCaretaker {

    private final Stack<RideBookingMemento> undoStack = new Stack<>();

    /**
     * Pushes a new memento snapshot onto the history stack only if it differs from the top.
     */
    public void saveSnapshot(RideBookingMemento memento) {
        if (memento == null) return;

        // Prevent duplicate snapshots of the exact same state back-to-back
        if (!undoStack.isEmpty()) {
            RideBookingMemento top = undoStack.peek();
            if (isSameState(top, memento)) {
                return;
            }
        }
        undoStack.push(memento);
    }

    private boolean isSameState(RideBookingMemento a, RideBookingMemento b) {
        return safeEquals(a.getPassengerName(), b.getPassengerName()) &&
               safeEquals(a.getPickupLocation(), b.getPickupLocation()) &&
               safeEquals(a.getDestinationLocation(), b.getDestinationLocation()) &&
               safeEquals(a.getRideType(), b.getRideType()) &&
               safeEquals(a.getCategory(), b.getCategory()) &&
               safeEquals(a.getPricingMode(), b.getPricingMode());
    }

    private boolean safeEquals(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1 == null || s2 == null) return false;
        return s1.equals(s2);
    }

    /**
     * Pops and returns the most recent snapshot for restoration.
     */
    public RideBookingMemento undo() {
        if (!undoStack.isEmpty()) {
            return undoStack.pop();
        }
        return null;
    }

    /**
     * Checks if there are any snapshots available to undo.
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Returns the count of saved snapshots currently in history.
     */
    public int getHistoryCount() {
        return undoStack.size();
    }
}
