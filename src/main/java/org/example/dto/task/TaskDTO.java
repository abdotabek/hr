package org.example.dto.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDTO {

    Long id;

    String title;

    String content;

    LocalDateTime createdDate;

    Long employeeId;
}
