package org.example.entity.redis;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("token_store")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenStore {
    @Id
    String id;
    String userId;
    String token;
    boolean active;

    public TokenStore() {
    }

    public TokenStore(String id, String userId, String token, boolean active) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.active = active;
    }

    public TokenStore(String id, boolean active) {
        this.id = id;
        this.active = active;
    }
}
