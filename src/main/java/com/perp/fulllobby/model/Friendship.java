package com.perp.fulllobby.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "friendship")
public class Friendship {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_1_id")
    private MyUser firstUser;

    @ManyToOne
    @JoinColumn(name = "user_2_id")
    private MyUser secondUser;

    private Boolean accepted;

    public Friendship() {
        this.accepted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyUser getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(MyUser firstUser) {
        this.firstUser = firstUser;
    }

    public MyUser getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(MyUser secondUser) {
        this.secondUser = secondUser;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Friendship [id=" + id + ", firstUser=" + firstUser + ", secondUser=" + secondUser + ", accepted="
                + accepted + "]";
    }

    

}
