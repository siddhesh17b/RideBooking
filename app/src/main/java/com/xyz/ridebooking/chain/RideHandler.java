package com.xyz.ridebooking.chain;

public abstract class RideHandler {

    protected RideHandler nextHandler;

    public void setNextHandler(RideHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean handle(RideRequest request);
}