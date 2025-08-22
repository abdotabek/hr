package org.example.dto.position;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.LogType;

@Getter
@Setter
public class PositionDTO {
    private Long id;
    private String name;
    private LogType logType;
}
