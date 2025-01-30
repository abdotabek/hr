package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
@Table(name = "company")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE company SET deleted = true WHERE id=?")
public class Company extends BaseEntity {

    @Column(name = "name")
    String name;

    @Column(name = "tin")
    String tin;

    @Column(name = "brand")
    String brand;

    @Embedded
    Address address;

    @Column(name = "deleted")
    Boolean deleted = false;  // soft delete flag
}
