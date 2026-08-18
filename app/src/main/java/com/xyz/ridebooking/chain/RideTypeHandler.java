package com.xyz.ridebooking.chain;

public class RideTypeHandler extends RideHandler {

    @Override
    public boolean handle(RideRequest request) {

        String type = request.getRideType();

        if(!type.equalsIgnoreCase("Bike") &&
                !type.equalsIgnoreCase("Auto") &&
                !type.equalsIgnoreCase("Car")) {
            return false;
        }

        if(nextHandler != null)
            return nextHandler.handle(request);

        return true;
    }
}