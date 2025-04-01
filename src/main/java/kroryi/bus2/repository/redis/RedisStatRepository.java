package kroryi.bus2.repository.redis;

import kroryi.bus2.dto.RedisStat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RedisStatRepository extends CrudRepository<RedisStat, Long> {
    Optional<RedisStat> findTopByOrderByTimestampDesc(); // Redis 최근 사용량

    List<RedisStat> findByTimestampBetween(LocalDateTime timestampAfter, LocalDateTime timestampBefore);
}
