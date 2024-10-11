package com.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "hotel")
public class Hotel extends EntityBase {
    @Column(name = "name")
    @JsonbProperty("name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonbProperty("countryId")
    public Country country;
}
