package api.invest.clients;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "${clients.cbr-client.name}", url = "${clients.cbr-client.url}", path = "${clients.cbr-client.path}")
public interface CbrReactiveFeignClient {
    @GetMapping("/XML_daily.asp")
    Mono<String> getQuotesForDate(@RequestParam("date_req") String dateReq);
}