package api.invest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
public class TinkoffServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinkoffServiceApplication.class, args);
    }
}