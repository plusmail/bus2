package kroryi.bus2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Builder
@RedisHash(value = "sport", timeToLive = 300)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SportRedis  implements Serializable {

    @Id
    public Long id;
    public String name;

}