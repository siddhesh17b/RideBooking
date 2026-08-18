package com.xyz.ridebooking.chain;

public class DetailsHandler extends RideHandler {

    @Override
    public boolean handle(RideRequest request) {

        if(request.getName().trim().isEmpty())
            return false;

        if(request.getPickup().trim().isEmpty())
            return false;

        if(request.getDestination().trim().isEmpty())
            return false;

        if(nextHandler != null)
            return nextHandler.handle(request);

        return true;
    }
}