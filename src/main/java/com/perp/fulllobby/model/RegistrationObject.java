package com.perp.fulllobby.model;

import java.sql.Date;

public class RegistrationObject {

    private String username;
    private String email;
    private Date dateOfBirth;
    private String password;

    public RegistrationObject() {
        super();
    }

    public RegistrationObject( String username, String email, Date dateOfBirth, String password) {
        super();
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

}
