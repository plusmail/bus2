package kroryi.bus2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Builder
@RedisHash(value = "sport", timeToLive = 300)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sport {

    @Id
    public Long id;
    public String name;

}