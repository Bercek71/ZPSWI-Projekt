package com.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Table(name = "booking")
public class Booking extends EntityBase {
    @Column(name = "price_total")
    @JsonbProperty("priceTotal")
    public int priceTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id") // Ensure this is correct
    @JsonbTransient
    public AppUser appUser;

    @JsonbProperty("appUserRefId")
    public Long getAppUserId() {
        return (appUser != null) ? appUser.getId() : null;
    }

    public void setAppUserId(Long appUserId) {
        if (appUserId != null) {
            this.appUser = AppUser.findById(appUserId);
        }
    }

    @JsonbProperty("appUser")
    public AppUser getAppUserIfExpanded() {
        if (Hibernate.isInitialized(this.appUser)) {
            return appUser;
        }
        return null;
    }
}
