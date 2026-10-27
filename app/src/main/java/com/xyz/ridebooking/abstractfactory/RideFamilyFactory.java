package com.xyz.ridebooking.abstractfactory;

/**
 * ============================================================================
 * DESIGN PATTERN: ABSTRACT FACTORY PATTERN (GoF - Creational)
 *
 * Defines the factory interface for creating complete families of related
 * vehicle tiers (Economy vs Premium) across all supported vehicle categories.
 * ============================================================================
 */
public interface RideFamilyFactory {
    String createBike();
    String createAuto();
    String createCar();
}
