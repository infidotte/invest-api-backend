package api.invest.service;

import api.invest.clients.CbrReactiveFeignClient;
import api.invest.domain.CurrencyQuote;
import api.invest.service.api.CurrencyQuotesParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CbrService {

    private static final Logger log = LoggerFactory.getLogger(CbrService.class);
    private static final String KEY = "DAY_QUOTES";

    private final ReactiveHashOperations<String, String, String> hashOperations;
    private final CurrencyQuotesParser parser;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final CbrReactiveFeignClient cbrWebClient;

    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Mono<Map<String, CurrencyQuote>> getQuotesForDate(LocalDate date) {
        if (date == null) date = LocalDate.now();
        String dateString = date.format(formatters);
        return hashOperations.get(KEY, dateString)
                .switchIfEmpty(
                        sendClientRequest(dateString)
                                .flatMap(quotes -> updateRedisCache(dateString, quotes)))
                .map(parser::parse);
    }

    private Mono<String> sendClientRequest(String date) {
        log.info("getting quotes from service");
        return cbrWebClient.getQuotesForDate(date);
    }

    private Mono<String> updateRedisCache(String date, String quotes) {
        return hashOperations.put(KEY, date, quotes).thenReturn(quotes);
    }
}
