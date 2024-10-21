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

    @Column(name = "wifi")
    @JsonbProperty("wifi")
    @DefaultValue("false")
    public boolean wifi;

    @Column(name = "single_bed")
    @JsonbProperty("singleBed")
    @DefaultValue("0")
    public int singleBed;

    @Column(name = "double_bed")
    @JsonbProperty("doubleBed")
    @DefaultValue("0")
    public int doubleBed;

    @Column(name = "bunk_bed")
    @JsonbProperty("bunk_bed")
    @DefaultValue("0")
    public int bunkBed;

    @Column(name = "king_bed")
    @JsonbProperty("kingBed")
    @DefaultValue("0")
    public int kingBed;

    @Column(name = "minibar")
    @JsonbProperty("minibar")
    @DefaultValue("false")
    public boolean minibar;

    @Column(name = "tv")
    @JsonbProperty("tv")
    @DefaultValue("false")
    public boolean tv;

    @Column(name = "room_service")
    @JsonbProperty("room_service")
    @DefaultValue("false")
    public boolean roomService;

    @Column(name = "balcony")
    @JsonbProperty("balcony")
    @DefaultValue("false")
    public boolean balcony;

    @Enumerated(EnumType.STRING)
    @JsonbProperty("bathroom")
    @DefaultValue("CLASSIC")
    public BathroomType bathroom;

    public enum BathroomType{
        SHARED,
        CLASSIC,
        LUXURY
    }

    @Enumerated(EnumType.STRING)
    @JsonbProperty("board")
    @DefaultValue("FULL")
    public BoardType board;

    public enum BoardType{
        FULL,
        HALF,
        BREAKFAST
    }

    @ManyToMany(mappedBy = "amenities")
    @JsonbTransient
    public Set<Room> rooms = new HashSet<>();
}
