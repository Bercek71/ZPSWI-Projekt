package com.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "booking")
public class Booking extends EntityBase {
    @Column(name = "price_total")
    @JsonbProperty("priceTotal")
    public int priceTotal;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    @JsonbProperty("appUserId")
    public AppUser appUser;
}
