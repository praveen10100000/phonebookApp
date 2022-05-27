package com.phone.book.dto;

public class UserResponse {
    
    private final String token ;

    public UserResponse(String jwt) {
        this.token = jwt;
    }

    public String getToken() {
        return token;
    }

    
    
    
}
