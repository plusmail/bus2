package kroryi.bus2.service;

import kroryi.bus2.entity.Sport;
import kroryi.bus2.repository.jpa.SportRepository;
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
    private RedisTemplate<String, Object> redisTemplate;

    @Cacheable(value = "sport", key = "#id", unless = "#result == null")
    public Sport getSport(Long id) {
        return sportRepository.findById(id).orElseThrow();
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
        sportRepository.save(sport); // JPA 저장

        String key = "sport:" + sport.getId();
        redisTemplate.opsForHash().put(key, "id", sport.getId());
        redisTemplate.opsForHash().put(key, "name", sport.getName());
    }

}