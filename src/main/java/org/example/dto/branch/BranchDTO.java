package org.example.dto.branch;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.address.AddressDTO;
import org.example.dto.base.CommonDTO;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchDTO extends CommonDTO {

    @NotNull
    Long companyId;

    @NonNull
    AddressDTO addressDTO;
}
