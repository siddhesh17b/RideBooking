package com.xyz.ridebooking.strategy;

/**
 * ============================================================================
 * DESIGN PATTERN: STRATEGY PATTERN (GoF - Behavioral)
 * PRESENTER: Presenter 3 (Strategy Pattern Lead)
 *
 * ROLE: Concrete Strategy (Peak Rush Hour Surge Pricing)
 *
 * Implements high-demand surge pricing (1.8x multiplier) during peak traffic
 * or adverse weather conditions.
 * Formula: (Base Fare * Category Multiplier) * 1.8
 * ============================================================================
 */
public class SurgeFareStrategy implements FareCalculationStrategy {

    private static final double SURGE_MULTIPLIER = 1.8;

    @Override
    public int calculateFare(int baseFare, double categoryMultiplier) {
        return (int) Math.round((baseFare * categoryMultiplier) * SURGE_MULTIPLIER);
    }

    @Override
    public String getStrategyName() {
        return "Peak Rush Surge (1.8x)";
    }

    @Override
    public String getFormulaDescription() {
        return "High-demand peak pricing (Base × Tier × 1.8x Surge)";
    }
}
