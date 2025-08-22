package org.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.enums.LogType;

@Getter
@Setter
@Entity
@Table(name = "position")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position extends BaseEntity {

    @Column(name = "name")
    String name;

    @Column(name = "log_type")
    @Enumerated(EnumType.STRING)
    LogType logType;
}
