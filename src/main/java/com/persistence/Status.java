package com.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "status")
public class Status extends EntityBase {
    @Column(name = "name")
    public String name;
}
