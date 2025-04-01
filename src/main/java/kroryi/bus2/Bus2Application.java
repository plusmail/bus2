package kroryi.bus2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableJpaRepositories(basePackages = "kroryi.bus2.repository.jpa")
@EnableRedisRepositories(basePackages = "kroryi.bus2.repository.redis")
@ComponentScan(basePackages = {
        "kroryi.bus2.config",
        "kroryi.bus2.controller",
        "kroryi.bus2.dto",
        "kroryi.bus2.entity",
        "kroryi.bus2.service",
        "kroryi.bus2.repository.redis",
        "kroryi.bus2.repository.jpa",
        "kroryi.bus2.repository",
        "kroryi.bus2.controller"
})
@SpringBootApplication
public class Bus2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Bus2Application.class, args);
    }


    // Redis 저장 확인용 로그
    @Override
    public void run(String... args) throws Exception {
        System.out.println("애플리케이션 시작됨");

//        // Redis 사용량 저장 테스트
//        double testMemoryUsage = 50.0;
//        busRedisService.saveRedisUsage(testMemoryUsage);
//        System.out.println("Redis 사용량 저장 완료");
    }



}
