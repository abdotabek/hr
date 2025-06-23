package org.example.dto.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenDTO {

    String refreshToken;

    String accessToken;

    Long expired;

    String type = "Bearer";
}
