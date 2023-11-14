package com.perp.fulllobby.model;

public class LoginResponse {
    
    private MyUser user;
    private String token;

    public LoginResponse() {
        super();
    }

    public LoginResponse(MyUser user, String token) {
        super();
        this.user = user;
        this.token = token;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse [user=" + user + ", token=" + token + "]";
    }

    

}
