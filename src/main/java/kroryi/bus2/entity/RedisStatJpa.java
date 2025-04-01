package kroryi.bus2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "redis_stat")
@Builder
@Getter
@Setter
@RedisHash(value = "redis_stat", timeToLive = 300)
@NoArgsConstructor
@AllArgsConstructor
public class RedisStatJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private double memoryUsageMb;
}