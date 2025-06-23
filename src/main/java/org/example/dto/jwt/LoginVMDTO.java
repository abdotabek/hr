package org.example.dto.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginVMDTO {
    @JsonProperty("access_token")
    private String accessToken;

}
