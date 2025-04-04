package kroryi.bus2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
// 외부 API 호출을 위한 RestTemplate의 Bean을 생성하여 Spring 컨테이너에 등록합니다.
public class AppConfig {

    // 해당 메서드를 통해 RestTemplate을 Bean으로 등록하면, @Autowired 또는 @RequiredArgsConstructor 등을 통해 어디서든 주입받아 사용가능
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}