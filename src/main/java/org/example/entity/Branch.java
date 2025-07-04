package org.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
@Table(name = "branch")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE branch SET deleted = true WHERE id=?")
public class Branch extends BaseEntity {

    @Column(name = "name")
    String name;

    @Column(name = "company_id")
    Long companyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id", updatable = false, insertable = false)
    Company company;

    @Embedded
    Address address;

    @Column(name = "deleted")
    Boolean delete = false;
}
