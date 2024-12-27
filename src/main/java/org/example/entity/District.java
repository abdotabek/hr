package org.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "district")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class District extends BaseEntity {

    @Column(name = "name")
    String name;

    @Column(name = "region_id")
    Long regionId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "region_id", updatable = false, insertable = false)
    Region region;
}
