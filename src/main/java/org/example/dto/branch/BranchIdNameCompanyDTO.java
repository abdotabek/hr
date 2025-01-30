package org.example.dto.branch;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchIdNameCompanyDTO extends CommonDTO {

    Long companyId;
}
