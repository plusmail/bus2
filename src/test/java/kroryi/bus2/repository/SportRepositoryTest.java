package kroryi.bus2.repository;

import kroryi.bus2.entity.Sport;
import kroryi.bus2.repository.jpa.SportRepository;
import kroryi.bus2.service.SportService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log4j2
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SportRepositoryTest {

    @Autowired
    SportRepository sportRepository;
    @Autowired
    SportService sportService;

    @Test
    @DisplayName("redisBasicTest")
    void redisBasicTest() {
        Sport soccer = Sport.builder().id(4L).name("Soccer").build();
//        sportRepository.save(soccer);
//        log.info("Sport 저장");
//
//        Sport found = sportRepository.findById(2L).get();
//        log.info("Soccer 찾기");
//
//        assertEquals(found.getId(), 2L);
//        assertEquals(found.getName(), "Soccer");
        sportService.saveSport(soccer);

    }

    @Test
    void redisTestGet() {

        Sport sport = sportService.getSport(1L);
        log.info(sport);
    }

}