package com.xyz.ridebooking.chain;

public class LoginHandler extends RideHandler {

    @Override
    public boolean handle(RideRequest request) {

        if(!request.isLoggedIn()) {
            return false;
        }

        if(nextHandler != null)
            return nextHandler.handle(request);

        return true;
    }
}