package org.example.entity.redis;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(value = "token_store", timeToLive = 3600 * 8)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenStore {

    @Id
    String id;
    Long employeeId;
    String refreshToken;
    String accessToken;
}
