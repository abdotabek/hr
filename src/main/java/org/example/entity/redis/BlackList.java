package org.example.entity.redis;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(value = "black_list", timeToLive = 3600 * 8)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlackList {

    @Id
    Long id;

    public BlackList(Long id) {
        this.id = id;
    }

}
