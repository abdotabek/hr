package org.example.dto.branch;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.address.AddressDetailsDTO;
import org.example.dto.base.CommonDTO;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchDetailsDTO extends CommonDTO {
    AddressDetailsDTO addressDetailsDTO;
}
