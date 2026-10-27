package com.xyz.ridebooking.strategy;

/**
 * ============================================================================
 * DESIGN PATTERN: STRATEGY PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 3 (Strategy Pattern Lead)
 *
 * ROLE: Concrete Strategy (Night Owl Pricing)
 *
 * Implements night-time pricing between 11 PM and 5 AM.
 * Applies a 1.25x late-night rate plus a fixed ₹20 night safety & convenience fee.
 * Formula: ((Base Fare * Category Multiplier) * 1.25) + 20
 * ============================================================================
 */
public class NightFareStrategy implements FareCalculationStrategy {

    private static final double NIGHT_MULTIPLIER = 1.25;
    private static final int NIGHT_CONVENIENCE_FEE = 20;

    @Override
    public int calculateFare(int baseFare, double categoryMultiplier) {
        return (int) Math.round((baseFare * categoryMultiplier) * NIGHT_MULTIPLIER) + NIGHT_CONVENIENCE_FEE;
    }

    @Override
    public String getStrategyName() {
        return "Night Owl Rate (1.25x + ₹20)";
    }

    @Override
    public String getFormulaDescription() {
        return "Late night rate (Base × Tier × 1.25x + ₹20 Night Surcharge)";
    }
}
