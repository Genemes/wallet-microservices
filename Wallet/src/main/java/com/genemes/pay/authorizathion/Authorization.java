package com.genemes.pay.authorizathion;

public record Authorization(String message) {
    public Boolean isAuthorized() {
        return message.equals("AUTHORIZATION");
    }
}
