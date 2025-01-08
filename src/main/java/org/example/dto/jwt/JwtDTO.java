package org.example.dto.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtDTO {

    Long id;
    String userName;
    String role;
    String tokenType;

    public JwtDTO(Long id, String userName, String role, String tokenType) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.tokenType = tokenType;
    }

    public JwtDTO(Long id, String userName, String role) {
        this.userName = userName;
        this.role = role;
        this.id = id;
    }
}
