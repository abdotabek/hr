package org.example.dto.deparment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentIdNameBranchIdDTO extends CommonDTO {
    Long branchId;

    public DepartmentIdNameBranchIdDTO(Long id, String name, Long branchId) {
        super.setId(id);
        super.setName(name);
        this.setBranchId(branchId);
    }
}
