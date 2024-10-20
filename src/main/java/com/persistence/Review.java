package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "review")
public class Review extends PanacheEntity {

    @Embeddable
    @EqualsAndHashCode
    public static class ReviewId {
        public Long hotelId;
        public Long userId;
    }

    public ReviewId reviewId;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    public Hotel hotel;

    @Transient
    @JsonbProperty("hotelId")
    public Long hotelId;

    @Column(name = "message", length = 2000)
    @JsonbProperty("message")
    public String message;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    public AppUser appUser;

    @Transient
    @JsonbProperty("userId")
    public Long userId;

    @Column(name = "rating")
    @JsonbProperty("rating")
    public int rating;
}
