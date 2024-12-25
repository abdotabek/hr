package org.example.dto.deparment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentDTO extends CommonDTO {
    Long branchId;
}
