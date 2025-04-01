package kroryi.bus2.repository.jpa;

import kroryi.bus2.entity.RedisStatJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStatRepository extends JpaRepository<RedisStatJpa, Long> {
}
