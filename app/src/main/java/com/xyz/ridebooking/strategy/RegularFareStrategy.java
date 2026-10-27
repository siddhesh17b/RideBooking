package com.xyz.ridebooking.strategy;

/**
 * ============================================================================
 * DESIGN PATTERN: STRATEGY PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 3 (Strategy Pattern Lead)
 *
 * ROLE: Concrete Strategy (Standard Normal Pricing)
 *
 * Implements standard off-peak pricing without surge or nocturnal surcharges.
 * Formula: Base Fare * Category Multiplier
 * ============================================================================
 */
public class RegularFareStrategy implements FareCalculationStrategy {

    @Override
    public int calculateFare(int baseFare, double categoryMultiplier) {
        return (int) Math.round(baseFare * categoryMultiplier);
    }

    @Override
    public String getStrategyName() {
        return "Standard Rate (1.0x)";
    }

    @Override
    public String getFormulaDescription() {
        return "Standard off-peak calculation (Base × Tier)";
    }
}
