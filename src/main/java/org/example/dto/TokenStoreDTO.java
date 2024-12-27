package org.example.dto;

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
    String token;
    Boolean active = Boolean.TRUE;
}