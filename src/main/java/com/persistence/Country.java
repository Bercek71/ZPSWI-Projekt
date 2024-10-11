package com.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "country")
public class Country extends EntityBase {
    @Column(name = "name")
    public String name;

    @Column(name = "iso_code")
    public String isoCode;
}
