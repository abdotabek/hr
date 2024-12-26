package org.example.entity.redis;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    String id;
    String username;
    String status;

    public User(String id, String username, String status) {
        this.id = id;
        this.username = username;
        this.status = status;
    }
}
