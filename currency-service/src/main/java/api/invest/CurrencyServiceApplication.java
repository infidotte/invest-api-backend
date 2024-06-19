package api.invest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
@EnableCaching
public class CurrencyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyServiceApplication.class, args);
    }
}