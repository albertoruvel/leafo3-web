package com.leafo3.web.core.security;

public class AuthenticatedUser {
    private String userId;

    public AuthenticatedUser(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isAnonymous(){
        return userId == null;
    }
}
