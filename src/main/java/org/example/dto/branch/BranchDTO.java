package org.example.dto.branch;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.address.AddressDTO;
import org.example.dto.base.CommonDTO;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchDTO extends CommonDTO {
    Long companyId;
    AddressDTO addressDTO;
}
