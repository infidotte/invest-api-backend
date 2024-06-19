package api.invest.client.tinkoffinvestapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

@Component
public class FeignClientInterceptor implements ReactiveHttpRequestInterceptor {
    @Value("${clients.tinkoff-invest-api.token}")
    private String token;

    @Override
    public Mono<ReactiveHttpRequest> apply(ReactiveHttpRequest reactiveHttpRequest) {
        Map<String, List<String>> headers =
                Map.of("accept", singletonList("application/json"),
                        "Authorization", singletonList("Bearer " + token),
                        "Content-Type", singletonList("application/json"));
        reactiveHttpRequest.headers().putAll(headers);
        return Mono.just(reactiveHttpRequest);
    }
}
