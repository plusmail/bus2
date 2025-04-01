package kroryi.bus2.service;

import jakarta.transaction.Transactional;
import kroryi.bus2.dto.RedisStat;
import kroryi.bus2.entity.RedisStatJpa;
import kroryi.bus2.repository.jpa.JpaStatRepository;
import kroryi.bus2.repository.redis.RedisStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RedisSyncService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JpaStatRepository jpaStatRepository;

    // 읽기 (캐시 조회 후 DB 조회)
    public RedisStatJpa getStat(Long id) {
        String key = "redis_stat:" + id;
        Map<Object, Object> hash = redisTemplate.opsForHash().entries(key);

        if (hash != null && !hash.isEmpty()) {
            RedisStat dto = new RedisStat(
                    Long.valueOf(hash.get("id").toString()),
                    (LocalDateTime) hash.get("timestamp"),
                    Double.valueOf(hash.get("memoryUsageMb").toString())
            );
            return convertToJpaStat(dto);
        }

        return jpaStatRepository.findById(id)
                .map(stat -> {
                    saveStat(stat);
                    return stat;
                })
                .orElse(null);
    }
    // 쓰기 (DB와 Redis에 동시에 저장)
    @Transactional
    public void saveStat(RedisStatJpa stat) {
        jpaStatRepository.save(stat);

        String key = "redis_stat:" + stat.getId();

        Map<String, Object> hash = new HashMap<>();
        hash.put("id", stat.getId());
        hash.put("timestamp", stat.getTimestamp().toString());
        hash.put("memoryUsageMb", stat.getMemoryUsageMb());

        redisTemplate.opsForHash().putAll(key, hash);
        redisTemplate.expire(key, Duration.ofMinutes(10)); // TTL 설정
    }

    // 삭제 (DB와 Redis 동시에 삭제)
    public void deleteStat(Long id) {
        // DB 삭제
        jpaStatRepository.deleteById(id);

        // 캐시 무효화
        redisTemplate.delete("redis_stat:" + id);
    }

    private RedisStatJpa convertFromHash(Map<Object, Object> hash) {
        RedisStatJpa stat = new RedisStatJpa();
        stat.setId(Long.valueOf(hash.get("id").toString()));
        stat.setTimestamp(LocalDateTime.parse(hash.get("timestamp").toString()));
        stat.setMemoryUsageMb(Double.valueOf(hash.get("memoryUsageMb").toString()));
        return stat;
    }

    private RedisStat convertToRedisStat(RedisStatJpa jpaStat) {
        return new RedisStat(
                jpaStat.getId(),
                jpaStat.getTimestamp(), // LocalDateTime → 문자열
                jpaStat.getMemoryUsageMb()
        );
    }

    private RedisStatJpa convertToJpaStat(RedisStat redisStat) {
        RedisStatJpa jpaStat = new RedisStatJpa();
        jpaStat.setId(redisStat.getId());

        jpaStat.setTimestamp(redisStat.getTimestamp());
        jpaStat.setMemoryUsageMb(redisStat.getMemoryUsageMb());
        return jpaStat;
    }
}