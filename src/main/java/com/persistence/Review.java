package com.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "review")
public class Review extends PanacheEntityBase {

    @Embeddable
    @EqualsAndHashCode
    public static class ReviewId {

        public int hotelId;
        public int userId;
    }
    @EmbeddedId
    public ReviewId reviewId;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonbProperty("hotelId")
    public Hotel hotel;

    @Column(name = "message", length = 2000)
    @JsonbProperty("message")
    public String message;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    @JsonbProperty("appUserId")
    public AppUser appUser;

    @Column(name = "rating")
    @JsonbProperty("rating")
    public int rating;
}
