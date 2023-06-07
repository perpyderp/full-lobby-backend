package com.cuisonet.fulllobby.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


// @Entity automatically generates User table in SQL Database
@Entity
public class User {
    
    // Automatically generates id
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String name;
    private String email;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    


}
