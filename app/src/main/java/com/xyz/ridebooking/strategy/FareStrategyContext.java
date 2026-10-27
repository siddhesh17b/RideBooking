package com.xyz.ridebooking.strategy;

/**
 * ============================================================================
 * DESIGN PATTERN: STRATEGY PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 3 (Strategy Pattern Lead)
 *
 * ROLE: Context (Fare Strategy Context)
 *
 * Maintains a reference to the active {@link FareCalculationStrategy} instance.
 * Allows the client (MainActivity) to swap the strategy at runtime based on
 * user selection, without modifying the underlying pricing workflow.
 * ============================================================================
 */
public class FareStrategyContext {

    private FareCalculationStrategy strategy;

    public FareStrategyContext(FareCalculationStrategy initialStrategy) {
        this.strategy = initialStrategy;
    }

    /**
     * Swaps the strategy dynamically at runtime.
     */
    public void setStrategy(FareCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public FareCalculationStrategy getStrategy() {
        return strategy;
    }

    /**
     * Executes the currently configured strategy.
     */
    public int executeFareCalculation(int baseFare, double categoryMultiplier) {
        if (strategy == null) {
            strategy = new RegularFareStrategy();
        }
        return strategy.calculateFare(baseFare, categoryMultiplier);
    }
}
