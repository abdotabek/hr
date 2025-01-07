package org.example.dto.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenStoreDTO {

    String id;
    Long employeeId;
    String refreshToken;
    String accessToken;
}
