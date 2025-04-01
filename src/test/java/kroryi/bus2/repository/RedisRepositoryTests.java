package kroryi.bus2.repository;


import kroryi.bus2.dto.RedisStat;
import kroryi.bus2.entity.RedisStatJpa;
import kroryi.bus2.repository.jpa.JpaStatRepository;
import kroryi.bus2.repository.redis.RedisStatRepository;
import kroryi.bus2.service.RedisSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RequiredArgsConstructor
@Log4j2
public class RedisRepositoryTests {
    @Autowired
    public RedisSyncService redisSyncService;

    @Autowired
    private JpaStatRepository jpaStatRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    void saveStat_shouldSaveToDbAndRedis() {
        // given
        RedisStatJpa stat = new RedisStatJpa();
        stat.setId(1L);
        stat.setTimestamp(LocalDateTime.now());
        stat.setMemoryUsageMb(123.45);

        // when
        redisSyncService.saveStat(stat);

        // then
        RedisStatJpa fromDb = jpaStatRepository.findById(1L).orElse(null);
        assertNotNull(fromDb);
        assertEquals(stat.getMemoryUsageMb(), fromDb.getMemoryUsageMb());

        Map<Object, Object> redisData = redisTemplate.opsForHash().entries("redis_stat:1");
        assertFalse(redisData.isEmpty());
        assertEquals("123.45", redisData.get("memoryUsageMb").toString());
    }


}
