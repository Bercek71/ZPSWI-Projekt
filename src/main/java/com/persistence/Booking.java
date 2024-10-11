package com.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "booking")
public class Booking extends EntityBase {
    @Column(name = "price_total")
    public int priceTotal;

    @ManyToOne
    @JoinColumn(name = "id")
    public User user;
}
