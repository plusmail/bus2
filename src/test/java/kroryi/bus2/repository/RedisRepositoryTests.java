package kroryi.bus2.repository;


import kroryi.bus2.entity.RedisStatJpa;
import kroryi.bus2.repository.jpa.JpaStatRepository;
import kroryi.bus2.service.RedisSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;


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

    @BeforeEach
    void cleanup() {
        jpaStatRepository.deleteAll(); // 또는 deleteById(1L);
        redisTemplate.delete("redis_stat:1");
    }


    @Test
    @DisplayName("redisBasicTest")
    void redisBasicTest() {
        RedisStatJpa soccer = RedisStatJpa.builder().id(1L).timestamp(LocalDateTime.now()).memoryUsageMb(113L).build();
        jpaStatRepository.save(soccer);
        log.info("Sport 저장");

        RedisStatJpa found = jpaStatRepository.findById(1L).get();
        log.info("Soccer 찾기");


    }

}
