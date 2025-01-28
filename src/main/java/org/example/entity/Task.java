package org.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "task")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE task SET deleted = TRUE WHERE id=?")
public class Task extends BaseEntity {

    @Column(name = "title")
    String title;

    @Column(name = "content")
    String content;

    @Column(name = "created_date")
    LocalDateTime createdDate;

    @Column(name = "employee_id")
    Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", updatable = false, insertable = false)
    Employee employee;

    @Column(name = "deleted")
    Boolean deleted = false;
}
