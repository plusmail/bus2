package kroryi.bus2.service;

import kroryi.bus2.entity.Sport;
import kroryi.bus2.entity.SportRedis;
import kroryi.bus2.repository.jpa.SportRepository;
import kroryi.bus2.repository.redis.SportRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SportService {
    @Autowired
    private SportRepository sportRepository;
    @Autowired
    private SportRedisRepository sportRedisRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Cacheable(value = "sport", key = "#id", unless = "#result == null")
    public SportRedis getSport(Long id) {
        return sportRedisRepository.findById(id)
                .orElseGet(() -> {
                    Sport sport = sportRepository.findById(id).orElseThrow();
                    SportRedis redisData = mapToRedis(sport);
                    sportRedisRepository.save(redisData);
                    return redisData;
                });
    }

    @CachePut(value = "sport", key = "#sport.id")
    public Sport updateSport(Sport sport) {
        return sportRepository.save(sport);
    }

    @CacheEvict(value = "sport", key = "#id")
    public void deleteSport(Long id) {
        sportRepository.deleteById(id);
    }
    @Transactional
    public void saveSport(Sport sport) {
        // 1. DB 저장
        sportRepository.save(sport);

        // 2. Redis에 저장
        SportRedis redis = mapToRedis(sport);
        sportRedisRepository.save(redis); // CrudRepository 기반 저장
    }
    private SportRedis mapToRedis(Sport sport) {
        SportRedis redis = new SportRedis();
        redis.setId(sport.getId());
        redis.setName(sport.getName());
        return redis;
    }
}