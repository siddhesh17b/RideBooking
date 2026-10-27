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
     * Pushes a new memento snapshot onto the history stack.
     */
    public void saveSnapshot(RideBookingMemento memento) {
        if (memento != null) {
            undoStack.push(memento);
        }
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
