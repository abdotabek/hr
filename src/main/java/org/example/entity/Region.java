package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "region")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Region extends BaseEntity {
    @Column(name = "name")
    String name;
}
