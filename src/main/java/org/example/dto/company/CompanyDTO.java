package org.example.dto.company;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dto.address.AddressDTO;
import org.example.dto.base.CommonDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDTO extends CommonDTO {

    String tin;

    String brand;

    AddressDTO addressDTO;
}
