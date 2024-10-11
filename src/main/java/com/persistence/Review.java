package com.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "review")
public class Review extends EntityBase {
    @ManyToOne
    @JoinColumn(name = "id")
    public Hotel hotel;

    @Column(name = "message", length = 2000)
    public String message;

    @ManyToOne
    @JoinColumn(name = "id")
    public User user;

    @Column(name = "rating")
    public int rating;
}
