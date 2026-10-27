package com.xyz.ridebooking.strategy;

/**
 * ============================================================================
 * DESIGN PATTERN: STRATEGY PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 3 (Strategy Pattern Lead)
 *
 * PURPOSE & PROBLEM SOLVED:
 * Fare calculation in ride-hailing is dynamic: pricing varies during standard hours,
 * peak rush-hour demand (surge), and late-night travel.
 *
 * If implemented with rigid if-else blocks or switch statements in the client,
 * adding new pricing tiers (e.g., Weekend Promo, Holiday Surge) would violate
 * the Open/Closed Principle (modifying tested code).
 *
 * HOW IT WORKS HERE:
 * - Strategy Interface: {@link FareCalculationStrategy} defines the uniform calculation contract.
 * - Concrete Strategies: {@link RegularFareStrategy}, {@link SurgeFareStrategy}, {@link NightFareStrategy}.
 * - Context: {@link FareStrategyContext} maintains a reference to a Strategy object
 *   and delegates pricing logic to it dynamically at runtime.
 *
 * KEY TALKING POINTS FOR PRESENTER 3:
 * 1. Open/Closed Principle: New fare algorithms can be added without modifying existing ones.
 * 2. Runtime Flexibility: The user/system can change the fare strategy on the fly.
 * 3. Eliminates massive nested conditional logic for pricing calculations.
 * ============================================================================
 */
public interface FareCalculationStrategy {

    /**
     * Calculates the estimated trip fare using the strategy's internal algorithm.
     *
     * @param baseFare           The base rate for the vehicle (e.g. ₹50 Bike, ₹90 Auto, ₹180 Car).
     * @param categoryMultiplier The Abstract Factory luxury multiplier (e.g. 1.0x Economy, 1.5x Premium).
     * @return Calculated final fare amount in INR.
     */
    int calculateFare(int baseFare, double categoryMultiplier);

    /**
     * Human-readable label for this pricing strategy.
     */
    String getStrategyName();

    /**
     * Detailed breakdown explaining the math behind this strategy.
     */
    String getFormulaDescription();
}
