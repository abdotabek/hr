package org.example.dto.filter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyFilterDTO extends BaseFilter {
    Long companyId;
    String sortingBy;
    String sortDirection;
}
