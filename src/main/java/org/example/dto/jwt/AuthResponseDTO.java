package org.example.dto.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.enums.EmployeeRole;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponseDTO {
    String accessToken;
    String refreshToken;

}
