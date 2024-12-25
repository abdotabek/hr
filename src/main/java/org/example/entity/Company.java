package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "company")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company extends BaseEntity {

    @Column(name = "name")
    String name;

    @Column(name = "tin")
    String tin;

    @Column(name = "brand")
    String brand;

    @Embedded
    Address address;
}
