package com.xyz.ridebooking.factory;

public abstract class Ride {

    protected String name;

    public Ride(String name) {
        this.name = name;
    }

    public abstract String getRideType();

    public String getDetails() {
        return name + " - " + getRideType();
    }
}