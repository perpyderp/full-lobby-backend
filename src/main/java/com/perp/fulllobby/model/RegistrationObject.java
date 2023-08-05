package com.perp.fulllobby.model;

import java.sql.Date;

public class RegistrationObject {
    
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Date dateOfBirth;

    public RegistrationObject() {
        super();
    }

    public RegistrationObject(String firstName, String lastName, String username, String email, Date dateOfBirth) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "RegistrationObject [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
                + ", email=" + email + ", dateOfBirth=" + dateOfBirth + "]";
    }
    

}
