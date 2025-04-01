package kroryi.bus2.repository.redis;

import kroryi.bus2.entity.Sport;
import kroryi.bus2.entity.SportRedis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRedisRepository extends JpaRepository<SportRedis, Long> {


}