package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "amenity")
public class Amenity extends PanacheEntity {

    @Column(name = "name")
    @JsonbProperty("name")
    @DefaultValue("name")
    public String name;
}
