package com.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "country")
public class Country extends EntityBase {
    @Column(name = "name")
    @JsonbProperty("name")
    public String name;

    @Column(name = "iso_code")
    @JsonbProperty("isoCode")
    public String isoCode;
}
