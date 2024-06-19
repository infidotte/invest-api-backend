package api.invest.client;

import api.invest.dto.VerificationDto;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
        name = "${clients.mail-service.name}",
        path = "${clients.mail-service.path}",
        url = "${clients.mail-service.url}"
)
public interface MailServiceReactiveFeignClient {
    @PostMapping("/send-verification")
    Mono<Void> send(VerificationDto verificationDto);
}
