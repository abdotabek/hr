package org.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "department")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department extends BaseEntity{
    @Column(name = "name")
    String name;

    @Column(name = "branch_id")
    Long branchId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "branch_id", updatable = false, insertable = false)
    Branch branch;

}
